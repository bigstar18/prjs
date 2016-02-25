<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>华夏他行签约信息</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>		
		
		
	<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//执行%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("您确认要执行此操作吗？")){
				$("#subfrm").submit();
				}
				
				}
			});
	    });
		function AccountPropChange(){
		var AccountProp = document.getElementsByName("AccountProp");
		var strNew;
		for(var i=0;i<AccountProp.length;i++){
			if(AccountProp.item(i).checked){
				strNew = AccountProp.item(i).getAttribute("value");  
				break;
			}else{
				continue;
			}
		}
		if(strNew == "1"){
			document.getElementById('TrForCompany').style.display = "none";
		}else if(strNew == "0"){
			document.getElementById('TrForCompany').style.display = "block";
		}else{
			alert("未知错误");
		}
		}
		function NoteFlagChange(){
		var NoteFlag = document.getElementsByName("NoteFlag");
		var str;
		for(var i=0;i<NoteFlag.length;i++){
			if(NoteFlag.item(i).checked){
				str = NoteFlag.item(i).getAttribute("value");  
				break;
			}else{
				continue;
			}
		}
		if(str == "0"){
			document.getElementById('Phone').style.display = "none";
		}else if(str == "1"){
			document.getElementById('Phone').style.display = "block";
		}else{
			alert("未知错误");
		}
		}
		
	  function showMsgBoxCallbak(result,msg){<%//回掉函数%>
			$("#frm").submit();
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoRgstDelFirmInfoPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以进行华夏他行签约操作。</div>
			</div>
			<div class="clear"></div>
			<div id="u24" class="u24"  >						
			<div id="message" class="form margin_10b">			
						
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/gotoRgstHX.action" method="post" >
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
					<tr>
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">							
							<input name="firmID" type=text readonly value=${firmIDAndAccount.firm.firmID}>
							<input name="bankID" type=hidden readonly value=${firmIDAndAccount.bank.bankID}>						
					</td>
				</tr>
				<tr >
					<td align="right">子账户名称：&nbsp;</td>
					<td align="left">
						<input name="accountName1" id="accountName1" type=text class="validate[required,maxSize[10]]"style="width: 150px"><span>*</span>				
					</td>
				</tr>
				<tr>
					<td align="right">账户类型：&nbsp;</td>
					<td align="left">
						<input onclick="AccountPropChange()" type = "radio" value = "1" name="AccountProp" id="AccountProp" />个人
						<input onclick="AccountPropChange()" type = "radio" value = "0" name="AccountProp" id="AccountProp" CHECKED />企业
					</td>
				</tr>
				<tr >
					<td align="right">关联账号(卡号)：&nbsp;</td>
					<td align="left">
					
						<input name="RelatingAcct" id="RelatingAcct" value="" type=text  class="validate[required,maxSize[32]]" style="width: 150px"><span>*</span>	
					
					</td>
				</tr>
				
				<tr >
					<td align="right">关联账户名：&nbsp;</td>
					<td align="left">
					
						<input name="RelatingAcctName" id="RelatingAcctName" readonly value=${firmIDAndAccount.firm.name} type=text class="validate[required,maxSize[30]]" style="width: 150px"><span>*</span>
						
					</td>
				</tr>
				<tr >
					<td align="right">开户行：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBank" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="RelatingAcctBank" id="RelatingAcctBanks" style="width: 150px" >
							<option value="0" selected>--请选择--</option>
							<option value="人民银行">中国人民银行</option>
							<option value="工商银行">中国工商银行</option>
							<option value="农业银行">中国农业银行</option>
							<option value="中国银行">中国银行</option>
							<option value="建设银行">中国建设银行</option>
							<option value="农业发展银行">中国农业发展银行</option>
							<option value="交通银行">交通银行</option>
							<option value="中信银行">中信银行</option>
							<option value="光大银行">光大银行</option>
							<option value="华夏银行">华夏银行</option>
							<option value="民生银行">民生银行</option>
							<option value="广东发展银行">广东发展银行</option>
							<option value="深圳发展银行">深圳发展银行</option>
							<option value="招商银行">招商银行</option>
							<option value="兴业银行">兴业银行</option>
							<option value="浦东发展银行">上海浦东发展银行</option>
							<option value="北京银行">北京银行</option>
							<option value="农村商业银行">农村商业银行</option>
							<option value="农村信用社">农村信用社</option>
							<option value="城市信用社">城市信用社</option>
							<option value="邮政储蓄银行">中国邮政储蓄银行</option>
						</select>
					</td>
				</tr>
				
				
				<tr>
					<td align="right">开户行地址：&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankAddr" id="RelatingAcctBankAddr"  value="" type=text  class="validate[required]"  style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr >
					<td align="right">开户行行号：&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankCode" id="RelatingAcctBankCode" value="" type=text  class="validate[required,custom[onlyNumberSp],maxSize[12]]" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="padding-left:148px" id="TrForCompany" style="display: block;">
							<table border="0">
								<tr >
									<td align="right">联系人：&nbsp;</td>
									<td align="left">
										<input name="PersonName" id="PersonName" type=text  class="validate[required,maxSize[50]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr >
									<td align="right">办公电话：&nbsp;</td>
									<td align="left">
										<input name="OfficeTel" id="OfficeTel" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right" >移动电话：&nbsp;</td>
									<td align="left">
										<input name="MobileTel" id="MobileTel" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr >
									<td align="right">联系地址：&nbsp;</td>
									<td align="left">
										<input name="Addr" id="Addr" type=text  class="validate[required,maxSize[30]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr >
									<td align="right">邮政编码：&nbsp;</td>
									<td align="left">
										<input name="ZipCode" id="ZipCode" type=text  class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr>
									<td align="right">法人姓名：&nbsp;</td>
									<td align="left">
										<input name="LawName" id="LawName" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr >
					<td align="right">是否需要短信通知：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="NoteFlag" CHECKED onclick="NoteFlagChange()"/>需要
						<input type = "radio" value = "0" name="NoteFlag" onclick="NoteFlagChange()"/>不需要
					</td>
				</tr>
				<tr id="Phone">
					<td align="right" >短信通知手机号：&nbsp;</td>
					<td align="left">
						<input name="NotePhone"  id="NotePhone" value="" type=text   class="validate[required,maxSize[20]]" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr >
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="eMail" value="" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;</font>
					</td>
				</tr>
				<tr >
					<td align="right">复核标识：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="CheckFlag" CHECKED/>需要
						<input type = "radio" value = "0" name="CheckFlag" />不需要
					</td>
				</tr>
					
			</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="确认" class="btbg"/>
							</span>
						</label>
					</div>
					
				
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>