package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {
    private static final int CREATE_CSV = 9001;
    private static final String PREFS = "wos_tulostaulu";
    private static final String API_BASE = "https://woscontrol.com/api/v1";

    private EditText stateInput;
    private EditText apiKeyInput;
    private TextView statusText;
    private TextView resultsText;
    private Button exportButton;
    private final List<PlayerRow> rows = new ArrayList<>();
    private String currentState = "77";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(48, 72, 48, 72);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(13, 20, 32));
        scroll.addView(root);

        TextView title = new TextView(this);
        title.setText("WOS TULOSTAULU");
        title.setTextColor(Color.WHITE);
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView subtitle = new TextView(this);
        subtitle.setText("Whiteout Survival – State / Server");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(-1, -2);
        subParams.setMargins(0, 12, 0, 36);
        root.addView(subtitle, subParams);

        stateInput = new EditText(this);
        stateInput.setHint("Serverinumero");
        stateInput.setText(prefs.getString("state", "77"));
        stateInput.setTextSize(22);
        stateInput.setSingleLine(true);
        stateInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        stateInput.setGravity(Gravity.CENTER);
        root.addView(stateInput, new LinearLayout.LayoutParams(-1, -2));

        apiKeyInput = new EditText(this);
        apiKeyInput.setHint("WOS Control API key (wos_...)");
        apiKeyInput.setText(prefs.getString("api_key", ""));
        apiKeyInput.setSingleLine(true);
        apiKeyInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams keyParams = new LinearLayout.LayoutParams(-1, -2);
        keyParams.setMargins(0, 18, 0, 0);
        root.addView(apiKeyInput, keyParams);

        Button loadButton = new Button(this);
        loadButton.setText("LATAA TULOSTAULU");
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(-1, -2);
        buttonParams.setMargins(0, 28, 0, 14);
        root.addView(loadButton, buttonParams);

        exportButton = new Button(this);
        exportButton.setText("VIE CSV / EXCEL");
        exportButton.setEnabled(false);
        root.addView(exportButton, new LinearLayout.LayoutParams(-1, -2));

        statusText = new TextView(this);
        statusText.setText("Valitse serveri ja syötä oma WOS Control API-avain.");
        statusText.setTextColor(Color.WHITE);
        statusText.setTextSize(17);
        statusText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(-1, -2);
        statusParams.setMargins(0, 28, 0, 20);
        root.addView(statusText, statusParams);

        resultsText = new TextView(this);
        resultsText.setTextColor(Color.LTGRAY);
        resultsText.setTextSize(14);
        resultsText.setText("Ei ladattua dataa.");
        root.addView(resultsText, new LinearLayout.LayoutParams(-1, -2));

        TextView credit = new TextView(this);
        credit.setText("Data integration: WOS Control public API • App owner: HAND33h");
        credit.setTextColor(Color.GRAY);
        credit.setTextSize(12);
        credit.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams creditParams = new LinearLayout.LayoutParams(-1, -2);
        creditParams.setMargins(0, 42, 0, 0);
        root.addView(credit, creditParams);

        loadButton.setOnClickListener(v -> loadState());
        exportButton.setOnClickListener(v -> chooseCsvLocation());
        setContentView(scroll);
    }

    private void loadState() {
        String state = stateInput.getText().toString().trim();
        String apiKey = apiKeyInput.getText().toString().trim();

        if (state.isEmpty()) {
            Toast.makeText(this, "Anna serverinumero", Toast.LENGTH_SHORT).show();
            return;
        }
        if (apiKey.isEmpty()) {
            Toast.makeText(this, "Syötä oma WOS Control API-avain", Toast.LENGTH_LONG).show();
            return;
        }

        currentState = state;
        getSharedPreferences(PREFS, MODE_PRIVATE).edit()
                .putString("state", state)
                .putString("api_key", apiKey)
                .apply();

        rows.clear();
        exportButton.setEnabled(false);
        resultsText.setText("Haetaan...");
        statusText.setText("Haetaan serverin " + state + " dataa…");
        hideKeyboard();

        new Thread(() -> fetchLeaderboard(state, apiKey)).start();
    }

    private void fetchLeaderboard(String state, String apiKey) {
        HttpURLConnection connection = null;
        try {
            String endpoint = API_BASE + "/leaderboard?state_id=" +
                    URLEncoder.encode(state, StandardCharsets.UTF_8.name());
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(20000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-API-Key", apiKey);
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            int code = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    code >= 200 && code < 300 ? connection.getInputStream() : connection.getErrorStream(),
                    StandardCharsets.UTF_8));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) body.append(line);
            reader.close();

            if (code == 401 || code == 403) {
                showError("API-avain ei kelpaa tai sillä ei ole oikeutta (HTTP " + code + ").");
                return;
            }
            if (code == 429) {
                showError("API-kutsujen raja tuli vastaan. Yritä hetken kuluttua uudelleen.");
                return;
            }
            if (code < 200 || code >= 300) {
                String msg = extractError(body.toString());
                showError("WOS Control vastasi HTTP " + code + (msg.isEmpty() ? "" : ": " + msg));
                return;
            }

            List<PlayerRow> parsed = parsePlayers(body.toString(), state);
            if (parsed.isEmpty()) {
                showError("Yhteys WOS Controliin toimii, mutta API ei palauttanut tälle serverille Might-rankingrivejä. Sovellus ei näytä API:n yleistä käyttöleaderboardia pelaajalistana.");
                return;
            }

            Collections.sort(parsed, Comparator.comparingLong((PlayerRow p) -> p.might).reversed());
            for (int i = 0; i < parsed.size(); i++) {
                if (parsed.get(i).rank <= 0) parsed.get(i).rank = i + 1;
            }
            rows.clear();
            rows.addAll(parsed);

            runOnUiThread(() -> {
                statusText.setText("Serveri " + state + " • " + rows.size() + " pelaajaa ✓");
                resultsText.setText(renderRows(rows));
                exportButton.setEnabled(true);
            });
        } catch (Exception e) {
            showError("Datan haku epäonnistui: " + e.getMessage());
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private List<PlayerRow> parsePlayers(String body, String requestedState) throws Exception {
        Object root = body.trim().startsWith("[") ? new JSONArray(body) : new JSONObject(body);
        List<JSONObject> objects = new ArrayList<>();
        collectObjects(root, objects, 0);

        List<PlayerRow> result = new ArrayList<>();
        for (JSONObject o : objects) {
            long might = longValue(o, "might", "power", "total_power", "player_power");
            if (might <= 0) continue;

            String rowState = stringValue(o, "state_id", "state", "kid", "server", "server_id");
            if (!rowState.isEmpty() && !normalizeState(rowState).equals(normalizeState(requestedState))) continue;

            String name = stringValue(o, "nickname", "name", "player_name", "username");
            String fid = stringValue(o, "fid", "player_id", "id");
            String alliance = stringValue(o, "alliance", "alliance_name", "alliance_tag", "tag");
            long rank = longValue(o, "rank", "ranking", "position");

            if (name.isEmpty() && fid.isEmpty()) continue;
            result.add(new PlayerRow((int) rank, name, fid, alliance, might));
        }
        return dedupe(result);
    }

    private void collectObjects(Object node, List<JSONObject> out, int depth) throws Exception {
        if (node == null || depth > 6) return;
        if (node instanceof JSONObject) {
            JSONObject obj = (JSONObject) node;
            out.add(obj);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                Object child = obj.opt(keys.next());
                if (child instanceof JSONObject || child instanceof JSONArray) collectObjects(child, out, depth + 1);
            }
        } else if (node instanceof JSONArray) {
            JSONArray array = (JSONArray) node;
            for (int i = 0; i < array.length(); i++) {
                Object child = array.opt(i);
                if (child instanceof JSONObject || child instanceof JSONArray) collectObjects(child, out, depth + 1);
            }
        }
    }

    private List<PlayerRow> dedupe(List<PlayerRow> input) {
        List<PlayerRow> out = new ArrayList<>();
        for (PlayerRow p : input) {
            boolean exists = false;
            for (PlayerRow q : out) {
                if (!p.fid.isEmpty() && p.fid.equals(q.fid)) { exists = true; break; }
                if (p.fid.isEmpty() && !p.name.isEmpty() && p.name.equals(q.name) && p.might == q.might) { exists = true; break; }
            }
            if (!exists) out.add(p);
        }
        return out;
    }

    private String renderRows(List<PlayerRow> list) {
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(list.size(), 100);
        for (int i = 0; i < limit; i++) {
            PlayerRow p = list.get(i);
            sb.append(p.rank).append(". ")
                    .append(p.name.isEmpty() ? "FID " + p.fid : p.name)
                    .append("\nMight: ").append(formatNumber(p.might));
            if (!p.alliance.isEmpty()) sb.append(" • ").append(p.alliance);
            if (!p.fid.isEmpty()) sb.append(" • FID ").append(p.fid);
            sb.append("\n\n");
        }
        if (list.size() > limit) sb.append("Näytetään 100 ensimmäistä. CSV sisältää kaikki rivit.");
        return sb.toString();
    }

    private void chooseCsvLocation() {
        if (rows.isEmpty()) return;
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "WOS_state_" + currentState + "_might.csv");
        startActivityForResult(intent, CREATE_CSV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_CSV && resultCode == RESULT_OK && data != null && data.getData() != null) {
            writeCsv(data.getData());
        }
    }

    private void writeCsv(Uri uri) {
        try (OutputStream out = getContentResolver().openOutputStream(uri)) {
            if (out == null) throw new Exception("Tiedostoa ei voitu avata");
            StringBuilder csv = new StringBuilder("\uFEFF");
            csv.append("Rank;Player;FID;Alliance;Might;State\r\n");
            for (PlayerRow p : rows) {
                csv.append(p.rank).append(';')
                        .append(csv(p.name)).append(';')
                        .append(csv(p.fid)).append(';')
                        .append(csv(p.alliance)).append(';')
                        .append(p.might).append(';')
                        .append(csv(currentState)).append("\r\n");
            }
            out.write(csv.toString().getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "CSV tallennettu ✓", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Tallennus epäonnistui: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String csv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private String extractError(String body) {
        try {
            JSONObject o = new JSONObject(body);
            return stringValue(o, "error", "message", "detail");
        } catch (Exception ignored) {
            return body.length() > 160 ? body.substring(0, 160) : body;
        }
    }

    private String stringValue(JSONObject o, String... keys) {
        for (String key : keys) {
            Object v = o.opt(key);
            if (v != null && v != JSONObject.NULL) {
                String s = String.valueOf(v).trim();
                if (!s.isEmpty() && !s.equals("{}") && !s.equals("[]")) return s;
            }
        }
        return "";
    }

    private long longValue(JSONObject o, String... keys) {
        for (String key : keys) {
            Object v = o.opt(key);
            if (v == null || v == JSONObject.NULL) continue;
            try {
                String s = String.valueOf(v).replace(" ", "").replace(",", "");
                if (s.endsWith("B") || s.endsWith("b")) return (long) (Double.parseDouble(s.substring(0, s.length() - 1)) * 1_000_000_000L);
                if (s.endsWith("M") || s.endsWith("m")) return (long) (Double.parseDouble(s.substring(0, s.length() - 1)) * 1_000_000L);
                return (long) Double.parseDouble(s);
            } catch (Exception ignored) { }
        }
        return 0;
    }

    private String normalizeState(String value) {
        return value.replace("#", "").replaceAll("[^0-9]", "");
    }

    private String formatNumber(long n) {
        return String.format(java.util.Locale.US, "%,d", n).replace(',', ' ');
    }

    private void showError(String message) {
        runOnUiThread(() -> {
            statusText.setText(message);
            resultsText.setText("Ei ranking-dataa.");
            exportButton.setEnabled(false);
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(stateInput.getWindowToken(), 0);
    }

    private static class PlayerRow {
        int rank;
        final String name;
        final String fid;
        final String alliance;
        final long might;

        PlayerRow(int rank, String name, String fid, String alliance, long might) {
            this.rank = rank;
            this.name = name;
            this.fid = fid;
            this.alliance = alliance;
            this.might = might;
        }
    }
}
