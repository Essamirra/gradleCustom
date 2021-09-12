package chordpro;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileType;
import org.gradle.api.tasks.*;
import org.gradle.work.ChangeType;
import org.gradle.work.FileChange;
import org.gradle.work.Incremental;
import org.gradle.work.InputChanges;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import static org.gradle.internal.FileUtils.removeExtension;

@CacheableTask
public abstract class GenerateChordProPdfsTask extends DefaultTask {

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract File getConfig();
    abstract void setConfig(File config);

    @InputFiles
    @PathSensitive(PathSensitivity.NAME_ONLY)
    @Incremental
    abstract FileCollection getSources();
    abstract void setSources(FileCollection sources);

    @OutputDirectory
    abstract File getOutputDir();
    abstract void setOutputDir(File outputDir);

    @TaskAction
    void buildChord(InputChanges inputChanges) throws Exception {
        for (FileChange change : inputChanges.getFileChanges(getSources())) {
            if (change.getFileType() != FileType.DIRECTORY) {
                File choFile = change.getFile();
                File pdfFile = new File(getOutputDir(), removeExtension(choFile.getName()) + ".pdf");
                if (change.getChangeType() == ChangeType.REMOVED) {
                    pdfFile.delete();
                } else {
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
    }
}