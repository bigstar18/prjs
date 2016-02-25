<%@ page import="gnnt.trade.bank.vo.bankdz.sfz.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.sfz.resave.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.sfz.resave.child.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.sfz.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.sfz.sent.child.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.gs.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.hx.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.hx.sent.child.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.jh.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.pf.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.pf.resave.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.xy.sent.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.xy.resave.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.xy.sent.child.*"%>
<%@ page import="gnnt.trade.bank.vo.bankdz.xy.resave.child.*"%>
<%@ page import="java.util.Date"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%!
public boolean getdisableBtn (){
boolean disableBtn = true;//判断现在是否不允许做清算
String compareTime = Tool.getCompareTime();
Calendar sysCalendar = Calendar.getInstance();
Calendar calendar = Calendar.getInstance();
String[] time = compareTime.split(":");
calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
calendar.set(Calendar.SECOND,Integer.parseInt(time[2]));
if(sysCalendar.before(calendar)) {
	disableBtn = true;
} else {
	disableBtn = false;	
}
return disableBtn;
}

%>
<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>