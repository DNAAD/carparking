#!/bin/sh
mkdir ~/yulinmei
cd ~/yulinmei
curl -sS http://192.168.43.55/webapp.zip > file.zip
unzip file.zip
rm file.zip
