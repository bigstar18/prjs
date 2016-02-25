<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ include file="/timebargain/common/meta.jsp" %>
<html>
<head>
<%
  String tradeTime = (String)request.getAttribute("tradeTime");
  String result=(String)request.getAttribute("status");
  String result2=(String)request.getAttribute("result2");
  String sectionID = (String)request.getAttribute("sectionID");
  String note = (String)request.getAttribute("note");
  String sysdate = (String)request.getAttribute("sysdate");
  String recoverTime2 = (String)request.getAttribute("recoverTime2");
 %>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script type="text/javascript">
function window_load(){
	var result = delayForm.status.value;//状态
	if (result == "1" || result == "3") {
		delayForm.ok1.disabled = false;
		delayForm.ok2.disabled = true;
		delayForm.ok3.disabled = false;
	}else if (result == "4") {
		delayForm.ok1.disabled = true;
		delayForm.ok2.disabled = false;
		delayForm.ok3.disabled = false;
		document.getElementById("t").innerHTML = delayForm.recoverTime.value;
	}else if (result == "5") {
		delayForm.ok1.disabled = true;
		delayForm.ok2.disabled = true;
		delayForm.ok3.disabled = true;
	}else if (result == "0" || result == "2") {
		delayForm.ok1.disabled = true;
		delayForm.ok2.disabled = true;
		delayForm.ok3.disabled = false;
	}
}

function balanceChk_onclick(id)
{
  var name;
  if(id=='01')
  {
  name="暂停交收";
  }
  if(id=='02')
  {
   name="恢复交收";
  }
  if(id=='03')
  {
   name="交收结束";
  }
  
  if (id == "99") {
  	name="设定恢复时间"
  }
  
  if (confirm("您确定要" + name + "吗？")){
	  delayForm.type.value=id;
	  delayForm.submit();
	 
	  delayForm.ok1.disabled = true;
	  delayForm.ok2.disabled = true;
	  delayForm.ok3.disabled = true;
  
  }
}

function writeTime(){
	pTop("<c:url value="/timebargain/delay/delayStatus_WriteFrame.jsp"/>", 350, 250);
}

function recieveTime(time){
	document.getElementById("t").innerHTML = time;
}
</script>
</head>
<body  leftmargin="0" topmargin="0"  onkeypress="keyEnter(event.keyCode);" onload="window_load()">
<table border="0" height="230" align="center" width="260">
<tr><td>
<html:form  method="post" action="/timebargain/delay/delay.do?funcflg=operate" target="HiddFrame">
<fieldset class="pickList">
	<legend class="common">
	<b>交收状态管理</b>
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
        <span >交收日期：<%=tradeTime%></span>
        </td>
        </tr>	
        <tr height="20">
      	<td>
      		&nbsp;&nbsp;
      	</td>
        <td width="320">
        <span >状态：<%=result2%></span>&nbsp;&nbsp;
        
        <%
       		if (sectionID != null && !"".equals(sectionID)) {
       		%>
       		
       		<span >&nbsp;&nbsp;&nbsp;&nbsp;交收节：<%=sectionID%></span>
       	
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
       				 <span >备注：<%=note == "null" ? "" : note%></span>
       			</td>
       			
       		</tr>
       		<%
       		}
       	%>
    
       
        
        <tr  >
        <td ><font color=red>●</font></td>
        <td height="30" width="150" valign="middle">
        <html:button  property="ok1" styleClass="button" onclick="javascript:balanceChk_onclick('01');">&nbsp;&nbsp;&nbsp;暂停交收&nbsp;&nbsp;&nbsp;</html:button>
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
        <html:button  property="ok2" styleClass="button" onclick="javascript:balanceChk_onclick('02');">&nbsp;&nbsp;&nbsp;恢复交收&nbsp;&nbsp;&nbsp;</html:button>
        </td>
       
        </tr> 
        
        
        
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="300">
        <html:button  property="ok3" styleClass="button" onclick="javascript:balanceChk_onclick('03');">&nbsp;&nbsp;&nbsp;交收结束&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        </tr>
        
       	
        <html:hidden property="type"/>
        <html:hidden property="recoverTime" value="<%=recoverTime2%>"/>
        <tr>
        	<td>
        	<div id="status1" align="center" class="common1"><html:hidden property="status" value="<%=result%>"/></div>
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


