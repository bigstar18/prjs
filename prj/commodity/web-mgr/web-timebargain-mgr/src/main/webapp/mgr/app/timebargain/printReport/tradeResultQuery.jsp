<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<%
    String reportTiltle = "���ռ�¼��";
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
	document.getElementById("zEndDate").value=new Date().getYear()+'-'+month+'-'+day;
	
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
		<b>���ռ�¼��</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		
      <tr>
        <td align="right">��ʼ���ڣ�</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
      <tr>
        <td align="right">�������ڣ�</td>
        <td >
        	<input type="text" style="width: 100px" id="zEndDate"
				class="wdate" maxlength="10" name="zEndDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
	 
	
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<rightButton:rightButton name="�鿴" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/tradeResultReport/tradeResultReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="����Ϊexcel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/tradeResultReport/tradeResultReportExcel.action" id="saveExcel"></rightButton:rightButton>
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
	var zEndDate=document.forms[0].zEndDate.value;	
	


	if (zDate == "") {
		alert("��ʼ���ڲ���Ϊ�գ�");
		return false;
	}
	if (zEndDate == "") {
		alert("�������ڲ���Ϊ�գ�");
		return false;
	}
	
	 if(exportTo=="excel"){
			//��ȡ����Ȩ�޵� URL
			var url = "${mgrPath}/app/timebargain/printReport/tradeResultReportExcel.jsp?startClearDate="+zDate+"&endClearDate="+ zEndDate+"&title=<%=reportTiltle%>";
			document.location.href = url;
		 }else{
			//��ȡ����Ȩ�޵� URL
			var url = "${mgrPath}/app/timebargain/printReport/tradeResultReport.jsp?startClearDate="+zDate+"&endClearDate="+ zEndDate;
			showDialog(url, "", 900, 650);
			
		}
}
</SCRIPT>