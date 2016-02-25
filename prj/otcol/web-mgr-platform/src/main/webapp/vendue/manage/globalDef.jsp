<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.math.*"%>
<%@ page import="gnnt.MEBS.vendue.manage.right.CheckRight"%>
<%@ page import="gnnt.MEBS.vendue.manage.tranpass.TranPass"%>
<%@ page import="gnnt.MEBS.vendue.manage.querybean.LinkOracle"%>
<%@ page import="gnnt.MEBS.vendue.manage.servlet.AddRight"%>
<%@ page import="gnnt.MEBS.vendue.manage.bean.DBBean"%>
<%@ page import="gnnt.MEBS.vendue.manage.log.OptLog"%>
<%@ page import="gnnt.MEBS.vendue.manage.util.ManaUtil"%>
<%@ page import="gnnt.MEBS.vendue.manage.util.CheckBean"%>
<%@ page import="gnnt.MEBS.vendue.manage.util.KeycodeBean"%>
<%@ page import="gnnt.MEBS.vendue.manage.util.FindData"%>
<%@ page import="gnnt.MEBS.vendue.manage.report.*"%>
<%@ page import="gnnt.MEBS.vendue.server.DeliveryAction"%>
<%@ page import="gnnt.MEBS.vendue.server.DeliveryActionImpl"%>
<%@ page import="gnnt.MEBS.vendue.server.rmi.*"%>
<%@ page import="gnnt.MEBS.vendue.server.vo.*"%>
<%@ page import="gnnt.MEBS.vendue.server.dao.*"%>
<%@ page import="gnnt.MEBS.vendue.server.*"%>
<%@ page import="gnnt.MEBS.vendue.util.*"%>
<%@ page import="gnnt.MEBS.vendue.kernel.ActiveUserManager"%>
<%@ page import="gnnt.MEBS.common.security.AclCtrl"%>
<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page import="java.sql.Connection"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.MEBS.member.ActiveUser.LogonManager"%>
<%@ taglib prefix="db" tagdir="/WEB-INF/tags/db" %>
<%@ taglib prefix="fun" tagdir="/WEB-INF/tags/fun" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String comPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); %>

<IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/vendue/manage/jslib/calendar.htc">
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
    .PageNext{page-break-after: always;}<!--��ӡ��ҳ-->
    
</style>

<%@ include file="../../common/public/session.jsp" %>

<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/tools.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/frameCtrl.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/formInit.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/print.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/common.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/order.js"></script>
<!-- <script language="javascript" src="<%=comPath%>/timebargain/scripts/global.js"></script> -->
<!--ȫѡ����ѡ������ -->
<!--<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script> -->

<%
//����ҳ���ǿ��ˢ��
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%> 

<!--���÷�ҳ��С-->
<c:set var="PAGESIZE" value="20"/>
<!--���ñ���-->
<c:set var="TRADERUSER" value="v_tradeuser"/>
<c:set var="TRADERUSEREXT" value="v_tradeuserext"/>
<c:set var="SYSUSER" value="v_sysuser"/>
<!--��Ʒ����-->
<c:set var="CATEGORY" value="1"/>
<!--ҵ�����-->
<c:set var="OPERATION" value="506"/>
<!--web����-->
<c:set var="CONTEXT" value="<%=request.getContextPath()%>"/>
<!--ʱ����ʾģʽ-->
<c:set var="TIMEPATTERN" value="yyyy/MM/dd HH:mm:ss"/>
<!--�����ʾ��ʽ-->
<c:set var="FUNDPATTER" value="####0.00"/>
<!--ʡ�ݶ�Ӧ�ֵ�����-->
<c:set var="PROVINCETYPE" value="2"/>
<!-- ������ʾ�벻��ʾ�������ɺ�ͬ��ϳ���ͬ��ť-->
<c:set var="CONVIEWNOT" value="1"/>
<!--��������ȫ���г����-->
<c:set var="ALLPARTITION" value="0"/>
<!--������keyĬ����-->
<c:set var="DEFKEYCODE" value="0123456789ABCDE"/>
<!--��Ա������Ϣ���¼�¼״̬��־��Ӧֵ-->
<c:set var="NEWPARTUSER" value="1"/>
<!--����ϵͳ�û������ĸ��г�,���н��װ������ƽ��װ�鶼��ʾ��Ӧmarketpartition��marketid��־ֵ-->
<c:set var="ALLVIEW" value="0"/>
<!--����������(���ƽ��װ��,���н��װ��)��Ӧ�İ��id-->
<c:set var="QUOTATION" value="10"/>
<c:set var="CONCENTRATION" value="11"/>
<c:set var="EXPORTERROR" value="���ڽ�����,���ܵ���excel,��ȵ��������Ժ��ٵ�!"/>
<%
HashMap PROESTADATE=new HashMap();
PROESTADATE.put(new Integer(36),"2006-12-13");//����
PROESTADATE.put(new Integer(42),"2007-01-09");//����
PROESTADATE.put(new Integer(43),"2007-01-09");//����
PROESTADATE.put(new Integer(32),"2007-01-05");//����
PROESTADATE.put(new Integer(37),"2006-12-28");//ɽ��
PROESTADATE.put(new Integer(51),"2010-01-01");//�Ĵ�



DataSource dataSource=TradeDAOFactory.getDAO().getDataSource();
Map ipMap=LogonManager.getRMIConfig("4", dataSource);
//�µ�ϵͳ������ip��˿�(����Զ���µ�ϵͳ�ӿ�)
String host=(String)ipMap.get("host");
int port=(Integer)ipMap.get("port");

%>
<%!
public final String partitionID = "4";
//��������
public final String TITLE = "���۽���ϵͳ";//ϵͳ����
public final String ESTADATE="2006-12-20";//�ܳɽ�����ʡ�������������
public final int HEBEI=13;
public final int ANHUI=34;
public final String[] HEBEIA={"","37"};
public final String[] ANHUIA={"36,42,43,51","32,42"};
public final String JNDI="mgr";//����Դ
public final int SESSION_INTERVAL = 30000;//�û���¼session��Чʱ�� ��λ����
public final String FUNDPATTERN = "####0.00";//�ʽ���ʾ��ʽ����
public final String FUNDPATTERNEXT = "####0.0000";//�ʽ���ʾ��ʽ����
public final String AMOUNTPATTERN = "####0";//������ʾ��ʽ����
public final String DATEPATTERN = "yyyy/MM/dd";//������ʾ��ʽ����
public final String DATEPATTERN1 = "yyyy/MM/dd HH:mm:ss";//������ʾ��ʽ����
//public final String COLS_USER = "id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3";  //��Ա���ֶ��б�  xieying
public final String COLS_USER = "t2.password,t1.id,t1.usercode,t1.userflag, t1.name, t1.address,t1.postid,t1.managername,t1.manageremail,t1.managerid,t1.mgtele,t1.mgfax,t1.mgmobile,t1.tradername,t1.traderemail,t1.traderid,t1.tdtele,t1.tdfax,t1.tdmobile,t1.enterpriseid,t1.validperiod,t1.poster,t1.bank,t1.account,t1.userlevel,t1.createtime,t1.modifytime,t1.str1,t1.str2,t1.str3";  //��Ա���ֶ��б�
public final String COLS_USER_O = "id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3";
public final String COLS_TRADERUSER = "userCode, password, status, overdraft, feecut,balance,frozencapital,category1,category2,tradecount,totalsecurity,feecutfee";//�����û���
public final String COLS_SYSUSER="sysuser,remark,password,accessnum,createtime";//ϵͳ�û�
public final String COLS_COMMODITY="id,code,firsttime,createTime,status,splitid,category,beginprice,stepprice,amount,tradeunit,alertprice,b_security,b_fee,s_security,s_fee,minamount";//��Ʒ��
public final String COLS_CURCOMMODITY="tradepartition,code,commodityid,section,lpflag,bargainflag,modifytime";//��ǰ������Ʒ��
public final String COLS_HISCOMMODITY="tradedate,tradepartition,code,commodityid,section,lpflag,bargainflag,modifytime";//��ʷ������Ʒ��
public final String COLS_FLOWCONTROL="tradepartition,unitid,unittype,startmode,starttime,durativetime";//�������̿��Ʊ�
public final String COLS_SYSPARTITION="partitionid,engineclass,quotationclass,submitactionclass,validflag,description,trademode";//ϵͳ������ñ�
public final String COLS_BARGAIN="tradepartition,submitid,code,contractid,price,amount,userid,tradedate,section,contractcontent";//�ɽ���
public final String COLS_HISBARGAIN="tradepartition,code,contractid,price,amount,userid,tradedate,section,commodityid,status,b_bail,b_poundage,b_lastBail,s_bail,s_poundage,s_lastBail,lastamount,contractcontent,trademode";//��ʷ�ɽ���
public final String COLS_CURSUBMIT="tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime";//��ǰί�б�
public final String COLS_HISSUBMIT="tradedate,tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime";//��ʷί�б�
public final String COLS_BROADCAST="id,title,author,content,type,sendtime,endtime,createtime,updatetime";//�㲥��Ϣ��
public final String COLS_DICTABLE="id,type,name,value";//��Ŀ�ֵ��
public final String COLS_ACCESSRIGHT="id,parentid,marknum,description,num";//ϵͳȨ���ֵ��
public final String COLS_SYSPROPERTY="tradepartition,durativetime,spacetime,countdownstart,countdowntime,optmode";//ϵͳ���Կ��Ʊ�
public final String COLS_USERPART="id,userid,operuser,market,operdate,tradername,traderemail,traderid,tdtele,tdfax,tdmobile"; //��Ա������Ϣ�޸ļ�¼��
public final String COLS_MARKETALLCALL="id,marketid,name,account,province,city,bank";//�г�ȫ�Ʊ�
public final String COLS_ACCEPTPAYMENT="id,contractid,name,account,province,city,bank,status";//�տ�����Ϣ��
//���ֶ���Ϣ
public final String COLS_PARTUSER = "t1.id, t1.usercode, t1.userflag, t1.name, t1.address,t1.postid,t1.managername,t1.manageremail,t1.managerid,t1.mgtele,t1.mgfax,t1.mgmobile,t1.enterpriseid,t1.validperiod,t1.poster,t1.bank,t1.account,t1.userlevel,t1.createtime,t1.modifytime,t1.str1,t1.str2,t1.str3";  //��Ա�����ֶ��б�(��������������Ϣ)
public final String COLS_OPERPARTUSER="t2.tradername,t2.traderemail,t2.traderid,t2.tdtele,t2.tdfax,t2.tdmobile";
public final String COLS_LOGINDEX="logindex,description,type";//��־�ֵ��
public final String COLS_USERLOG="id,tradepartition,userid,ip,logtime,description";//��������־��

//��Ȩ�޹��ܿ��Ʋ������� ��Ӧ��id��
//ϵͳ�û�
public final int SYSUSERMANAGE=24;//ϵͳ�û�����
public final int SYSUSERVIEW=25;//ϵͳ�û����
public final int SYSUSERLOG=27;//�û�������־
public final int ONLINESYSUSER=29;//����ϵͳ�û�

//��Ա�û�  //------------�ϳ�
public final int MANAGER=1;//��Ա�û�����


//�����̹���
public final int TRADERMANAGE=3;//�����̹���
public final int MANAGERVIEW=2;//�����̹���Ȩ
public final int TRADERVIEW=4;//�����̹���Ȩ  //-------------�ϳ�
public final int CURTRADERVIEW=28;//���߽��������
public final int AUTOPROPASS=38;//������ȷ�ϵ���ӡ
public final int TRADERLOG=35;//��ѯ�����̲�����־ //-----------�ϳ�

//��Ʒ������
public final int COMMODITYTRADE=5;//��Ʒ�����׹���
public final int CURCOMMODITYVIEW=6;//��ǰ������Ʒ���
public final int COMMODITYVIEW=8;//������Ʒ���
public final int COMMODITYUSER=9;//��Ʒ����Ȩ
public final int TRADEFLOWVIEW=7;//��ǰ�����������
public final int HISTRADECOMM=10;//������Ʒ��ʷ��¼

//������Ϣ
public final int TRADEINFOMANAGE=11;//������Ϣ����
public final int CURBARGAINQUERY=12;//��ǰ�ɽ���ѯ
public final int CURSUBMITQUERY=13;//��ǰ������ѯ
public final int HISTORYBARGAINQUERY=14;//��ʷ�ɽ���ѯ
public final int HISSUBMITQUERY=31;//��ʷ������ѯ

//��ͬ
public final int CONTRACTMANA=15;//��ͬ����
public final int CURCONPRINT=16;//��ǰ��ͬ��ӡ
public final int HISCONTRACT=17;//��ͬ���ٹ���
public final int ADDGOODS=18;//��ӳ����¼
public final int RETURNFILE=30;//�鵵��ͬ��ѯ
public final int EXAMRECORD=32;//���ⵥ���
public final int CONTRACTSTATUS=33;//��ͬ¼�����
public final int CONTRACTEXAM=36;//��ͬ��˲���
public final int CONTRACTASSURANCE=37;//��ͬȷ������
public final int RETURNCONTRACT=34;//ʣ���ʽ�ת����

//ϵͳ����
public final int SYSTEMMANAGE=19;//ϵͳ����
public final int SYSPARTITIONVIEW=23;//ϵͳ�������
public final int SYSPROPERTY=20;//ϵͳ���Կ���
public final int SYSTRADEMANA=22;//���׹���
public final int SYSPROCONTROL=17;//ϵͳ���Կ���
public final int BROADCASTVIEW=21;//�㲥��Ϣ����
public final int COMMODITYPROPERTY=39;//��Ʒ���Թ���

//�������
public final int REPORT=26;//�������  //------------�ϳ�

//���� 1:���� 2:���� 3:�б�   �������µİ�飬����ѹ���Ӱ���
public final String USERPAR="1,2,3,";

/*
 *������־�ֵ���Ӧ����
 */

//��ӽ����������ʽ�
public final int OVERDRAFT=1;//��������ʽ�

//������״̬����
public final int INSTATRADER=2;//�ָ�������
public final int FORBIDTRADER=2;//��ֹ������
public final int DELTRADER=2;//ɾ��������

//ά����������
public final int ADDFLOW=3;//��ӽ�������
public final int MODFLOW=3;//�޸Ľ�������
public final int DELFLOW=3;//ɾ����������
//ϵͳ�û���¼
public final int LOGIN=4;//ϵͳ�û���¼
public final int LOGOUT=4;//ϵͳ�û��˳�

//��ǰ������Ʒ����
public final int ADDSECTION=5;//ָ�����׽�
public final int INSERTSECTION=5;//���뽻�׽�
public final int DELSECTION=5;//ɾ�����׽�
public final int PROSECTION=5;//�Զ����佻�׽�
public final int CURCOMM=5;//��ǰ��Ʒ����

//��ǰ����ʷ�ɽ��г����ɽ�����
public final int CURWITHDRAW=6;//��ǰ�ɽ��г����ɽ�
public final int HISWITHDRAW=6;//��ʷ�ɽ��г����ɽ�

//ϵͳ�й�����Ĭ�������޸�
public final int SYSCONTROL=7;//Ĭ�������޸�

//�㲥��Ϣ����
public final int BROADCASTADD=8;//�㲥��Ϣ���
public final int BROADCASTMOD=8;//�㲥��Ϣ�޸�
public final int BROADCASTDEL=8;//�㲥��Ϣɾ��

//ϵͳ�û�����
public final int LOGSYSUSERADD=9;//���ϵͳ�û�
public final int LOGSYSUSERDEL=9;//ɾ��ϵͳ�û�
public final int LOGADDRIGHT=9;//ϵͳ�û�Ȩ������
public final int LOGESTAPASS=9;//ϵͳ�û���������

//������Ʒ�б����
public final int LOGCOMMDEL=10;//������Ʒ�б�
public final int LOGCOMMEXP=10;//������Ʒ�б���Ʒ�������

//�����������޸�
public final int MODPWDUSERCODE=11;//�����������޸�

//���׹������
public final int STARTSYS=12;//����ϵͳ
public final int MOVESYS=12;//�ֶ�����
public final int PAUSETRADE=12;//��ͣ����
public final int INSTATRADE=12;//�ָ�����
public final int SHUTSYS=12;//����
public final int COMPULSORYSHUTSYS=12;//ǿ�ƿ���
public final int BALANCEACC=12;//����

//��ͬ���ٹ������
public final int HISCONTRACTOPER=13;

//���ƽ����̴������λ
public final int CODECOUNT=6;

//���ⵥλ��
public final int OUTRECORDCOUNT=6;

//�б���Ʒ������
public final int BIDDINGCOMMODITY=5; //�б���Ʒ
public final int BIDDINGCOMMODITYVIEW=8;//������Ʒ���

//���ֵ���������г���Ϣ���Ͷ�Ӧֵ
public final int MARKETTYPE=4;

//������������볤��
public final int PASSLEN=8;

//��������ȫ�������
public final int ALLPARTITION=0;

//��־����������dictable��Ӧ����
public final int LOGTYPE=3;

//ͳ�������û���������
public final int SPACE=30; //��ʱɨ��ļ��ʱ��
public final int EXPIRETIME=15; //��ʱʱ��
public final int MODE=1; //�û���¼ģʽ

//Ĭ�Ͻ��ײ���
public final String DEFAULTSECURITY="55"; //Ĭ�ϱ�֤��
public final String DEFAULTFEE="0.0008"; //Ĭ��������

//��ӡ���ƾ֤�ƶ���ֵ(��λ:����)
public final double tranPayX=0.00d;
public final double tranPayY=0.00d;

//�����µ�ϵͳip
public final String INNERIP="172.16.2.212:22207";

//�����µ�ϵͳip
public final String OUTERIP="218.249.27.123:22207";

//�Ƚ�ip
public final String COMPAREIP="127.0.%.%";

//���߽������ж�����������ip
public final String FIXIP1="172.16.1.81";
public final String FIXIP2="172.16.2.212";






//rmi������
public final String REMOTECLASS="KernelEngineRMI";
//����ϵͳ�û�
//public final String ACTIVEHOST="172.16.2.212";
//public final String ACTIVEPORT="3129";
//������
public final String ACTIVEUSERCLASS="ActiveUserService";

//������������
public final String DELIVERYCLASS="gnnt.MEBS.vendue.server.DeliveryActionImpl";


//����ʾ
public final String DW = "��";

//����ʾ
public final String DWS = "��";

//��������
public void errOpt() //�쳣������
{
	
}

public void alert(String s,javax.servlet.jsp.JspWriter out){	
	try{
	    out.println("<script>alert('"+ s +"');</script>");
	}catch(Exception e){
		System.out.println("function alert() exception : " + e.toString());
	}
}

public String delNull(String s){
	if(s == null||"null".equals(s)){ s = "";}
	return s;
}

//(�����ҳ��)�ж��ַ����Ƿ�Ϊ��,���Ϊ�����&nbsp;
public String replaceNull(String s){
  if(s==null||"".equals(s)) {return "&nbsp;";}
  else{ return s;}
}

//(�����ҳ��)�ж�double�Ƿ����0,���Ϊ�����0.00;
public String replaceDZero(double d){
  if(d>0){ return String.valueOf(d);}
  else{ return "0.00";}
}

//(�����ҳ��)�ж�Long�Ƿ����0,���Ϊ�����0.00;
public String replaceLongZero(long d){
  if(d>0){ return String.valueOf(d);}
  else{ return "0";}
}

//(�����ҳ��)(�жϳ���ƻ�����)�ж��ַ����Ƿ�Ϊ��,���Ϊ�����0;
public String replaceLNull(String s){
  if(s==null||"".equals(s)){ return "0";}
  else{ return s;}
}

public String disDouble(double f)
{
	String result = "0.00";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.00");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disInt(double f)
{
	String result = "0";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}


//���ش�ʱ���������
public String disDate(Timestamp ts)
{
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(ts);
	}
	catch(Exception e)
	{
	}	
	return "";

}

//��ʱ��תҳ
public void sendRedirect(String url,javax.servlet.jsp.JspWriter out){	
	try{
	    if(url != null && !url.trim().equals("")){
			out.println("<script>parent.window.location='"+ url +"'</script>");
		}
	}catch(Exception e){
		System.out.println(e.toString());
	}
}

//�쳣ʱ���쳣��Ϣд����־(���jstl�쳣����)
public void log(HttpServletRequest request,String mess){
    try{
	    Throwable t=new Throwable(mess);
		t.printStackTrace();
		request.getSession().getServletContext().log(t.getMessage(),t);
	}catch(Exception e){
	    e.printStackTrace();
	}
}

//���������Ϣ��ʾ
public void hintError(javax.servlet.jsp.JspWriter out){
	try{
        out.print("<SCRIPT LANGUAGE='JavaScript'>");
        out.print("alert('�����쳣,����ʧ�ܣ�')");
        out.print("</SCRIPT>");
	}catch(Exception e){
	    e.printStackTrace();
	}
}


public Timestamp getSysTime() //ȡ��ϵͳʱ��
{
	Timestamp ts = new Timestamp(System.currentTimeMillis());
	return ts;
	
}
public int toInt(String s){
	int re = 0;
	try{
        re = Integer.parseInt(s);
	}catch(Exception e){
		re = -10000;
	}
	return re;
}

public float toFloat(String s){
	float re = 0;
	try{
        re = Float.parseFloat(s);
	}catch(Exception e){
		//re = -10000;
	}
	return re;
}

public double toDouble(String s){
	double re = 0;
	try{
        re = Double.parseDouble(s);
	}catch(Exception e){
		//re = -10000;
	}
	return re;
}

public long toLong(String s){
	long re = 0;
	try{
        re = Long.parseLong(s);
	}catch(Exception e){
		//re = -10000;
	}
	return re;
}
%>