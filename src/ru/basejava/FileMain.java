package ru.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileMain {
    public static void main(String[] args) {
        FileMain fileMain = new FileMain();
        File file = new File(".\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src");
        System.out.println(dir.isDirectory());
       for (String name : dir.list()) {
           System.out.println(name);
       }
        try (FileInputStream fis = new FileInputStream(file)){
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileMain.recursiveTraversal(dir);
    }

    public void recursiveTraversal(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                recursiveTraversal(subFile);
            }
        } else {
            System.out.println(file.getParent() + " + " + file.getName());
        }
    }
}
