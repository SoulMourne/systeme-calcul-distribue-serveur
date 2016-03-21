#!/bin/bash

yum install -y java-1.8.0-openjdk-devel.x86_64;
eject;
echo 3 > t;
update-alternatives --config java < t;
rm -f t;
java -jar client.jar;
echo "client installé et exécuté avec succès !" > /home/etude/Documents/calculDistribue/resultat.txt;
