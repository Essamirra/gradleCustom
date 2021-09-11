package chordpro;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;


public abstract class GenerateChordProPdfsTask extends DefaultTask {
    @TaskAction
    void buildChord() throws Exception {
        for (File choFile:getProject().fileTree("src").getFiles()) {
            File pdfFile = new File(getProject().getBuildDir().getAbsolutePath() +"/"+  FileUtils.removeExtension(choFile.getName()) + ".pdf");
            getProject().exec(execSpec -> {
                try {
                    execSpec.setStandardOutput(new FileOutputStream(pdfFile));
                    execSpec.setExecutable("chordpro");
                    execSpec.setArgs(Arrays.asList(choFile.getAbsolutePath(), "--config=./config/chordpro.json"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}