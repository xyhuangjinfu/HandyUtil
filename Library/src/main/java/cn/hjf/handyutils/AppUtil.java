package cn.hjf.handyutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

/**
 * Android Application 信息相关的工具类
 * Created by huangjinfu on 2017/6/28.
 */

public final class AppUtil {

    /**
     * 获取应用程序名称，读取 <application> 标签中的 'android:label' 属性。
     *
     * @param context
     * @return null-'android:label' 属性没有指定，或者有异常产生。否则返回 'android:label' 属性的值，有可能是 ""，如果 'android:label' 属性的值为 "" 的话。
     */
    @Nullable
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称
     *
     * @param context
     * @return null-没有指定 versionName 属性，或者有异常产生。否则返回 versionName 的值。
     */
    @Nullable
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序 VersionCode
     *
     * @param context
     * @return -1-有异常产生。0-没有指定 versionCode。否则返回 versionCode 的值。
     */
    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
