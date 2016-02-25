<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<%@page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.webapp.action.BaseAction"%>
<%
	StatQueryManager mgr = (StatQueryManager) new BaseAction().getBean("statQueryManager");
	String sysdate = mgr.getSysdate1();
%>
<html>
	<body class="topframe" onload="oneAjax()">
		<div style="width: 100%; height: 60px; overflow: hidden;">
		<div style="width: 100%; height: 60px; overflow: hidden; background-color: #6A7EA8;">
		<form name=frm id=frm>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"  height="100%">
			<c:set var="default" value="default"/>
			<c:set var="gray" value="gray"/>
			<c:choose>
				<c:when test="${skinstyle eq default }">
					<tr width="100%"  style="background-image: url(<%=skinPath%>/pic/top_01.jpg)">
					<td width="600" style="background-image: url(<%=skinPath%>/pic/top01.jpg);background-repeat: no-repeat;" ></td>
					<td width="230px" class="userFont" valign="bottom">
					  <font style="font-size:14;font-family:宋体_GBK;color:#ffffff;">
						  系统时间：<span id="sysdate" style="font-size:15"></span>
						 <input type="hidden" name="sysdate1" value="<%=sysdate %>"/>
					  </font>
					</td>
					<td width="150px" class="userFont" valign="bottom">
						<font style="font-size:14;font-family:宋体_GBK;color:#ffffff;">
							用户：${CURRENUSERID }&nbsp;&nbsp;
						</font>
					</td>
					<td width="69" valign="bottom" bgcolor="6B7EA9">
						<div align="center">
                        	<img src="<%=skinPath%>/images1/modify.gif" width="69" height="19" border="0" onclick="changePwd();" style="cursor:hand">
                        </div>
                    </td>
					<td width="44" valign="bottom" bgcolor="6B7EA9">
						<div align="center">
							<img src="<%=skinPath%>/images1/out.gif" width="44" height="19" border="0" onclick="logout();" style="cursor:hand">
						</div>
					</td>
					<td width="5" valign="bottom" bgcolor="6B7EA9">&nbsp;</td>
					</tr>
				</c:when>
				<c:when test="${skinstyle eq gray }">
					<tr width="100%"  style="background-image: url(<%=skinPath%>/pic/top2.jpg)">
					<td width="600" style="background-image: url(<%=skinPath%>/pic/top02.jpg);background-repeat: no-repeat;"></td>
					<td width="230px" class="userFont" valign="bottom">
					  <font style="font-size:14;font-family:宋体_GBK;color:#ffffff;">
						  系统时间：<span id="sysdate" style="font-size:15"></span>
						 <input type="hidden" name="sysdate1" value="<%=sysdate %>"/>
					  </font>
					</td>
					<td width="150px" class="userFont" valign="bottom">
						<font style="font-size:14;font-family:宋体_GBK;color:#ffffff;">
							用户：${CURRENUSERID }&nbsp;&nbsp;
						</font>
					</td>
					<td width="63" valign="bottom">
						<div align="center">
                        	<img src="<%=skinPath%>/images1/modify.gif" width="69" height="19" border="0" onclick="changePwd();" style="cursor:hand">
                        </div>
                    </td>
					<td width="44" valign="bottom">
						<div align="center">
							<img src="<%=skinPath%>/images1/out.gif" width="44" height="19" border="0" onclick="logout();" style="cursor:hand">
						</div>
					</td>
					<td width="5">&nbsp;</td>
					</tr>
				</c:when>
			</c:choose>
			</table>
		</form>
		</div>
		</div>
	</body>
</html>
<script language="javascript">
	function logout()
	{
		if(confirm("您确实要退出吗？"))
		{
			parent.window.location="<%=commonUserControllerPath %>commonUserLogout";
		}
    }
    
	function changePwd()
	{
		window.showModalDialog("<%=commonUserControllerPath %>commonUserModPasswordForward&sign=old&d="+Date(),"", "dialogWidth=400px; dialogHeight=350px; status=no;scroll=no;help=no;");
	}
	function versionInfo()
	{
		window.showModalDialog("<%=surfacePath %>/version.jsp?d="+Date(),"", "dialogWidth=385px; dialogHeight=270px; status=no;scroll=no;help=no;")
	}
	function shinStyle(){
		var result = window.showModalDialog("<%=commonUserControllerPath %>commonUserModStyle&d="+Date(),"","dialogWidth=400px; dialogHeight=250px; status=no;scroll=no;help=no;resizable=no");
		if(result)
		{		
			parent.window.location = "<%=surfacePath %>/index.jsp";
		}
	}

	var sysdate = document.getElementById("sysdate1").value;
	var vtime = new Date(sysdate);
	var year = vtime.getFullYear();
	var month = vtime.getMonth() + 1;
	var day = vtime.getDate();
	var hour = vtime.getHours();
	var minute = vtime.getMinutes();
	var second = vtime.getSeconds();

	var seconds = vtime.getHours()*3600 + vtime.getMinutes()*60 + vtime.getSeconds();
	
	function oneAjax(){
		seconds = seconds + 1;
		var strTime = year + "-" + month + "-" + day + " " + toStrTime0(parseInt(seconds/3600)) + ":" + toStrTime0((parseInt(seconds/60))%60) + ":" + toStrTime0(seconds%60);
		document.getElementById("sysdate").innerHTML = strTime;
		//每1秒读取一次后台数据
		setTimeout("oneAjax()", 1000); 
	}
	function toStrTime0(t) {
		if(t < 10) 
			return "0"+t;
		else
			return ""+t;
	}	
</script>