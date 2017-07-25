package cn.hjf.handyutils;

import android.content.Context;

/**
 * Created by huangjinfu on 2017/7/25.
 */

public final class DensityUtil {

    public static int dp2Px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float px2Dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return px * 1f / scale;
    }
}
