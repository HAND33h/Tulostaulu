package com.hand33h.tulostaulu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText stateInput;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(48, 72, 48, 48);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(13, 20, 32));

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
        subParams.setMargins(0, 12, 0, 56);
        root.addView(subtitle, subParams);

        stateInput = new EditText(this);
        stateInput.setHint("Serverinumero");
        stateInput.setText("1674");
        stateInput.setTextSize(22);
        stateInput.setSingleLine(true);
        stateInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        stateInput.setGravity(Gravity.CENTER);
        root.addView(stateInput, new LinearLayout.LayoutParams(-1, -2));

        Button loadButton = new Button(this);
        loadButton.setText("LATAA TULOSTAULU");
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(-1, -2);
        buttonParams.setMargins(0, 32, 0, 28);
        root.addView(loadButton, buttonParams);

        statusText = new TextView(this);
        statusText.setText("Valitse serveri. Oletus: 1674");
        statusText.setTextColor(Color.WHITE);
        statusText.setTextSize(18);
        statusText.setGravity(Gravity.CENTER);
        root.addView(statusText, new LinearLayout.LayoutParams(-1, -2));

        TextView info = new TextView(this);
        info.setText("Tämä on WOS Tulostaulu -sovelluksen Android-runko. Seuraava vaihe yhdistää palvelimen ranking-datalähteen ja Excel/CSV-viennin.");
        info.setTextColor(Color.LTGRAY);
        info.setTextSize(14);
        info.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(-1, -2);
        infoParams.setMargins(0, 48, 0, 0);
        root.addView(info, infoParams);

        loadButton.setOnClickListener(v -> loadState());
        setContentView(root);
    }

    private void loadState() {
        String state = stateInput.getText().toString().trim();
        if (state.isEmpty()) {
            Toast.makeText(this, "Anna serverinumero", Toast.LENGTH_SHORT).show();
            return;
        }
        statusText.setText("Serveri " + state + " valittu ✓");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(stateInput.getWindowToken(), 0);
    }
}
