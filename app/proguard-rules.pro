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
-ignorewarnings

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


-keep public class * extends android.view.View{
	*** get*();
	void set*(***);
	public <init>(android.content.Context);
	public <init>(android.content.Context,android.util.AttributeSet);
	public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context,android.util.AttributeSet);
	public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$* {
 *;
}
