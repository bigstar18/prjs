<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
   List firmAscList=getList("select brokerid from m_b_broker order by brokerid ");
   List firmDescList=getList("select brokerid from m_b_broker order by brokerid  desc");
   pageContext.setAttribute("firmAscList",firmAscList);
   pageContext.setAttribute("firmDescList",firmDescList);
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
		<b>分加盟商订货统计表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
        <td align="right">起始加盟商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" value="" size=8 maxlength=8 onblur="fandealcontent(this)" >
         <SELECT name="select5" onchange="dealcontent(this)"  >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${firmAscList}" var="result">
	     <option value="${result.brokerid}">${result.brokerid}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req">*</span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束加盟商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" value="" size=8 maxlength=8 onblur="fandealcontent1(this)">
            <SELECT  name="select6" onchange="dealcontent1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${firmDescList}" var="result">
	        <option value="${result.brokerid}">${result.brokerid}</option>
		    </c:forEach>
          </SELECT>   
		  <span class="req">*</span>
            </div></td>
      </tr>    
      <tr>
        <td align="right">查询日期：</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
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
    var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;

	if (zStart == "") {
		alert("起始加盟商不能为空！");
		return false;
	}
	if (zEnd == "") {
		alert("结束加盟商不能为空！");
		return false;
	}


	if (zDate == "") {
		alert("日期不能为空！");
		return false;
	}
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=holdBybroker&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&startClearDate=" + zDate  + "&title=分加盟商订货统计表");
		else 
	window.showModalDialog("reportContainer2.jsp?sign=holdBybroker&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&startClearDate=" + zDate  + "&title=分加盟商订货统计表","", "dialogWidth=1000px; dialogHeight=700px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>