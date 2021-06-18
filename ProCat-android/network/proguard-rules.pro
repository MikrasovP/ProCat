# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

    -keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
    -keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

    -dontnote com.google.gson.annotations.Expose
    -keepclassmembers class * {
        @com.google.gson.annotations.Expose <fields>;
    }


    -keepclasseswithmembers,allowobfuscation,includedescriptorclasses class * {
        @com.google.gson.annotations.Expose <fields>;
    }

    -dontnote com.google.gson.annotations.SerializedName
    -keepclasseswithmembers,allowobfuscation,includedescriptorclasses class * {
        @com.google.gson.annotations.SerializedName <fields>;
    }
