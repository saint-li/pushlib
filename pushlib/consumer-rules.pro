#OPPO推送混淆配置
-keep public class * extends android.app.Service

#华为推送混淆配置
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

#小米推送混淆配置
#这里com.saint.pushlib.mipush.MiPushReceiver改成app中定义的完整类名
-keep class com.saint.pushlib.mipush.MiPushReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**

#极光推送混淆配置
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
#-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-keep class * extends cn.jpush.android.service.JPushMessageReceiver{*;}

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#GSON混淆配置
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
