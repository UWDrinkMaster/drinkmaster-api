#!/usr/bin/env bash
sudo kill -9 `sudo lsof -t -i:8498`
rm -rf /home/ubuntu/jars