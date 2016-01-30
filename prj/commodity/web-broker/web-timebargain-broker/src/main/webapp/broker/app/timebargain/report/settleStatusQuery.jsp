<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="common/util.jsp" %>
<html xmlns:MEBS>
<head>

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
		<b>交割情况表</b><input name="userType" type="hidden" value="${CurrentUserType }">
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="" NAME="zStart" value="" style="ime-mode:disabled" size=15 maxlength=10 onblur="fandealcontent(this)" onkeypress="notSpace()" readonly="readonly" >
         <SELECT name="select5" style="width:130px"  onchange="dealcontent(this)"  >
         <OPTION value="" >请选择</OPTION>
         <c:forEach items="${brokerAscList}" var="result">
	     <option value="${result.FIRMID}">${result.FIRMID}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="required">*</span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束交易商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="" NAME="zEnd" value=""  style="ime-mode:disabled"  size=15 maxlength=10 onblur="fandealcontent1(this)" onblur="fandealcontent1(this)" onkeypress="notSpace()" readonly="readonly">
            <SELECT  name="select6" style="width:130px"  onchange="dealcontent1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${brokerDescList}" var="result">
	        <option value="${result.FIRMID}">${result.FIRMID}</option>
		    </c:forEach>
          </SELECT>   
		  <span class="required">*</span>
            </div></td>
      </tr>    
	  <c:if test="${CurrentUserType=='0'}">
	  <tr>
        <td align="right">居间：</td>
        <td>
          <div align="left">
            <INPUT TYPE="" NAME="zBrokerage" value=""  style="ime-mode:disabled"  size=15 maxlength=10 onblur="fandealcontent2(this)" onkeypress="notSpace()" readonly="readonly">
            <SELECT  name="select7" style="width:130px"  onchange="dealcontent2(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${brokerageList}" var="result">
	        <option value="${result.BROKERAGEID}">${result.BROKERAGEID}</option>
		    </c:forEach>
          </SELECT>   
            </div></td>
      </tr>    
	  </c:if>
	 <tr>
        <td align="right">交易商类别：</td>
        <td>
          <div align="left">
            <INPUT TYPE="" NAME="zFirmcategory" value=""  style="ime-mode:disabled"  size=15 maxlength=10 onblur="" onkeypress="notSpace()" readonly="readonly">
            <SELECT  name="firmcategory" style="width:130px"  onchange="dealcontent3(this)">
            <OPTION value="">请选择</OPTION>
            <OPTION value="-1">未分类</OPTION>
            <c:forEach items="${firmcategoryList}" var="result">
	        <option value="${result.ID}">${result.NAME}</option>
		    </c:forEach>
          </SELECT>   
            </div></td>
      </tr>
      <tr>
        <td align="right">起始日期：</td>
        <td >
        	<input type="text" style="width: 102px" id="zDate"
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
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/report/settleStatusReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/report/settleStatusReportExcel.action" id="saveExcel"></rightButton:rightButton>
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
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}


function dealcontent1(sel)
{
    document.forms[0].zEnd.value=sel.options[sel.selectedIndex].value;
}
function dealcontent2(sel)
{
    document.forms[0].zBrokerage.value=sel.options[sel.selectedIndex].value;
}
function dealcontent3(sel)
{
    document.forms[0].zFirmcategory.value=sel.options[sel.selectedIndex].text;
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
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}

function dealcontentCommodity(sel)
{
    document.forms[0].zStartCommodity.value=sel.options[sel.selectedIndex].value;
}
function dealcontentStrartCommodity(sel)
{

    var len=document.forms[0].selectStartCommodity.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].selectStartCommodity.options[i].value-sel.value==0)
        {
            document.forms[0].selectStartCommodity.options[i].selected=true;
            break;
        }
    }
}

function dealcontentCommodity1(sel)
{
    document.forms[0].zEndCommodity.value=sel.options[sel.selectedIndex].value;
}
function dealcontentEndCommodity1(sel)
{

    var len=document.forms[0].selectEndCommodity.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].selectEndCommodity.options[i].value-sel.value==0)
        {
            document.forms[0].selectEndCommodity.options[i].selected=true;
            break;
        }
    }
}
function fandealcontent2(sel)
{

    var len=document.forms[0].select7.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select7.options[i].value-sel.value==0)
        {
            document.forms[0].select7.options[i].selected=true;
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
	var firmcategory=document.forms[0].firmcategory.value;
	var userType=document.forms[0].userType.value;
	var zBrokerage;
	if(userType=='0'){
    	zBrokerage=document.forms[0].zBrokerage.value;
    }
	if (zStart == "") {
		alert("起始交易商不能为空！");
		return false;
	}
	if (zEnd == "") {
		alert("结束交易商不能为空！");
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
	
     if(exportTo=="excel"){
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/report/settleStatusReportExcel.jsp?startFirmID="+zStart +"&endFirmID="+ zEnd +"&startdate="+zDate +"&enddate="+zEndDate+"&brokerageId="+zBrokerage+"&firmcategory="+firmcategory;
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/report/settleStatusReport.jsp?startFirmID="+zStart +"&endFirmID="+ zEnd +"&startdate="+zDate +"&enddate="+zEndDate+"&brokerageId="+zBrokerage+"&firmcategory="+firmcategory;
		showDialog(url, "", 900, 650);
	}
}
</SCRIPT>