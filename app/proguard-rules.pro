# Project-specific R8 / ProGuard hardening rules.
# Keep runtime annotations and generic signatures used by Android/Firebase libraries.
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,AnnotationDefault,Signature,InnerClasses,EnclosingMethod

# Remove source file names from release stack traces and rename line-number source marker.
-renamesourcefileattribute SourceFile

# Keep Android components referenced from the manifest. R8 usually detects these,
# but this explicit rule makes release builds resilient to future refactors.
-keep class com.hand33h.tulostaulu.*Activity { *; }

# Preserve Firebase component discovery metadata/classes. Firebase libraries also
# ship consumer rules; these rules are an additional safety net.
-keep class com.google.firebase.components.ComponentRegistrar { *; }
-keep class * implements com.google.firebase.components.ComponentRegistrar { *; }

# Do not weaken optimization globally; allow R8 to shrink and obfuscate all
# application code not required by the rules above.
