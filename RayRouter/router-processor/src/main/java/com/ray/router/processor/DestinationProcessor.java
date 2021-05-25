package com.ray.router.processor;

import com.google.auto.service.AutoService;
import com.ray.router.annotations.Destination;

import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
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
        if (roundEnvironment.processingOver()) {
            //避免多次调用
            return false;
        }
        System.out.println(TAG + " >>>> process start...");
        Set<? extends Element> allDestinationElements =
                roundEnvironment.getElementsAnnotatedWith(Destination.class);
        System.out.println(TAG + " >>>> all Destination Elements count = " + allDestinationElements.size());
        if (allDestinationElements.size() < 1) {
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String mappingClassName = "RouterMapping_" + System.currentTimeMillis();
        stringBuilder.append("package com.ray.rayrouter.mapping;").append("\n\n");
        stringBuilder.append("import java.util.HashMap;").append("\n");
        stringBuilder.append("import java.util.Map;").append("\n\n");
        stringBuilder.append("public class ")
                .append(mappingClassName)
                .append(" {")
                .append("\n\n");
        stringBuilder.append("    public static Map<String, String> get() {").append("\n");
        stringBuilder.append("        Map<String, String> mapping = new HashMap<>();").append("\n");

        for (Element destinationElement : allDestinationElements) {
            TypeElement typeElement = (TypeElement) destinationElement;
            Destination annotation = typeElement.getAnnotation(Destination.class);
            if (null == annotation) {
                continue;
            }
            String url = annotation.url();
            String description = annotation.description();
            String realPath = typeElement.getQualifiedName().toString();
            stringBuilder.append("        mapping.put(\"")
                    .append(url).append("t\",\"")
                    .append(realPath)
                    .append("\");")
                    .append("\n");
            System.out.println(TAG + " >>>> url = " + url);
            System.out.println(TAG + " >>>> description = " + description);
            System.out.println(TAG + " >>>> realPath = " + realPath);
        }

        stringBuilder.append("        return mapping;").append("\n");
        stringBuilder.append("    }").append("\n\n");
        stringBuilder.append("}");

        String mappingFullName = "com.ray.rayrouter.mapping." + mappingClassName;
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(mappingFullName);
            Writer writer = sourceFile.openWriter();
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while create router mapping file", e);
        }
        System.out.println(TAG + " >>>> process end.");
        System.out.println(stringBuilder.toString());
        return false;
    }

    /**
     * @return 当前注解处理器支持的注解类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Destination.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
