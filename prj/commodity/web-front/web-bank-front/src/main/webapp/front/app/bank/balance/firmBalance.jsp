<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>����ѯ</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//ִ��ת��%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm()){
						if($("#bankID").val()=='66'){
							alert("��������֧������ѯ��");
							subfrm.action="${basePath}/bank/balance/gotoQueryMoneyPage.action";
						}
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showPasswordTr(){<%//ͨ�����б��ȷ���Ƿ�Ҫ��ʾ����%>
			var bankIDs='${queryMoneyNeedPasswordBankID}';
			var bankID = $("#bankID").val();
			if(bankIDs && bankID && bankCheckPwd(bankIDs,bankID)){
				// document.getElementById("passwordtr").style.display="inline";
				document.getElementById("passwordth").innerText="�������룺";
				document.getElementById("password").className="validate[required,custom[onlyNumberSp],minSize[6],maxSize[6]]";
			}else{//���δѡ��������Ϣ
				if(${isCheckMarketPassword}){
					// document.getElementById("passwordtr").style.display="inline";
					document.getElementById("passwordth").innerText="���������룺";
					document.getElementById("password").className="validate[required,custom[password],maxSize[16]]";
				}else{
					document.getElementById("passwordtr").style.display="none";
					document.getElementById("password").className="";
				}
			}
	    	$("#subfrm").validationEngine("attach");
		}
		function bankCheckPwd(bankIDs,bankID){<%//�����Ƿ��ж�����%>
			if(!(bankIDs && bankIDs.length>=bankID.length)){<%//���δ������Ҫ�ж���������У�����Ϊ���в���Ҫ�ж�%>
				return false;
			}
			if(bankIDs==bankID){<%//���������ȣ�˵��ֻ����������ж�����%>
				return true;
			}
			if(bankIDs.indexOf(bankID+",")==0){<%//���������Ϣ�Ա�������Ϣ��ͷ����Ϊ��Ҫ��֤����%>
				return true;
			}
			bankIDs += ",";
			if(bankIDs.indexOf(","+bankID+",")>=0){<%//���������Ϣ�а�����������Ϊ��Ҫ��֤%>
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
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Բ�ѯ�������м�����������</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/balance/queryMoney.action" method="post">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row" width="30%">ѡ�����У� </th>
							<td>
								<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">��ѡ��</option>
									<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
										<option value="${firmIDAndAccount.bank.bankID}" <c:if test="${bank.bankID eq firmIDAndAccount.bank.bankID}">selected="selected"</c:if>>${firmIDAndAccount.bank.bankName}</option>
									</c:forEach>
								</select>
								&nbsp;&nbsp;
								<input type="button" id="performBtn"  value="��ѯ" class="btbg"/>
							</td>
						</tr>
						<tr id="passwordtr" <c:if test="${!isCheckMarketPassword}">style="display: none;"</c:if>>
							<th scope="row" id="passwordth">��֤���룺</th>
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
							<th scope="row" width="30%">���У� </th>
							<td>${bank.bankName}</td>
						</tr>
						<tr>
							<th scope="row">�г�����</th>
							<td><fmt:formatNumber value="${marketBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">�г�������</th>
							<td><fmt:formatNumber value="${frozenBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">�г�������</th>
							<td><fmt:formatNumber value="${avilableBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">�����ʺţ�</th>
							<td>${bankAccount}&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">������</th>
							<td><fmt:formatNumber value="${bankBalance}" pattern="#,##0.00" />&nbsp;</td>
						</tr>
					</table>
				</c:if>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>