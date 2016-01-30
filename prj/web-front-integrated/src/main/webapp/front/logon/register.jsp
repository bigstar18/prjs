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
	//�����ύ�ĵ���¼�
	$("#register").click(function(check) {
		if ($("#registfrm").validationEngine('validateform')) {
		}
	});
});
//�����֤��ֵ
function clearValue(){
	document.getElementById("imgcode").value="";
}
//��ʾ��ɫ  
function pwStrength(pwd){
	PasswordMode.pwStrength(pwd,"strength_L","strength_M","strength_H");
	return;
}

//������֤��ͼƬ
function changeregistimg(id) {
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
	}else{
		//�������֤��ֵ��ϵͳ���ɵ�ֵ��һ��ʱ������ϵͳ���ɵ���֤��ֵ
		changeregistimg();
		//���������������¼�
		clearSubmitCount();
		//�����֤������ֵ
		clearValue();
	}
}

//������������Ϊ'���˵�ʱ��',���ص����š���ҵ��š���֯�ṹ����ͷ��˴���
	function getHidden(type){
		if(type !=3){
			$("#content").css("display","block");
		}else{
			$("#content").css("display","none");
		}
	}

	function get(id){
		return document.getElementById(id);
	}
	function checkUserId(){
		var userId =$("#userID").val();
		if(!isStr(userId,true)){
			return "*�����Ƿ��ַ�";
		}
		
	}
	/**
 * �ж��Ƿ���������ַ� s ����֤���ַ��� ch true���ֲ��������ַ�����false ���������ַ��� vec �����ַ����飬�����������еĲ��������ַ�
 * true �����������ַ� false ���������ַ�
 */
	function isStr(s,ch,vec){
	if(isEmpty(s)){
		return true;
	}
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
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
	/**
 * �ж��Ƿ�Ϊ���ַ���
 */
	function isEmpty(s){
		if(mytrim(s+'').length<=0){
			return true;
		}
		return false;
	}
	function mytrim(s){
		return s.replace(/(^\s*)|(\s*$)/g, "");
	}
</script>
<!-------------------------State Begin------------------------->
<div class="state stateBg">
	<div class="step">
		ע�Ჽ�裺
		<span>1.��дע����Ϣ����</span> 2.ע��ɹ� 3.��̨��˳ɹ���ָ�����ڵ�¼�Ľ���Ա���룬��ͨ���绰��������ϵ��ʽ֪ͨע���û���
	</div>
</div>

<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<iframe  name="hiddenframe" width=0 height=0  application='yes'></iframe>
<form id="registfrm" action="<%=basePath%>/checkneedless/firmapply/register.action" method="post" target="hiddenframe">
	<div class="bodyStep2">
		<!-------------------------�������ʻ���Ϣ------------------------->
		<h3>�����ʻ���Ϣ��</h3><div class="step2R">
			<em>*</em><font style="font-weight: bold;font-size: 13px;color:darkorange;">Ϊ������</font>
		</div>
		<div class="clear"></div>
		<fieldset>
			<input id="createTime" type="hidden" name="entity.createTime" value="" />
			<input id="status" type="hidden" name="entity.status" value="0" />
			
			<div>
				<label width="25%" align="center">�û�����</label>
				<label width="35%">
					<input id="userID" name="entity.userID" class="validate[required,funcCall[checkUserId],maxSize[16],ajax[checknoUserID]] datepicker" data-prompt-position="topLeft:0,8"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">���룺</label>
				<label width="35%">
					<input id="password" name="entity.password" type="password" class="validate[required,minSize[6],maxSize[32]]" onKeyUp="pwStrength(this.value);" onblur="pwStrength(this.value)" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"/><em>*</em>
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
					<input id="password1" type="password" class="validate[required,equals[password]]" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false" data-prompt-position="topLeft:0,8"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">���������ƣ�</label>
				<label width="35%">
					<input id="name" name="entity.name" class="validate[required,maxSize[16]]"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">������ȫ�ƣ�</label>
				<label width="35%">
					<input id="fullName" name="entity.fullName" class="validate[maxSize[64]]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">���������ͣ�</label>
				<label style="width: 143px;">
				<select id="type" class="validate[required] input_text" name="entity.type" onchange="getHidden(this.value)" onclick="reduceValue(this.value)" style="width: 131px;">
								<option value="" >��ѡ��</option>
								<c:forEach var="type" items="${com_firmTypeMap}" varStatus="status">
									<option id="type${type.key}" value="${type.key}" name="entity.type" title='${type.key}'
										<c:if  test="${entity.type==type.key }">selected="selected"</c:if>>
										${type.value}
									</option>
								</c:forEach>
				</select><em>*</em>
				</label>
				<script>
					function reduceValue(type){
						if(type==3){
							//������������Ϊ����ʱ����ֹ֮ǰ��������š���ҵ��š���֯�ṹ����ͷ��˴�����ֵ¼��
							$("#zoneCode").attr("value","");
							$("#industryCode").attr("value","");
							$("#organizationCode").attr("value","");
							$("#corporateRepresentative").attr("value","");
						}
					}
				</script>
			</div>
			<div id="content" style="display: block;">
				<label width="25%">��������</label>
				<label width="35%">
					<select id="zoneCode" class="validate[maxSize[16]] input_text" name="entity.zoneCode"  style="width: 131px; data-prompt-position="topLeft:0,8">
					<option value="">��ѡ��</option>
						<c:forEach var="zone" items="${zoneList}">
							<c:if test="${zone.isvisibal eq 'Y'}">
							<option value="${zone.code}">${zone.name}</option>
							</c:if>
						</c:forEach>
					</select><em>&nbsp;&nbsp;</em>
				</label>
				<div>
					<label width="25%">������ҵ��</label>
					<label width="35%">
						<select id="industryCode" class="validate[maxSize[16]] input_text" name="entity.industryCode"  style="width: 131px; data-prompt-position="topLeft:0,8">
							<option value="">��ѡ��</option>
							<c:forEach var="industry" items="${industryList}">
								<c:if test="${industry.isvisibal eq 'Y'}">
								<option value="${industry.code}">${industry.name}</option>
								</c:if>
							</c:forEach>
						</select><em>&nbsp;&nbsp;</em>
					</label>	
			    </div>
				<div>
				<label width="25%">��֯�������룺</label>
				<label width="35%">
					<input id="organizationCode" type="text" class="validate[required,custom[onlyLetterNumber],maxSize[9]]" name="entity.organizationCode" value="${entity.organizationCode}"/><em>*</em>
				</label>	
			</div>
				<div>
				<label width="25%">���˴�����</label>
				<label width="35%">
					<input id="corporateRepresentative" class="validate[maxSize[16]]" name="entity.corporateRepresentative"/><em>&nbsp;&nbsp;</em>
				</label>	
			</div>
			</div>
			<div>
				<label widht="25%">֤�����ͣ�</label>
				<label style="width: 143px;">
					<select id="certificateType" class="validate[required] input_text" name="entity.certificate.code"  style="width: 131px; data-prompt-position="topLeft:0,8">
					<option value="">��ѡ��</option>
						<c:forEach var="certificate" items="${certificateTypeList}">
							<c:if test="${certificate.isvisibal eq 'Y'}">
							<option value="${certificate.code}">${certificate.name}</option>
							</c:if>
						</c:forEach>
					</select><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">֤����ţ�</label>
				<label width="35%">
					<input id="certificateNO" name="entity.certificateNO" class="validate[required,custom[onlyLetterNumber],maxSize[32]]" data-prompt-position="topLeft:0,8"/><em>*</em>
				</label>
			</div>
		</fieldset>
		<!-------------------------����д��ϵ��ʽ------------------------->
		<h3>��д��ϵ��ʽ��</h3>
		<fieldset>
			<div>
				<label width="25%">��ϵ�ˣ�</label>
				<label width="35%">
					<input id="contactMan" class="validate[required,maxSize[16]]" name="entity.contactMan"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">��ϵ��ַ��</label>
				<label width="35%">
					<input id="address" name="entity.address" class="validate[required,maxSize[64]]" data-prompt-position="topLeft:0,8"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">�ֻ����룺</label>
				<label width="35%">
					<input id="mobile" class="validate[required,maxSize[12],custom[mobile]]" name="entity.mobile"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">�������䣺</label>
				<label width="35%">
					<input id="email" class="validate[required,custom[email],maxSize[64]]" name="entity.email" data-prompt-position="bottomLeft:0,-3"/><em>*</em>
				</label>
			</div>
			<div>
				<label width="25%">�绰��</label>
				<label width="35%">
					<input id="phone" class="validate[phone,maxSize[32]]" name="entity.phone" data-prompt-position="centerRight:5"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">���棺</label>
				<label width="35%">
					<input id="fax" name="entity.fax" class="validate[custom[fax],maxSize[16]]"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%">�ʱࣺ</label>
				<label width="35%">
					<input id="postcode" class="validate[custom[onlyNumberSp],maxSize[10]]"  name="entity.postcode"/><em>&nbsp;&nbsp;</em>
				</label>
			</div>
			<div>
				<label width="25%" for="valclassate">��֤�룺</label>
				<label style="width: 143px;">
				    <input id="imgcode" name="imgcode" type="text" class="validate[required] imgcode"/><em>*</em>
				</label>
				<img id="verifycode" src="${publicPath}/jsp/loginimage.jsp"
					width="90" height="20" align="absmiddle" />
				<a href="#" onclick= "changeregistimg(id); return false;">�����壿</a>
			</div>
		</fieldset>
		<fieldset>
			
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