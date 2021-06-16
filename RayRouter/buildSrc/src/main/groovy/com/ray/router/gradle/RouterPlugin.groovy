package com.ray.router.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //注册RouterMappingTransform
        if (project.plugins.hasPlugin(AppPlugin)) {
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            appExtension.registerTransform(new RouterMappingTransform())
        }

        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg('root-project-dir', project.rootProject.projectDir.absolutePath)
            }
        }

        //执行clean 任务的时候清除上一次生成的router mapping目录的所有文件
        project.clean.doFirst {
            deleteGeneratedJsonFiles(project)
        }

        project.getExtensions().create("router", RouterExtension)
        project.afterEvaluate {
            RouterExtension extension = project["router"]
            println("用户配置的wiki目录为：${extension.wikiDir}")
            //assemble*
            project.tasks.findAll { task ->
                task.name.startsWith('assemble')
            }.each { task ->
                task.doLast {
                    deleteGeneratedJsonFiles(project)
                }
            }
            //compileDebugJavaWithJavac
            project.tasks.findAll { task ->
                task.name.startsWith('compile') && task.name.endsWith('JavaWithJavac')
            }.each { task ->
                task.doLast {
                    File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                    if (!routerMappingDir.exists()) {
                        return
                    }
                    File[] allChildFiles = routerMappingDir.listFiles()
                    if (allChildFiles.length < 1) {
                        return
                    }
                    StringBuilder builder = new StringBuilder("# 页面文档\n\n")
                    allChildFiles.each { child ->
                        if (child.name.endsWith(".json")) {
                            JsonSlurper jsonSlurper = new JsonSlurper()
                            def content = jsonSlurper.parse(child)
                            content.each { innerContent ->
                                def description = innerContent['description']
                                def realPath = innerContent['realPath']
                                def url = innerContent['url']
                                builder.append("## $description\n")
                                builder.append("- url: $url\n")
                                builder.append("- realPath: $realPath\n\n")
                            }
                        }
                    }
                    File wikiFile = new File(project.rootProject.projectDir, "页面文档.md")
                    if (wikiFile.exists()) {
                        wikiFile.delete()
                    }
                    wikiFile.write(builder.toString())
                }
            }

        }
    }

    private static void deleteGeneratedJsonFiles(Project project) {
        File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
        if (routerMappingDir.exists()) {
            routerMappingDir.deleteDir()
        }
    }
}