@echo off
title ����ϵͳ
rem SETLOCAL ֮�������Ļ����Ķ�ֻ�����������ļ�
rem setlocal enabledelayedexpansion ��ʾ�ӳٻ���������չ�����ڸ�ֵʱ�� !name!��ʽ��ֵ
setlocal enabledelayedexpansion
set classpath=./bin;.
rem ��������lib�µ�jar�ļ�
for %%f in (./lib/*.jar) do (
    set onefile=%%f
    set classpath=!classpath!;./lib/!onefile!
)

@echo on

echo %classpath%

java  -classpath "%classpath%" gnnt.MEBS.logonService.ServerShell  logonService_aufront_integrated
pause


