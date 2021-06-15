package com.ray.router.gradle

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RouterMappingTransform extends Transform {

    @Override
    String getName() {
        return 'RouterMappingTransform'
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        RouterMappingCollector collector = new RouterMappingCollector()
        transformInvocation.inputs.each {
            //拷贝目录
            it.directoryInputs.each { dirInput ->
                def destDir = transformInvocation.outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes,
                        dirInput.scopes,
                        Format.DIRECTORY
                )
                FileUtils.copyDirectory(dirInput.file, destDir)
                collector.collect(dirInput.file)
            }
            //拷贝jar
            it.jarInputs.each { dirInput ->
                def dest = transformInvocation.outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes,
                        dirInput.scopes,
                        Format.JAR
                )
                FileUtils.copyFile(dirInput.file, dest)
                collector.collectJar(dirInput.file)
            }
        }
        println("${getName()} RouterMappingClasses = ${collector.routerMappings}")
        File mappingJarFile = transformInvocation.outputProvider.getContentLocation(
                "router_mapping",
                getOutputTypes(),
                getScopes(),
                Format.JAR
        )
        if (!mappingJarFile.getParentFile().exists()) {
            mappingJarFile.getParentFile().mkdirs()
        }
        if (mappingJarFile.exists()) {
            mappingJarFile.delete()
        }
        println("${getName()} mappingJarFile = $mappingJarFile.absolutePath")
        FileOutputStream fos = new FileOutputStream(mappingJarFile)
        JarOutputStream jos = new JarOutputStream(fos)
        ZipEntry zipEntry = new ZipEntry(RouterMappingBytecodeBuilder.CLASS_NAME + ".class")
        jos.putNextEntry(zipEntry)
        jos.write(RouterMappingBytecodeBuilder.get(collector.routerMappings))
        jos.closeEntry()
        jos.close()
        fos.close()
    }
}