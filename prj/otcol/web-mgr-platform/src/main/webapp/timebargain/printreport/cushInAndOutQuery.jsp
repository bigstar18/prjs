<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
   //List firmAscList=getList("select firmId from t_firm order by firmId");
  // List firmDescList=getList("select firmId from t_firm order by firmId desc");
  // pageContext.setAttribute("firmAscList",firmAscList);
   //pageContext.setAttribute("firmDescList",firmDescList);
   List brokerIdList=getList("select brokerId from m_b_broker");
   pageContext.setAttribute("brokerIdList",brokerIdList);
   List CateGoryIdList=getList("select id,name from m_firmcategory order by id");  
   pageContext.setAttribute("CateGoryIdList",CateGoryIdList);
   	Date sysdate = new Date();
	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
	String date = df.format(sysdate);
	System.out.println("date:"+date);
%>
<html xmlns:MEBS>
<head>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>
<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar2.htc"/>">

<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
function window_onload(){
	highlightFormElements();
	document.forms[0].zDate.value = '<%=date%>';
	document.forms[0].zEndDate.value = '<%=date%>';
}
</script>
<script language="javascript" for="document" event="onkeydown">
<!--
  if(event.keyCode==13)
     query();
-->
</script>

</head>

<body  onload="return window_onload()">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>交易商入金出金记录表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
      </tr>
	<tr>
	<tr>
        <td align="right">加盟商：</td>
        <td>
        <INPUT TYPE="text" NAME="BrokerId" value="" style="ime-mode:disabled" size=8 maxlength=10  onkeypress="notSpace()" id="brokerId" onblur="fandealcontentBroker(this)">
         <SELECT name="select7"  onchange="dealcontentBroker(this)">
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${brokerIdList}" var="result">
	     <option value="${result.brokerId}">${result.brokerId}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr> 
      <tr>
        <td align="right">交易商类别：</td>
        <td>
        <INPUT TYPE="text" NAME="cateGoryId" value="" style="ime-mode:disabled" size=8 maxlength=10  onkeypress="notSpace()" id="cateGoryId" onblur="fandealcontentBroker(this)">
         <SELECT name="select8" id="cateGorySel"  onchange="dealcontentCateGory(this)">
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${CateGoryIdList}" var="result">
	     <option value="${result.id}">${result.name}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr>  
		<tr>
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" value="" style="ime-mode:disabled" size=8 maxlength=10 onblur="fandealcontent(this)"onkeypress="notSpace()" >
         <div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束交易商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" value=""  style="ime-mode:disabled"  size=8 maxlength=10 onblur="fandealcontent1(this)" onkeypress="notSpace()" >
            <div id="divContentEnd" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
            </div></td>
      </tr>   
      <tr>
        <td align="right">起始日期：</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
			
      </tr>
	  <tr>
		<td align="right">结束日期：</td>
        <td >
			<MEBS:calendar eltID="zEndDate" eltName="zEndDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
	  </tr>
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<input name="Submit22" type="button" class="button" value="查看" onclick="return query('pdf')">
	     		</td>
	     		<td>
				<input name="Submit22" type="button" class="button" value="保存为excel" onclick="return query('excel')">
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
<!--
function dealcontentCateGory(sel)
{
    document.forms[0].cateGoryId.value=sel.options[sel.selectedIndex].innerHTML;
}
function fandealcontentCateGory(sel)
{

    var len=document.forms[0].select8.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select8.options[i].value-sel.value==0)
        {
            document.forms[0].select8.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}
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
function query(exportTo)
{
    var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;
	var zEndDate=document.forms[0].zEndDate.value;
	var brokerId=document.forms[0].BrokerId.value;
	var cateGoryId = document.getElementById("cateGorySel").value;
	if (zStart == "" && zEnd != "") {
		alert("起始交易商不能为空！");
		return false;
	}
	if (zEnd == "" && zStart != "") {
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
	if(!isDateFomat(zDate))
    {
        alert("起始日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
    if(!isDateFomat(zEndDate))
    {
        alert("结束日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zEndDate.value = "";
        return false;
    }
    if (zDate > zEndDate) {
		alert("起始日期不能大于结束日期！");
		return false;
	}
    if(zStart == "" && zEnd == ""){
    	zStart=null;
    	zEnd=null;
    }
    if(brokerId == ""){
    	brokerId=null;
    }
    if(cateGoryId == ""){
		cateGoryId=null;
	}
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=cushInAndOut&startFirmID=" + zStart + "&endFirmID=" + zEnd +"&startClearDate=" + zDate + "&endClearDate=" + zEndDate + "&title=交易商入金出金记录表&brokerId="+brokerId+"&cateGoryId="+cateGoryId);
		else 
	window.showModalDialog("reportContainer.jsp?sign=cushInAndOut&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&startClearDate=" + zDate + "&endClearDate=" + zEndDate + "&title=交易商入金出金记录表&brokerId="+brokerId+"&cateGoryId="+cateGoryId,"", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>