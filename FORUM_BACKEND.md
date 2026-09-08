# Bunny King Forum backend

Recommended backend: Firebase Authentication + Cloud Firestore + Cloud Storage.

## Roles
- `member`: create/edit own posts and replies, like, report.
- `helper_admin`: all member rights + hide/unhide posts, lock/unlock threads, remove posts/replies, review reports and moderation queue.
- `admin`: helper admin rights + manage forum users and roles below owner.
- `owner`: HAND33h super-admin; unrestricted forum administration.

Roles MUST be assigned server-side as Firebase Authentication custom claims. Never let the Android client promote itself.

Custom claim format:
`{ "role": "member" }`
`{ "role": "helper_admin" }`
`{ "role": "admin" }`
`{ "role": "owner" }`

## Collections
- `forum_posts/{postId}`
- `forum_posts/{postId}/replies/{replyId}`
- `forum_posts/{postId}/likes/{uid}`
- `forum_posts/{postId}/reports/{reportId}`
- `forum_users/{uid}`
- `moderation/...`
- `admin/...`
- `owner/...`

## Anonymous mode
Anonymous forum posting means the public UI does not expose the WOS FID/profile identity. Backend abuse prevention still uses an authenticated app UID. Do not promise network-level anonymity.

## Important
`firestore.rules` is included in this repository, but it does not become active until deployed to the Firebase project. A Firebase project/config (`google-services.json`) is still required before the Android forum can exchange live posts.
