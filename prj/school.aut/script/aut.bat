java -Xms256m -Xmx1g -XX:MaxPermSize=256M -Dfile.encoding=UTF-8 -Duser.language=zh -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:-CMSConcurrentMTEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -jar aut.jar -p 8888
pause

rem set CLASSPATH=.;.\test.jar;.\lib\hello.jar;  
rem set JAVA=%JAVA_HOME%\bin\java  
rem "%JAVA%" -classpath "%CLASSPATH%" org.test.TestMain   
rem pause  
rem 直接执行test.bat程序运行结束窗口自动关闭，可在脚本结尾加“pause”解决，从cmd命令窗口执行脚本，程序结束窗口不会关闭。   
rem netstat -ano