<%@ page contentType="text/html;charset=GBK"%>
<% request.setAttribute("pageTitle","用户注册");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" flush="false"></jsp:include>
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="${publicPath }/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${publicPath}/js/PasswordMode.js"></script>
<script>
jQuery(document).ready(function() {
	jQuery("#registfrm").validationEngine({
		ajaxFormValidation : true,
		ajaxFormValidationURL : "../../checkneedless/commoncheck/checkFirmApplyForm.action?t="+Math.random(),
		onAjaxFormComplete : ajaxValidationCallback,
		onBeforeAjaxFormValidation : beforeCall
	});
	//提交前事件
	function beforeCall(form, options) {
		return true;
	}
	//提交后事件
	function ajaxValidationCallback(status, form, json, options) {
		//如果返回成功
		if (status === true) {
			if (!$("#agree").is(":checked")) {
				alert("请您先同意我们的条款");
			}else{
				var vaild = affirm("您确定要操作吗？");
				if(vaild){
					//注册信息URL
					var registerUrl = $("#register").attr("action");
					//全URL路径
					var url = "${basePath}"+registerUrl;
					$("#registfrm").attr("action",url);
					registfrm.submit();
				}
			}
		}
	}
	$("#register").click(function(check) {
		if ($("#registfrm").validationEngine('validateform')) {
		}
	});
});

//显示颜色  
function pwStrength(pwd){
	PasswordMode.pwStrength(pwd,"strength_L","strength_M","strength_H");
	return;
}
/**更换验证码图片*/
function changeregistimg() {
	document.getElementById("verifycode").src = "${publicPath}/jsp/loginimage.jsp?"+ Math.random();
}
function onlyLetterNumber(ch,vec){
	var s = this.value;
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
//回调函数
function showMsgBoxCallbak(result,msg){
	if(result==1){
		window.location.href="${basePath}";
	}
}
</script>
<!-------------------------State Begin------------------------->
<div class="state stateBg">
	<div class="step">
		注册步骤：
		<span>1.填写注册信息……</span> 2.注册成功 3.后台审核成功后指定用于登录的交易员代码，再通过电话或其他联系方式通知注册用户。<div class="step2R">
		<em>*</em>为必填项
	</div>
	</div>
</div>
<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<iframe  name="hiddenframe" width=0 height=0  application='yes'></iframe>
<form id="registfrm" action="<%=basePath%>/checkneedless/firmapply/register.action" method="post" target="hiddenframe">
  <input name="entity.userID" value="${entity.userID}" type="hidden"/>
	<div class="bodyStep2">
		<!-------------------------↓设置帐户信息------------------------->
		<h3>↓设置帐户信息</h3>
		<div class="clear"></div>
		<fieldset>
			<legend>
				↓设置帐户信息
			</legend>
			<input id="createTime" type="hidden" name="entity.createTime" value="" />
			<input id="status" type="hidden" name="entity.status" value="0" />
			<div>
				<label width="25%">用户名：</label>
				<label width="35%">
					<input id="userID" name="entity.userID" class="validate[required,maxSize[10],custom[onlyLetterNumber],ajax[checknoUserID]] datepicker"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">密码：</label>
				<label width="35%">
					<input id="password" name="entity.password" type="password" class="validate[required,minSize[6],maxSize[8],custom[onlyLetterNumber]]" onKeyUp="pwStrength(this.value);" onblur="pwStrength(this.value)"/><em>*</em>
				</label>
			</div>
			<div>
				<label class="fontSize">密码强度：</label>
				<label width="35%">
					<table width="210" border="0" cellspacing="0" cellpadding="0" bordercolor="#eeeeee" height="15" style='display:inline'>  
						<tr align="center" bgcolor="#f5f5f5">  
							<td width="33%" id="strength_L" class="fontSize">弱</td>  
							<td width="33%" id="strength_M" class="fontSize">中</td>  
							<td width="33%" id="strength_H" class="fontSize">强</td>  
						</tr>
					</table>
				</label>
			</div>
			<div>
				<label width="25%">
					确认密码：
				</label>
				<label  width="35%">
					<input id="password1" type="password" class="validate[required,equals[password]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">交易商名称：</label>
				<label  width="35%">
					<input id="name" name="entity.name" class="validate[required,maxSize[15]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">交易商全称：</label>
				<label width="35%">
					<input id="fullName" name="entity.fullName" class="validate[maxSize[30]]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">交易商类型：</label>
				<fieldset class="type" width="35%" align="left">
					<c:forEach var="type" items="${com_firmTypeMap}" varStatus="status">
						<input id="type${type.key}" value="${type.key}" type="radio" name="entity.type" <c:if test="${status.index==0}">checked="checked"</c:if>/><label class="label" onclick="type${type.key}.click();">${type.value}</label>
					</c:forEach>
				</fieldset>
			</div>
			<div>
				<label width="25%">所属地域编码 ：</label>
				<label width="35%">
					<input id="zoneCode" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.zoneCode"/><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label width="25%">所属行业编码：</label>
				<label width="35%">
					<input id="industryCode" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.industryCode" /><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label width="25%">组织机构代码：</label>
				<label width="35%">
				<input id="organizationCode" type="text" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.organizationCode" value="${entity.organizationCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label width="25%">法人代表：</label>
				<label width="35%">
					<input id="corporateRepresentative" class="validate[maxSize[30]]" name="entity.corporateRepresentative"/><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label widht="25%">证件类型：</label>
				<label width="35%">
					<select id="certificateType" name="entity.certificate.code"  style="width: 133px;">
						<c:forEach var="certificate" items="${certificateList}">
							<option value="${certificate.code}">${certificate.name}</option>
						</c:forEach>
					</select><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">证件号码：</label>
				<label width="35%">
					<input id="certificateNO" name="entity.certificateNO" class="validate[required,custom[onlyLetterNumber],maxSize[30]]"/><em>*</em>
				</label>
			</div>
		</fieldset>
		<!-------------------------↓填写联系方式------------------------->
		<h3>↓填写联系方式</h3>
		<fieldset>
			<div>
				<label width="25%">联系人：</label>
				<label width="35%">
					<input id="contactMan" class="validate[required,maxSize[30]]" name="entity.contactMan"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">联系地址：</label>
				<label width="35%">
					<input id="address" name="entity.address" class="validate[required,maxSize[50]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">电话：</label>
				<label width="35%">
					<input id="phone" class="validate[phone]" name="entity.phone" data-prompt-position="centerRight:5"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">手机号：</label>
				<label width="35%">
					<input id="mobile" class="validate[required,phone]" name="entity.mobile" data-prompt-position="centerRight:5"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">传真：</label>
				<label width="35%">
					<input id="fax" name="entity.fax" class="validate[fax]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">电子邮箱：</label>
				<label width="35%">
					<input id="email" class="validate[email]" name="entity.email"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">邮编：</label>
				<label width="35%">
					<input id="postcode" class="validate[required,integer]" maxlength="6" name="entity.postcode"/><em>*</em>
				</label>
			</div>
		</fieldset>
		<!-------------------------↓提交------------------------->
		<h3></h3>
		<div class="center">
			<a href="#" class="link_red_12_">
				<span class="label">
					<input id="agree" name="take2" class="take" type="checkbox" checked="checked"/>
				</span>同意服务条款及交易规则 点此阅读服务条款
			</a>
		</div>
		<div class="center">
			<input id="register" type="button" class="button" action="/checkneedless/firmapply/register.action" value="提交注册信息" />
		</div>
	</div>
</form>
<!-------------------------Body End------------------------->
<jsp:include page="/front/frame/footer.jsp" flush="false"></jsp:include>
<%@include file="/front/public/jsp/commonmsg.jsp"%>