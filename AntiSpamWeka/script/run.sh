#!/bin/bash

BASEDIR=$(pwd)
JAR_PATH="${BASEDIR}/../target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
MAX_HEAP_SIZE=12G
MAX_STACK_SIZE=8m
METADATA="${BASEDIR}/../data-sets/metadata.txt"
METHOD=(A1DE A2DE BFTREE DTNB FURIA HP J48 J48C J48G JRIP MLP MLPCS NBTREE RBF RC RF SGD SPEGASOS SVM)
RUNS=5
SKIP_TRAIN="-skipTrain"
SKIP_TEST="-skipTest"
TEST_EMPTY="-testEmpty"
SAVE_MODEL="-saveModel"

for METHOD in "${METHOD[@]}"; do
    VM_OPTIONS="-Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:-UseConcMarkSweepGC"
    RUN_COMMAND="java ${VM_OPTIONS} -jar ${JAR_PATH} -metadata ${METADATA} -method ${METHOD} -runs ${RUNS} ${TEST_EMPTY}"
    LOG_FILENAME="$(dirname ${JAR_PATH})/${METHOD}.log"
    echo "$(date) - Executing [${RUN_COMMAND}] > [${LOG_FILENAME}]"
    command ${RUN_COMMAND} > ${LOG_FILENAME}
done
