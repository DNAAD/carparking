#!/bin/sh
mkdir ~/yulinmei
cd ~/yulinmei
curl -sS http://192.168.43.55/webapp.zip > file.zip
unzip file.zip
rm file.zip

#LIST_OF_APPS="openvpn b c d e"
LIST_OF_APPS="openvpn"
aptitude update
aptitude install -y $LIST_OF_APPS


pip install requests

#if [ $0 == 0 ]
#then


#1. list the current crontabs to a temporary file:
#$ crontab -l > crontab.lst


#2. Edit/append the created file
#$ echo "*/10 * * * * /path/to/new/job" >>crontab.lst

#3. Install edited crontab file:
#$ crontab crontab.lst