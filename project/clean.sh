#!/bin/bash
cd src/main/java
for f in `find . -name "*.class"`; do
  rm -f $f
done