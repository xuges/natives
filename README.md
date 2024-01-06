# Natives

**Java native utilities.**

[中文](doc/README_cn.md)



## Overview

*How to know what platform are running?*

*How to load shared library form JAR resource?*

The Natives library can help you!



## Usage

Import Natives classes:

```java
import com.github.xuges.natives.*;
```



Check os, arch and platform:

```java
System.out.println("current os: " + OS.CURRENT);
System.out.println("current arch: " + Arch.CURRENT);
System.out.println("current platform: " + Platform.CURRENT);

if (OS.CURRENT.isUnixLike())
    System.out.println("unix-like");
else if (OS.CURRENT == OS.Windows)
    System.out.println("windows");
```



Load native shared library from JAR:

```java
try {
    //will be:
    //load !jar/org/example/libs/windows_x64/example.dll on Windows x64
    //load !jar/org/example/libs/linux_aarch64/libexample.so on Linux aarch64
    //load !jar/org/example/libs/macos_x64/libexample.dylib on MacOSX x64
    //tips: load path format: org/example/libs/Platform.CURRENT
	LibraryLoader.loadFromJar("org/example/libs", "example");
} catch (UnsatisfiedLinkError e) {
	System.out.println("load library error: " + e);
}
```



Store native shared library to JAR:

```shell
#linux
$> ./natives-add example.jar org/example/libs linux_x64 libtest.so

#windows
$> natives-add example.jar org/example/libs windows_x64 test.dll
```



## Why

*Why use this library?*

- **Multi-Platform:** Windows Linux MacOSX Android BSD,  x86 x64 aarch64 mips ppc loongarch64 and more.
- **Easy-To-Use:** Simple API (like System.loadLibrary) and tool scripts.
- **Production-Ready:** Automatic clean temporary file and production validated.



## License

MIT License

Copyright(C) 2023 xuges@qq.com