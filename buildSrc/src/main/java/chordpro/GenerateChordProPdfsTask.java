package chordpro;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import static org.gradle.internal.FileUtils.removeExtension;

public abstract class GenerateChordProPdfsTask extends DefaultTask {

    @InputFile
    abstract File getConfig();
    abstract void setConfig(File config);

    @TaskAction
    void buildChord() throws Exception {
        for (File choFile : getProject().fileTree("src").getFiles()) {
            File pdfFile = new File(getProject().getBuildDir().getAbsolutePath(), removeExtension(choFile.getName()) + ".pdf");
            getProject().exec(execSpec -> {
                try {
                    execSpec.setStandardOutput(new FileOutputStream(pdfFile));
                    execSpec.setExecutable("chordpro");
                    execSpec.setArgs(Arrays.asList(choFile.getAbsolutePath(), "--config=" + getConfig()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}