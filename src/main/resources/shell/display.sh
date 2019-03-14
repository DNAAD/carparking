#!/usr/bin/env bash

You need the display number. To find that:

> xauth list
localhost.localdomain/unix:1  MIT-MAGIC-COOKIE-1  ea5bf
It's the number after the colon. Then set:

export DISPLAY=:1
Notice I included the colon. You should now be able to launch xclock or

firefox &
You have to be logged in as the same user who opened the display, or else run that root/sudo, which in the case of a web browser is a bad idea.