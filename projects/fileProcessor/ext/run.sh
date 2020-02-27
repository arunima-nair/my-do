#!/bin/sh
/u01/jdk/jdk1.7.0_79/bin/java -jar -Dspring.profiles.active=env -Dspring.config.location=/appconfig/dev/apps/do/fprocessext/ /appconfig/dev/apps/do/fprocessext/dof.jar
