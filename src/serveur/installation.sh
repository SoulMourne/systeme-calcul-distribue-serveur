#!/bin/bash
# 1 ip dst
# 2 chemin de l'exécutable sur pc src

sshpass -p "#TpLinux#" ssh -o StrictHostKeyChecking=no root@$1 '
mkdir /home/etude/Documents/calculDistribue;
exit;'
sshpass -p "#TpLinux#" scp $2 root@$1:/home/etude/Documents/calculDistribue
sshpass -p "#TpLinux#" ssh -o StrictHostKeyChecking=no root@$1 '
bash /home/etude/Documents/calculDistribue/client.sh
exit;'
