<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTable("T_commodity", "commodityID",
						"commodityID"," where Status<>1 order by commodityID "));
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/interface/commodityManager.js"/>"></script>
<script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/engine.js"/>"></script>
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/util.js"/>"></script>	
		<title>
			修改开盘指导价
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (document.forms(0).commodityID.value == "") {
			alert("商品代码不能为空！");
			return false;
		}
		if (document.forms(0).openingPrice.value == "") {
			alert("开盘价不能为空！");
			return false;
		}
		
    	document.forms(0).submit();
    	document.forms(0).save.disabled = true;
	}
  
}

var xmlHttprequest;
function createXML(){
	if (window.ActiveXObject) {
    	xmlHttprequest = new ActiveXObject("Microsoft.XMLHTTP");
   	}else if (window.XMLHttpRequest) {
    	xmlHttprequest = new XMLHttpRequest();
   	}
}

//commodity_onchange
function commodity_onchange(){
	if (document.forms(0).commodityID.value == "") {
		alert("商品代码不能为空！");
		return false;
	}
   createXML();
   var url = "<c:url value="/timebargain/xtgl/agency.do?funcflg=getLastPrice&timeStamp="/>" + new Date().getTime();
   var queryStr = "commodityID=" + document.forms(0).commodityID.value;
   xmlHttprequest.onreadystatechange = operate;
   xmlHttprequest.open("POST", url, true);
   xmlHttprequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
   xmlHttprequest.send(queryStr);
}

function operate(){
	if (xmlHttprequest.readyState == 4) {
    	if (xmlHttprequest.status == 200) {
    		var xmlDoc = xmlHttprequest.responseXML;
    		var openingPrice = xmlDoc.getElementsByTagName("openingPrice")[0].childNodes[0].nodeValue;
    		document.forms(0).openingPrice.value = openingPrice;
    	}
    }
}

function cancle_onclick(){
	window.close();
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/xtgl/agency.do?funcflg=editLastPrice"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  修改开盘指导价
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											商品代码：
									</td>
									<td>
										<html:select property="commodityID"  style="width:80" onchange="javascript:commodity_onchange()">
                                          <html:options collection="commoditySelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
										开盘指导价：
									</td>
									<td>
										<input type="text" name="openingPrice" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>																																							
								<tr>
									<td colspan="3" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="save" styleClass="button"
											onclick="javascript:return cancle_onclick();">
											关闭
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>