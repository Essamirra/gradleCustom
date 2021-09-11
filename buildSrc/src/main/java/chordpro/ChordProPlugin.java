package chordpro;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ChordProPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("Hello from chord pro plugin");

        // https://docs.gradle.org/current/userguide/base_plugin.html
        project.getPlugins().apply("base");

        project.getTasks().register("generateChordProPdfs", GenerateChordProPdfsTask.class);

        project.getTasks().named("assemble").configure(task -> task.dependsOn("generateChordProPdfs"));
    }
}
