#!/usr/bin/env bash
sudo kill -9 `sudo lsof -t -i:80`
rm -rf /home/ubuntu/jars