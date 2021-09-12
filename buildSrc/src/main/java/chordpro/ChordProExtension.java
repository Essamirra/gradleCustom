package chordpro;

import java.io.File;

public abstract class ChordProExtension {
    abstract File getConfig();
    abstract void setConfig(File config);
}
