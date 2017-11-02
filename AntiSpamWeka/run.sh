#!/bin/bash

TOTAL_RAM_B=$(sysctl hw.memsize | cut -d ' ' -f 2)
TOTAL_RAM_KB=$((${TOTAL_RAM_B}/1024))
TOTAL_RAM_MB=$((${TOTAL_RAM_KB}/1024))
TOTAL_RAM_GB=$((${TOTAL_RAM_MB}/1024))

HEAP_SIZE="${TOTAL_RAM_GB}G"
STACK_SIZE="${TOTAL_RAM_GB}m"
GC_TYPE="+UseG1GC"
JAR_PATH="./target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"

java -Xmx${HEAP_SIZE} -Xss${STACK_SIZE} -XX:${GC_TYPE} -jar ${JAR_PATH}
