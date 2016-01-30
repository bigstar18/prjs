<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="MarketMessage" var="MarketMessage" />
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
%>
<html>
<head>
	<title>�û�ע��</title>
	<style type="text/css">
body{margin:0; padding:0; font:12px/20px"����",Arial; color:#333;}
div,dl,dt,dd,ul,ol,li,pre,form,fieldset,input,textarea,blockquote,p,img{margin:0px; padding:0px;font:12px/20px"����",Arial, ;list-style:none;}
h1,h2,h3,h4,h5,h6{ margin:0px; padding:0px; font-weight:normal;font-style:normal;}
a img{ border:0px;}
.clear{ clear:both;}
.input{width:120px;height:20px;background-color:#FFFFFF;border:1px solid #7f9db9;}
.input_text_pwdmin{width:132px;height:20px;background-color:#FFFFFF;border:2px solid #330099;}
/*ע��ҳ*/
/* State
----------------------------------------------- */
.state{ width:860px; height:40px; margin:0 auto; padding:0 50px;background:#eee  no-repeat right top; border-top:3px solid #09C; font:600 14px/40px Arial, "΢���ź�", "����"; color:#09C;}
	.state span{ color:#f00;}
	.step2{ width:700px; float:left; line-height:40px;}
	.step2R{ width:100px; float:right; font:100 12px/40px "����"; color:#333; text-align:right;}
/* Body
----------------------------------------------- */

/* register.asp ��дע����Ϣ
----------------------------------------------- */
/*.bodyStep2{ position:relative; width:790px; left:50%; margin-left:-395px;}/* ��ʵ����Ч������ */
.bodyStep2{ width:790px; margin:20px auto;}
.bodyStep2 h3{ padding:5px 0; border-bottom:1px solid #666; font:600 14px/20px Arial, "΢���ź�", "����"; color:#666; text-align:right;}
.bodyStep2 fieldset{ border:none; display:inline; margin:0; padding:0;}
.bodyStep2 legend{ text-indent:-1000em;}
.bodyStep2 label{ float:left; width:12em; text-align:right; font:600 12px/25px Arial, "΢���ź�", "����";}
.bodyStep2 div{ clear:left; padding:3px 0; background:#fff;}
.bodyStep2 .warning{ clear:left; padding:3px 0; background:#ffffd1;}
.bodyStep2 .wrong{ color:#f30101;}
.bodyStep2 .right{ color:#338f21;}
.bodyStep2 .help{ color:#238cee;}
.bodyStep2 .sell_label,.bodyStep2 .stock_label{ float:none; margin-left:20px; font-weight:100; text-align:right;}
.bodyStep2 .label{ float:none; text-align:left; width:auto; font-weight:100;}
.bodyStep2 .no{ color:#999;}
.bodyStep2 .disabled{ background-color:#ddd; border-top:2px solclass #aaa; border-left:2px solclass #aaa;}
.bodyStep2 .button{ font-size:14px; height:30px; width:250px;color:#243177;}

a:link {  color: #0d40ad; text-decoration: none } 
a:visited {  color: #0d40ad; text-decoration: none ;border: dashed 1px gray;} 
a:hover {  color: #F60;; text-decoration: none }

.top_bg{ width:100%; height:26px; margin:0;background:url(./image/top_bg.gif) repeat-x;}
.top{ width:940px; height:26px; margin:0 auto; padding:0px 10px;color:#686868;}
.top_left{ float:left; line-height:25px;}
.top_left a.link_blue{ padding-left:15px; font-weight:bold; color:#0d40ad;}
.top_left a.link_blue:hover{ color:#F60;}
.top_left a.link_orange{ padding-left:15px; font-weight:bold; color:#ff6512;}
.top_left a.link_orange:hover{ color:#F60;}
.top_right{ float:right; text-align:right;}
.top_right ul li{ float:left; width:100px; padding:0 3px;}
.top_right ul li a{ line-height:25px; color:#686868;}
.top_right ul li a:hover{color:#F60;}

.header_bg{width:960px; height:80px; margin:0 auto; /*background:url(./image/header_bg.gif) repeat-x;*/}
.wrap{ width:960px; height:auto; margin:0 auto 7px;}
.wrapL{ float:left; width:210px;}
.wrapR{ float:right; width:740px;}
.logo{ float:left; height:80px; margin-left:7px;}
.login_title{ float:left;width:550px; height:80px; padding-left:80px;}
.login_title .phone{ float:left; width:550px; height:20px; line-height:20px; text-align:right; padding-top:10px;}
.login_title .name{float:left; line-height:50px; height:50px; font-size:25px; width:300px; font-weight:bold; color:#6e6e6e;}

/* done.asp ע��ɹ�
----------------------------------------------- */
	.bodyStep4{ width:960px; margin:0 auto 7px;}
	.bodyStep4 .title{ background:url(../../../image/display/done_ico.jpg) no-repeat 0 center; width:320px; height:90px; margin:0 auto; padding-left:80px; font:600 28px/90px Arial, "΢���ź�", "����"; color:#ff6904;}
	.bodyStep4 li{ float:left;}
	.bodyStep4 textarea{ width:300px; height:100px;}
	.bodyStep4 .content{ width:650px; margin:0 auto;}
	.bodyStep4 label{ float:left; width:6em; text-align:right; font:100 12px/25px Arial, "΢���ź�", "����";}
	.bodyStep4 .div{ padding:3px 0; background:#fff;}
	.bodyStep4 .div2{ float:left; width:385px;}
	.bodyStep4 .help{ color:#238cee; width:250px; float:right; font:100 12px/20px Arial, "΢���ź�", "����";}
	.bodyStep4 span{ line-height:40px;}
	
	
/* ��������
----------------------------------------------- */
.clear{ clear:both;}
.center{ text-align:center;}
.right{ text-align:right;}
.mar10{ padding-bottom:10px;}
em{ margin:0 3px; color:#f60; font-style:normal; font-size:12px;}

	
/*=======   �߿���ʽ����   {��������Border+��ɫ+��������}=======*/

	.border_gray_b{ border-bottom:1px dashed #b8b8b8; margin:20px 0; }
	
/* Footer
----------------------------------------------- */
.footer{ width:960px; margin:20px auto; padding:10px 0 0; border-top:3px solid #09C;}
/*.footer .center{ width:768px; margin:0 auto;}*/
.footer ul li{ float:left; width:128px; text-align:center;}
.footer ul li img{ border:1px solclass #ddd;}
.footer .copyright{ width:768px; margin:10px auto 0 auto; text-align:center; color:#666;}
/*End Footer*/
/*======Footer End======*/
/* ������ */
em{ margin:0 3px; color:#f60; font-style:normal; font-size:12px;}
.fontSize{
	font:12px,"����";
	color:#666;
}
</style>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
</head>
   <script type="text/javascript">
	$(document).ready(function(){
		//�ı���۽���ʧ��ʱ��border��ɫ�仯
		$(":text,:password").focus(function(){$(this).addClass("input_text_pwdmin")});
		$(":text,:password").blur(function(){
			$(this).removeClass("input_text_pwdmin");
			var className = $(this).attr("class").split(" ");
			var spid="sp"+$(this).attr("id");
			for(var i=0;i<className.length;i++){
				//�ж��Ƿ�Ϊ��
				if(className[i]=="required"){
					if($(this).val()==""){
						$("#"+spid).html("����Ϊ�գ�");
						$("#"+spid).css("color","red");
						return false;
					}else{
						$("#"+spid).html("");
						$("#"+spid).css("color","green");
					}
					
				}
			}
		});
		//ʧȥ����ʱ���û�����������֤��
		$("#userID").blur(function(){
			var userId = $(this).val();
			var url = "../../../../ajaxcheck/checkRegiesterUserId.action?userId="+userId;
			$.getJSON(url,null,function call(result){
				$.each(result,function(i,field){
					if(field == true || field == 'true'){
						 var reg=/^[0-9a-zA-Z]+$/;
						 if(pimg("userID").value !=""){
							 if(reg.test(pimg("userID").value)){
								$("#spuserID").html("�û����ƿ���ʹ�ã�");
								 $("#spuserID").css("color","green");
								 return true;
							 }else{
								  $("#spuserID").html("�û�����ʽ����ȷ��");
								  $("#spuserID").css("color","red");
								 return false;
							 }
						 }
					}else{
						$("#spuserID").html("������û����Ѵ��ڣ�");
						$("#spuserID").css("color","red");
						return false;
					}
					if(i==1){
						var spid="sp"+$(this).attr("id");
						$("#"+spid).html(field);
						return false;
					}
				});
			});
		});
		//ʧȥ����ʱ�������������֤��
		$("#password").blur(checkPwd);
		//ʧȥ����ʱ��ȷ�������������֤��
		$("#password1").blur(checkPwdAgain);
		//ʧȥ����ʱ�����������Ƶ�������֤��
		$("#name").blur(checkName);
		//ʧȥ����ʱ��֤�����͵�������֤��
		//$("#certificateType").blur(checkCardType);
		//ʧȥ����ʱ��֤���ŵ�������֤��
		$("#certificateNO").blur(checkCardNo);
		//ʧȥ����ʱ����ϵ�˵�������֤��
		$("#contactMan").blur(checkCustant);
		//ʧȥ����ʱ����ϵ��ַ��������֤��
		$("#address").blur(checkAddress);
		//ʧȥ����ʱ���ֻ������������֤��
		$("#mobile").blur(checkMobile);
		//ʧȥ����ʱ�������������֤��
		$("#email").blur(checkEmail);
		//ʧȥ����ʱ���ʱ��������֤��
		$("#postcode").blur(checkPostCode);

	});
</script>
	<script type="text/javascript">
	
//�����ַ�����
function showKeyPress(evt) {
	evt = (evt) ? evt : window.event
	return checkSpecific(String.fromCharCode(evt.keyCode));
}
//��ȡ�û������� ͬʱ���û�����ʱ�丳ֵ
function getFocus(){
	pimg("userID").focus();
	var sdate=new Date().getFullYear() +"-"+ (new Date().getMonth()+1) +"-"+ new Date().getDate()+" "+new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds();
	document.getElementById("createTime").value=sdate;
}
/**
 * ͨ��ID�Ż�ȡҳ�����
 * @param {Object} id
 * @return {TypeName} 
 */
function pimg(id){
	return  document.getElementById(id);
}
 //�û�������֤
 function checkUserID(){
	 var reg=/^[0-9a-zA-Z]+$/;
	 if(pimg("userID").value !=""){
		 if(reg.test(pimg("userID").value)){
			$("#spuserID").html("�û����ƿ���ʹ�ã�");
			 $("#spuserID").css("color","green");
			 return true;
		 }else{
			  $("#spuserID").html("�û�����ʽ����ȷ��");
			  $("#spuserID").css("color","red");
			 return false;
		 }
	 }
	 
 }
 //������֤
 function checkPwd(){
	 var reg=/^(.{1})((.|\s){5,500})$|^\s*$/;
	 if(pimg('password').value !=""){
		 if(reg.test(pimg('password').value)){
			 $("#sppassword").html("�����ʽ��ȷ��");
		 	 $("#sppassword").css("color","green");
			 return true;
		 }else{
			 $("#sppassword").html("��������6λ��");
			  $("#sppassword").css("color","red");
			 return false;
		 }
	 }
		
 }
 //ȷ��������֤
 function checkPwdAgain(){
	 if(pimg('password1').value !="" && pimg('password').value !=""){
		 if(pimg('password').value !=pimg('password1').value){
			 $("#sppassword1").html("������������벻һ�£�");
			$("#sppassword1").css("color","red");
		 }
	 }
 }
 //������������֤
 function checkName(){
	 if(pimg('name').value !=""){
	    $("#spname").html("���������ƿ���ʹ�ã�");
	    $("#spname").css("color","green");
		 return true;
	 }
 }
 //֤��������֤
 function checkCardNo(){
	  var reg =/^[0-9a-zA-z]+$/;
	  if(pimg('certificateNO').value !=''){
		  if(reg.test(pimg('certificateNO').value)){
			  $("#spcertificateNO").html("");
			  return true;
		  }else{
			  $("#spcertificateNO").html("������Ч֤���ţ�");
			  $("#spcertificateNO").css("color","red");
			  return false;
		  }
	  }
	
 }
 // ��ϵ����֤ 
 function checkCustant(){
	 if(pimg('contactMan').value !=""){
		 $("#spcontactMan").html("");
		 return true;
	 }
	 
 }
 //��ϵ��ַ��֤
  function checkAddress(){
	 if(pimg('address').value !=""){
		 $("#spaddress").html("");
		 return true;
	 }
	 
 }
 //�ֻ�������֤
 function checkMobile(){
	 reg =/^1[3|4|5|8][0-9]\d{4,8}$/;
	 if(pimg('mobile').value !=""){
		 if(reg.test(pimg('mobile').value)){
			 $("#spmobile").html("�ֻ������ʽ��ȷ��")
			 $("#spmobile").css("color","green");
			 return true;
		 }else{
			 $("#spmobile").html("�ֻ������ʽ����ȷ��")
			 $("#spmobile").css("color","red");
			 return false;
		 }
	 }
 }
 //����������֤
 function checkEmail(){
	 var reg=/^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$/i;
	 if(pimg('email').value !="" ){
		  if(reg.test(pimg('email').value)){
			$("#errorMessage").html("  ���������ʽ��ȷ��");
			$("#errorMessage").css("color","green");
			return true;
		 }else{
			 $("#errorMessage").html("  ���������ʽ����ȷ��");
			 $("#errorMessage").css("color","red");
			 return false;
		 }
	 }else{
		$("#errorMessage").html(" ");
		return true;
	 }
 }
 //�ʱ���֤
 function checkPostCode(){
	var reg=/^\d+$/;
	if(pimg('postcode').value !=""){
		if(pimg('postcode').value.length >6){
			$("#sppostcode").html("�ʱ����ֻ������6���֣�");
		 	$("#sppostcode").css("color","red");
		 	return false;
		}
		 if(reg.test(pimg('postcode').value)){
			 $("#sppostcode").html("");
		 	 return true;
		 }else{
		 	$("#sppostcode").html("�ʱ�ֻ���������֣�");
		 	$("#sppostcode").css("color","red");
		 	return false;
		 }
	 }
 }
 function checkAll(){
	var flag = true;
	if (!$("#agree").is(":checked")) {
		alert("������ͬ�����ǵ�����");
		return false;
	}else{
		if($("#password").val()==''){
			$("#sppassword").html("����Ϊ�գ�");
			$("#sppassword").css("color","red");
			flag = false;
		}
		if($("#password1").val()==''){
			$("#sppassword1").html("����Ϊ�գ�");
			$("#sppassword1").css("color","red");
			flag = false;
		}
		if($("#name").val()==''){
			$("#spname").html("����Ϊ�գ�");
			$("#spname").css("color","red");
			flag = false;
		}
		if($("#certificateNO").val()==''){
			$("#spcertificateNO").html("����Ϊ�գ�");
			$("#spcertificateNO").css("color","red");
			flag = false;
		}
		if($("#contactMan").val()==''){
			$("#spcontactMan").html("����Ϊ�գ�");
			$("#spcontactMan").css("color","red");
			flag = false;
		}
		if($("#address").val()==''){
			$("#spaddress").html("����Ϊ�գ�");
			$("#spaddress").css("color","red");
			flag = false;
		}
		if($("#mobile").val()==''){
			$("#spmobile").html("����Ϊ�գ�");
			$("#spmobile").css("color","red");
			flag = false;
		}
		if($("#postcode").val()==''){
			$("#sppostcode").html("����Ϊ�գ�");
			$("#sppostcode").css("color","red");
			flag =false;
		}
		if($("#imgcode").val()==''){
			$("#spimgcode").html("����Ϊ�գ�");
			$("#spimgcode").css("color","red");
			flag =false;
		}
		if(!flag){
			return flag;
		}
		if(checkUserID&&checkPwd()&&!checkPwdAgain()&&checkName()&&checkCardNo()&&checkCustant()&&checkAddress()&&checkMobile()&&checkEmail()&&checkPostCode()){
			return true;
		}else{
			return false;
		}
	}
 }
//�ж��������������  
function CharMode(iN){  
	if (iN>=48 && iN <=57) //����  
		return 1;  
	if (iN>=65 && iN <=90) //��д  
		return 2;  
	if (iN>=97 && iN <=122) //Сд  
		return 4;  
	else  
		return 8;   
}  
//bitTotal����  
//��������ģʽ  
function bitTotal(num){  
	modes=0;  
	for (i=0;i<4;i++){  
		if (num & 1) modes++;  
	num>>>=1;  
	}  
	return modes;  
}  
//����ǿ�ȼ���  
function checkStrong(sPW){  
	if (sPW.length<=4)  
		return 0; //����̫��  
	Modes=0;  
	for (i=0;i<sPW.length;i++){  
		//����ģʽ  
		Modes|=CharMode(sPW.charCodeAt(i));  
	}  
	return bitTotal(Modes);  
}  
  
//��ʾ��ɫ  
function pwStrength(pwd){  
	O_color="#c1c1c1";  
	L_color="#FF0000";  
	M_color="#FF9900";  
	H_color="#33CC00";  
	if (pwd==null||pwd==''){  
		Lcolor=Mcolor=Hcolor=O_color;  
	}  
	else{  
		S_level=checkStrong(pwd);  
		switch(S_level) {  
		case 0:  
		Lcolor=Mcolor=Hcolor=O_color;  
		case 1:  
		Lcolor=L_color;  
		Mcolor=Hcolor=O_color;  
		break;  
		case 2:  
		Lcolor=Mcolor=M_color;  
		Hcolor=O_color;  
		break;  
		default:  
		Lcolor=Mcolor=Hcolor=H_color;  
	}  
}  
	document.getElementById("strength_L").style.background=Lcolor;  
	document.getElementById("strength_M").style.background=Mcolor;  
	document.getElementById("strength_H").style.background=Hcolor;  
	return;  
}  
</script>



<body onload="getFocus()">

<div align="center">
<!-- Top -->
<div class="top_bg">
<div class="top">
	<div class="top_left"><a href="#" class="link_orange">${CurrentUser.name}</a>&nbsp;���ã���ӭ����${_marketName}�ֻ���</div>
</div>
</div>
<!-- End Top -->
<!-- Logo and search Begin -->
<div class="header_bg">
	<div class="wrap">
		<div class="logo"><img src="./image/logo.gif" /></div>
		<div class="login_title">
			<div class="phone"><img src="./image/phone.gif" width="15" height="15" />&nbsp;���������벦�绰��010-62196108</div>
			<div class="name">${pageTitle}</div>
		</div>
	</div>
</div>




<!-------------------------State Begin------------------------->
<div class="state stateBg">
	<div class="step">
		ע�Ჽ�裺
		<span>1.��дע����Ϣ����</span> 
		2.ע��ɹ� 3.��̨��˳ɹ���ָ�����ڵ�¼�Ľ���Ա���롣<em>*</em><font color="#000000">Ϊ������</font>
		
	</div>
</div>
<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<form id="registfrm" action="<%=basePath%>/mgr/app/integrated/checkneedless/register.action" method="post" onsubmit="return checkAll();">
	<div class="bodyStep2">
		<!-------------------------�������ʻ���Ϣ------------------------->
		<h3>
			�������ʻ���Ϣ
		</h3>
		<div class="clear"></div>
		<fieldset>
			<legend>
				�������ʻ���Ϣ
			</legend>
			<div class="">
			<input id="createTime" type="hidden" name="entity.createTime" value="" />
			<input id="status" type="hidden" name="entity.status" value="0" />
			<div class="">
				<label for="Name" width="25%">
					�û�����
				</label>
				<label width="35%">
				<input id="userID" type="text" class="required" 
					name="entity.userID" value="${entity.userID}"/><em>*</em></label>
				<label width="30%"><span  id="spuserID" ></span></label>
				
			</div>
			<div class="">
				<label for="password"  width="25%">
					���룺
				</label>
				<label width="35%">
				<input id="password" type="password"  onKeyUp=pwStrength(this.value) class="required"
				onblur="pwStrength(this.value)"
					name="entity.password" value="${entity.password}" /><em>*</em></label>
					<label width="30%"><span  id="sppassword"></span></label>
			</div>
			<div>
				<label for="fontSize" class="fontSize">����ǿ��: </label> 
				<table width="210" border="1" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="20" style='display:inline'>  
				<tr align="center" bgcolor="#d9d9d9">  
				<td width="33%" id="strength_L" class="fontSize">��</td>  
				<td width="33%" id="strength_M" class="fontSize">��</td>  
				<td width="33%" id="strength_H" class="fontSize">ǿ</td>  
				</tr>
				</table>
			</div>
			<div class="">
				<label for="Name" width="25%">
					ȷ�����룺
				</label>
				<label  width="35%">
				<input id="password1" type="password" class="required"
					value="${entity.password1}"/><em>*</em></label>
					<label  width="35%"><span  id="sppassword1"></span></label>
			</div>
			<div class="">
				<label for="Name" width="25%">
					���������ƣ�
				</label>
				<label  width="35%">
				<input id="name" type="text" class="required"
					name="entity.name" value="${entity.name}"/><em>*</em></label>
					<label  width="30%"><span  id="spname"></span></label>
			</div>
			<div>
				<label for="fullName" width="25%">
					������ȫ�ƣ�
				</label>
				<label  width="35%">
				<input id="fullName" type="text" name="entity.fullName" value="${entity.fullName}" class="required" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="type"  width="25%">
					���������ͣ�
				</label>
				<fieldset class="type" width="35%" align="left">
					<label class="label" for="sex1">
						<input value=1 type="radio" name="entity.type" checked="checked" onclick="checkCode(this.value)"/>����
					</label><label class="label" for="sex2">
						<input value=2 type="radio" name="entity.type"  onclick="checkCode(this.value)"/>����
					</label><label class="label" for="sex3"><input value=3 type="radio" name="entity.type"  onclick="checkCode(this.value)"/>����</label>
				</fieldset>
			</div>
			<script>
				function checkCode(val){
						if(val==1 || val==2){
							document.getElementById("content").style.display = "block";
						}else{
							document.getElementById("content").style.display = "none";
						}
					}
			</script>
		<div style="display: block;" id="content">
			<div>
				<label for="zoneCodes"  width="25%">
					����������� ��
				</label>
				<label width="35%">
				<input id="zoneCode" type="text" class="required" 
					name="entity.zoneCode" value="${entity.zoneCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="industryCodes"  width="25%">
					������ҵ���룺
				</label>
				<label width="35%">
				<input id="" type="text" class="required" 
					name="entity.industryCode" value="${entity.industryCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="organizationCodes" width="25%">
					��֯�������룺
				</label>
				<label width="35%">
				<input id="organizationCode" type="text" class="required" 
					name="entity.organizationCode" value="${entity.organizationCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="corporateRepresentatives" width="25%">
					���˴���
				</label>
				<label width="35%">
				<input id="corporateRepresentative" type="text" class="required" 
					name="entity.corporateRepresentative" value="${entity.corporateRepresentative}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
		</div>
			<div>
				<label for="certificateTypes" widht="25%">
					֤�����ͣ�
				</label>
				<label width="35%">
				<select id="certificateType" name="entity.certificate.code"  style="width: 133px;">
					<option value="1">�������֤</option>
					<option value="2">ʿ��֤</option>
					<option value="3">ѧ��֤</option>
					<option value="4">����֤</option>
					<option value="5">����</option>
					<option value="6">�۰�ͨ��֤</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="certificateNOs" width="25%">
					֤�����룺
				</label>
				<label width="35%">
				<input id="certificateNO" type="text" name="entity.certificateNO" class="required" value="${entity.certificateNO}"  /><em>*</em></label>
				<label width="30%"><span  id="spcertificateNO"></span></label>
			</div>
		</fieldset>
		<!-------------------------����д��ϵ��ʽ------------------------->
		<h3>
			����д��ϵ��ʽ
		</h3>
		<fieldset>
			<legend>
				����д��ϵ��ʽ
			</legend>
			<div>
				<label for="contactMan" width="25%">
					��ϵ�ˣ�
				</label>
				<label width="35%">
				<input id="contactMan" data-prompt-position="topLeft:0" class="required"
					type="text" name="entity.contactMan" value="${entity.contactMan}" /><em>*</em></label>
					<label width="30%"><span  id="spcontactMan"></span></label>
			</div>
			<div>
				<label for="address" width="25%">��ϵ��ַ��</label>
				<label width="35%"><input id="address" type="text" name="entity.address" class="required" value="${entity.address}" /><em>*</em></label>
				<label width="30%"><span  id="spaddress"></span></label>
			</div>
			<div>
				<label for="phone" width="25%">
					�绰��
				</label>
				<label width="35%"><input id="phone" data-prompt-position="centerRight:5" class="required"
					type="text" name="entity.phone" value="${entity.phone}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="phone" width="25%">
					�ֻ��ţ�
				</label>
				<label width="35%"><input id="mobile" data-prompt-position="centerRight:5" class="required" 
					type="text" name="entity.mobile" value="${entity.mobile}"/><em>*</em></label>
					<label width="30%"><span  id="spmobile"></span></label>
			</div>
			<div>
				<label for="fax" width="25%">
					���棺
				</label>
				<label width="35%">
				<input id="fax" type="text" name="entity.fax" value="${entity.fax}" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="email" width="25%">
					�������䣺
				</label>
				<label width="35%">
				<input id="email" class="required"  
					type="text" name="entity.email" value="${entity.email}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label width="30%"><span id="errorMessage"></span></label>
			</div>
			<div>
				<label for="postcode" width="25%">
					�ʱࣺ
				</label>
				<label width="35%">
				<input id="postcode" type="text" class="required"
					name="entity.postcode" value="${entity.postcode}" /><em>*</em></label>
					<label width="30%"><span  id="sppostcode"></span></label>
			</div>
		</fieldset>
		<fieldset>
			<div>
				<label for="valclassate" width="25%">
					��֤�룺
				</label>
				<label width="35%">
				<input id="imgcode" name="imgcode" type="text" class="required" style="width: 75px;" size="8" /><em>*</em></label>
				<label  width="30%"><span id="spimgcode"></span></label>
				<script type="text/javascript">
					/**������֤��ͼƬ*/
					function changeregistimg() {
						document.getElementById("verifycode").src = "./jsp/loginimage.jsp?"
								+ Math.random();
					}
				</script>
			</div>
			<div style="border: 0px solid red;height: 35px;width: 300px" align="center">
				<a href="#" onclick= "changeregistimg(); return false;"><img id="verifycode" src="./jsp/loginimage.jsp"	
					width="100" height="20" align="absmiddle" /></a>&nbsp;&nbsp;
				<a href="#" onclick= "changeregistimg(); return false;">�����壿</a>
			</div>
		</fieldset>
		</fieldset>
		<!-------------------------���ύ------------------------->
		<h3></h3>
		<div class="center">
			<a href="#" class="link_red_12_"> <span class="label"> <input
						id="agree" name="take2" class="take" type="checkbox"
						checked="checked" /> </span>ͬ�����������׹��� ����Ķ��������� </a>
		</div>
		<div class="center">
			<input id="register" type="submit" class="button" onclick="" action="/mgr/app/integrated/checkneedless/register.action"
				value="�ύע����Ϣ" />
		</div>
	</div>
</form>
<!-- ====== Footer Begin ====== -->
<!-- Footer -->
<div class="footer">
	<div class="copyright"><a href="#" class="link_gray">��������</a> | <a href="#" class="link_gray">�ͷ�����</a> | <a href="#" class="link_gray">������</a> | <a href="#" class="link_gray">��ϵ����</a> | <a href="#" class="link_gray">�������</a> | <a href="#" class="link_gray">��������</a> | <a href="#" class="link_gray">�����ɲ�</a> | <a href="#" class="link_gray">��������</a> | <a href="#" class="link_gray">��˾��̬</a><br />
  ������Ͷ��:gnnt@gnnt.com.cn ���ɹ��ʣ�**********<br />
  ��Ӫ���֤��ž�ICP֤*****�� <a href="#"><img src="image/count.jpg" width="20" height="20" border="0" /></a>
 </div>
</div>
<!-- End Footer -->
<!-- ====== Footer End ====== -->
</div>
</body>
<script type="text/javascript">
</script>
</html>
