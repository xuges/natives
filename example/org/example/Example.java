package org.example;

import com.github.xuges.natives.LibraryLoader;

public class Example {
    private static native void print();
    
    public static void main(String[] args) {
        LibraryLoader.loadFromJar("org/example/libs", "example");
        print();
    }
}
