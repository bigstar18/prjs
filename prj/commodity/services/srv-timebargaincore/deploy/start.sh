./stop.sh
cp=./bin
for file in ./lib/*.jar
do
   cp=$cp:$file
done
nohup java -Djava.rmi.server.hostname=172.18.11.30 -Xms1024m -Xmx4000m -classpath $cp -Djava.security.policy=ServerShell.policy gnnt.MEBS.timebargain.server.ServerShell timebargaincoreServerShell >>./logs/sys.log & 
