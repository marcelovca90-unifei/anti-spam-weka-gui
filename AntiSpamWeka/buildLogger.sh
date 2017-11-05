#!/bin/bash

METHODS=(LIBSVM_POLY_G_neg_C_small LIBSVM_POLY_G_zero_C_small LIBSVM_POLY_G_pos_C_small LIBSVM_POLY_G_neg_C_med LIBSVM_POLY_G_zero_C_med LIBSVM_POLY_G_pos_C_med LIBSVM_POLY_G_neg_C_high LIBSVM_POLY_G_zero_C_high LIBSVM_POLY_G_pos_C_high LIBSVM_RBF_G_neg_C_small LIBSVM_RBF_G_zero_C_small LIBSVM_RBF_G_pos_C_small LIBSVM_RBF_G_neg_C_med LIBSVM_RBF_G_zero_C_med LIBSVM_RBF_G_pos_C_med LIBSVM_RBF_G_neg_C_high LIBSVM_RBF_G_zero_C_high LIBSVM_RBF_G_pos_C_high LIBSVM_SIG_G_neg_C_small LIBSVM_SIG_G_zero_C_small LIBSVM_SIG_G_pos_C_small LIBSVM_SIG_G_neg_C_med LIBSVM_SIG_G_zero_C_med LIBSVM_SIG_G_pos_C_med LIBSVM_SIG_G_neg_C_high LIBSVM_SIG_G_zero_C_high LIBSVM_SIG_G_pos_C_high)

echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"

echo "<Configuration status=\"WARN\">"
echo "    <Appenders>"
echo "        <Console name=\"TraceConsole\" target=\"SYSTEM_OUT\"> <PatternLayout pattern=\"%d %p %m%n\" /> </Console>"
echo "        <File name=\"TraceFile\" fileName=\"logs/trace.log\"> <PatternLayout pattern=\"%d %p %m%n\" /> </File>"

for METHOD in "${METHODS[@]}"
do
    echo "        <File name=\"$METHOD\" filename=\"logs/$METHOD.log\"> <PatternLayout pattern=\"%d %p %m%n\" /> </File>"
done

echo "    </Appenders>"
echo "    <Loggers>"
echo "        <Root level=\"trace\"> <appender-ref ref=\"TraceConsole\" /> <appender-ref ref=\"TraceFile\" /> </Root>"

for METHOD in "${METHODS[@]}"
do
    echo "        <logger name=\"$METHOD\" level=\"info\"> <appender-ref ref=\"$METHOD\" /> </logger>"
done

echo "    </Loggers>"
echo "</Configuration>"
