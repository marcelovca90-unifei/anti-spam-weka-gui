#!/bin/sh

GIGABYTE="8G"

DATETIME=`date +%Y-%m-%d_%H-%M-%S`
echo "$DATETIME Starting BPROP ..."
java -Xmx$GIGABYTE -jar bprop.jar > bprop_$DATETIME.log

DATETIME=`date +%Y-%m-%d_%H-%M-%S`
echo "$DATETIME Starting RPROP ..."
java -Xmx$GIGABYTE -jar rprop.jar > rprop_$DATETIME.log

DATETIME=`date +%Y-%m-%d_%H-%M-%S`
echo "$DATETIME Starting NEAT ..."
java -Xmx$GIGABYTE -jar neat.jar > neat_$DATETIME.log

DATETIME=`date +%Y-%m-%d_%H-%M-%S`
echo "$DATETIME Starting SVM ..."
java -Xmx$GIGABYTE -jar svm.jar > svm_$DATETIME.log

DATETIME=`date +%Y-%m-%d_%H-%M-%S`
echo "$DATETIME Starting RBF ..."
java -Xmx$GIGABYTE -jar rbf.jar > rbf_$DATETIME.log
