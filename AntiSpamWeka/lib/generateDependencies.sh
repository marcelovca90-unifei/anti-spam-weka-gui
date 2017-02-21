#!/bin/bash

for f in *.jar; do
    filename=`echo $f | cut -d '.' -f 1`
    echo "<dependency>"
    echo "    <groupId>$filename</groupId>"
    echo "    <artifactId>$filename</artifactId>"
    echo "    <scope>system</scope>"
    echo "    <version>1.0</version>"
    echo '    <systemPath>${basedir}/lib/'$f'</systemPath>'
    echo "</dependency>"
done
