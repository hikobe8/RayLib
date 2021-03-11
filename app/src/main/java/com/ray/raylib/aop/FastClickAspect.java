package com.ray.raylib.aop;

import android.util.Log;
import android.view.View;

import com.ray.raylib.util.FastClickCheckUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class FastClickAspect {
    //定义切入点
    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void methodViewOnClick() {

    }

    //定义环绕，包装methodViewOnClick方法切入点
    @Around("methodViewOnClick")
    public void aroundViewOnClick(ProceedingJoinPoint joinPoint) throws Throwable {
        View target = (View) joinPoint.getArgs()[0];
        if (FastClickCheckUtil.isFastClick(target, 1000L)) {
            Log.e("FastClickAspect", target + "快速点击");
        } else {
            joinPoint.proceed();
        }
    }

}
