<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	
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

		<script type="text/javascript">
		jQuery(document).ready(function() {
			var result = $("#status").val();
			if (result == "1" || result == "3") {
				$("#ok1").attr("disabled", false);
				$("#ok2").attr("disabled", true);
				$("#ok3").attr("disabled", false);
			}else if (result == "4") {
				$("#ok1").attr("disabled", true);
				$("#ok2").attr("disabled", false);
				$("#ok3").attr("disabled", false);
				
					   var  tt=$("#recoverTime").val();
					   document.getElementById("t").innerHTML = tt;
					   if(document.getElementById("t").innerHTML=="null"){
							document.getElementById("t").innerHTML="";
						}	   
					  
			}else if (result == "5") {
				$("#ok1").attr("disabled", true);
				$("#ok2").attr("disabled", true);
				$("#ok3").attr("disabled", true);
			}else if (result == "0" || result == "2") {
				$("#ok1").attr("disabled", true);
				$("#ok2").attr("disabled", true);
				$("#ok3").attr("disabled", false);
			}
		});

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
	  $("#type").val(id);
	  $("#frm").submit();

	  $("#ok1").attr("disabled", true);
	  $("#ok2").attr("disabled", true);
	  $("#ok3").attr("disabled", true);
  }
}

function writeTime(){
	var url = "${basePath}/timebargain/delay/updateDelayStatus.action?type=99";
	
	if(showDialog(url, "", 350, 250)){
		$("#frm").attr("action", "${basePath}/timebargain/delay/delayStatusList.action");
		$("#frm").submit();
	}	
}

function recieveTime(time){

	document.getElementById("t").innerHTML = time;
			
}
</script>
</head>
<body>
<table border="0" height="230" align="center" width="260">
<tr><td>
<form id="frm" name="frm" method="post" action="${basePath }/timebargain/delay/updateDelayStatus.action">
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
    
       
        
        <tr>
        <td ><font color=red>●</font></td>
        <td height="30" width="150" valign="middle">
        <rightButton:rightButton name="暂停交收" onclick="javascript:balanceChk_onclick('01');" className="btn_sec" action="/timebargain/delay/updateDelayStatus.action" id="ok1"></rightButton:rightButton>
        </td>
        
        
        </tr> 
        
        <%
        	if ("4".equals(result)) {
        %>
        <tr>
        	<td>&nbsp;</td>
        	<td>
        		恢复时间：<span class="req" id="t"></span>
        		<a href="#"  onclick="writeTime()"><img align="middle" height="30" width="30"  title="设定恢复时间" src="<c:url value="${skinPath }/image/app/timebargain/clock.gif"/>"/></a>
        	</td>
        	
        </tr>
        <%
        	}
        %>
        
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="150">
                <rightButton:rightButton name="恢复交收" onclick="javascript:balanceChk_onclick('02');" className="btn_sec" action="/timebargain/delay/updateDelayStatus.action" id="ok2"></rightButton:rightButton>
        </td>
       
        </tr> 
        
        
        
        <tr height="30">
        <td><font color=red>●</font></td>
        <td width="300">
                <rightButton:rightButton name="交收结束" onclick="javascript:balanceChk_onclick('03');" className="btn_sec" action="/timebargain/delay/updateDelayStatus.action" id="ok3"></rightButton:rightButton>
        </td>
        </tr>
        
       	
        <input type="hidden" id="type" name="type"/>
        <input type="hidden" id="recoverTime" name="entity.recoverTime" value="<%=recoverTime2%>"/>
        <tr>
        	<td>
        	<div id="status1" align="center" class="common1"><input type="hidden" id="status" name="entity.status" value="<%=result%>"/></div>
        	</td>
        </tr>
      </table>
      
</fieldset>    
</form>
</td></tr>
</table>
</body>
</html>