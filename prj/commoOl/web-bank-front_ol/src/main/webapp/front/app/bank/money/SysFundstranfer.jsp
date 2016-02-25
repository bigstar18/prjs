<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.trade.bank.vo.SystemMessage"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统间资金划转</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
	   jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			
			$("#performBtn").click(function(){//预签约
				if($("#subfrm").validationEngine('validate')){
				if($("#collectionSys").val())
					if(affirm("您确认要执行此操作吗？")){
						$("#subfrm").submit();
					}
				}
			});
			
	    });
		function reset(obj){
			subfrm.PersonName.value = "";
			subfrm.AmoutDate.value = "";
			subfrm.BankName.value = "";
			if(obj == "4"){
				subfrm.OutAccount.value == "";
			}
		}
		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
			$("#frm").submit();
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/money/gotoSysFundstranferPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以进行系统间资金划转操作。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/money/SysMoneyTransfer.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th width="40%" align="center">付款系统：</th>
							<td>
								<select id="PaycollectionSys" name="PaycollectionSys" class="validate[required]"style="width: 127px;">
									<option value="-1">请选择</option>
									<c:forEach var="trade" items="${collectionSys}">
										<option value="${trade.systemID}">${trade.systemName}</option>
								   	</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">收款系统： </th>
							<td>
								<select id="collectionSys" name="collectionSys" class="validate[required]"style="width: 127px;">
									<option value="-1">请选择</option>
									<c:forEach var="trade" items="${collectionSys}">
										<option value="${trade.systemID}">${trade.systemName}</option>
								   </c:forEach>
								</select> 
							</td>
						</tr>
						<tr>
							<th scope="row">转账金额：</th>
							<td>
								<input id="money" name="money" class="validate[required,custom[doubleCus],maxSize[9]]" style="width: 127px;"/>
							</td>
						</tr>
						<tr id="passwordtr">
							<th scope="row" id="passwordth">资金密码：</th>
							<td>
								<input id="password" type="password" name="password" class="validate[required,custom[password],maxSize[16]]" style="width: 127px;"/>
							</td>
						</tr>
						
					</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
							
								<input type="button" id="performBtn"  value="确认" class="btbg"/>
							</span>
						</label>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>