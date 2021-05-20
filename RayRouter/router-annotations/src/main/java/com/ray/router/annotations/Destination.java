package com.ray.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 当前注解可以标记在类文件上面
 */
@Target(ElementType.TYPE)
/*
 * 当前注解的生命周期为class文件,编译时期
 */
@Retention(RetentionPolicy.CLASS)
public @interface Destination {

    /**
     * 当前页面的url
     * @return 页面的url
     */
    String url();

    /**
     * 页面的描述
     * @return 页面的描述，比如 "我的收藏"
     */
    String description();
}
