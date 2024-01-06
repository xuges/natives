//linux: gcc -shared -fPIC -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -o libexample.so example.c
//windows: cl.exe /LD /I%JAVA_HOME%/include /I%JAVA_HOME%/include/win32 example.c

#include <stdio.h>
#include "jni.h"

JNIEXPORT void JNICALL Java_org_example_Example_print(JNIEnv *env, jobject obj) {
    printf("org.example: hello world!\n");
}