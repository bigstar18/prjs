<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
   //List firmAscList=getList("select firmId from t_firm order by firmId");
   //List firmDescList=getList("select firmId from t_firm order by firmId desc");
   //pageContext.setAttribute("firmAscList",firmAscList);
  // pageContext.setAttribute("firmDescList",firmDescList);

   List commodityStartList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID asc");
   List commodityEndList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID desc");
   pageContext.setAttribute("commodityStartList",commodityStartList);
   pageContext.setAttribute("commodityEndList",commodityEndList);

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
		<b>当日分商品成交记录表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		

	<tr>
        <td align="right">商品代码：</td>
        <td>
        <INPUT TYPE="text" NAME="zStartCommodity" style="ime-mode:disabled"  value="" size=8 maxlength=8 onblur="dealcontentStrartCommodity(this)" onkeypress="notSpace()">
         <SELECT name="selectStartCommodity" onchange="dealcontentCommodity(this)"  >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${commodityStartList}" var="commodity">
	     <option value="${commodity.commodityID}">${commodity.commodityID}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req">*</span>
        </td>
      </tr>    
     
		 
      <tr>
        <td align="right">查询日期：</td>
        <td >
        <!-- 	<input name="zDate" type="text" class="text"  size="11" maxlength="10" value="" id="date"><input type="button" name="settleDateBtn" class="calendarImgButton" style="width:16"  onclick="return showCalendar('date','%Y-%m-%d', '24', true);">  -->
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
	var zDate=document.forms[0].zDate.value;
	var zStartCommodity=document.forms[0].zStartCommodity.value;
	if (zStartCommodity == "") {
		alert("商品代码不能为空！");
		return false;
	}
	if (zDate == "") {
		alert("查询日期不能为空！");
		return false;
	}
	if(!isDateFomat(zDate))
    {
        alert("查询日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
	
   if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=commodityTradeDayRecord&cleardate=" + zDate + "&commodityID=" + zStartCommodity + "&title=分商品成交记录表");
		else 
	window.showModalDialog("reportContainer.jsp?sign=commodityTradeDayRecord"  + "&cleardate=" + zDate  + "&commodityID=" + zStartCommodity + "&title=分商品成交记录表","", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>