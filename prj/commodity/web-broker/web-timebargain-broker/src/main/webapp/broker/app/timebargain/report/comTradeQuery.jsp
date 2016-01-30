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
<FORM METHOD=POST ACTION="" name="frm" >
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>分商品成交量统计表 </b><input name="userType" type="hidden" value="${CurrentUserType }">
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		 <tr>
        <td align="right">起始商品：</td>
        <td>
        <INPUT TYPE="text" NAME="zStartCommodity" style="ime-mode:disabled"   value="" size=8 maxlength=8 onblur="dealcontentStrartCommodity(this)"onkeypress="notSpace()" >
         <SELECT name="selectStartCommodity" onchange="dealcontentCommodity(this)" style="width:100px;" >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${commodityIdAscList}" var="commodity">
	     <option value="${commodity.COMMODITYID}">${commodity.COMMODITYID}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="required">*</span>
        </td>
      </tr> 
       <tr>
        <td align="right">结束商品：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEndCommodity" style="ime-mode:disabled"  value="" size=8 maxlength=8 onblur="dealcontentEndCommodity1(this)"onkeypress="notSpace()" >
            <SELECT  name="selectEndCommodity" onchange="dealcontentCommodity1(this)" style="width:100px;">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${commodityIdDescList}" var="commodity">
	        <option value="${commodity.COMMODITYID}">${commodity.COMMODITYID}</option>
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
            <INPUT TYPE="" NAME="zBrokerage" value=""  style="ime-mode:disabled"  size=8 maxlength=10 onblur="fandealcontent1(this)" onkeypress="notSpace()" readonly="readonly">
            <SELECT  name="select6" style="width:100px"  onchange="dealcontent2(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${brokerageList}" var="result">
	        <option value="${result.BROKERAGEID}">${result.BROKERAGEID}</option>
		    </c:forEach>
          </SELECT>   
            </div></td>
        </tr>    
      </c:if>  
       <tr>
        <td align="right">品种：</td>
        <td>
          <div align="left">
            <INPUT TYPE="" NAME="zBreed" value=""  style="ime-mode:disabled"  size=8 maxlength=10 onblur="" onkeypress="notSpace()" readonly="readonly">
            <SELECT  name="select6" style="width:100px"  onchange="dealcontent3(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${breedList}" var="result">
	        <option value="${result.ID}">${result.ID}</option>
		    </c:forEach>
          </SELECT>   
            </div></td>
      </tr>
      <tr>
        <td align="right">起始日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur="onloaddate()"
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
      <tr>
        <td align="right">结束日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zEndDate"
				class="wdate" maxlength="10" name="zEndDate"
				value="" onblur="onloaddate()"
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>

	
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/report/comTradeReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/report/comTradeReportExcel.action" id="saveExcel"></rightButton:rightButton>
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
function dealcontent2(sel)
{
    document.forms[0].zBrokerage.value=sel.options[sel.selectedIndex].value;
}

function dealcontent1(sel)
{
    document.forms[0].zEnd.value=sel.options[sel.selectedIndex].value;
}
function dealcontent3(sel)
{
    document.forms[0].zBreed.value=sel.options[sel.selectedIndex].value;
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
function query(exportTo)
{
	var zStartCommodity=document.forms[0].zStartCommodity.value;
	var zEndCommodity=document.forms[0].zEndCommodity.value;
    var zDate=document.forms[0].zDate.value;
    var breed=document.forms[0].zBreed.value;
	var zEndDate=document.forms[0].zEndDate.value;	
	var userType=document.forms[0].userType.value;
	var zBrokerage;
	if(userType=='0'){
    	zBrokerage=document.forms[0].zBrokerage.value;
    }

	if (zStartCommodity == "") {
		alert("起始商品不能为空！");
		return false;
	}
	if (zEndCommodity == "") {
		alert("结束商品不能为空！");
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
		var url = "${mgrPath}/app/timebargain/report/comTradeReportExcel.jsp?startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity +"&startClearDate="+zDate+"&endClearDate="+ zEndDate+"&brokerageId="+zBrokerage+"&breed="+breed;
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/report/comTradeReport.jsp?startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity +"&startClearDate="+zDate+"&endClearDate="+ zEndDate+"&brokerageId="+zBrokerage+"&breed="+breed;
		showDialog(url, "", 900, 650);
		
	}
}
</SCRIPT>