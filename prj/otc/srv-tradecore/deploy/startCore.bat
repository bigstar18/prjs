@echo off

setlocal enabledelayedexpansion
set classpath=.;./bin
for %%f in (./lib/*.jar) do (
    set onefile=%%f
    set classpath=!classpath!;./lib/!onefile!
)

@echo on

echo %classpath%

java  -classpath "%classpath%" gnnt.MEBS.timebargain.server.ServerShell
pause


