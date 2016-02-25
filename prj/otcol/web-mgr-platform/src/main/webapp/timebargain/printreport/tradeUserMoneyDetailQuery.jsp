<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
   //List firmAscList=getList("select firmId from t_firm order by firmId");
   //List firmDescList=getList("select firmId from t_firm order by firmId desc");
   //pageContext.setAttribute("firmAscList",firmAscList);
  // pageContext.setAttribute("firmDescList",firmDescList);
   List brokerIdList=getList("select brokerId from m_b_broker");
   pageContext.setAttribute("brokerIdList",brokerIdList);

   List commodityStartList = getList("select commodityID from t_commodity order by commodityID asc");
   List commodityEndList = getList("select commodityID from t_commodity order by commodityID desc");
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
		<b>当日交易商资金情况表</b>
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
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" value="" style="ime-mode:disabled" size=8 maxlength=10 onblur="fandealcontent(this)"onkeypress="notSpace()" >
         <div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
        </td>
      </tr>      

	<tr>
        <td align="right">查询日期</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
      </tr>

	<tr>
        <td align="right">选择排序</td>
        <td>
         <SELECT name="order"  >
         <OPTION value="">请选择</OPTION>
		 <option value="firmid">交易商</option>
	     <option value="todaybalance">可用资金</option>
		 <option value="allMoney">本次余额</option>
          </SELECT>   
		  <span class="req">*</span>
        </td>
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

	var order=document.forms[0].order.value;
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
	if(!isDateFomat(zDate))
    {
        alert("查询日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
		if (order == "") {
		alert("选择排序不能为空！");
		return false;
	}
			if(zStart == "" && zEnd == ""){
		zStart=null;
		zEnd=null;
	}
	if(brokerId == ""){
		brokerId=null;
	}
    if(expif(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=tradeUserMoneyDetail&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&cleardate=" + zDate +  "&order=" + order + "&title=当日交易商资金情况表&brokerId="+brokerId);
		else
	window.showModalDialog("reportContainer.jsp?sign=tradeUserMoneyDetail&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&cleardate=" + zDate +  "&order=" + order + "&title=当日交易商资金情况表&brokeRId="+brokerId,"", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>