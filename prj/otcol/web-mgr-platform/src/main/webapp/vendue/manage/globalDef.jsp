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
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
    .PageNext{page-break-after: always;}<!--打印分页-->
    
</style>

<%@ include file="../../common/public/session.jsp" %>

<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/tools.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/frameCtrl.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/formInit.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/print.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/common.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/vendue/manage/jslib/order.js"></script>
<!-- <script language="javascript" src="<%=comPath%>/timebargain/scripts/global.js"></script> -->
<!--全选不能选的问题 -->
<!--<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script> -->

<%
//设置页面的强制刷新
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%> 

<!--设置分页大小-->
<c:set var="PAGESIZE" value="20"/>
<!--设置表名-->
<c:set var="TRADERUSER" value="v_tradeuser"/>
<c:set var="TRADERUSEREXT" value="v_tradeuserext"/>
<c:set var="SYSUSER" value="v_sysuser"/>
<!--商品分类-->
<c:set var="CATEGORY" value="1"/>
<!--业务代码-->
<c:set var="OPERATION" value="506"/>
<!--web名称-->
<c:set var="CONTEXT" value="<%=request.getContextPath()%>"/>
<!--时间显示模式-->
<c:set var="TIMEPATTERN" value="yyyy/MM/dd HH:mm:ss"/>
<!--金额显示格式-->
<c:set var="FUNDPATTER" value="####0.00"/>
<!--省份对应字典类型-->
<c:set var="PROVINCETYPE" value="2"/>
<!-- 控制显示与不显示重新生成合同与废除合同按钮-->
<c:set var="CONVIEWNOT" value="1"/>
<!--控制所属全部市场标号-->
<c:set var="ALLPARTITION" value="0"/>
<!--交易商key默认码-->
<c:set var="DEFKEYCODE" value="0123456789ABCDE"/>
<!--会员部份信息最新记录状态标志对应值-->
<c:set var="NEWPARTUSER" value="1"/>
<!--不管系统用户属于哪个市场,集中交易板块与挂牌交易板块都显示对应marketpartition表marketid标志值-->
<c:set var="ALLVIEW" value="0"/>
<!--两个特殊板块(挂牌交易板块,集中交易板块)对应的板块id-->
<c:set var="QUOTATION" value="10"/>
<c:set var="CONCENTRATION" value="11"/>
<c:set var="EXPORTERROR" value="正在交易中,不能导出excel,请等到交易完以后再导!"/>
<%
HashMap PROESTADATE=new HashMap();
PROESTADATE.put(new Integer(36),"2006-12-13");//江西
PROESTADATE.put(new Integer(42),"2007-01-09");//湖北
PROESTADATE.put(new Integer(43),"2007-01-09");//湖南
PROESTADATE.put(new Integer(32),"2007-01-05");//江苏
PROESTADATE.put(new Integer(37),"2006-12-28");//山东
PROESTADATE.put(new Integer(51),"2010-01-01");//四川



DataSource dataSource=TradeDAOFactory.getDAO().getDataSource();
Map ipMap=LogonManager.getRMIConfig("4", dataSource);
//下单系统服务器ip与端口(调用远程下单系统接口)
String host=(String)ipMap.get("host");
int port=(Integer)ipMap.get("port");

%>
<%!
public final String partitionID = "4";
//常量定义
public final String TITLE = "竞价交易系统";//系统标题
public final String ESTADATE="2006-12-20";//总成交汇总省份设分配置日期
public final int HEBEI=13;
public final int ANHUI=34;
public final String[] HEBEIA={"","37"};
public final String[] ANHUIA={"36,42,43,51","32,42"};
public final String JNDI="mgr";//数据源
public final int SESSION_INTERVAL = 30000;//用户登录session有效时间 单位：秒
public final String FUNDPATTERN = "####0.00";//资金显示格式控制
public final String FUNDPATTERNEXT = "####0.0000";//资金显示格式控制
public final String AMOUNTPATTERN = "####0";//数据显示格式控制
public final String DATEPATTERN = "yyyy/MM/dd";//日期显示格式控制
public final String DATEPATTERN1 = "yyyy/MM/dd HH:mm:ss";//日期显示格式控制
//public final String COLS_USER = "id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3";  //会员表字段列表  xieying
public final String COLS_USER = "t2.password,t1.id,t1.usercode,t1.userflag, t1.name, t1.address,t1.postid,t1.managername,t1.manageremail,t1.managerid,t1.mgtele,t1.mgfax,t1.mgmobile,t1.tradername,t1.traderemail,t1.traderid,t1.tdtele,t1.tdfax,t1.tdmobile,t1.enterpriseid,t1.validperiod,t1.poster,t1.bank,t1.account,t1.userlevel,t1.createtime,t1.modifytime,t1.str1,t1.str2,t1.str3";  //会员表字段列表
public final String COLS_USER_O = "id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3";
public final String COLS_TRADERUSER = "userCode, password, status, overdraft, feecut,balance,frozencapital,category1,category2,tradecount,totalsecurity,feecutfee";//交易用户表
public final String COLS_SYSUSER="sysuser,remark,password,accessnum,createtime";//系统用户
public final String COLS_COMMODITY="id,code,firsttime,createTime,status,splitid,category,beginprice,stepprice,amount,tradeunit,alertprice,b_security,b_fee,s_security,s_fee,minamount";//商品表
public final String COLS_CURCOMMODITY="tradepartition,code,commodityid,section,lpflag,bargainflag,modifytime";//当前交易商品表
public final String COLS_HISCOMMODITY="tradedate,tradepartition,code,commodityid,section,lpflag,bargainflag,modifytime";//历史交易商品表
public final String COLS_FLOWCONTROL="tradepartition,unitid,unittype,startmode,starttime,durativetime";//交易流程控制表
public final String COLS_SYSPARTITION="partitionid,engineclass,quotationclass,submitactionclass,validflag,description,trademode";//系统板块配置表
public final String COLS_BARGAIN="tradepartition,submitid,code,contractid,price,amount,userid,tradedate,section,contractcontent";//成交表
public final String COLS_HISBARGAIN="tradepartition,code,contractid,price,amount,userid,tradedate,section,commodityid,status,b_bail,b_poundage,b_lastBail,s_bail,s_poundage,s_lastBail,lastamount,contractcontent,trademode";//历史成交表
public final String COLS_CURSUBMIT="tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime";//当前委托表
public final String COLS_HISSUBMIT="tradedate,tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime";//历史委托表
public final String COLS_BROADCAST="id,title,author,content,type,sendtime,endtime,createtime,updatetime";//广播消息表
public final String COLS_DICTABLE="id,type,name,value";//项目字典表
public final String COLS_ACCESSRIGHT="id,parentid,marknum,description,num";//系统权限字典表
public final String COLS_SYSPROPERTY="tradepartition,durativetime,spacetime,countdownstart,countdowntime,optmode";//系统属性控制表
public final String COLS_USERPART="id,userid,operuser,market,operdate,tradername,traderemail,traderid,tdtele,tdfax,tdmobile"; //会员部分信息修改记录表
public final String COLS_MARKETALLCALL="id,marketid,name,account,province,city,bank";//市场全称表
public final String COLS_ACCEPTPAYMENT="id,contractid,name,account,province,city,bank,status";//收款人信息表
//表字段信息
public final String COLS_PARTUSER = "t1.id, t1.usercode, t1.userflag, t1.name, t1.address,t1.postid,t1.managername,t1.manageremail,t1.managerid,t1.mgtele,t1.mgfax,t1.mgmobile,t1.enterpriseid,t1.validperiod,t1.poster,t1.bank,t1.account,t1.userlevel,t1.createtime,t1.modifytime,t1.str1,t1.str2,t1.str3";  //会员表部份字段列表(不包含交易商信息)
public final String COLS_OPERPARTUSER="t2.tradername,t2.traderemail,t2.traderid,t2.tdtele,t2.tdfax,t2.tdmobile";
public final String COLS_LOGINDEX="logindex,description,type";//日志字典表
public final String COLS_USERLOG="id,tradepartition,userid,ip,logtime,description";//交易商日志表

//各权限功能控制参数定义 对应的id号
//系统用户
public final int SYSUSERMANAGE=24;//系统用户管理
public final int SYSUSERVIEW=25;//系统用户浏览
public final int SYSUSERLOG=27;//用户操作日志
public final int ONLINESYSUSER=29;//在线系统用户

//会员用户  //------------废除
public final int MANAGER=1;//会员用户管理


//交易商管理
public final int TRADERMANAGE=3;//交易商管理
public final int MANAGERVIEW=2;//交易商管理权
public final int TRADERVIEW=4;//交易商管理权  //-------------废除
public final int CURTRADERVIEW=28;//在线交易商浏览
public final int AUTOPROPASS=38;//交易商确认单打印
public final int TRADERLOG=35;//查询交易商操作日志 //-----------废除

//商品及交易
public final int COMMODITYTRADE=5;//商品及交易管理
public final int CURCOMMODITYVIEW=6;//当前交易商品浏览
public final int COMMODITYVIEW=8;//所有商品浏览
public final int COMMODITYUSER=9;//商品操作权
public final int TRADEFLOWVIEW=7;//当前交易流程浏览
public final int HISTRADECOMM=10;//交易商品历史记录

//交易信息
public final int TRADEINFOMANAGE=11;//交易信息管理
public final int CURBARGAINQUERY=12;//当前成交查询
public final int CURSUBMITQUERY=13;//当前报单查询
public final int HISTORYBARGAINQUERY=14;//历史成交查询
public final int HISSUBMITQUERY=31;//历史报单查询

//合同
public final int CONTRACTMANA=15;//合同管理
public final int CURCONPRINT=16;//当前合同打印
public final int HISCONTRACT=17;//合同跟踪管理
public final int ADDGOODS=18;//添加出库记录
public final int RETURNFILE=30;//归档合同查询
public final int EXAMRECORD=32;//出库单审核
public final int CONTRACTSTATUS=33;//合同录入操作
public final int CONTRACTEXAM=36;//合同审核操作
public final int CONTRACTASSURANCE=37;//合同确定操作
public final int RETURNCONTRACT=34;//剩余资金转货款

//系统管理
public final int SYSTEMMANAGE=19;//系统管理
public final int SYSPARTITIONVIEW=23;//系统板块配置
public final int SYSPROPERTY=20;//系统属性控制
public final int SYSTRADEMANA=22;//交易管理
public final int SYSPROCONTROL=17;//系统属性控制
public final int BROADCASTVIEW=21;//广播消息管理
public final int COMMODITYPROPERTY=39;//商品属性管理

//报表管理
public final int REPORT=26;//报表管理  //------------废除

//板块号 1:竞买 2:竞卖 3:招标   如增加新的板块，则需压增加板块号
public final String USERPAR="1,2,3,";

/*
 *操作日志字典表对应类型
 */

//添加交易商虚拟资金
public final int OVERDRAFT=1;//添加虚拟资金

//交易商状态操作
public final int INSTATRADER=2;//恢复交易商
public final int FORBIDTRADER=2;//禁止交易商
public final int DELTRADER=2;//删除交易商

//维护交易流程
public final int ADDFLOW=3;//添加交易流程
public final int MODFLOW=3;//修改交易流程
public final int DELFLOW=3;//删除交易流程
//系统用户登录
public final int LOGIN=4;//系统用户登录
public final int LOGOUT=4;//系统用户退出

//当前交易商品操作
public final int ADDSECTION=5;//指定交易节
public final int INSERTSECTION=5;//插入交易节
public final int DELSECTION=5;//删除交易节
public final int PROSECTION=5;//自动分配交易节
public final int CURCOMM=5;//当前商品操作

//当前与历史成交中撤消成交操作
public final int CURWITHDRAW=6;//当前成交中撤消成交
public final int HISWITHDRAW=6;//历史成交中撤消成交

//系统中管理中默认属性修改
public final int SYSCONTROL=7;//默认属性修改

//广播消息操作
public final int BROADCASTADD=8;//广播消息添加
public final int BROADCASTMOD=8;//广播消息修改
public final int BROADCASTDEL=8;//广播消息删除

//系统用户操作
public final int LOGSYSUSERADD=9;//添加系统用户
public final int LOGSYSUSERDEL=9;//删除系统用户
public final int LOGADDRIGHT=9;//系统用户权限设置
public final int LOGESTAPASS=9;//系统用户密码设置

//所有商品列表操作
public final int LOGCOMMDEL=10;//所有商品列表
public final int LOGCOMMEXP=10;//所有商品列表商品导入操作

//交易商密码修改
public final int MODPWDUSERCODE=11;//交易商密码修改

//交易管理操作
public final int STARTSYS=12;//启动系统
public final int MOVESYS=12;//手动开市
public final int PAUSETRADE=12;//暂停交易
public final int INSTATRADE=12;//恢复交易
public final int SHUTSYS=12;//闭市
public final int COMPULSORYSHUTSYS=12;//强制开市
public final int BALANCEACC=12;//结算

//合同跟踪管理操作
public final int HISCONTRACTOPER=13;

//控制交易商代码多少位
public final int CODECOUNT=6;

//出库单位数
public final int OUTRECORDCOUNT=6;

//招标商品及交易
public final int BIDDINGCOMMODITY=5; //招标商品
public final int BIDDINGCOMMODITYVIEW=8;//所有商品浏览

//在字典表中配置市场信息类型对应值
public final int MARKETTYPE=4;

//产生随机数密码长度
public final int PASSLEN=8;

//控制所属全部板块标号
public final int ALLPARTITION=0;

//日志操作类型在dictable对应数字
public final int LOGTYPE=3;

//统计在线用户初化参数
public final int SPACE=30; //超时扫描的间隔时间
public final int EXPIRETIME=15; //超时时间
public final int MODE=1; //用户登录模式

//默认交易参数
public final String DEFAULTSECURITY="55"; //默认保证金
public final String DEFAULTFEE="0.0008"; //默认手续费

//打印电汇凭证移动点值(单位:毫米)
public final double tranPayX=0.00d;
public final double tranPayY=0.00d;

//内网下单系统ip
public final String INNERIP="172.16.2.212:22207";

//外网下单系统ip
public final String OUTERIP="218.249.27.123:22207";

//比较ip
public final String COMPAREIP="127.0.%.%";

//在线交易商判断是外网访问ip
public final String FIXIP1="172.16.1.81";
public final String FIXIP2="172.16.2.212";






//rmi类名称
public final String REMOTECLASS="KernelEngineRMI";
//在线系统用户
//public final String ACTIVEHOST="172.16.2.212";
//public final String ACTIVEPORT="3129";
//类名称
public final String ACTIVEUSERCLASS="ActiveUserService";

//交收类名配置
public final String DELIVERYCLASS="gnnt.MEBS.vendue.server.DeliveryActionImpl";


//批显示
public final String DW = "批";

//吨显示
public final String DWS = "吨";

//函数定义
public void errOpt() //异常处理函数
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

//(在浏览页中)判断字符串是否为空,如果为空输出&nbsp;
public String replaceNull(String s){
  if(s==null||"".equals(s)) {return "&nbsp;";}
  else{ return s;}
}

//(在浏览页中)判断double是否大于0,如果为空输出0.00;
public String replaceDZero(double d){
  if(d>0){ return String.valueOf(d);}
  else{ return "0.00";}
}

//(在浏览页中)判断Long是否大于0,如果为空输出0.00;
public String replaceLongZero(long d){
  if(d>0){ return String.valueOf(d);}
  else{ return "0";}
}

//(在浏览页中)(判断出库计划数量)判断字符串是否为空,如果为空输出0;
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


//返回带时分秒的日期
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

//超时跳转页
public void sendRedirect(String url,javax.servlet.jsp.JspWriter out){	
	try{
	    if(url != null && !url.trim().equals("")){
			out.println("<script>parent.window.location='"+ url +"'</script>");
		}
	}catch(Exception e){
		System.out.println(e.toString());
	}
}

//异常时将异常信息写入日志(针对jstl异常处理)
public void log(HttpServletRequest request,String mess){
    try{
	    Throwable t=new Throwable(mess);
		t.printStackTrace();
		request.getSession().getServletContext().log(t.getMessage(),t);
	}catch(Exception e){
	    e.printStackTrace();
	}
}

//错误操作信息提示
public void hintError(javax.servlet.jsp.JspWriter out){
	try{
        out.print("<SCRIPT LANGUAGE='JavaScript'>");
        out.print("alert('操作异常,操作失败！')");
        out.print("</SCRIPT>");
	}catch(Exception e){
	    e.printStackTrace();
	}
}


public Timestamp getSysTime() //取得系统时间
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