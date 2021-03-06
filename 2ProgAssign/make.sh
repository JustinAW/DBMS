#!/bin/sh

if [ "$1" = "run" ]; then
    java -cp mysql-connector-java-5.1.29-bin.jar:. Main_JDBC
elif [ "$1" = "clean" ]; then
    echo "cleaning..."
    rm *.class
else
    echo "compiling..."
    javac -bootclasspath /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar -target 8 -source 8 -cp mysql-connector-java-5.1.29-bin.jar:. Main_JDBC.java Helper_JDBC.java View2_GUI.java
fi
