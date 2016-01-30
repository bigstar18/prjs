<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<base target="_self"/> 
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<title>仓单发票信息</title>
	<link href="${skinPath}/css/mgr/memberadmin/module.css"
		rel="stylesheet" type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
</head>
	<body>
		<div class="main">
			<div class="warning">
				<div class="title font_orange_14b">温馨提示:</div>
				<div class="content">开具有发票的仓单才能够查看，否则查看失败！</div>
			</div>
			<iframe  name="hiddenframe" width=0 height=0  application='yes'></iframe>
			
				<div class="form margin_10b">
					<div class="column1">
						发票信息：
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row">
								仓单号：
							</th>
							<td width="70%">${invoiceinform.stockId }</td>
						</tr>
						<tr>
							<th scope="row">
								品种：
							</th>
							<td>${invoiceinform.breedName }</td>
						</tr>
						<tr>
							<th scope="row">
								仓库编号：
							</th>
							<td>${invoiceinform.warehouseId }</td>
						</tr>
						<tr>
							<th scope="row">
								发票类型：
							</th>
							<td>
							<c:if test="${invoiceinform.invoiceType eq 1}">公司发票</c:if>
							<c:if test="${invoiceinform.invoiceType eq 0 }">个人发票</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">
								数量：
							</th>
							<td>${invoiceinform.quantity }</td>
						</tr>
						<c:if test="${invoiceinform.invoiceType eq 1 }">
						<tr>
							<th scope="row">
								公司名称：
							</th>
							<td>${invoiceinform.companyName }</td>
						</tr>
						<tr>
							<th scope="row">
								纳税人识别码：
							</th>
							<td>${invoiceinform.dutyParagraph }</td>
						</tr>
						<tr>
							<th scope="row">
								开户银行：
							</th>
							<td>${invoiceinform.bank }</td>
					    </tr>
						<tr>
							<th scope="row">
								开户帐号：
							</th>
							<td>${invoiceinform.bankAccount }</td>
					    </tr>
					     <tr>
							<th scope="row">
								公司注册电话：
							</th>
							<td>${invoiceinform.phone }</td>
					    </tr>
					    <tr>
							<th scope="row">
								公司注册地址：
							</th>
							<td>${invoiceinform.address }</td>
					    </tr>
						</c:if>
						
						<c:if test="${invoiceinform.invoiceType eq 0 }">
						<tr>
							<th scope="row">
								收票人姓名：
							</th>
							<td>${invoiceinform.name }</td>
						</tr>
						<tr>
							<th scope="row">
								个人电话：
							</th>
							<td>${invoiceinform.phone }</td>
					    </tr>
					    <tr>
							<th scope="row">
								个人详细地址：
							</th>
							<td>${invoiceinform.address }</td>
					    </tr>
						</c:if>
						
					</table>
					<div class="page text_center">
					<input type="button" value="关闭" onclick="window.close();" class="btbg"/>
				</div>
				</div>
			</form>
		</div>
	</body>
</html>