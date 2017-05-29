package storage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wei-qiang on 29-Apr-17.
 */
public interface Storageable {
    void uploadFile(File inputFile);
    void downloadFile(String fileOutputPath, String filePath);
    ArrayList<Folder> getFiles(String Path);
    void makeClient(String accessToken);
}