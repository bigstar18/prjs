<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<base target="_self"/> 
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<title>�ֵ���Ʊ��Ϣ</title>
	<link href="${skinPath}/css/mgr/memberadmin/module.css"
		rel="stylesheet" type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
</head>
	<body>
		<div class="main">
			<div class="warning">
				<div class="title font_orange_14b">��ܰ��ʾ:</div>
				<div class="content">�����з�Ʊ�Ĳֵ����ܹ��鿴������鿴ʧ�ܣ�</div>
			</div>
			<iframe  name="hiddenframe" width=0 height=0  application='yes'></iframe>
			
				<div class="form margin_10b">
					<div class="column1">
						��Ʊ��Ϣ��
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row">
								�ֵ��ţ�
							</th>
							<td width="70%">${invoiceinform.stockId }</td>
						</tr>
						<tr>
							<th scope="row">
								Ʒ�֣�
							</th>
							<td>${invoiceinform.breedName }</td>
						</tr>
						<tr>
							<th scope="row">
								�ֿ��ţ�
							</th>
							<td>${invoiceinform.warehouseId }</td>
						</tr>
						<tr>
							<th scope="row">
								��Ʊ���ͣ�
							</th>
							<td>
							<c:if test="${invoiceinform.invoiceType eq 1}">��˾��Ʊ</c:if>
							<c:if test="${invoiceinform.invoiceType eq 0 }">���˷�Ʊ</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">
								������
							</th>
							<td>${invoiceinform.quantity }</td>
						</tr>
						<c:if test="${invoiceinform.invoiceType eq 1 }">
						<tr>
							<th scope="row">
								��˾���ƣ�
							</th>
							<td>${invoiceinform.companyName }</td>
						</tr>
						<tr>
							<th scope="row">
								��˰��ʶ���룺
							</th>
							<td>${invoiceinform.dutyParagraph }</td>
						</tr>
						<tr>
							<th scope="row">
								�������У�
							</th>
							<td>${invoiceinform.bank }</td>
					    </tr>
						<tr>
							<th scope="row">
								�����ʺţ�
							</th>
							<td>${invoiceinform.bankAccount }</td>
					    </tr>
					     <tr>
							<th scope="row">
								��˾ע��绰��
							</th>
							<td>${invoiceinform.phone }</td>
					    </tr>
					    <tr>
							<th scope="row">
								��˾ע���ַ��
							</th>
							<td>${invoiceinform.address }</td>
					    </tr>
						</c:if>
						
						<c:if test="${invoiceinform.invoiceType eq 0 }">
						<tr>
							<th scope="row">
								��Ʊ��������
							</th>
							<td>${invoiceinform.name }</td>
						</tr>
						<tr>
							<th scope="row">
								���˵绰��
							</th>
							<td>${invoiceinform.phone }</td>
					    </tr>
					    <tr>
							<th scope="row">
								������ϸ��ַ��
							</th>
							<td>${invoiceinform.address }</td>
					    </tr>
						</c:if>
						
					</table>
					<div class="page text_center">
					<input type="button" value="�ر�" onclick="window.close();" class="btbg"/>
				</div>
				</div>
			</form>
		</div>
	</body>
</html>