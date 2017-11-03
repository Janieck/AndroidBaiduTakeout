# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\develop\Android\AndroidStudio\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

#凡事smsSdk的类,全部不混淆,类名保持不变,保证反射已经找到
-keep class cn.smssdk.**{*;}
-keep class com.mob.**{*;}

-dontwarn com.mob.**
-dontwarn cn.smssdk.**
