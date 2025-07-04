package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveContent {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void save(String content) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                out.write(content.charAt(i));
            }
        }
    }
}
