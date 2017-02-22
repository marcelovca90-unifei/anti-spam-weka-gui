#!/bin/bash

BASEDIR=$(pwd)
JAR_PATH="${BASEDIR}/target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
MAX_MEMORY_IN_GB=12
DATA_SET_FOLDER=${1}
NUMBER_OF_REPETITIONS=${2}
CSV_METHODS=(A1DE A2DE BFTREE DTNB FURIA HP J48 JRIP MLP MLPCS NBTREE RBF RF SGD SPEGASOS SVM)

if [ "${#}" -ne 2 ]; then
    echo "Illegal number of parameters. Usage: ./run.sh \"DATA_SET_FOLDER\" NUMBER_OF_REPETITIONS"
else
    for METHOD in "${CSV_METHODS[@]}"; do
        echo "$(date) - Started [$(basename ${JAR_PATH})] for [${METHOD}] with [${NUMBER_OF_REPETITIONS}] repetitions..."
        java -Xmx${MAX_MEMORY_IN_GB}G -jar ${JAR_PATH} ${DATA_SET_FOLDER} ${METHOD} ${NUMBER_OF_REPETITIONS} > $(dirname ${JAR_PATH})/${METHOD}.log
    done
fi
