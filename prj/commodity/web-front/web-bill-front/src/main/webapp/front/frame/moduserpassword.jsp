<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<base target="_self"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改用户密码</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
		jQuery(document).ready(function() {
		
			$("#frm").validationEngine('attach');
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						$("#frm").validationEngine('detach');
						//$('#frm').attr('action', 'action');
					$('#frm').submit();
				}
			}
		}	);
		});
		function showMsgBoxCallbak(result){
			if(result==1){
				closeDialog(1);
			}else{
				clearSubmitCount();
			}
		}
</script>
	</head>
	<body>
		<div class="main">
			<div class="warning">
				<div class="title font_orange_14b">
					操作小贴士:
				</div>
				<div class="content">
					原密码必须正确 否则无法修改！
				</div>
			</div>

			<form id="frm" name="frm"
				action="${frontPath}/app/mgr/self/passwordSelfSave.action"
				method="post">
				<input type="hidden" id="traderId" name="entity.traderID"
					value="${CurrentUser.traderID }" />
				<div class="form margin_10b">
					<div class="column1">
						密码修改：
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th scope="row">
								原密码：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<div class="validInput"><input type="password" id="oldPassword" maxLength="<fmt:message key="oldPassword" bundle="${proplength}" />" name="oldPassword"
									class="validate[required]"  value="${oldPassword }" onkeyup="value=value.replace(' ','')"/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入原密码！
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">
								新密码：
								<span class="font_orange_12">*</span>
							</th>
							<td>
								<div class="validInput"><input type="password" id="password" maxLength="<fmt:message key="password" bundle="${proplength}" />" name="entity.password"
									class="validate[required,custom[password],maxSize[16]] input_text"  value="${user.password }" onkeyup="value=value.replace(' ','')"/>
								&nbsp;</div>
								<div class="warning_tip">
									请输入新密码！
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">
								确认密码：
								<span class="font_orange_12">*</span>
							</th>
							<td><div class="validInput">
								<input type="password" id="truePassword" maxLength="<fmt:message key="truePassword" bundle="${proplength}" />" name="truePassword"
									class="validate[required,equals[password]] input_text" value="${user.password }" onkeyup="value=value.replace(' ','')"/>
								&nbsp;</div>
								<div class="warning_tip">
									确认密码必须与新密码一致！
								</div>
							</td>
						</tr>
					</table>
					<div class="page text_center">
					<label><input type="button" id="update" value="修改" class="btbg"/></label>&nbsp;&nbsp;
					<label><input type="button" value="关闭" onclick="window.close();" class="btbg"/></label>
				</div>
				</div>
			</form>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/dialogmessage.jsp"%>