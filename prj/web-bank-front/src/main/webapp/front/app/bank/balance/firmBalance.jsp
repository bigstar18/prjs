<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>余额查询</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//执行转账%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm()){
						if($("#bankID").val()=='66'){
							alert("国付宝不支持余额查询！");
							subfrm.action="${basePath}/bank/balance/gotoQueryMoneyPage.action";
						}
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showPasswordTr(){<%//通过银行编号确认是否要显示密码%>
			var bankIDs='${queryMoneyNeedPasswordBankID}';
			var bankID = $("#bankID").val();
			if(bankIDs && bankID && bankCheckPwd(bankIDs,bankID)){
				// document.getElementById("passwordtr").style.display="inline";
				document.getElementById("passwordth").innerText="银行密码：";
				document.getElementById("password").className="validate[required,custom[onlyNumberSp],minSize[6],maxSize[6]]";
			}else{//如果未选定银行信息
				if(${isCheckMarketPassword}){
					// document.getElementById("passwordtr").style.display="inline";
					document.getElementById("passwordth").innerText="交易所密码：";
					document.getElementById("password").className="validate[required,custom[password],maxSize[16]]";
				}else{
					document.getElementById("passwordtr").style.display="none";
					document.getElementById("password").className="";
				}
			}
	    	$("#subfrm").validationEngine("attach");
		}
		function bankCheckPwd(bankIDs,bankID){<%//银行是否判断密码%>
			if(!(bankIDs && bankIDs.length>=bankID.length)){<%//如果未设置需要判断密码的银行，则认为银行不需要判断%>
				return false;
			}
			if(bankIDs==bankID){<%//如果两个相等，说明只有这个银行判断密码%>
				return true;
			}
			if(bankIDs.indexOf(bankID+",")==0){<%//如果设置信息以本银行信息开头，认为需要验证密码%>
				return true;
			}
			bankIDs += ",";
			if(bankIDs.indexOf(","+bankID+",")>=0){<%//如果设置信息中包含本银行认为需要验证%>
				return true;
			}
			return false;
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/balance/gotoQueryMoneyPage.action"></form>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以查询您在银行及交易所的余额。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/balance/queryMoney.action" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row" width="30%">选择银行： </th>
							<td>
								<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">请选择</option>
									<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
										<option value="${firmIDAndAccount.bank.bankID}" <c:if test="${bank.bankID eq firmIDAndAccount.bank.bankID}">selected="selected"</c:if>>${firmIDAndAccount.bank.bankName}</option>
									</c:forEach>
								</select>
								&nbsp;&nbsp;
								<input type="button" id="performBtn"  value="查询" class="btbg"/>
							</td>
						</tr>
						<tr id="passwordtr" <c:if test="${!isCheckMarketPassword}">style="display: none;"</c:if>>
							<th scope="row" id="passwordth">验证密码：</th>
							<td>
								<input id="password" type="password" name="password" class="validate[required,custom[password],maxSize[16]]" style="width: 127px;"/>
								&nbsp;<script type="">showPasswordTr();</script>
							</td>
						</tr>
					</table>
				</form>
				<c:if test="${bank != null}">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row" width="30%">银行： </th>
							<td>${bank.bankName}</td>
						</tr>
						<tr>
							<th scope="row">市场总余额：</th>
							<td><fmt:formatNumber value="${marketBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">市场冻结余额：</th>
							<td><fmt:formatNumber value="${frozenBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">市场可用余额：</th>
							<td><fmt:formatNumber value="${avilableBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">银行帐号：</th>
							<td>${bankAccount}&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">银行余额：</th>
							<td><fmt:formatNumber value="${bankBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
					</table>
				</c:if>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>