./stop.sh
cp=./bin
for file in ./lib/*.jar
do
   cp=$cp:$file
done
nohup java -Djava.rmi.server.hostname=172.18.11.30 -Xms256m -Xmx512m -classpath $cp -Djava.security.policy=ServerShell.policy gnnt.mebsv.hqservice.ServerStart gnnt.mebsv.hqservice.service.server.ReceiverThread2 port=20063 poollength=100 processMarketName=HQServiceServerStart >>./logs/sys.log & 
