<%@ page contentType="text/html;charset=GBK"%>
<% request.setAttribute("pageTitle","�û�ע��");%>
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
	//�ύǰ�¼�
	function beforeCall(form, options) {
		return true;
	}
	//�ύ���¼�
	function ajaxValidationCallback(status, form, json, options) {
		//������سɹ�
		if (status === true) {
			if (!$("#agree").is(":checked")) {
				alert("������ͬ�����ǵ�����");
			}else{
				var vaild = affirm("��ȷ��Ҫ������");
				if(vaild){
					//ע����ϢURL
					var registerUrl = $("#register").attr("action");
					//ȫURL·��
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

//��ʾ��ɫ  
function pwStrength(pwd){
	PasswordMode.pwStrength(pwd,"strength_L","strength_M","strength_H");
	return;
}
/**������֤��ͼƬ*/
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
//�ص�����
function showMsgBoxCallbak(result,msg){
	if(result==1){
		window.location.href="${basePath}";
	}
}
</script>
<!-------------------------State Begin------------------------->
<div class="state stateBg">
	<div class="step">
		ע�Ჽ�裺
		<span>1.��дע����Ϣ����</span> 2.ע��ɹ� 3.��̨��˳ɹ���ָ�����ڵ�¼�Ľ���Ա���룬��ͨ���绰��������ϵ��ʽ֪ͨע���û���<div class="step2R">
		<em>*</em>Ϊ������
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
		<!-------------------------�������ʻ���Ϣ------------------------->
		<h3>�������ʻ���Ϣ</h3>
		<div class="clear"></div>
		<fieldset>
			<legend>
				�������ʻ���Ϣ
			</legend>
			<input id="createTime" type="hidden" name="entity.createTime" value="" />
			<input id="status" type="hidden" name="entity.status" value="0" />
			<div>
				<label width="25%">�û�����</label>
				<label width="35%">
					<input id="userID" name="entity.userID" class="validate[required,maxSize[10],custom[onlyLetterNumber],ajax[checknoUserID]] datepicker"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">���룺</label>
				<label width="35%">
					<input id="password" name="entity.password" type="password" class="validate[required,minSize[6],maxSize[8],custom[onlyLetterNumber]]" onKeyUp="pwStrength(this.value);" onblur="pwStrength(this.value)"/><em>*</em>
				</label>
			</div>
			<div>
				<label class="fontSize">����ǿ�ȣ�</label>
				<label width="35%">
					<table width="210" border="0" cellspacing="0" cellpadding="0" bordercolor="#eeeeee" height="15" style='display:inline'>  
						<tr align="center" bgcolor="#f5f5f5">  
							<td width="33%" id="strength_L" class="fontSize">��</td>  
							<td width="33%" id="strength_M" class="fontSize">��</td>  
							<td width="33%" id="strength_H" class="fontSize">ǿ</td>  
						</tr>
					</table>
				</label>
			</div>
			<div>
				<label width="25%">
					ȷ�����룺
				</label>
				<label  width="35%">
					<input id="password1" type="password" class="validate[required,equals[password]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">���������ƣ�</label>
				<label  width="35%">
					<input id="name" name="entity.name" class="validate[required,maxSize[15]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">������ȫ�ƣ�</label>
				<label width="35%">
					<input id="fullName" name="entity.fullName" class="validate[maxSize[30]]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">���������ͣ�</label>
				<fieldset class="type" width="35%" align="left">
					<c:forEach var="type" items="${com_firmTypeMap}" varStatus="status">
						<input id="type${type.key}" value="${type.key}" type="radio" name="entity.type" <c:if test="${status.index==0}">checked="checked"</c:if>/><label class="label" onclick="type${type.key}.click();">${type.value}</label>
					</c:forEach>
				</fieldset>
			</div>
			<div>
				<label width="25%">����������� ��</label>
				<label width="35%">
					<input id="zoneCode" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.zoneCode"/><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label width="25%">������ҵ���룺</label>
				<label width="35%">
					<input id="industryCode" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.industryCode" /><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label width="25%">��֯�������룺</label>
				<label width="35%">
				<input id="organizationCode" type="text" class="validate[custom[onlyLetterNumber],maxSize[10]]" name="entity.organizationCode" value="${entity.organizationCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label width="25%">���˴���</label>
				<label width="35%">
					<input id="corporateRepresentative" class="validate[maxSize[30]]" name="entity.corporateRepresentative"/><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			<div>
				<label widht="25%">֤�����ͣ�</label>
				<label width="35%">
					<select id="certificateType" name="entity.certificate.code"  style="width: 133px;">
						<c:forEach var="certificate" items="${certificateList}">
							<option value="${certificate.code}">${certificate.name}</option>
						</c:forEach>
					</select><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">֤�����룺</label>
				<label width="35%">
					<input id="certificateNO" name="entity.certificateNO" class="validate[required,custom[onlyLetterNumber],maxSize[30]]"/><em>*</em>
				</label>
			</div>
		</fieldset>
		<!-------------------------����д��ϵ��ʽ------------------------->
		<h3>����д��ϵ��ʽ</h3>
		<fieldset>
			<div>
				<label width="25%">��ϵ�ˣ�</label>
				<label width="35%">
					<input id="contactMan" class="validate[required,maxSize[30]]" name="entity.contactMan"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">��ϵ��ַ��</label>
				<label width="35%">
					<input id="address" name="entity.address" class="validate[required,maxSize[50]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">�绰��</label>
				<label width="35%">
					<input id="phone" class="validate[phone]" name="entity.phone" data-prompt-position="centerRight:5"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">�ֻ��ţ�</label>
				<label width="35%">
					<input id="mobile" class="validate[required,phone]" name="entity.mobile" data-prompt-position="centerRight:5"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">���棺</label>
				<label width="35%">
					<input id="fax" name="entity.fax" class="validate[fax]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">�������䣺</label>
				<label width="35%">
					<input id="email" class="validate[email]" name="entity.email"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">�ʱࣺ</label>
				<label width="35%">
					<input id="postcode" class="validate[required,integer]" maxlength="6" name="entity.postcode"/><em>*</em>
				</label>
			</div>
		</fieldset>
		<!-------------------------���ύ------------------------->
		<h3></h3>
		<div class="center">
			<a href="#" class="link_red_12_">
				<span class="label">
					<input id="agree" name="take2" class="take" type="checkbox" checked="checked"/>
				</span>ͬ�����������׹��� ����Ķ���������
			</a>
		</div>
		<div class="center">
			<input id="register" type="button" class="button" action="/checkneedless/firmapply/register.action" value="�ύע����Ϣ" />
		</div>
	</div>
</form>
<!-------------------------Body End------------------------->
<jsp:include page="/front/frame/footer.jsp" flush="false"></jsp:include>
<%@include file="/front/public/jsp/commonmsg.jsp"%>