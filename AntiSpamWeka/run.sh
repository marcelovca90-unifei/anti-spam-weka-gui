#!/bin/bash

# Java Virtual Machine settings
MAX_HEAP_SIZE=14G
MAX_STACK_SIZE=8m

# Anti Spam settings
JAR_PATH="$(pwd)/target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
METADATA="$(pwd)/../../anti-spam-weka-data/2017_MULT10/metadata.txt"
METHOD=(A1DE A2DE BFTREE DTNB FURIA HP J48 J48C J48G JRIP MLP MLPCS NB NBTREE RBF RF RT SGD SMO SPEGASOS SVM)
RUNS=10
SKIP_TRAIN="-SkipTrain"
SKIP_TEST="-SkipTest"
SHRINK_FEATURES="-ShrinkFeatures"
BALANCE_CLASSES="-BalanceClasses"
TEST_EMPTY="-TestEmpty"
SAVE_MODEL="-SaveModel"
SAVE_MODEL="-SaveSets"

# E-mail settings
SENDER="sender@server.com"
RECIPIENT="recipient@server.com"
SERVER="mail.server.com:port"
OPTIONS="tls=yes"
USERNAME="sender@server.com"
PASSWORD="sender123password"

for METHOD in "${METHOD[@]}"; do

    # run the experiments
    VM_OPTIONS="-Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:-UseConcMarkSweepGC"
    RUN_COMMAND="java ${VM_OPTIONS} -jar \"${JAR_PATH}\" -metadata ${METADATA} -method ${METHOD} -runs ${RUNS} ${BALANCE_CLASSES} ${TEST_EMPTY}"
    LOG_FILENAME="$(dirname ${JAR_PATH})/${METHOD}.log"
    echo "$(date) - Executing [${RUN_COMMAND}] >> [${LOG_FILENAME}] and sending results to [${RECIPIENT}]"
    eval ${RUN_COMMAND} >> ${LOG_FILENAME}

    # zip the log file
    LOG_FILENAME_ZIP="${LOG_FILENAME}.zip"
    ZIP_COMMAND="zip --junk-paths ${LOG_FILENAME_ZIP} ${LOG_FILENAME}"
    eval ${ZIP_COMMAND}

    # mail the zipped log file
    MAIL_SUBJECT="\"[ASW] $(date) - $(basename ${LOG_FILENAME})\""
    MAIL_BODY="\"$(du -h ${LOG_FILENAME} | cut -f1) $(file ${LOG_FILENAME})\""
    MAIL_COMMAND="sendemail -f ${SENDER} -t ${RECIPIENT} -u ${MAIL_SUBJECT} -m ${MAIL_BODY} -a ${LOG_FILENAME_ZIP} -s ${SERVER} -o ${OPTIONS} -xu ${USERNAME} -xp ${PASSWORD}"
    eval ${MAIL_COMMAND}

done
