import org.gradle.api.Plugin
import org.gradle.api.Project

abstract  class BasePlugin: Plugin<Project> {

    lateinit var project: Project
    override fun apply(target: Project) {
        this.project = target
        if(project.hasProperty("android")){
            with(project)
            {
                initPlugins()
                configureCommonDependencies()
                configureJetPackNavigation()
                testingDependencies()
                configuredAndroidBlock()
            }
        }
    }

    abstract fun configuredAndroidBlock()

}