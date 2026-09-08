# WOS Control API setup

The Android APK must not contain the WOS Control API key. The app uses the Firebase Function `wosProxy`, and the API key is stored only as a Firebase Functions secret.

The proxy is configured for:

- Base URL: `https://woscontrol.com/api/v1`
- Authentication: `Authorization: Bearer <WOS_API_KEY>`

## 1. Add the API key as a Firebase secret

From a computer with Firebase CLI installed and logged in to the `bunny-king` project, run:

```bash
firebase use bunny-king
firebase functions:secrets:set WOS_API_KEY
```

When Firebase asks for the value, paste the approved WOS Control API key. Do not commit the key to GitHub or include it in the Android APK.

## 2. Deploy the proxy

```bash
cd functions
npm install
cd ..
firebase deploy --only functions:wosProxy
```

The function is deployed in region `europe-north1`.

## 3. Test

Example for the documented `codes` endpoint:

```text
https://europe-north1-bunny-king.cloudfunctions.net/wosProxy?endpoint=codes
```

The Firebase Function adds the Bearer token on the server, so the Android app never needs to know the API key.
