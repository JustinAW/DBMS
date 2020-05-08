#!/bin/sh

if [ "$1" = "run" ]; then
    java -cp mysql-connector-java-8.0.19.jar:. Main_JDBC
elif [ "$1" = "clean" ]; then
    echo "cleaning..."
    rm *.class
else
    echo "compiling..."
    javac -cp mysql-connector-java-8.0.19.jar:. Main_JDBC.java Helper_JDBC.java View2_GUI.java
fi
