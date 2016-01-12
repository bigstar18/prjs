<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html xmlns:MEBS>
<head>
<%
    String reportTiltle = "资金不足交易商情况表";
%>
<script language="javascript">

function onloaddate(){
	var month=new Date().getMonth()+1;
	if(month.toString().length==1){
		month="0"+month;
	}
	var day=new Date().getDate();
	if(day.toString().length==1){
		day="0"+day;
	}	
	document.getElementById("zDate").value=new Date().getYear()+'-'+month+'-'+day;
	
}
</script>

</head>

<body onload="onloaddate()">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>资金不足交易商情况表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">  
      <tr>
        <td align="right">查询日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur="onloaddate()"
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
		<tr>
				<td>
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/tradeUserNotEnoughMoneyReport/tradeUserNotEnoughMoneyReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/tradeUserNotEnoughMoneyReport/tradeUserNotEnoughMoneyReportExcel.action" id="saveExcel"></rightButton:rightButton>
	     		</td>
		</tr>
	</table>
	</fieldset>
	</td>
	</tr>
</table>
</FORM>
</body>

</html>
<SCRIPT LANGUAGE="JavaScript">

function query(exportTo)
{
	var zDate=document.forms[0].zDate.value;
	if (zDate == "") {
		alert("查询日期不能为空！");
		return false;
	}
	
	if(exportTo=="excel"){
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/tradeUserNotEnoughMoneyReportExcel.jsp?cleardate="+zDate+"&title=资金不足交易商情况表";
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/tradeUserNotEnoughMoneyReport.jsp?cleardate="+zDate;
		showDialog(url, "", 900, 650);
		
	}
}
</SCRIPT>