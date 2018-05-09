package com.ray.lib.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ray.lib.R;

/***
 *  Author : ryu18356@gmail.com
 *  Create at 2018-05-09 18:46
 *  description : loading view logic controller
 */
public class LoadingViewController {

    private View mTargetView;

    /**
     * for activity use
     * @param parentView
     */
    public LoadingViewController(ViewGroup parentView) {
        View loadingMainLayout = getLoadingMainLayout(parentView.getContext());
        parentView.addView(loadingMainLayout);
    }

    public LoadingViewController(View targetView) {
        ViewGroup loadingMainLayout = getLoadingMainLayout(targetView.getContext());
        ViewGroup parentLayout = (ViewGroup) targetView.getParent();
        ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
        FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(layoutParams);
        layoutParams1.topMargin = 0;
        layoutParams1.bottomMargin = 0;
        layoutParams1.leftMargin = 0;
        layoutParams1.rightMargin = 0;
        int index = parentLayout.indexOfChild(targetView);
        parentLayout.removeView(targetView);
        loadingMainLayout.addView(targetView, 0, layoutParams1);
        parentLayout.addView(loadingMainLayout, index, layoutParams);
        mTargetView = targetView;
    }

    private ViewGroup getLoadingMainLayout(Context context) {
        return (ViewGroup) LayoutInflater.from(context).inflate(R.layout.load_main_layout, null, false);
    }


    public void switchLoading() {

    }

    public void switchEmpty() {

    }

    public void switchError() {

    }

}
