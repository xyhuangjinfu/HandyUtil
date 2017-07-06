package cn.hjf.handyutils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangjinfu on 2016/9/23.
 */
public final class ViewUtil {

    /**
     * 设置View的尺寸
     *
     * @param view        View 对象
     * @param base        尺寸基数
     * @param widthRatio  view 宽度 对 base 的比率，view.width = base * widthRatio
     * @param heightRatio view 高度 对 base 的比率，view.height = base * heightRatio
     */
    public static void setViewSize(View view, float base, float widthRatio, float heightRatio) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        float width = base * widthRatio;
        float height = base * heightRatio;
        lp.width = (int) width;
        lp.height = (int) height;

        view.setLayoutParams(lp);
    }
}
