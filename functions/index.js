const { onRequest } = require("firebase-functions/v2/https");
const { defineSecret } = require("firebase-functions/params");

const WOS_API_KEY = defineSecret("WOS_API_KEY");
const WOS_API_BASE_URL = defineSecret("WOS_API_BASE_URL");
const WOS_API_HEADER = defineSecret("WOS_API_HEADER");

exports.wosProxy = onRequest(
  {
    region: "europe-north1",
    secrets: [WOS_API_KEY, WOS_API_BASE_URL, WOS_API_HEADER],
    timeoutSeconds: 30,
    memory: "256MiB"
  },
  async (req, res) => {
    try {
      if (req.method !== "GET") {
        return res.status(405).json({ error: "GET only" });
      }

      const endpoint = String(req.query.endpoint || "").replace(/^\/+/, "");
      if (!endpoint || !/^[A-Za-z0-9/_-]+$/.test(endpoint)) {
        return res.status(400).json({ error: "Invalid endpoint" });
      }

      const baseUrl = WOS_API_BASE_URL.value().replace(/\/$/, "");
      if (!/^https:\/\//i.test(baseUrl)) {
        return res.status(500).json({ error: "WOS_API_BASE_URL is not configured" });
      }

      const headerName = WOS_API_HEADER.value() || "x-api-key";
      const url = new URL(`${baseUrl}/${endpoint}`);
      for (const [key, value] of Object.entries(req.query)) {
        if (key === "endpoint" || value == null) continue;
        if (Array.isArray(value)) {
          for (const item of value) url.searchParams.append(key, String(item));
        } else {
          url.searchParams.set(key, String(value));
        }
      }

      const response = await fetch(url, {
        headers: {
          [headerName]: WOS_API_KEY.value(),
          "accept": "application/json",
          "user-agent": "WOSControl/1.0"
        }
      });

      const body = await response.text();
      res.status(response.status);
      res.set("content-type", response.headers.get("content-type") || "application/json; charset=utf-8");
      res.set("cache-control", "no-store");
      return res.send(body);
    } catch (error) {
      console.error("WOS proxy error", error);
      return res.status(502).json({ error: "WOS upstream request failed" });
    }
  }
);
