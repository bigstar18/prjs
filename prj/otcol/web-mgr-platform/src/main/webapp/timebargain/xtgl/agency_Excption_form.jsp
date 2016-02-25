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
						"commodityID"," order by commodityID "));
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
			在线设置保证金
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
		if (document.forms(0).marginRate_B.value == "") {
			alert("买保证金不能为空！");
			return false;
		}
		if (document.forms(0).marginRate_S.value == "") {
			alert("卖保证金不能为空！");
			return false;
		}
		if (document.forms(0).marginAssure_B.value == "") {
			alert("买担保金不能为空！");
			return false;
		}
		if (document.forms(0).marginAssure_S.value == "") {
			alert("卖担保金不能为空！");
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
   var url = "<c:url value="/timebargain/xtgl/agency.do?funcflg=editOnlineMargin&timeStamp="/>" + new Date().getTime();
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
    		if (xmlDoc.getElementsByTagName("marginRate_B").length == 0) {
    			alert("此交易商不存在！");
    			return false;
    		}
    		var marginRate_B = xmlDoc.getElementsByTagName("marginRate_B")[0].childNodes[0].nodeValue;
    		var marginRate_S = xmlDoc.getElementsByTagName("marginRate_S")[0].childNodes[0].nodeValue;
    		var marginAssure_B = xmlDoc.getElementsByTagName("marginAssure_B")[0].childNodes[0].nodeValue;
    		var marginAssure_S = xmlDoc.getElementsByTagName("marginAssure_S")[0].childNodes[0].nodeValue;
    		var marginAlgr = xmlDoc.getElementsByTagName("marginAlgr")[0].childNodes[0].nodeValue;
    		document.forms(0).marginRate_B.value = marginRate_B;
    		document.forms(0).marginRate_S.value = marginRate_S;
    		document.forms(0).marginAssure_B.value = marginAssure_B;
    		document.forms(0).marginAssure_S.value = marginAssure_S;
    		document.forms(0).marginAlgr.value = marginAlgr;
    		isPencent();
    	}
    }
}

function isPencent(){
	if (document.forms(0).marginAlgr.value == "1") {
		document.getElementById("marginRate_BPercent").innerHTML = '%';
     	document.getElementById("marginRate_SPercent").innerHTML = '%';
     	document.getElementById("marginAssure_BPercent").innerHTML = '%';
     	document.getElementById("marginAssure_SPercent").innerHTML = '%';
	}else {
		document.getElementById("marginRate_BPercent").innerHTML = '';
     	document.getElementById("marginRate_SPercent").innerHTML = '';
     	document.getElementById("marginAssure_BPercent").innerHTML = '';
     	document.getElementById("marginAssure_SPercent").innerHTML = '';
	}
}

function cancle_onclick(){
	window.close();
}
//仅输入数字和.-允许输入
function onlyNumberAndSpecCharInput()
{
  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/xtgl/agency.do?funcflg=saveMargin"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  在线设置保证金
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
										买保证金：
									</td>
									<td>
										<html:text property="marginRate_B" maxlength="10" onkeypress="return onlyNumberAndSpecCharInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginRate_BPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											卖保证金：
									</td>
									<td>
										<html:text property="marginRate_S" maxlength="15" onkeypress="return onlyNumberAndSpecCharInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginRate_SPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											买担保金：
									</td>
									<td>
										<html:text property="marginAssure_B" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginAssure_BPercent"></span><span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
											卖担保金：
									</td>
									<td>
										<html:text property="marginAssure_S" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span id="marginAssure_SPercent"></span><span class="req">*</span>
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
						<html:hidden property="marginAlgr"/>
					</html:form>
				</td>
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
