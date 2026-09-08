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

    public boolean save(PlayerRecord incoming) {
        if (incoming == null || incoming.fid == null || incoming.fid.trim().isEmpty() || !isVerifiedSource(incoming.sourceType)) return false;
        long now = System.currentTimeMillis();
        if (incoming.fetchedAt <= 0) incoming.fetchedAt = now;
        if (incoming.observedAt <= 0) incoming.observedAt = incoming.fetchedAt;

        SQLiteDatabase db = getWritableDatabase();
        PlayerRecord current = getByFid(incoming.fid);
        PlayerRecord merged = current == null ? incoming : merge(current, incoming);

        db.insertWithOnConflict("current_player", null, values(merged), SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues snapshot = values(incoming);
        snapshot.remove("event_type"); snapshot.remove("alliance_name"); snapshot.remove("hero_total_power");
        snapshot.remove("exploration"); snapshot.remove("labyrinth"); snapshot.remove("chief_gear");
        snapshot.remove("chief_charms"); snapshot.remove("rank_value");
        db.insert("player_snapshot", null, snapshot);
        return true;
    }

    private PlayerRecord merge(PlayerRecord old, PlayerRecord n) {
        PlayerRecord r = new PlayerRecord();
        r.fid = pick(n.fid, old.fid); r.nickname = pick(n.nickname, old.nickname); r.stateId = pick(n.stateId, old.stateId);
        r.allianceTag = pick(n.allianceTag, old.allianceTag); r.allianceName = pick(n.allianceName, old.allianceName);
        r.personalPower = pick(n.personalPower, old.personalPower); r.combatPower = pick(n.combatPower, old.combatPower);
        r.heroPower = pick(n.heroPower, old.heroPower); r.heroTotalPower = pick(n.heroTotalPower, old.heroTotalPower);
        r.troopPower = pick(n.troopPower, old.troopPower); r.petPower = pick(n.petPower, old.petPower);
        r.expertPower = pick(n.expertPower, old.expertPower); r.kills = pick(n.kills, old.kills);
        r.furnaceLevel = pick(n.furnaceLevel, old.furnaceLevel); r.exploration = pick(n.exploration, old.exploration);
        r.labyrinth = pick(n.labyrinth, old.labyrinth); r.chiefGear = pick(n.chiefGear, old.chiefGear);
        r.chiefCharms = pick(n.chiefCharms, old.chiefCharms); r.eventType = pick(n.eventType, old.eventType);
        r.rank = n.rank > 0 ? n.rank : old.rank;
        r.source = pick(n.source, old.source); r.sourceType = pick(n.sourceType, old.sourceType);
        r.observedAt = n.observedAt > 0 ? n.observedAt : old.observedAt;
        r.fetchedAt = Math.max(n.fetchedAt, old.fetchedAt);
        r.confidence = Math.max(n.confidence, old.confidence);
        return r;
    }

    private String pick(String n, String old) { return n != null && !n.trim().isEmpty() ? n : old; }
    private long pick(long n, long old) { return n > 0 ? n : old; }

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

    public PlayerRecord getByFid(String fid) {
        if (fid == null || fid.trim().isEmpty()) return null;
        Cursor c = getReadableDatabase().query("current_player", null, "fid=?", new String[]{fid.trim()}, null, null, null, "1");
        try { return c.moveToFirst() ? fromCursor(c) : null; } finally { c.close(); }
    }

    public List<PlayerRecord> listState(String stateId, int limit) {
        ArrayList<PlayerRecord> out = new ArrayList<>();
        Cursor c = getReadableDatabase().query("current_player", null, "state_id=?", new String[]{stateId}, null, null,
                "personal_power DESC, combat_power DESC, fetched_at DESC", String.valueOf(limit));
        try { while (c.moveToNext()) out.add(fromCursor(c)); } finally { c.close(); }
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
