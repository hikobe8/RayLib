package com.ray.router.processor;

import com.ray.router.annotations.Destination;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class DestinationProcessor extends AbstractProcessor {

    private final static String TAG = "DestinationProcessor";

    /**
     * 编译器找到支持的注解之后会调用此方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + " >>>> process start...");
        Set<Element> allDestinationElements =
                (Set<Element>) roundEnvironment.getElementsAnnotatedWith(Destination.class);
        System.out.println(TAG + " >>>> all Destination Elements count = " + allDestinationElements.size());
        if (allDestinationElements.size() < 1) {
            return false;
        }

        for (Element destinationElement : allDestinationElements) {
            TypeElement typeElement = (TypeElement) destinationElement;
            Destination annotation = typeElement.getAnnotation(Destination.class);
            if (null == annotation) {
                continue;
            }
            String url = annotation.url();
            String description = annotation.description();
            String realPath = typeElement.getQualifiedName().toString();

            System.out.println(TAG + " >>>> url = " + url);
            System.out.println(TAG + " >>>> description = " + description);
            System.out.println(TAG + " >>>> realPath = " + realPath);
        }

        System.out.println(TAG + " >>>> process end.");
        return false;
    }

    /**
     * @return 当前注解处理器支持的注解类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Destination.class.getCanonicalName());
    }
}
