<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.trade.bank.vo.CorrespondValue"%>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>������ǩԼ����</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script type="text/javascript">
		  jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			
			$("#preRgs").click(function(){//ԤǩԼ
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						subfrm.action="${basePath}/bank/firmInfo/gotoInputInfo.action";
						$("#subfrm").submit();
					}
				}
			});
			
			$("#DelAcc").click(function(){//��Լ�˺�
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						subfrm.action="${basePath}/bank/firmInfo/gotoUnsign.action?flag=3";
						$("#subfrm").submit();
					}
				}
			});
			$("#ModAcc").click(function(){//�޸���Ϣ
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						subfrm.action="${basePath}/bank/firmInfo/gotoModAcc.action";
						$("#subfrm").submit();
					}
				}
			});
			$("#RgsAcc").click(function(){//ע���˺�
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						subfrm.action="${basePath}/bank/firmInfo/gotoRgsAccPage.action";
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showMsgBoxCallbak(result,msg){<%//�ص�����%>
		alert("firminfpo");
			$("#frm").submit();
		}
		</script>
	</head>
	<body>
	<!-------------------------Body Begin------------------------->
	<form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action"></form>
<div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">��ܰ��ʾ :</div>
		<div class="content">�ڴ�չʾ�����̸�����Ϣ��</div>
	</div>
<div class="main_copy">
	<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action" method="post">
		<div class="content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="tb_bg">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
								<tr class="font_b tr_bg">
									<td width="10%" align="center"><div class="ordercol" id="bankId">ƽ̨��</div></td>
									<td width="10%" align="center">ǩԼ��</td>
									<td width="10%" align="center">ǩԼ����</td>
									<td width="10%" align="center">�˻���</td>
									<td width="20%" align="center">�����˺�</td>
									<td width="10%" align="center">֤������</td>
									<td width="10%" align="center">֤������</td>
									<td width="20%" align="center">ǩԼ״̬</td>
								</tr><!--  <input type="hidden" name="account1" value="${firmInfo.account1}"/>-->
								<c:forEach var="firmInfo" items="${cvs}">
								<tr><input type="hidden" name="bankID" value="${firmInfo.bankID}"/>
									<td align="center">${firmInfo.firmID}</td>
									<td align="center">${firmInfo.contact}</td>
									<td align="center">${banksMap[firmInfo.bankID].bankName}
									</td>
									<td align="center">${firmInfo.accountName}</td>
									<td align="center">${firmInfo.account}</td>
									<td align="center">
									<c:forEach items="${cardTypeMap}" var="map">
										<c:if test="${firmInfo.cardType eq map.key}">
											<c:out value="${map.value}"/>
										</c:if>
 									</c:forEach>
									</td>
									<td align="center">${firmInfo.card}</td>
									<td align="center">
										<c:if test="${firmInfo.isOpen==0}">δǩԼ</c:if>
										<c:if test="${firmInfo.isOpen==1}">��ǩԼ</c:if>
									</td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<TABLE width=100%>
	<TR align=center>
		<TD>
						     <input type="button" id="preRgs"  value="ǩԼ" class="btbg"/>&nbsp;
							
							 <input type="button" id="DelAcc"  value="��Լ�˺�" class="btbg"/>&nbsp;
							 
							 <input type="button" id="ModAcc"  value="�޸���Ϣ" class="btbg"/>&nbsp;
		</TD>
	</TR>
	</TABLE>
	</form>
</div></div>
<!-------------------------Body End------------------------->
	</body>
</html>