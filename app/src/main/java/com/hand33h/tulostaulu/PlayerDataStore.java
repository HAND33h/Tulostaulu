package com.hand33h.tulostaulu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerDataStore extends SQLiteOpenHelper {
    private static final String DB = "wos_player_data.db";
    private static final int VERSION = 1;

    public static final Set<String> VERIFIED_SOURCES = new HashSet<>(Arrays.asList(
            "WOS_CONTROL_API",
            "WOS_OFFICIAL_VALIDATION",
            "LEADERBOARD_OCR",
            "PROFILE_SCREENSHOT_OCR",
            "WOS_OBSERVER",
            "PUBLIC_WOS_STATE_SITE",
            "WSCO_STATIC_DATA",
            "MANUAL_IMPORT"
    ));

    public PlayerDataStore(Context context) { super(context, DB, null, VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE current_player ("+
                "fid TEXT PRIMARY KEY,"+
                "nickname TEXT,state_id TEXT,alliance_tag TEXT,alliance_name TEXT,"+
                "personal_power INTEGER DEFAULT 0,combat_power INTEGER DEFAULT 0,"+
                "hero_power INTEGER DEFAULT 0,hero_total_power INTEGER DEFAULT 0,"+
                "troop_power INTEGER DEFAULT 0,pet_power INTEGER DEFAULT 0,expert_power INTEGER DEFAULT 0,"+
                "furnace_level TEXT,kills INTEGER DEFAULT 0,exploration TEXT,labyrinth TEXT,"+
                "chief_gear TEXT,chief_charms TEXT,rank_value INTEGER DEFAULT 0,event_type TEXT,"+
                "source TEXT,source_type TEXT,observed_at INTEGER DEFAULT 0,fetched_at INTEGER DEFAULT 0,"+
                "confidence INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE player_snapshot ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,fid TEXT,nickname TEXT,state_id TEXT,alliance_tag TEXT,"+
                "personal_power INTEGER DEFAULT 0,combat_power INTEGER DEFAULT 0,hero_power INTEGER DEFAULT 0,"+
                "troop_power INTEGER DEFAULT 0,pet_power INTEGER DEFAULT 0,expert_power INTEGER DEFAULT 0,"+
                "furnace_level TEXT,kills INTEGER DEFAULT 0,source TEXT,source_type TEXT,"+
                "observed_at INTEGER DEFAULT 0,fetched_at INTEGER DEFAULT 0,confidence INTEGER DEFAULT 0)");

        db.execSQL("CREATE INDEX idx_snapshot_fid_time ON player_snapshot(fid,fetched_at DESC)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public boolean isVerifiedSource(String sourceType) {
        return sourceType != null && VERIFIED_SOURCES.contains(sourceType);
    }

    public boolean save(PlayerRecord r) {
        if (r == null || r.fid == null || r.fid.trim().isEmpty() || !isVerifiedSource(r.sourceType)) return false;
        long now = System.currentTimeMillis();
        if (r.fetchedAt <= 0) r.fetchedAt = now;
        if (r.observedAt <= 0) r.observedAt = r.fetchedAt;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = values(r);
        db.insertWithOnConflict("current_player", null, v, SQLiteDatabase.CONFLICT_REPLACE);
        ContentValues s = values(r);
        s.remove("event_type"); s.remove("alliance_name"); s.remove("hero_total_power"); s.remove("exploration"); s.remove("labyrinth"); s.remove("chief_gear"); s.remove("chief_charms"); s.remove("rank_value");
        db.insert("player_snapshot", null, s);
        return true;
    }

    private ContentValues values(PlayerRecord r) {
        ContentValues v = new ContentValues();
        v.put("fid", r.fid); v.put("nickname", r.nickname); v.put("state_id", r.stateId);
        v.put("alliance_tag", r.allianceTag); v.put("alliance_name", r.allianceName);
        v.put("personal_power", r.personalPower); v.put("combat_power", r.combatPower);
        v.put("hero_power", r.heroPower); v.put("hero_total_power", r.heroTotalPower);
        v.put("troop_power", r.troopPower); v.put("pet_power", r.petPower); v.put("expert_power", r.expertPower);
        v.put("furnace_level", r.furnaceLevel); v.put("kills", r.kills); v.put("exploration", r.exploration);
        v.put("labyrinth", r.labyrinth); v.put("chief_gear", r.chiefGear); v.put("chief_charms", r.chiefCharms);
        v.put("rank_value", r.rank); v.put("event_type", r.eventType); v.put("source", r.source);
        v.put("source_type", r.sourceType); v.put("observed_at", r.observedAt); v.put("fetched_at", r.fetchedAt);
        v.put("confidence", r.confidence);
        return v;
    }

    public List<PlayerRecord> listState(String stateId, int limit) {
        ArrayList<PlayerRecord> out = new ArrayList<>();
        Cursor c = getReadableDatabase().query("current_player", null, "state_id=?", new String[]{stateId}, null, null,
                "personal_power DESC, fetched_at DESC", String.valueOf(limit));
        try {
            while (c.moveToNext()) out.add(fromCursor(c));
        } finally { c.close(); }
        return out;
    }

    private PlayerRecord fromCursor(Cursor c) {
        PlayerRecord r = new PlayerRecord();
        r.fid = get(c,"fid"); r.nickname = get(c,"nickname"); r.stateId = get(c,"state_id");
        r.allianceTag = get(c,"alliance_tag"); r.allianceName = get(c,"alliance_name");
        r.personalPower = getLong(c,"personal_power"); r.combatPower = getLong(c,"combat_power");
        r.heroPower = getLong(c,"hero_power"); r.heroTotalPower = getLong(c,"hero_total_power");
        r.troopPower = getLong(c,"troop_power"); r.petPower = getLong(c,"pet_power"); r.expertPower = getLong(c,"expert_power");
        r.furnaceLevel = get(c,"furnace_level"); r.kills = getLong(c,"kills"); r.exploration = get(c,"exploration");
        r.labyrinth = get(c,"labyrinth"); r.chiefGear = get(c,"chief_gear"); r.chiefCharms = get(c,"chief_charms");
        r.rank = (int)getLong(c,"rank_value"); r.eventType = get(c,"event_type"); r.source = get(c,"source");
        r.sourceType = get(c,"source_type"); r.observedAt = getLong(c,"observed_at"); r.fetchedAt = getLong(c,"fetched_at");
        r.confidence = (int)getLong(c,"confidence");
        return r;
    }

    private String get(Cursor c,String col){ int i=c.getColumnIndex(col); return i<0||c.isNull(i)?"":c.getString(i); }
    private long getLong(Cursor c,String col){ int i=c.getColumnIndex(col); return i<0||c.isNull(i)?0:c.getLong(i); }

    public static class PlayerRecord {
        public String fid="", nickname="", stateId="", allianceTag="", allianceName="";
        public long personalPower, combatPower, heroPower, heroTotalPower, troopPower, petPower, expertPower, kills;
        public String furnaceLevel="", exploration="", labyrinth="", chiefGear="", chiefCharms="", eventType="";
        public int rank, confidence;
        public String source="", sourceType="";
        public long observedAt, fetchedAt;
    }
}
