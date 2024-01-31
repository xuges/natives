package com.github.xuges.natives;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Objects;

public class LibraryLoader {
    //load library from jar
    public static void loadFromJar(String path, String baseName) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(baseName);

        if (baseName.isEmpty())
            throw new UnsatisfiedLinkError("natives: empty library name");

        if (Platform.CURRENT.ordinal() == Platform.UNSUPPORTED.ordinal()) {
            String msg = String.format("natives: unsupported platform: os.name=%s os.arch=%s java.vendor=%s",
                    System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("java.vendor"));
            throw new UnsatisfiedLinkError(msg);
        }

        String fullName = getLibraryFullName(baseName);
        String jarLibraryFile = getJarLibraryFilePath(path, fullName);
        Path tmpLibraryPath = makeTempFile(fullName);
        saveJarFile(jarLibraryFile, tmpLibraryPath.toString());
        try {
            System.load(tmpLibraryPath.toString());
        } catch (Throwable e) {
            throw new UnsatisfiedLinkError(String.format("natives: load '%s' occurs exception: %s", jarLibraryFile, e));
        } finally {
            deleteTempFile(tmpLibraryPath);
        }
    }

    private static void saveJarFile(String path, String target) {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (stream == null)
                throw new UnsatisfiedLinkError(String.format("natives: 'jar://%s' not found", path));
            Files.copy(stream, Paths.get(target));
        } catch (IOException e) {
            throw new UnsatisfiedLinkError(String.format("natives: save '%s' to '%s' occurs exception: %s", path, target, e));
        }
    }

    private static final Path libsTmpDir = Paths.get(System.getProperty("java.io.tmpdir"), "com.github.xuges.natives.libs");

    private static Path makeTempFile(String name) {
        try {
            Files.createDirectories(libsTmpDir);
            return Paths.get(libsTmpDir.toString(), makeRandomFileName(name + "-", ".temp"));
        } catch (Throwable e) {
            throw new UnsatisfiedLinkError(String.format("natives: make '%s' temp file occurs exception: %s", name, e));
        }
    }

    private static void deleteTempFile(Path path) {
        try {
            if (OS.CURRENT.isUnixLike() || OS.CURRENT.ordinal() == OS.MACOS.ordinal()) {
                //unix-like system can delete used file
                Files.delete(path);
                return;
            }

            //windows can not delete used file, but can rename it
            Files.move(path, Paths.get(libsTmpDir.toString(), makeRandomFileName(".delete-", ".temp")));

            //find and delete
            File[] files = libsTmpDir.toFile().listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().startsWith(".delete-")) {
                        try {
                            files[i].delete();
                        } catch (Throwable e) {
                            //eat exceptions
                        }
                    }
                }
            }

        } catch (Throwable e) {
            //eat exceptions
        }
    }

    private static final SecureRandom random = new SecureRandom();

    private static String makeRandomFileName(String prefix, String suffix) {
        long n = random.nextLong();
        n = (n == Long.MIN_VALUE) ? 0 : Math.abs(n);
        return prefix + n + suffix;
    }

    //get jar library file path
    private static String getJarLibraryFilePath(String path, String file) {
        return Paths.get(path, Platform.CURRENT.toString(), file).toString().replace("\\","/");
    }

    //get full library name
    //baeName.dll in Windows
    //libbaseName.so in unix-like
    //libbaseName.dylib in MacOSX
    private static String getLibraryFullName(String baseName) {
        if (OS.CURRENT.isUnixLike())
            return "lib" + baseName + ".so";

        if (OS.CURRENT.ordinal() == OS.WINDOWS.ordinal())
            return baseName + ".dll";

        if (OS.CURRENT.ordinal() == OS.MACOS.ordinal())
            return "lib" + baseName + ".dylib";

        throw new UnsatisfiedLinkError(String.format("natives: unsupported operation system: %s", OS.CURRENT));
    }
}
