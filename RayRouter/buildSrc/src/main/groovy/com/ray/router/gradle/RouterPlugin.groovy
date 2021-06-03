package com.ray.router.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg('root-project-dir', project.rootProject.projectDir.absolutePath)
            }
        }

        project.getExtensions().create("router", RouterExtension)
        project.afterEvaluate {
            RouterExtension extension = project["router"]
            println("用户配置的wiki目录为：${extension.wikiDir}")
        }
    }
}