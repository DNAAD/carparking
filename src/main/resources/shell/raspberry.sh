#!/usr/bin/env bash


cat /proc/cpuinfo
vcgencmd measure_temp

cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq

vcgencmd measure_clock arm

for src in arm core h264 isp v3d uart pwm emmc pixel vec hdmi dpi ; do \
echo -e "$src:\t$(vcgencmd measure_clock $src)" ; \
done

vcgencmd measure_volts core

for id in core sdram_c sdram_i sdram_p ; do \
echo -e "$id:\t$(vcgencmd measure_volts $id)" ; \
done


free -h
cat /etc/debian_version
cat /etc/os-release

uname -a


cat /proc/meminfo


cat /proc/device-tree/model


cat /proc/cpuinfo | grep Serial | cut -d ' ' -f 2