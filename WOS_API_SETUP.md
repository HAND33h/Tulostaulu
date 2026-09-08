# WOS API setup

The Android APK must not contain the WOS API key. The key is stored as a Firebase Functions secret and the app calls the `wosProxy` backend.

## 1. Add the WOS API key

From a computer with Firebase CLI installed and logged in to the `bunny-king` Firebase project, run:

```bash
firebase use bunny-king
firebase functions:secrets:set WOS_API_KEY
```

When Firebase asks for the value, paste the WOS API key there. Do not commit the key to GitHub.

## 2. Configure the WOS API base URL

Run:

```bash
firebase functions:config:set WOS_API_BASE_URL="https://YOUR-WOS-API-BASE-URL"
```

If your provider uses a header other than `x-api-key`, configure it too:

```bash
firebase functions:config:set WOS_API_HEADER="Authorization"
```

Note: the included function defaults to the `x-api-key` header. If the provider expects `Authorization: Bearer ...`, the proxy needs the Bearer prefix added before deployment.

## 3. Deploy

```bash
cd functions
npm install
cd ..
firebase deploy --only functions:wosProxy
```

The deployed function will be in region `europe-north1`.

## 4. Example request

```text
https://europe-north1-bunny-king.cloudfunctions.net/wosProxy?endpoint=YOUR_ENDPOINT&state=1674
```

The exact endpoint names and authentication format depend on the WOS API provider.
