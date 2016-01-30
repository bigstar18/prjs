<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户信息</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${frontPath }/app/integrated/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
			//查看基本信息
			$("#msg").click(function() {
				flag=0;
				closediv();
				$("#msg").parent().attr("class","c_titlebt_on");
				$("#pass").parent().attr("class","c_titlebt");
				$("#checkCode").parent().attr("class","c_titlebt");
				$("#shop").parent().attr("class","c_titlebt");
				$("#message").show(); 
				$("#pwd").hide(); 
				$("#code").hide(); 
				$("#shopDetail").hide(); 
			});
	    });
		
	//回掉函数
	function showMsgBoxCallbak(result,msg){
		if(result==1){
			var url = "${frontPath}/app/integrated/userInformation/userInformation.action";
			$("#queryfrm").attr("action",url);
			$("#queryfrm").submit();
		}else{
			$("#frm1").validationEngine('attach');
			clearSubmitCount();
		}
	}
    </script>
	</head>
	<body>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<form id="queryfrm" action="${frontPath}/app/integrated/userInformation/userInformation.action" method="post"></form>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此展示您的开户信息。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="frm" name="frm" action="${frontPath}/app/integrated/userInformation/userInformation.action" method="post" target="frame">
					<input name="entity" type="hidden" value="${entity.traderID}"/>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th width="14%" align="center">交易员代码：</th>
							<td><span>${CurrentUser.traderID }</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">交易员用户名： </th>
							<td><span>${CurrentUser.userID}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">交易员名称：</th>
							<td><span>${CurrentUser.name}</span>&nbsp;</td>
						</tr>
						
						<tr>
							<th scope="row">交易商代码：</th>
							<td><span>${CurrentUser.belongtoFirm.firmID}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">交易商名称：</th>
							<td><span>${firm.name}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">交易商地址：</th>
							<td><span>${firm.address}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">联系人：</th>
							<td><span>${firm.contactMan}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">联系电话：</th>
							<td><span>${firm.phone}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">手机号码：</th>
							<td><span>${firm.mobile}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">传真：</th>
							<td><span>${firm.fax}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">邮编：</th>
							<td><span>${firm.postCode}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">电子邮箱：</th>
							<td><span>${firm.email}</span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">创建时间： </th>
							<td><span><fmt:formatDate value="${CurrentUser.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>&nbsp;</td>
						</tr>
						<tr>
							<th scope="row">修改时间：</th>
							<td><span><fmt:formatDate value="${CurrentUser.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>&nbsp;</td>
						</tr>
						
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>