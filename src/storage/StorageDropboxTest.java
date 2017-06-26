package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by wei-qiang on 31-May-17.
 */
class StorageDropboxTest {
    @org.junit.jupiter.api.Test
    void getCurrentDirectory() {
        StorageDropbox storage = new StorageDropbox();
        assertEquals("/", storage.getCurrentDirectory());
    }

    @org.junit.jupiter.api.Test
    void setCurrentDirectory() {
        StorageDropbox storage = new StorageDropbox();
        assertEquals("/", storage.getCurrentDirectory());
        storage.setCurrentDirectory("/test");
        assertEquals("/test", storage.getCurrentDirectory());
    }
}