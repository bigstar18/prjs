<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="common/util.jsp" %>
<html xmlns:MEBS>
<head>
<%
    String reportTiltle = "分加盟商成交统计表";
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
		<b>分加盟商成交统计表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
        <td align="right">起始加盟商：</td>
        <td>
        <INPUT TYPE="" NAME="zStart" value="" style="ime-mode:disabled" size=8 maxlength=10 onblur="fandealcontent(this)"onkeypress="notSpace()"  >
         <SELECT name="select5" style="width:100px"  onchange="dealcontent(this)"  >
         <OPTION value="" >请选择</OPTION>
         <c:forEach items="${listAsc}" var="result">
	     <option value="${result.BROKERID}">${result.BROKERID}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="required">*</span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束加盟商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="" NAME="zEnd" value=""  style="ime-mode:disabled"  size=8 maxlength=10 onblur="fandealcontent1(this)" onkeypress="notSpace()" >
            <SELECT  name="select6" style="width:100px"  onchange="dealcontent1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${listDesc}" var="result">
	        <option value="${result.BROKERID}">${result.BROKERID}</option>
		    </c:forEach>
          </SELECT>   
		  <span class="required">*</span>
            </div></td>
      </tr>    
		 
      <tr>
        <td align="right">起始日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
      <tr>
        <td align="right">结束日期：</td>
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
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/tradeBybrokerReport/tradeBybrokerReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/tradeBybrokerReport/tradeBybrokerReportExcel.action" id="saveExcel"></rightButton:rightButton>
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
function dealcontent(sel)
{
    document.forms[0].zStart.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent(sel)
{

    var len=document.forms[0].select5.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select5.options[i].value-sel.value==0)
        {
            document.forms[0].select5.options[i].selected=true;
            break;
        }
        
    }
}


function dealcontent1(sel)
{
    document.forms[0].zEnd.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent1(sel)
{

    var len=document.forms[0].select6.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select6.options[i].value-sel.value==0)
        {
            document.forms[0].select6.options[i].selected=true;
            break;
        }
        
    }
}

function query(exportTo)
{
    var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;
	var zEndDate=document.forms[0].zEndDate.value;	
	

	if (zStart == "") {
		alert("起始加盟商不能为空！");
		return false;
	}
	if (zEnd == "") {
		alert("结束加盟商不能为空！");
		return false;
	}
	if (zDate == "") {
		alert("起始日期不能为空！");
		return false;
	}
	if (zEndDate == "") {
		alert("结束日期不能为空！");
		return false;
	}
	if(zDate > zEndDate){
		alert("起始日期不能大于结束日期！");
		return;
	}
	if(exportTo=="excel"){
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/tradeBybrokerReportExcel.jsp?startBrokerID="+zStart +"&endBrokerID="+ zEnd +"&startClearDate="+zDate+"&endClearDate="+zEndDate+"&title=<%=reportTiltle%>";
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/tradeBybrokerReport.jsp?startBrokerID="+zStart +"&endBrokerID="+ zEnd +"&startClearDate="+zDate+"&endClearDate="+zEndDate;
		showDialog(url, "", 900, 650);
		
	}
}
</SCRIPT>