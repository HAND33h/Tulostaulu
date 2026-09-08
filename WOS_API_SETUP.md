# WOS API setup

The Android APK must not contain the WOS API key. The key is stored as a Firebase Functions secret and the app calls the `wosProxy` backend.

## 1. Add the WOS API key

From a computer with Firebase CLI installed and logged in to the `bunny-king` Firebase project, run:

```bash
firebase use bunny-king
firebase functions:secrets:set WOS_API_KEY
```

When Firebase asks for the value, paste the WOS API key there. Do not commit the key to GitHub.

## 2. Add the WOS API base URL

Run:

```bash
firebase functions:secrets:set WOS_API_BASE_URL
```

Paste the API base URL when prompted, for example:

```text
https://api.example.com
```

## 3. Add the authentication header name

Run:

```bash
firebase functions:secrets:set WOS_API_HEADER
```

For most API-key based services the value is:

```text
x-api-key
```

If your WOS API provider documents another header name, use that exact name instead.

## 4. Deploy

```bash
cd functions
npm install
cd ..
firebase deploy --only functions:wosProxy
```

The deployed function will be in region `europe-north1`.

## 5. Example request

```text
https://europe-north1-bunny-king.cloudfunctions.net/wosProxy?endpoint=YOUR_ENDPOINT&state=1674
```

The exact endpoint names and authentication format depend on the WOS API provider.
