#!/bin/sh
echo "stopping"
ps -ef | grep dof.jar | grep -v grep | awk '{print $2}' | xargs kill
echo "Ending"
