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
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//ִ��%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
						 if(${flag=='1'}){
						    subfrm.action="${basePath}/bank/firmInfo/PreRgsAccPage.action";
						 }else if(${flag=='2'}){
						    subfrm.action="${basePath}/bank/firmInfo/RgsQueryPage.action";
						 }else if(${flag=='3'}){
						    subfrm.action="${basePath}/bank/firmInfo/delRegistByPlat.action";
						 }
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
     
  <body><form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action"></form>
  <iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Բ�ѯ���ڽ��׹�˾����Ϣ��</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
			<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/PreRgsAccPage.action" method="post" target="frame">
			<input type="hidden" value="${flag}"/>
				 <table>
					<tr>
						<th>�����ʽ����룺&nbsp;</th>
						<td><input type="password" name="pwd" id="pwd" class="validate[required,maxSize[6]]"/>Ԥ��ƽ̨�ʽ�����,���ڳ����,ǩ��Լ</td>
					</tr>
					<!--  <tr>
						<th>���������˻����룺&nbsp;</th>
						<td><input type="password" name="bankpwd" id="bankpwd" class="validate[required,maxSize[30]]"/></td>
					</tr>-->
					<input name="firmID" type="hidden" value="${firmID}"/>
					<input name="bankID" type="hidden" value="${bankID}"/>
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
		</form>
  </body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>