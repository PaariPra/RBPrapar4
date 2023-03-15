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

-keep class org.wysaid.nativePort.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.chetssholic.removebackgeround.activity.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.chetssholic.removebackgeround.adapter.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.chetssholic.removebackgeround.interfaceces.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.chetssholic.removebackgeround.utils.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.lyrebirdstudio.croppylib.cropview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.cropview.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.lyrebirdstudio.croppylib.inputview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.inputview.** { *; }

-keep class com.lyrebirdstudio.croppylib.main.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.main.** { *; }


-keep class com.lyrebirdstudio.croppylib.state.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.state.** { *; }

-keep class com.lyrebirdstudio.croppylib.ui.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.ui.** { *; }

-keep class com.lyrebirdstudio.croppylib.util.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.util.** { *; }

-keep class com.lyrebirdstudio.croppylib.cropview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.cropview.** { *; }

-keep class com.lyrebirdstudio.aspectratiorecyclerviewlib.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.aspectratiorecyclerviewlib.** { *; }

# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class com.huawei.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.** { *; }

# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class com.huawei.hms.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.hms.** { *; }

# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class com.huawei.agconnect.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.agconnect.** { *; }


# keep everything in this package from being removed or renamed
-keep class com.huawei.hianalytics.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.hianalytics.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.huawei.hmf.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.hmf.** { *; }


-keep class com.google.ads.** # Don't proguard AdMob classes
-dontwarn com.google.ads.** # Temporary workaround for v6.2.1. It gives a warning that you can ignore
# Add this global rule
-keepattributes Signature


# keep everything in this package from being removed or renamed
-keep class com.huawei.hms.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.hms.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.huawei.secure.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.secure.** { *; }


# keep everything in this package from being removed or renamed
-keep class com.huawei.updatesdk.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.huawei.updatesdk.** { *; }






