import os
import shutil
import subprocess


text_file = open("new.txt", "w+")

s = """[Unit]
Description=Keep Alive Service
After=network-online.target
Wants=network-online.target

Description=ROT13 demo service
After=network.target
StartLimitIntervalSec=0
[Service]
Type=simple
Restart=always
RestartSec=1
User=centos
User=pi
ExecStart=/usr/bin/env php /path/to/server.php

[Install]
WantedBy=multi-user.target"""

text_file.write(s)


# Move a file by renaming it's path
os.rename('/Users/billy/d1/xfile.txt', '/Users/billy/d2/xfile.txt')

# Move a file from the directory d1 to d2
shutil.move('/Users/billy/d1/xfile.txt', '/Users/billy/d2/xfile.txt')

# Delete xfile.txt
os.remove('/Users/billy/d2/xfile.txt')



elif action == "shutdown":
print "Attempting to shut down"
return_code = subprocess.call(['sudo', 'shutdown', '-h', 'now'])
elif action == "startstream":
return_code = subprocess.call(['sudo', 'service', 'motion', 'start'])
elif action == "stopstream":
return_code = subprocess.call(['sudo', 'service', 'motion', 'stop'])
