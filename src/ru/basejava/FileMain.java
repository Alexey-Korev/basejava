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
        fileMain.recursiveTraversal(dir, 1);
    }


    public static void recursiveTraversal(File dir, int indent) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    printIndent(indent);
                    System.out.println("File: " + file.getName());
                } else if (file.isDirectory()) {
                    printIndent(indent);
                    System.out.println("Directory: " + file.getName());
                    recursiveTraversal(file, indent + 1);
                }
            }
        }
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }
    }
}

