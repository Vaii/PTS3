package storage;

import com.dropbox.core.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by wei-qiang on 29-Apr-17.
 */
public class StorageDropbox implements Storageable {
    private DbxClient client;
    public String currentDirectory = "/";

    public StorageDropbox(DbxClient client) {
        this.client = client;
    }

    public ArrayList<Folder> getFiles(String Path) {
        DbxEntry.WithChildren listing = null;
        try {
            listing = client.getMetadataWithChildren(Path);
        } catch (DbxException e) {
            e.printStackTrace();
        }

        ArrayList files = new ArrayList<DbxEntry>();
        for (DbxEntry child : listing.children) {
            files.add(new Folder(child));

            System.out.println("	" + child.name + ": " + child.toString());
        }
        return files;
    }

    public void uploadFile(File inputFile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            DbxEntry.File uploadedFile = client.uploadFile(currentDirectory + "/" + inputFile.getName(), DbxWriteMode.add(), inputFile.length(), inputStream);
            getFiles(currentDirectory);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadFile(String fileOutputPath, String filePath) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileOutputPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            DbxEntry.File downloadedFile = client.getFile(filePath, null, outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}