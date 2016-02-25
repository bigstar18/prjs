#### settings - begin #########
export ORACLE_BASE=/opt/ora10
export ORACLE_HOME=$ORACLE_BASE/product/10.2
export LANG=zh_CN.GB18030
export PATH=$ORACLE_HOME/bin:$ORACLE_HOME/Apache/bin:$PATH
export ORACLE_SID=gnnt
export ORACLE_OWNER=oracle
export ORACLE_TERM=vt100
export LD_LIBRARY_PATH=$ORACLE_HOME/lib:$ORACLE_HOME/lib32:$LD_LIBRARY_PATH
export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK

TNSNAME=DbHelper
FILEDATE=`date '+%Y%m%d%H%M'`

BACKUP_PATH="/users/mgr/DbBackup"

## 设置要备份的数据库用户名称
NAMELIST="trade_gnnt hquser_gnnt"

#### settings - end #########

if [ ! -d $BACKUP_PATH ]
then
  mkdir -p $BACKUP_PATH
fi

if ! which exp > /dev/null
then
  echo "-----------------------------------------------------------------------------"
  which exp
  echo "-----------------------------------------------------------------------------"
  echo " * ERROR: Please check variable ORACLE_BASE and ORACLE_HOME "
  exit 0
fi

if ! tnsping $TNSNAME > /dev/null
then
  echo "-----------------------------------------------------------------------------"
  tnsping $TNSNAME
  echo "-----------------------------------------------------------------------------"
  echo " * ERROR: Please check variable TNSNAME or check $ORACLE_HOME/network/admin/tnsnames.ora "
  exit 0
fi

#备份每个数据库用户
FILELIST=""
for USERNAME in $NAMELIST
do
  FILENAME=${USERNAME}-${FILEDATE}.dmp
  cmd="exp ${USERNAME}/password@${TNSNAME} file=${BACKUP_PATH}/${FILENAME} STATISTICS=NONE "
  FILELIST="$FILELIST $FILENAME"
  echo "-----------------------------------------------------------------------------"
  echo $cmd
  echo "----"
  `$cmd`
  echo "-----------------------------------------------------------------------------"
done

#压缩存储，删除dmp文件
cd ${BACKUP_PATH}
tar zcvf db-${FILEDATE}.tgz $FILELIST
if [ -e db-${FILEDATE}.tgz ]
then
  rm $FILELIST
fi