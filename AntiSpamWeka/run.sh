#-------------------------------------------------------------------------------
# Copyright (C) 2017 Marcelo Vinícius Cysneiros Aragão
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#-------------------------------------------------------------------------------
#!/bin/bash

# Java Virtual Machine settings
MAX_HEAP_SIZE=30G
MAX_STACK_SIZE=8m

# Anti Spam settings
JAR_PATH="$(pwd)/target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
METADATA="$(pwd)/../../anti-spam-weka-data/2017_BASE2/metadata.txt"
METHODS=(A1DE A2DE BFTREE CART DTNB FURIA FRF HP IBK J48 J48C J48G JRIP LIBLINEAR LIBSVM MLP NB NBTREE RBF RT SGD SMO SPEGASOS WRF)
RUNS=10
SKIP_TRAIN="-SkipTrain"
SKIP_TEST="-SkipTest"
SHRINK_FEATURES="-ShrinkFeatures"
BALANCE_CLASSES="-BalanceClasses"
INCLUDE_EMPTY="-IncludeEmpty"
REMOVE_OUTLIERS="-RemoveOutliers"
SAVE_ARFF="-SaveArff"
SAVE_MODEL="-SaveModel"
SAVE_SETS="-SaveSets"

# E-mail settings
SENDER='sender@server.com'
RECIPIENT='recipient@server.com'
SERVER='mail.server.com:port'
OPTIONS='tls=yes'
USERNAME='sender@server.com'
PASSWORD='sender123password'

# Log settings
LOG_LEVELS=(ERROR WARN INFO DEBUG TRACE)

for METHOD in "${METHODS[@]}"; do

    # run the experiments
    VM_OPTIONS="-Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:+UseG1GC"
    RUN_COMMAND="java ${VM_OPTIONS} -jar \"${JAR_PATH}\" -Metadata ${METADATA} -Methods ${METHOD} -Runs ${RUNS} ${SHRINK_FEATURES} ${BALANCE_CLASSES} ${INCLUDE_EMPTY}"
    LOG_FILENAME="$(dirname ${JAR_PATH})/${METHOD}.log"
    echo "$(date) - Executing [${RUN_COMMAND}] >> [${LOG_FILENAME}] and sending results to [${RECIPIENT}]"
    eval ${RUN_COMMAND} >> ${LOG_FILENAME}
    for LEVEL in "${LOG_LEVELS[@]}"; do
        cat ${LOG_FILENAME} | grep ${LEVEL} >> `echo ${LOG_FILENAME} | sed s/.log/_${LEVEL}.log/`
    done

    # zip the log files
    LOG_FILENAME_ZIP="${LOG_FILENAME}.zip"
    ZIP_COMMAND="zip --junk-paths ${LOG_FILENAME_ZIP} $(dirname ${LOG_FILENAME})/${METHOD}*.log"
    eval ${ZIP_COMMAND}

    # mail the zipped log file
    MAIL_SUBJECT="\"[ASW] $(date) - $(basename ${LOG_FILENAME})\""
    MAIL_BODY="\"$(du -h ${LOG_FILENAME} | cut -f1) $(file ${LOG_FILENAME})\""
    MAIL_COMMAND="sendemail -f ${SENDER} -t ${RECIPIENT} -u ${MAIL_SUBJECT} -m ${MAIL_BODY} -a ${LOG_FILENAME_ZIP} -s ${SERVER} -o ${OPTIONS} -xu ${USERNAME} -xp ${PASSWORD}"
    eval ${MAIL_COMMAND}

done
