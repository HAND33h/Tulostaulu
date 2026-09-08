package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Public WOS Control API v1 endpoint metadata used by Tulostaulu.
 * No API key is stored here. Authentication must be injected at runtime.
 * Source: https://woscontrol.com/api-docs (checked 2026-09-09).
 */
public final class WosControlApiCatalog {
    public static final String BASE_URL = "https://woscontrol.com/api/v1";
    public static final int DEFAULT_STATE_ID = 6;

    public static final class Endpoint {
        public final String method;
        public final String path;
        public final String purpose;
        public final boolean apiKeyRequired;
        Endpoint(String method, String path, String purpose, boolean apiKeyRequired) {
            this.method = method;
            this.path = path;
            this.purpose = purpose;
            this.apiKeyRequired = apiKeyRequired;
        }
    }

    public static final List<Endpoint> ENDPOINTS = Collections.unmodifiableList(Arrays.asList(
        new Endpoint("GET", "/state/{state_id}", "State metadata and public state information", true),
        new Endpoint("GET", "/transfer/{state_id}", "State transfer information", true),
        new Endpoint("GET", "/leaderboard", "Leaderboard data", true),
        new Endpoint("GET", "/player/{fid}", "Player data by FID", true),
        new Endpoint("GET", "/alliance", "Alliance data", true),
        new Endpoint("GET", "/stats", "Service/statistics data", true),
        new Endpoint("GET", "/me", "API account/key information", true),
        new Endpoint("GET", "/packages", "Available API packages", true),
        new Endpoint("GET", "/codes/active", "Active gift codes", false)
    ));

    /** Supported documented authentication header forms. Never log the supplied key. */
    public static String bearerHeader(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) throw new IllegalArgumentException("API key missing");
        return "Bearer " + apiKey.trim();
    }

    public static String statePath(int stateId) {
        if (stateId <= 0) throw new IllegalArgumentException("Invalid state id");
        return "/state/" + stateId;
    }

    public static String transferPath(int stateId) {
        if (stateId <= 0) throw new IllegalArgumentException("Invalid state id");
        return "/transfer/" + stateId;
    }

    public static String playerPath(long fid) {
        if (fid <= 0) throw new IllegalArgumentException("Invalid FID");
        return "/player/" + fid;
    }

    /** HTTP failures the app should expose clearly rather than treating as empty data. */
    public static String explainHttpStatus(int status) {
        switch (status) {
            case 401: return "Missing or invalid API key";
            case 403: return "API key or domain access denied";
            case 404: return "Requested WOS resource not found";
            case 429: return "API rate limit exceeded";
            case 500: return "WOS Control internal error";
            case 502: return "WOS backend temporarily unavailable";
            default: return status >= 200 && status < 300 ? "OK" : "HTTP " + status;
        }
    }

    private WosControlApiCatalog() {}
}
