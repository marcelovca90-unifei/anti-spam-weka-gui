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

TOTAL_RAM_B=$(wmic ComputerSystem get TotalPhysicalMemory)
TOTAL_RAM_KB=$((${TOTAL_RAM_B}/1024))
TOTAL_RAM_MB=$((${TOTAL_RAM_KB}/1024))
TOTAL_RAM_GB=$((${TOTAL_RAM_MB}/1024))

HEAP_SIZE="${TOTAL_RAM_GB}G"
STACK_SIZE="${TOTAL_RAM_GB}m"
GC_TYPE="+UseG1GC"
JAR_PATH="./target/AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar"

java -Xmx${HEAP_SIZE} -Xss${STACK_SIZE} -XX:${GC_TYPE} -jar ${JAR_PATH}
