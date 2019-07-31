#!/bin/bash

# run with sudo for first time

if [ -n "$1" ]
then
    file=$1
else  
    echo "filename required, e.g. map2.sc"
    exit 0
fi  

echo "--------------- map2 excercises -----------------"

# Test if ammonite exists, if not download it
command -v amm >/dev/null 2>&1 || \
    (echo "Downloading ammonite..." && \
    curl -L https://github.com/lihaoyi/Ammonite/releases/download/1.6.9/2.13-1.6.9 > /usr/local/bin/amm && \ 
    chmod +x /usr/local/bin/amm)

echo "-------------------------------------------------"
echo "Fill in the blanks "
# run ammonite shell with startup script
amm --watch ./scala-exercises/$file

exit 1