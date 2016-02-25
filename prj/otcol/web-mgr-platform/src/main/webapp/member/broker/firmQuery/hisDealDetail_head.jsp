<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易商历史成交明细</title>
		<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
		<script language="javaScript">
			function window_onload()
			{
  				changeColor("tb");
  				query();
			}
			
			function tb_onclick()
			{
  				changeColor("tb");
  				query();
			}

			function zcjs_onclick()
			{
  				changeColor("zcjs");    
  				query();
			}

			function vd_onclick()
			{
  				changeColor("vd");    
  				query();
			}

			function changeColor(name)
			{
  				oper.value = name;
  				if(name == "tb")
  				{
  					tb.style.color = "red";
  					zcjs.style.color = "#26548B";
  					vd.style.color = "#26548B";
  				}
  				else if(name == "zcjs")
  				{
  					tb.style.color = "#26548B";
  					zcjs.style.color = "red";
  					vd.style.color = "#26548B";
  				} 
 				else if(name == "vd")
  				{
  					tb.style.color = "#26548B";
  					zcjs.style.color = "#26548B";
  					vd.style.color = "red";
  				}
			}

			function query()
			{
  				if(oper.value == "tb")
  				{
  					parent.ListFrame.location.href = "<c:url value="/member/feeDetailController.mem?funcflg=hisDealDetailList"/>";
  				}
  				else if(oper.value == "zcjs")
  				{
  					parent.ListFrame.location.href = "<c:url value="/member/feeDetailController.mem?funcflg=hisDealDetailZcjsList"/>";
  				}
  				else if(oper.value == "vd")
  				{
  					parent.ListFrame.location.href = "<c:url value="/member/feeDetailController.mem?funcflg=hisDealDetailVdList"/>";
  				} 
			}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    			<td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    			<td width="47" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="tb" class="common" onclick="tb_onclick()">订单</a></td>
    			<td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
   				<td width="46" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="zcjs" class="common" onclick="zcjs_onclick()">挂牌</a></td>
   				<td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
   				<td width="46" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="vd" class="common" onclick="vd_onclick()">竞价</a></td>
    			<td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    			<td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    			<td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  			</tr>
		</table>
		<input type="hidden" name="oper" value="" >
	</body>
</html>