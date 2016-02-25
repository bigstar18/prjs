<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改密码</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//执行转账%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("您确认要修改密码吗？")){
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
			if(result==1){
				$("#frm").submit();
			}else{
				clearSubmitCount();
			}
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/user/gotoChangePasswordPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以修改您进行资金划转时交易所这边验证的资金密码。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/user/changePassword.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<c:if test="${isFirstChangePassword}">
						<tr>
							<td colspan="2" align="center" style="color: #FF0000;">
								您是初次设置资金密码，无需验证旧密码
							</td>
						</tr>
						</c:if>
						<c:if test="${!isFirstChangePassword}">
						<tr>
							<th width="14%" align="center">请输入原密码：</th>
							<td>
								<input type="password" id="oldpwd" name="oldpwd" class="validate[required,custom[password]]"/>
							</td>
						</tr>
						</c:if>
						<tr>
							<th scope="row">请输入新密码： </th>
							<td>
								<input type="password" id="newpwd" name="newpwd" class="validate[required,custom[password],maxSize[16]]"/>
							</td>
						</tr>
						<tr>
							<th scope="row">请重复新密码：</th>
							<td>
								<input type="password" id="newpwdagain" class="validate[required,equals[newpwd]]"/>
							</td>
						</tr>
					</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="修改" class="btbg"/>
							</span>
						</label>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>