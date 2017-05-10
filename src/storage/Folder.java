package storage;

import com.dropbox.core.DbxEntry;

/**
 * Created by wei-qiang on 29-Apr-17.
 */
public class Folder {
    private DbxEntry file;

    public Folder(DbxEntry file) {
        this.file = file;
    }

    public DbxEntry getFile() {
        return file;
    }

    @Override
    public String toString() {
        if (file.isFile()) {
            return "File: " + file.name;
        }
        return "Folder: " + file.name;
    }
}