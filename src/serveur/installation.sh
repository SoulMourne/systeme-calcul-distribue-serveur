#!/bin/bash
# 1 ip dst
# 2 chemin de l'exécutable sur pc src

sshpass -p "#TpLinux#" ssh -o StrictHostKeyChecking=no root@$1 '
mkdir /home/etude/Documents/calculDistribue;
exit;'
sshpass -p "#TpLinux#" scp $2 root@$1:/home/etude/Documents/calculDistribue
sshpass -p "#TpLinux#" ssh -o StrictHostKeyChecking=no root@$1 '
yum install -y java-1.8.0-openjdk-devel.x86_64;
eject;
echo 3 > t;
update-alternatives --config java < t;
rm -f t;
java -jar client.jar;
echo "client installé et exécuté avec succès !" > /home/etude/Documents/calculDistribue/resultat.txt;
exit;'
echo "done"
