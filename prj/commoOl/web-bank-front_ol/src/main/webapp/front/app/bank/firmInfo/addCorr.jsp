<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>ע�ύ������Ϣҳ��</title>
       <link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>		
	<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//ִ��%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
					$("#subfrm").submit();
				}
				}
			});
	    });
	
		function showMsgBoxCallbak(result,msg){<%//�ص�����%>
				$("#frm").submit();
		}
	</script>	
  </head>
  
  <body>
  <form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action"></form>
  <iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Խ���ע�ύ������Ϣ������</div>
			</div>
			<div class="clear"></div>
			<div id="u24" class="u24"  >						
			<div id="message" class="form margin_10b">			
						
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/gotoAddCorrPage.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
				<tr>
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">							
							<input name="firmID" type=text readonly value="${firmID}">						
					</td>
				</tr>
			    <tr>
			        <td align="right">ѡ�����У�&nbsp;</td>
					<td align="left">
						<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;">
							<option value="">��ѡ��</option>
							<c:forEach var="bank" items="${pageInfo.result}">
								<option value="${bank.bankID}">${bank.bankName}</option>
							</c:forEach>
						</select>
					</td>
			    </tr>
			    <tr>
					<td align="right">�����ʺţ�&nbsp;</td>
					<td align="left">
						<input id="account" name="account"  class="validate[required,maxSize[30]]" /><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">�ظ������ʺţ�&nbsp;</td>
					<td align="left">
						<input id="reaccount" name="reaccount" class="validate[required,equals[account],maxSize[30]]"/><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">�˻�����&nbsp;</td>
					<td align="left">
						<input id="accountName" name="accountName" class="validate[required,maxSize[30]]"><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType" class="validate[required,maxSize[30]]">
							<option value="">��ѡ��</option>
							<option value="1">���֤</option>
							<option value="8">��֯��������</option>
							<option value="9">Ӫҵִ��</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input id="card" name="card" value="" type=text class="validate[required,maxSize[30]]"><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<!--  <select  id="bankName" name="bankName">
							<option value="">--��ѡ��--</option>
							<option value="NBCB">��������</option>
							<option value="BOCOMM">��ͨ����</option>
							<option value="CIB">��ҵ����</option>
							<option value="BOC">�й�����</option>
							<option value="CEB">�������</option>
							<option value="CCB">��������</option>
							<option value="ABC">ũҵ����</option>
							<option value="ICBC">��������</option>
							<option value="CMB">��������</option>
							<option value="CMBC">��������</option>
							<option value="CITIC">��������</option>
							<option value="SPDB">�ַ�����</option>
							<option value="UNION">����</option>
							<option value="SDB">���ڷ�չ����</option>
							<option value="PINGANBANK">ƽ������</option>
							<option value="HXB">��������</option>
							<option value="GDB">������Ͽ����</option>
							<option value="CQRCB">����ũ����ҵ����</option>
							<option value="PSBC">������������</option>
						</select>-->
						<input id="bankName" name="bankName"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">	
						<input id="bankProvince" name="bankProvince"/>
					</td>
				</tr>
				<tr>
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<input id="bankCity" name="bankCity"/>
					</td>
				</tr>
				<tr>
					<td align="right">�绰��&nbsp;</td>
					<td align="left">
						<input name="mobile" value="" type=text>
					</td>
				</tr>
				<tr>
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="Email" value="" type=text>
					</td>
				</tr>
					
			</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="ȷ��" class="btbg"/>
							</span>
						</label>
					</div>
					
				
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
