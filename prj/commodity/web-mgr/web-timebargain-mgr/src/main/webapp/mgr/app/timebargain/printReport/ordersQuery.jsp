<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="common/util.jsp" %>
<html xmlns:MEBS>
<head>
<%
    String reportTiltle = "委托情况表";
%>
<%
   List brokerIdList=getList("select brokerId from BR_Broker");
   pageContext.setAttribute("brokerIdList",brokerIdList);
  
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
		<b>委托情况表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
	<tr>
        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
     </tr>
	 <tr>
        <td align="right">加盟商：</td>
        <td>
        <INPUT TYPE="text" NAME="BrokerId" value="" style="ime-mode:disabled" size="15" maxlength="4"  onkeypress="notSpace()" id="brokerId" onblur="fandealcontentBroker(this)">
         <SELECT name="select5"  onchange="dealcontentBroker(this)">
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${brokerIdList}" var="result">
	     <option value="${result.brokerId}">${result.brokerId}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr> 
		<tr>
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" value="" style="ime-mode:disabled" size="15" maxlength="32" onkeypress="notSpace()" >
         <div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
        </td>
      </tr>       
      <tr>
        <td align="right">结束交易商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" value=""  style="ime-mode:disabled"  size="15" maxlength="32"  onkeypress="notSpace()">
            <div id="divContentEnd" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		     <span class="req"></span>
            </div></td>
      </tr>  
		 
      <tr>
        <td align="right">查询日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur="onloaddate()"
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>

	
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/ordersReport/ordersReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/ordersReport/ordersReportExcel.action" id="saveExcel"></rightButton:rightButton>
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

function dealcontentBroker(sel)
{
    document.forms[0].BrokerId.value=sel.options[sel.selectedIndex].value;
}
function fandealcontentBroker(sel)
{

    var len=document.forms[0].select5.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select5.options[i].value-sel.value==0)
        {
            document.forms[0].select5.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}
function query(exportTo)
{
    var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;
	var brokerId =document.forms[0].BrokerId.value

	if (zStart == "" && zEnd != "") {
		alert("起始交易商不能为空！");
		return false;
	}
	if (zEnd == "" && zStart != "") {
		alert("结束交易商不能为空！");
		return false;
	}

	if (zDate == "") {
		alert("查询日期不能为空！");
		return false;
	}
	if(zStart == "" && zEnd == ""){
		zStart=null;
		zEnd=null;
	}
	if(brokerId == ""){
		brokerId=null;
	}
	if(exportTo=="excel"){
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/ordersReportExcel.jsp?startFirmID="+zStart +"&endFirmID="+ zEnd +"&cleardate="+zDate+"&title=委托情况表&brokerId="+brokerId;
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/ordersReport.jsp?startFirmID="+zStart +"&endFirmID="+ zEnd +"&cleardate="+zDate+"&title=委托情况表&brokerId="+brokerId;
		showDialog(url, "", 900, 650);
		
	}
}
</SCRIPT>