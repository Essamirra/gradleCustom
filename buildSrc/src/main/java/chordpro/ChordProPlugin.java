package chordpro;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class ChordProPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("Hello from chord pro plugin");

        ChordProExtension extension = project.getExtensions().create("chordpro", ChordProExtension.class);
        project.getTasks().register("generateChordProPdfs", GenerateChordProPdfsTask.class)
                .configure(task -> {
                    task.setConfig(extension.getConfig());
                    task.setSources(project.fileTree("src"));
                    task.setOutputDir(new File(project.getBuildDir(), "pdfs"));
                });

        // https://docs.gradle.org/current/userguide/base_plugin.html
        project.getPlugins().apply("base");
        project.getTasks().named("assemble").configure(task -> task.dependsOn("generateChordProPdfs"));
    }
}
