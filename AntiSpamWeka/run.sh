#!/bin/bash

# Java Virtual Machine settings
MAX_HEAP_SIZE=12G
MAX_STACK_SIZE=8m

# Anti Spam settings
JAR_PATH="$(pwd)/target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
METADATA="$(pwd)/../../anti-spam-weka-data/2017_MULT10/metadata.txt"
METHOD=(A1DE A2DE BFTREE DTNB FURIA HP J48 J48C J48G JRIP MLP MLPCS NBTREE RBF RC RF SGD SPEGASOS SVM)
RUNS=3
SKIP_TRAIN="-skipTrain"
SKIP_TEST="-skipTest"
SHRINK_FEATURES="-shrinkFeatures"
BALANCE_CLASSES="-balanceClasses"
TEST_EMPTY="-testEmpty"
SAVE_MODEL="-saveModel"

# E-mail settings
SENDER="sender@server.com"
RECIPIENT="recipient@server.com"
SERVER="nail.server.com:port"
OPTIONS="tls=yes"
USERNAME="sender@server.com"
PASSWORD="sender123password"

for METHOD in "${METHOD[@]}"; do
    VM_OPTIONS="-Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:-UseConcMarkSweepGC"
    RUN_COMMAND="java ${VM_OPTIONS} -jar \"${JAR_PATH}\" -metadata ${METADATA} -method ${METHOD} -runs ${RUNS} ${BALANCE_CLASSES} ${TEST_EMPTY} ${SAVE_MODEL}"
    LOG_FILENAME="$(dirname ${JAR_PATH})/${METHOD}.log"
    echo "$(date) - Executing [${RUN_COMMAND}] > [${LOG_FILENAME}] and sending results to [${RECIPIENT}]"
    eval ${RUN_COMMAND} > ${LOG_FILENAME}
    MAIL_COMMAND="cat ${LOG_FILENAME} | sendemail -f ${SENDER} -t ${RECIPIENT} -u \"[ASW] $(date) - $(basename ${LOG_FILENAME})\" -s ${SERVER} -o ${OPTIONS} -xu ${USERNAME} -xp ${PASSWORD}"
    eval ${MAIL_COMMAND} > /dev/null
done

