package com.ray.raylib.util;

import android.view.View;

import com.ray.raylib.R;


public class FastClickCheckUtil {

    public static boolean isFastClick(View view, long threshold) {
        int key = R.id.view_click_time;
        //当前点击时间
        long currentTimeMillis = System.currentTimeMillis();
        if (null == view.getTag(key)) {
            //第一次点击
            view.setTag(key, currentTimeMillis);
            return false;
        }
        //非第一次点击
        long preTime = (long) view.getTag(key);
        if (currentTimeMillis - preTime < threshold) {
            return true;
        }
        view.setTag(key, preTime);
        return false;
    }

}
