<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>�����ʺ�����</title>
       <link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>	
		<script>
		function valiate(){
			var newpwd=document.getElementById("newpwd").value;
			var oldpwd=document.getElementById("oldpwd").value;
			var flag=true;
			if(newpwd==oldpwd){
				flag=false;
			}
			return flag;
		}

		
			jQuery(document).ready(function() {
		    	$("#subfrm").validationEngine("attach");
				$("#performBtn").click(function(){
				flag=valiate();
				if(flag){
					if($("#subfrm").validationEngine('validate')){
						if(affirm("��ȷ��Ҫ���������޸���")){
							$("#subfrm").submit();
						}
					}
				}else{
				alert("���������������ͬ�������޸�");
				}
				});
		    });
			function showMsgBoxCallbak(result,msg){<%//�ص�����%>
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
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Խ���ϵͳ�ʽ�������޸ġ�</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/user/changePassword.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th width="40%">������ԭ���룺&nbsp;</th>
							<td><input type="password" id="oldpwd" name="oldpwd" class="validate[required,custom[password],maxSize[16]]"></td>
						</tr>
						<tr>
							<th>�����������룺&nbsp;</th>
							<td><input type="password" id="newpwd" name="newpwd"  class="validate[required,custom[password],maxSize[16]]"></td>
						</tr>
						<tr>
							<th>���ظ������룺&nbsp;</th>
							<td><input type="password" id="rnewpwd" name="rnewpwd"  class="validate[required,custom[password],maxSize[16],equals[newpwd]]"></td>
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