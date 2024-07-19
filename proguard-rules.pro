# Preserve public classes, methods, and fields
-keep public class * {
    public <methods>;
    public <fields>;
}

# Preserve classes with public API
-keep public class com.example.** {
    public *;
}

# Preserve annotations
-keepattributes *Annotation*

# Preserve enums
-keep enum com.example.** {
    *;
}

# Preserve the main entry point class
-keep public class * extends java.lang.Object {
    public static void main(java.lang.String[]);
}

# Shrink and optimize, but do not strip debug information
-dontwarn
-dontnote
-dontobfuscate
-dontshrink
-dontoptimize

# Keep the default ProGuard configurations
-keep class proguard.** { *; }