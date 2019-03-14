#!/bin/sh                                                                                                   

SESSIONNAME="script"
tmux has-session -t $SESSIONNAME 2> /dev/null

if [ $? -ne 0 ] 
then

    echo "No Session found.  Creating and configuring."
    tmux new-session -s $SESSIONNAME -n script -d
    tmux split-window -h 'sudo java -jar -Dconfiguration.delay.time=120 carparking.jar'
    tmux split-window -h 'python3 /home/pi/webapp/app.py'

else
    echo "Session found"
fi

#tvvmux attach -t $SESSIONNAME
