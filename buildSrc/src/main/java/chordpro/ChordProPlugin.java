package chordpro;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ChordProPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("Hello from chord pro plugin");
    }
}
