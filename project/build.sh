#!/bin/bash
cd src/main/java
for f in `find . -name "*.java"`; do
  javac -source 1.7 -target 1.7 $f
done