#!/usr/bin/env bash
export DISPLAY=:0.0
echo $DISPLAY
chromium-browser --incognito --kiosk http://localhost:10080/report/index/STORAGE00000001
