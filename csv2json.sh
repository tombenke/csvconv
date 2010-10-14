#!/bin/bash

# Set the path to the install dir of csvconv utility
declare -x csvconv_home=`dirname $0`
echo $csvconv_home

# Skip the first two "header" lines
tail $1.csv --lines=+2 > tmp.csv

 # Convert csv file to xml or json
java -jar $csvconv_home/csvconv.jar -i tmp.csv -o $1.json -p $1.properties -f json
rm tmp.csv


