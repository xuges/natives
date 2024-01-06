# Natives

**Java原生工具包.**



## 背景

*如何得知当前运行的平台？*

*如何直接从 JAR包里读取动态库而不用关心动态库放哪？*

Natives库可以做到！



## 用法

导入Natives类库：

```java
import com.github.xuges.natives.*;
```



检查运行系统、架构，即平台：

```java
System.out.println("current os: " + OS.CURRENT);
System.out.println("current arch: " + Arch.CURRENT);
System.out.println("current platform: " + Platform.CURRENT);

if (OS.CURRENT.isUnixLike())
    System.out.println("unix-like");
else if (OS.CURRENT == OS.Windows)
    System.out.println("windows");
```



从 JAR包读取动态库：

```java
try {
    //windows x64会读取 !jar/org/example/libs/windows_x64/example.dll
    //linux x64会读取 !jar/org/example/libs/linux_aarch64/libexample.so
    //MacOS x64会读取 !jar/org/example/libs/macos_x64/libexample.dylib
    //提示：读取路径为：org/example/libs/Platform.CURRENT
	LibraryLoader.loadFromJar("org/example/libs", "example");
} catch (UnsatisfiedLinkError e) {
	System.out.println("load library error: " + e);
}
```



将动态库打包进 JAR包：

```shell
#linux
$> ./natives-add example.jar org/example/libs linux_x64 libtest.so

#windows
$> natives-add.bat example.jar org/example/libs windows_x64 test.dll
```



## 特点

*为什么要使用这个库？*

- **多平台支持：** 支持Windows Linux MacOSX Android BSD,  x86 x64 aarch64 mips ppc loongarch64 等等。
- **简单易用：** 接口简洁，与 Java标准库用法类同，具备配套工具脚本。
- **生产可用：** 自动清理临时文件，经过生产环境验证。



## 许可

MIT License

Copyright(C) 2023 xuges@qq.com