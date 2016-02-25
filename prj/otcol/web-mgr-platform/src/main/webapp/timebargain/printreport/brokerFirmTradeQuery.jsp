<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>

<%
	List firmCategoryNames=getList("select id,name from M_FIRMCATEGORY t order by name");
	pageContext.setAttribute("firmCategoryNames",firmCategoryNames);

    String reportTiltle = "交易商历史成交情况汇总";
	Date sysdate = new Date();
	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
	String date = df.format(sysdate);
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
		<b>交易商历史成交情况汇总</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
	<tr>
        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
      </tr>
	  <tr>
        <td align="right">起始加盟商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" style="ime-mode:disabled" value="" size=8 maxlength=10 onblur="fandealcontent(this)" onkeypress="notSpace()" >
        <!--
         <SELECT name="select5" onchange="dealcontent(this)"  >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${brokerAscList}" var="result">
	     <option value="${result.brokerid}">${result.brokerid}</option>
		 </c:forEach>
          </SELECT>

		  <span class="req">*</span>
		  -->
        </td>
      </tr>
      <tr>
        <td align="right">结束加盟商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" style="ime-mode:disabled"  value="" size=8 maxlength=10 onblur="fandealcontent1(this)" onkeypress="notSpace()">
             <!--
             <SELECT  name="select6" onchange="dealcontent1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${brokerDescList}" var="result">
	        <option value="${result.brokerid}">${result.brokerid}</option>
		    </c:forEach>
          </SELECT>

		  <span class="req">*</span>
		   -->
            </div></td>
      </tr>

 <tr>
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="text" NAME="zFirmStart" style="ime-mode:disabled" value="" size=8 maxlength=10 onkeypress="notSpace()" >
        <!--
         <SELECT name="select5" onchange="dealcontent(this)"  >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${brokerAscList}" var="result">
	     <option value="${result.brokerid}">${result.brokerid}</option>
		 </c:forEach>
          </SELECT>

		  <span class="req">*</span>
		  -->
        </td>
      </tr>
      <tr>
        <td align="right">结束交易商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zFirmEnd" style="ime-mode:disabled"  value="" size=8 maxlength=10 onkeypress="notSpace()">
             <!--
             <SELECT  name="select6" onchange="dealcontent1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${brokerDescList}" var="result">
	        <option value="${result.brokerid}">${result.brokerid}</option>
		    </c:forEach>
          </SELECT>

		  <span class="req">*</span>
		   -->
            </div></td>
      </tr>
	  <tr>
        <td align="right">交易商类别：</td>
        <td >
			 <div style="position:relative;">
			<select style="width:118px;" onchange="document.getElementById('zFirmCategoryid').value=this.value" >
				<OPTION value="">请选择</OPTION>
	            <c:forEach items="${firmCategoryNames}" var="result">
		        <option value="${result.id}">${result.name}</option>
			    </c:forEach>
			</select>
			<input id="zFirmCategoryid" name="zFirmCategoryid"  type="hidden"/>
			</div>
      </tr>
      <tr>
        <td align="right">开始日期：</td>
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
	var zEndDate=document.forms[0].zEndDate.value;
	var zFirmStart=document.forms[0].zFirmStart.value;
    var zFirmEnd=document.forms[0].zFirmEnd.value;
	var zFirmCategoryid=document.forms[0].zFirmCategoryid.value;
	if(zStart == "" && zEnd !=""){
		alert("起始加盟商不能为空！");
		return false;
	}
	if(zStart != "" && zEnd ==""){
		alert("结束加盟商不能为空！");
		return false;
	}
	if(zFirmStart == "" && zFirmEnd != ""){
		alert("起始交易商不能为空！");
		return false;
	}
	if(zFirmStart != "" && zFirmEnd == ""){
		alert("结束交易商不能为空！");
		return false;
	}
	if (zDate == "") {
		alert("开始日期不能为空！");
		document.forms[0].zDate.focus();
		return false;
	}
	if (zEndDate == "") {
		alert("结束日期不能为空！");
		document.forms[0].zEndDate.focus();
		return false;
	}
	if(!isDateFomat(zDate))
    {
        alert("开始日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        document.forms[0].zDate.focus();
        return false;
    }
    if(!isDateFomat(zEndDate))
    {
        alert("结束日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zEndDate.value = "";
        document.forms[0].zEndDate.focus();
        return false;
    }
    if ( zDate > '<%=date%>' ) {
        alert("开始日期不能大于当天日期!");
        document.forms[0].zDate.focus();
        return false;
    }
    if (zDate > zEndDate) {
		alert("开始日期不能大于结束日期！");
		return false;
	}

	var params = "?sign=brokerFirmTrade&startBrokerID=" + zStart + "&endBrokerID=" + zEnd +
		"&startFirmID=" + zFirmStart + "&endFirmID=" + zFirmEnd+
		"&firmCategoryID=" + zFirmCategoryid +  //因为zFirmCategoryname中含有%
		"&beginDate=" + zDate + "&endDate=" + zEndDate+
		 "&title=<%=reportTiltle%>";

	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp"+params);
	else
		window.showModalDialog("reportContainer.jsp"+params,"", "dialogWidth="+(screen.width-100)+"px; dialogHeight="+(screen.height-200)+"px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>