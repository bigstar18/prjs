
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%><%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
request.setAttribute("commoditySelect", lookupMgr.getSelectLabelValueByTableDistinctCommodityID("T_SettleHoldPosition", "commodityID","commodityID"," order by commodityID "));
String commodityID = "";
List list = (List)request.getAttribute("commoditySelect");
if (list != null && list.size() > 0) {
	LabelValue lv = (LabelValue)list.get(1);
	if (lv != null) {
		commodityID = lv.getValue();
	}
}
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    commodity_onchange();
}
//query_onclick
function query_onclick()
{
  if (statQueryForm.year.value != "") {
  	  var Date1 = new Date(statQueryForm.year.value,0,0); 
  	  //alert(Date1.getFullYear()+1); 
 	  if (Date1.getFullYear()+1 != statQueryForm.year.value || statQueryForm.year.value.length != 4) {
  	  	alert("年份格式不正确！");
  	  	return false;
    }
  }

  if (statQueryForm.month.value != "") {
  	var Date1 = new Date(0,statQueryForm.month.value,0);
  	var month = statQueryForm.month.value - 1;
  	if (Date1.getMonth()+1 != statQueryForm.month.value || statQueryForm.month.value.length != 2) {
  		alert("月份格式不正确！");
  		return false;
 	 }
  }
   if (statQueryForm.day.value != "") {
  	var Date1 = new Date(0,0,statQueryForm.day.value);
	if (Date1.getDate()!= statQueryForm.day.value || statQueryForm.day.value.length != 2) { 
	 	alert("日期格式不正确！");
  		return false;
 	 }
  }
  if (statQueryForm.commodityID.value == "请选择") {
      alert("请选择商品代码！");
  	  return false;
  }
  statQueryForm.crud.value = statQueryForm.commodityID.value;
  statQueryForm.submit();
}

function commodity_onchange(){

	statQueryForm.crud.value = '<%=commodityID%>';
	statQueryForm.submit();
}

</script>
	</head>

	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listPair"
						method="POST" styleClass="form" target="ListFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0"  cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">年：</td>     
						            <td>  
										<html:text property="year" styleClass="text" maxlength="4" style="ime-mode:disabled"  size="12"onkeypress="onlyDate()"></html:text>
									</td>
									
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">月：</td>
                                    <td>
                                        <html:text property="month" styleClass="text" maxlength="2" size="12"  style="ime-mode:disabled"  onkeypress="onlyDate()"></html:text>
                                    </td>	
                                    <td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">日：</td>
                                    <td>
                                        <html:text property="day" styleClass="text" maxlength="2"  style="ime-mode:disabled"   size="12"onkeypress="onlyDate()"></html:text>
                                    </td>	
                                    <td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
                                    <td align="right">商品代码：</td>
                                    <td>
                                        <html:select property="commodityID" style="width:80" >
                                        	<html:options collection="commoditySelect" property="value"  style="ime-mode:disabled"  labelProperty="label"/>
                                        </html:select>
                                    </td>	
                                    <td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<input type="reset"  style="width:60" class="button" value="重置" />
									</td>																	
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
