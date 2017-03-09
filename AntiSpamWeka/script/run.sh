#!/bin/bash

BASEDIR=$(pwd)
JAR_PATH="${BASEDIR}/../target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
MAX_HEAP_SIZE=12G
MAX_STACK_SIZE=8m
DATA_SET_FOLDER=${1}
NUMBER_OF_REPETITIONS=${2}
CSV_METHODS=(A1DE A2DE BFTREE DTNB FURIA HP J48 J48C J48G JRIP MLP MLPCS NBTREE RBF RC RF SGD SPEGASOS SVM)

if [ "${#}" -ne 2 ]; then
    echo "Illegal number of parameters. Usage: ./run.sh \"DATA_SET_FOLDER\" NUMBER_OF_REPETITIONS"
else
    for METHOD in "${CSV_METHODS[@]}"; do
        echo "$(date) - Started [$(basename ${JAR_PATH})] with [-Xmx${MAX_HEAP_SIZE}] and [-Xss${MAX_STACK_SIZE}] for [${METHOD}] with [${NUMBER_OF_REPETITIONS}] repetitions..."
        java -Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:-UseConcMarkSweepGC -jar ${JAR_PATH} ${DATA_SET_FOLDER} ${METHOD} ${NUMBER_OF_REPETITIONS} > $(dirname ${JAR_PATH})/${METHOD}.log
    done
fi
