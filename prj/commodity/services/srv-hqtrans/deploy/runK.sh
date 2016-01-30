:

CLASSPATH=$CLASSPATH:\
./lib:\
./lib/commons-logging.jar:\
./lib/MEBS_HQTrans.jar:\
./lib/config.jar:\
./lib/nls_charset12.jar:\
./lib/ojdbc14.jar

echo ÕýÔÚÆô¶¯......
echo $CLASSPATH
nohup java -Xms256m -Xmx512m -classpath $CLASSPATH gnnt.mebsv.hqtrans.ServerStart KLine 00 >> hqtransK.log & tail -f hqtransK.log
