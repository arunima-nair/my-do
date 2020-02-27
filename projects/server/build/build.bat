@echo off

set foldName="server"


svn co http://svnweb.dubaitrade.ae/svn/Dubaitrade/DEV_12c/Delivery_Order/branches/Prod_Bug_Fix/projects/server
       

if "%1%" =="" (
    set AppName="do"
 ) else (
  set AppName=%1
 )
 
 echo %AppName%
 cd %foldName%
 call mvn clean
 call mvn package
 cd target 
 ren *.original %AppName%.war
cd ..\..