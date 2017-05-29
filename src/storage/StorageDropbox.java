package storage;

import com.dropbox.core.*;
import com.dropbox.core.v1.*;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by wei-qiang on 29-Apr-17.
 */
public class StorageDropbox implements Storageable {
    private DbxClientV1 client;
    private DbxClientV2 client2;
    private String currentDirectory = "/";
    private DbxRequestConfig config;
    private final String APP_KEY = "5f5yop2b2qwidq9";
    private final String APP_SECRET = "6njyd71d4ncif1d";
    private DbxWebAuthNoRedirect webAuth;

    public String getCurrentDirectory() {
        if (currentDirectory != "/" && currentDirectory.contains(".")) {
            return currentDirectory.substring(0, currentDirectory.lastIndexOf("/"));
        }
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public StorageDropbox(){
        config = new DbxRequestConfig("Phub/1.0", Locale.getDefault().toString());

    }

    public StorageDropbox(DbxClientV1 client, DbxClientV2 client2) {
        this.client = client;
        this.client2 = client2;
        config = new DbxRequestConfig("Phub/1.0", Locale.getDefault().toString());
    }

    public void authenticate(){
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        String authorizeUrl = webAuth.start();
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI(authorizeUrl));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void makeClient(String accessToken){
        client = new DbxClientV1(config, accessToken);
        client2 = new DbxClientV2(config, accessToken, new DbxHost("api.dropbox.com", "api-content.dropbox.com", "www.dropbox.com", "api-notify.dropbox.com"));
    }

    public ArrayList<Folder> getFiles(String Path) {
        com.dropbox.core.v1.DbxEntry.WithChildren listing = null;
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

    public void deleteFile(String filePath) throws DbxException, DbxException {
        client2.files().delete(filePath);
    }

    public void moveFile(String originPath, String path){
        try {
            client2.files().move(originPath, path);
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    public void addNewfolder(String name) throws DbxException {
        client2.files().createFolder(currentDirectory.substring(0, currentDirectory.lastIndexOf("/"))+"/"+ name);
    }
}