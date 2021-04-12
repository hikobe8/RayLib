package com.x.learnaspectj;

import android.util.Log;

/**
 * Author : Ray
 * Time : 3/10/21 6:36 PM
 * Description :
 */
public class Animal {
    private static final String TAG = "Animal";

    public void fly() {
        Log.e(TAG, "animal fly method:" + this.toString() + "#fly");
    }
}
