<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ include file="/timebargain/common/meta.jsp" %>
<html>
<head>
<%
  String date = (String)request.getAttribute("date");
  String result=(String)request.getAttribute("result");
  String result2=(String)request.getAttribute("result2");
  String sectionID = (String)request.getAttribute("sectionID");
  String note = (String)request.getAttribute("note");
  
  String runMode = (String)request.getAttribute("RunMode");
  
  String recoverTime2 = (String)request.getAttribute("recoverTime2");
  String sysdate = (String)request.getAttribute("sysdate");
  System.out.println("sysdate: "+sysdate);
 %>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script type="text/javascript">
function window_load(){
	var result = agencyForm.customerID.value;//借助customerID这个String型
	if (result == "3") {				// 资金结算完成
		agencyForm.ok2.disabled = true;
  		agencyForm.ok3.disabled = true;
  		agencyForm.ok4.disabled = false;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = true;
	}
	if (result == "5" || result == "8") {// 交易中、集合竞价交易中
	
		agencyForm.ok2.disabled = false;
  		agencyForm.ok3.disabled = false;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = true;
	}
	if (result == "4") {				// 暂停交易
		agencyForm.ok2.disabled = true;
  		agencyForm.ok3.disabled = true;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = false;
  		agencyForm.ok11.disabled = true;
  		document.getElementById("t").innerHTML = agencyForm.recoverTime.value;
	}
	if (result == "6") {// 节间休息
		agencyForm.ok2.disabled = false;
  		agencyForm.ok3.disabled = false;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = true;
	}
	if (result == "9") {// 集合竞价交易结束
		agencyForm.ok2.disabled = false;
  		agencyForm.ok3.disabled = true;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = true;
	}
	if (result == "7") {				// 交易结束
		agencyForm.ok2.disabled = true;         
  		agencyForm.ok3.disabled = true;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = false;
	}
	//if (result == "初始化失败") {
	//	agencyForm.ok2.disabled = true;
  	//	agencyForm.ok3.disabled = true;
  	//	agencyForm.ok4.disabled = false;
  	//	agencyForm.ok1.disabled = true;
  	//	agencyForm.ok11.disabled = false;
	//}
	if (result == "0" || result == "1" || result == "2" || result == "10") {// 初始化完成、闭市状态、结算中、交易结算完成
		agencyForm.ok2.disabled = true;
  		agencyForm.ok3.disabled = true;
  		agencyForm.ok4.disabled = true;
  		agencyForm.ok1.disabled = true;
  		agencyForm.ok11.disabled = true;
	}
	
	
	
}

function balanceChk_onclick(id)
{
  var name;
  
  if(id=='06')
  {
   name="恢复交易";
  }
  if(id=='09')
  {
   name="交易结束";
   agencyForm.marketStatus.value=3;
  }
  if(id=='05')
  {
  name="暂停交易";
  }
  if(id=='08')
  {
   name="开市准备";
  }
  if(id=='07')
  {
  name="闭市操作";
  }
  if (id == "99") {
  	name="设定恢复时间"
  }
  if (confirm("您确定要" + name + "吗？"))
    {
  agencyForm.type.value=id;
  agencyForm.submit();
 
  agencyForm.ok2.disabled = true;
  agencyForm.ok3.disabled = true;
  agencyForm.ok4.disabled = true;
  agencyForm.ok1.disabled = true;
  agencyForm.ok11.disabled = true;
     }
}

function writeTime(){
	//var returnValue = 
	pTop("<c:url value="/timebargain/xtgl/agency_marketStatusWriteFrame.jsp"/>", 350, 250);
	//recieveTime(returnValue);
}

function recieveTime(time){
	document.getElementById("t").innerHTML = time;
}
</script>
</head>
<body  leftmargin="0" topmargin="0"  onkeypress="keyEnter(event.keyCode);" onload="window_load()">
<table border="0" height="230" align="center" width="260">
<tr><td>
<html:form method="post" action="/timebargain/xtgl/agency.do?funcflg=operate" target="HiddFrame">
<fieldset class="pickList">
	<legend class="common">
	<b>交易状态管理</b>
	</legend>
      <table border="0" align="center" cellpadding="1" cellspacing="1" class="commonTest" height="180" width="260">
      <tr height="20">
        <td>&nbsp;&nbsp;</td>
        <td width="200" colspan="4">
        <span >当前时间：<%=sysdate%></span>
        </td>
      </tr>
      <tr height="20">
      	<td>
      		&nbsp;&nbsp;
      	</td>
        <td width="320">
        <span >交易日期：<%=date%></span>
        </td>
        </tr>	
        <tr height="20">
      	<td>
      		&nbsp;&nbsp;
      	</td>
        <td width="320">
        <span >市场状态：<%=result2%></span>&nbsp;&nbsp;
        
        <%
       		if (sectionID != null && !"".equals(sectionID)) {
       		%>
       		
       		<span >&nbsp;&nbsp;&nbsp;&nbsp;交易节：<%=sectionID%></span>
       	
       		<%
       		}
       	%>
        </td>
        
        </tr>
        
        
        
         <%
       		if (note != null && !"".equals(note)) {
       		%>
       		<tr>
       			<td >
       				&nbsp;&nbsp;
       			</td>
       			<td width="150">
       				 <span >备注：<%=note%></span>
       			</td>
       			
       		</tr>
       		<%
       		}
       	%>
    
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="180">
        <html:button  property="ok4" styleClass="button" onclick="javascript:balanceChk_onclick('08');">&nbsp;&nbsp;&nbsp;开市准备&nbsp;&nbsp;&nbsp;</html:button>
        (<%=runMode%>)
        </td>
        
        </tr>
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="140">
        <fieldset class="pickList" >
        <%
        	if ("0".equals(result)) {
        %>
        	<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始交易</font>
        <%	
        	}else {
        %>
        	<font color="gray">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始交易</font>
        <%	
        	}
        %>
         
        </fieldset>   
        </td>
        
        </tr>
        <tr  >
        <td ><font color=red>●</font></td>
        <td height="30" width="150" valign="middle">
        <html:button  property="ok3" styleClass="button" onclick="javascript:balanceChk_onclick('05');">&nbsp;&nbsp;&nbsp;暂停交易&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        
        </tr> 
        
        <%
        	if ("4".equals(result)) {
        %>
        <tr>
        	<td>&nbsp;</td>
        	<td>
        		恢复时间：<span class="req" id="t"></span>
        		<a href="#"  onclick="writeTime()"><img align="middle" height="30" width="30"  title="设定恢复时间" src="<c:url value="/timebargain/images/clock.gif"/>"/></a>
        	</td>
        	
        </tr>
        <%
        	}
        %>
        
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="150">
        <html:button  property="ok1" styleClass="button" onclick="javascript:balanceChk_onclick('06');">&nbsp;&nbsp;&nbsp;恢复交易&nbsp;&nbsp;&nbsp;</html:button>
        </td>
       
        </tr> 
        
        
        
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="300">
        <html:button  property="ok2" styleClass="button" onclick="javascript:balanceChk_onclick('09');">&nbsp;&nbsp;&nbsp;交易结束&nbsp;&nbsp;&nbsp;</html:button>
        (默认自动)
        </td>
        
        </tr>
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="150">
        <html:button  property="ok11" styleClass="button" onclick="javascript:balanceChk_onclick('07');">&nbsp;&nbsp;&nbsp;闭市操作&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        </tr>
       	
        <html:hidden property="marketStatus"/>
        <html:hidden property="type"/>
        <html:hidden property="recoverTime" value="<%=recoverTime2%>"/>
        <tr>
        	<td>
        	<div id="status1" align="center" class="common1"><html:hidden property="customerID" value="<%=result%>"/></div>
        	</td>
        </tr>
      </table>
      
</fieldset>    
</html:form>
</td></tr>
</table>

<%@ include file="/timebargain/common/messages.jsp" %> 
</body>


</html>


