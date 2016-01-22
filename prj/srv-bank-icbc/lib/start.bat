@echo off 
title 银行接口-适配器-工行监管

echo 本系统为工商银行银商通接口市场端适配器系统

java -Djava.rmi.server.hostname=172.18.100.22 -jar ICBCBank.jar

pause & exit 
