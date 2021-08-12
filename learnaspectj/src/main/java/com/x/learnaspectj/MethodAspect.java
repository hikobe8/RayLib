package com.x.learnaspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Author : Ray
 * Time : 3/10/21 6:33 PM
 * Description :
 */
@Aspect  //①
public class MethodAspect {

    private static final String TAG = "MethodAspect";

    @Pointcut("call(* com.x.learnaspectj.Animal.fly(..))")//②
    public void callMethod() {
    }

    @Before("callMethod()")//③
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, "before->" + joinPoint.getTarget()); //④
    }


    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")//②
    public void callClickMethod() {
    }

    @Before("callClickMethod()")//③
    public void beforeClickMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, "点击事件埋点" + joinPoint.getTarget().toString()); //④
    }



}

