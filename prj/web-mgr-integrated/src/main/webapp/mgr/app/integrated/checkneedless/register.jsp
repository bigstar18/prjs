<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="MarketMessage" var="MarketMessage" />
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
%>
<html>
<head>
	<title>用户注册</title>
	<style type="text/css">
body{margin:0; padding:0; font:12px/20px"宋体",Arial; color:#333;}
div,dl,dt,dd,ul,ol,li,pre,form,fieldset,input,textarea,blockquote,p,img{margin:0px; padding:0px;font:12px/20px"宋体",Arial, ;list-style:none;}
h1,h2,h3,h4,h5,h6{ margin:0px; padding:0px; font-weight:normal;font-style:normal;}
a img{ border:0px;}
.clear{ clear:both;}
.input{width:120px;height:20px;background-color:#FFFFFF;border:1px solid #7f9db9;}
.input_text_pwdmin{width:132px;height:20px;background-color:#FFFFFF;border:2px solid #330099;}
/*注册页*/
/* State
----------------------------------------------- */
.state{ width:860px; height:40px; margin:0 auto; padding:0 50px;background:#eee  no-repeat right top; border-top:3px solid #09C; font:600 14px/40px Arial, "微软雅黑", "宋体"; color:#09C;}
	.state span{ color:#f00;}
	.step2{ width:700px; float:left; line-height:40px;}
	.step2R{ width:100px; float:right; font:100 12px/40px "宋体"; color:#333; text-align:right;}
/* Body
----------------------------------------------- */

/* register.asp 填写注册信息
----------------------------------------------- */
/*.bodyStep2{ position:relative; width:790px; left:50%; margin-left:-395px;}/* 其实这种效果更好 */
.bodyStep2{ width:790px; margin:20px auto;}
.bodyStep2 h3{ padding:5px 0; border-bottom:1px solid #666; font:600 14px/20px Arial, "微软雅黑", "宋体"; color:#666; text-align:right;}
.bodyStep2 fieldset{ border:none; display:inline; margin:0; padding:0;}
.bodyStep2 legend{ text-indent:-1000em;}
.bodyStep2 label{ float:left; width:12em; text-align:right; font:600 12px/25px Arial, "微软雅黑", "宋体";}
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

/* done.asp 注册成功
----------------------------------------------- */
	.bodyStep4{ width:960px; margin:0 auto 7px;}
	.bodyStep4 .title{ background:url(../../../image/display/done_ico.jpg) no-repeat 0 center; width:320px; height:90px; margin:0 auto; padding-left:80px; font:600 28px/90px Arial, "微软雅黑", "宋体"; color:#ff6904;}
	.bodyStep4 li{ float:left;}
	.bodyStep4 textarea{ width:300px; height:100px;}
	.bodyStep4 .content{ width:650px; margin:0 auto;}
	.bodyStep4 label{ float:left; width:6em; text-align:right; font:100 12px/25px Arial, "微软雅黑", "宋体";}
	.bodyStep4 .div{ padding:3px 0; background:#fff;}
	.bodyStep4 .div2{ float:left; width:385px;}
	.bodyStep4 .help{ color:#238cee; width:250px; float:right; font:100 12px/20px Arial, "微软雅黑", "宋体";}
	.bodyStep4 span{ line-height:40px;}
	
	
/* 公共布局
----------------------------------------------- */
.clear{ clear:both;}
.center{ text-align:center;}
.right{ text-align:right;}
.mar10{ padding-bottom:10px;}
em{ margin:0 3px; color:#f60; font-style:normal; font-size:12px;}

	
/*=======   边框样式定义   {命名规则：Border+颜色+具体属性}=======*/

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
/* 必填项 */
em{ margin:0 3px; color:#f60; font-style:normal; font-size:12px;}
.fontSize{
	font:12px,"宋体";
	color:#666;
}
</style>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
</head>
   <script type="text/javascript">
	$(document).ready(function(){
		//文本框聚焦和失焦时，border颜色变化
		$(":text,:password").focus(function(){$(this).addClass("input_text_pwdmin")});
		$(":text,:password").blur(function(){
			$(this).removeClass("input_text_pwdmin");
			var className = $(this).attr("class").split(" ");
			var spid="sp"+$(this).attr("id");
			for(var i=0;i<className.length;i++){
				//判断是否为空
				if(className[i]=="required"){
					if($(this).val()==""){
						$("#"+spid).html("不能为空！");
						$("#"+spid).css("color","red");
						return false;
					}else{
						$("#"+spid).html("");
						$("#"+spid).css("color","green");
					}
					
				}
			}
		});
		//失去焦点时：用户名的特殊验证。
		$("#userID").blur(function(){
			var userId = $(this).val();
			var url = "../../../../ajaxcheck/checkRegiesterUserId.action?userId="+userId;
			$.getJSON(url,null,function call(result){
				$.each(result,function(i,field){
					if(field == true || field == 'true'){
						 var reg=/^[0-9a-zA-Z]+$/;
						 if(pimg("userID").value !=""){
							 if(reg.test(pimg("userID").value)){
								$("#spuserID").html("用户名称可以使用！");
								 $("#spuserID").css("color","green");
								 return true;
							 }else{
								  $("#spuserID").html("用户名格式不正确！");
								  $("#spuserID").css("color","red");
								 return false;
							 }
						 }
					}else{
						$("#spuserID").html("输入的用户名已存在！");
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
		//失去焦点时：密码的特殊验证。
		$("#password").blur(checkPwd);
		//失去焦点时，确认密码的特殊验证。
		$("#password1").blur(checkPwdAgain);
		//失去焦点时：交易商名称的特殊验证。
		$("#name").blur(checkName);
		//失去焦点时：证件类型的特殊验证。
		//$("#certificateType").blur(checkCardType);
		//失去焦点时：证件号的特殊验证。
		$("#certificateNO").blur(checkCardNo);
		//失去焦点时：联系人的特殊验证。
		$("#contactMan").blur(checkCustant);
		//失去焦点时：联系地址的特殊验证。
		$("#address").blur(checkAddress);
		//失去焦点时：手机号码的特殊验证。
		$("#mobile").blur(checkMobile);
		//失去焦点时：邮箱的特殊验证。
		$("#email").blur(checkEmail);
		//失去焦点时：邮编的特殊验证。
		$("#postcode").blur(checkPostCode);

	});
</script>
	<script type="text/javascript">
	
//特殊字符过滤
function showKeyPress(evt) {
	evt = (evt) ? evt : window.event
	return checkSpecific(String.fromCharCode(evt.keyCode));
}
//获取用户名焦点 同时给用户创建时间赋值
function getFocus(){
	pimg("userID").focus();
	var sdate=new Date().getFullYear() +"-"+ (new Date().getMonth()+1) +"-"+ new Date().getDate()+" "+new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds();
	document.getElementById("createTime").value=sdate;
}
/**
 * 通过ID号获取页面对象
 * @param {Object} id
 * @return {TypeName} 
 */
function pimg(id){
	return  document.getElementById(id);
}
 //用户名称验证
 function checkUserID(){
	 var reg=/^[0-9a-zA-Z]+$/;
	 if(pimg("userID").value !=""){
		 if(reg.test(pimg("userID").value)){
			$("#spuserID").html("用户名称可以使用！");
			 $("#spuserID").css("color","green");
			 return true;
		 }else{
			  $("#spuserID").html("用户名格式不正确！");
			  $("#spuserID").css("color","red");
			 return false;
		 }
	 }
	 
 }
 //密码验证
 function checkPwd(){
	 var reg=/^(.{1})((.|\s){5,500})$|^\s*$/;
	 if(pimg('password').value !=""){
		 if(reg.test(pimg('password').value)){
			 $("#sppassword").html("密码格式正确！");
		 	 $("#sppassword").css("color","green");
			 return true;
		 }else{
			 $("#sppassword").html("密码至少6位！");
			  $("#sppassword").css("color","red");
			 return false;
		 }
	 }
		
 }
 //确认密码验证
 function checkPwdAgain(){
	 if(pimg('password1').value !="" && pimg('password').value !=""){
		 if(pimg('password').value !=pimg('password1').value){
			 $("#sppassword1").html("两次输入的密码不一致！");
			$("#sppassword1").css("color","red");
		 }
	 }
 }
 //交易商名称验证
 function checkName(){
	 if(pimg('name').value !=""){
	    $("#spname").html("交易商名称可以使用！");
	    $("#spname").css("color","green");
		 return true;
	 }
 }
 //证件号码验证
 function checkCardNo(){
	  var reg =/^[0-9a-zA-z]+$/;
	  if(pimg('certificateNO').value !=''){
		  if(reg.test(pimg('certificateNO').value)){
			  $("#spcertificateNO").html("");
			  return true;
		  }else{
			  $("#spcertificateNO").html("不是有效证件号！");
			  $("#spcertificateNO").css("color","red");
			  return false;
		  }
	  }
	
 }
 // 联系人验证 
 function checkCustant(){
	 if(pimg('contactMan').value !=""){
		 $("#spcontactMan").html("");
		 return true;
	 }
	 
 }
 //联系地址验证
  function checkAddress(){
	 if(pimg('address').value !=""){
		 $("#spaddress").html("");
		 return true;
	 }
	 
 }
 //手机号码验证
 function checkMobile(){
	 reg =/^1[3|4|5|8][0-9]\d{4,8}$/;
	 if(pimg('mobile').value !=""){
		 if(reg.test(pimg('mobile').value)){
			 $("#spmobile").html("手机号码格式正确！")
			 $("#spmobile").css("color","green");
			 return true;
		 }else{
			 $("#spmobile").html("手机号码格式不正确！")
			 $("#spmobile").css("color","red");
			 return false;
		 }
	 }
 }
 //电子邮箱验证
 function checkEmail(){
	 var reg=/^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$/i;
	 if(pimg('email').value !="" ){
		  if(reg.test(pimg('email').value)){
			$("#errorMessage").html("  电子邮箱格式正确！");
			$("#errorMessage").css("color","green");
			return true;
		 }else{
			 $("#errorMessage").html("  电子邮箱格式不正确！");
			 $("#errorMessage").css("color","red");
			 return false;
		 }
	 }else{
		$("#errorMessage").html(" ");
		return true;
	 }
 }
 //邮编验证
 function checkPostCode(){
	var reg=/^\d+$/;
	if(pimg('postcode').value !=""){
		if(pimg('postcode').value.length >6){
			$("#sppostcode").html("邮编最多只能输入6数字！");
		 	$("#sppostcode").css("color","red");
		 	return false;
		}
		 if(reg.test(pimg('postcode').value)){
			 $("#sppostcode").html("");
		 	 return true;
		 }else{
		 	$("#sppostcode").html("邮编只能输入数字！");
		 	$("#sppostcode").css("color","red");
		 	return false;
		 }
	 }
 }
 function checkAll(){
	var flag = true;
	if (!$("#agree").is(":checked")) {
		alert("请您先同意我们的条款");
		return false;
	}else{
		if($("#password").val()==''){
			$("#sppassword").html("不能为空！");
			$("#sppassword").css("color","red");
			flag = false;
		}
		if($("#password1").val()==''){
			$("#sppassword1").html("不能为空！");
			$("#sppassword1").css("color","red");
			flag = false;
		}
		if($("#name").val()==''){
			$("#spname").html("不能为空！");
			$("#spname").css("color","red");
			flag = false;
		}
		if($("#certificateNO").val()==''){
			$("#spcertificateNO").html("不能为空！");
			$("#spcertificateNO").css("color","red");
			flag = false;
		}
		if($("#contactMan").val()==''){
			$("#spcontactMan").html("不能为空！");
			$("#spcontactMan").css("color","red");
			flag = false;
		}
		if($("#address").val()==''){
			$("#spaddress").html("不能为空！");
			$("#spaddress").css("color","red");
			flag = false;
		}
		if($("#mobile").val()==''){
			$("#spmobile").html("不能为空！");
			$("#spmobile").css("color","red");
			flag = false;
		}
		if($("#postcode").val()==''){
			$("#sppostcode").html("不能为空！");
			$("#sppostcode").css("color","red");
			flag =false;
		}
		if($("#imgcode").val()==''){
			$("#spimgcode").html("不能为空！");
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
//判断输入密码的类型  
function CharMode(iN){  
	if (iN>=48 && iN <=57) //数字  
		return 1;  
	if (iN>=65 && iN <=90) //大写  
		return 2;  
	if (iN>=97 && iN <=122) //小写  
		return 4;  
	else  
		return 8;   
}  
//bitTotal函数  
//计算密码模式  
function bitTotal(num){  
	modes=0;  
	for (i=0;i<4;i++){  
		if (num & 1) modes++;  
	num>>>=1;  
	}  
	return modes;  
}  
//返回强度级别  
function checkStrong(sPW){  
	if (sPW.length<=4)  
		return 0; //密码太短  
	Modes=0;  
	for (i=0;i<sPW.length;i++){  
		//密码模式  
		Modes|=CharMode(sPW.charCodeAt(i));  
	}  
	return bitTotal(Modes);  
}  
  
//显示颜色  
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
	<div class="top_left"><a href="#" class="link_orange">${CurrentUser.name}</a>&nbsp;您好，欢迎来到${_marketName}现货城</div>
</div>
</div>
<!-- End Top -->
<!-- Logo and search Begin -->
<div class="header_bg">
	<div class="wrap">
		<div class="logo"><img src="./image/logo.gif" /></div>
		<div class="login_title">
			<div class="phone"><img src="./image/phone.gif" width="15" height="15" />&nbsp;如有问题请拨电话：010-62196108</div>
			<div class="name">${pageTitle}</div>
		</div>
	</div>
</div>




<!-------------------------State Begin------------------------->
<div class="state stateBg">
	<div class="step">
		注册步骤：
		<span>1.填写注册信息……</span> 
		2.注册成功 3.后台审核成功后指定用于登录的交易员代码。<em>*</em><font color="#000000">为必填项</font>
		
	</div>
</div>
<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<form id="registfrm" action="<%=basePath%>/mgr/app/integrated/checkneedless/register.action" method="post" onsubmit="return checkAll();">
	<div class="bodyStep2">
		<!-------------------------↓设置帐户信息------------------------->
		<h3>
			↓设置帐户信息
		</h3>
		<div class="clear"></div>
		<fieldset>
			<legend>
				↓设置帐户信息
			</legend>
			<div class="">
			<input id="createTime" type="hidden" name="entity.createTime" value="" />
			<input id="status" type="hidden" name="entity.status" value="0" />
			<div class="">
				<label for="Name" width="25%">
					用户名：
				</label>
				<label width="35%">
				<input id="userID" type="text" class="required" 
					name="entity.userID" value="${entity.userID}"/><em>*</em></label>
				<label width="30%"><span  id="spuserID" ></span></label>
				
			</div>
			<div class="">
				<label for="password"  width="25%">
					密码：
				</label>
				<label width="35%">
				<input id="password" type="password"  onKeyUp=pwStrength(this.value) class="required"
				onblur="pwStrength(this.value)"
					name="entity.password" value="${entity.password}" /><em>*</em></label>
					<label width="30%"><span  id="sppassword"></span></label>
			</div>
			<div>
				<label for="fontSize" class="fontSize">密码强度: </label> 
				<table width="210" border="1" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="20" style='display:inline'>  
				<tr align="center" bgcolor="#d9d9d9">  
				<td width="33%" id="strength_L" class="fontSize">弱</td>  
				<td width="33%" id="strength_M" class="fontSize">中</td>  
				<td width="33%" id="strength_H" class="fontSize">强</td>  
				</tr>
				</table>
			</div>
			<div class="">
				<label for="Name" width="25%">
					确认密码：
				</label>
				<label  width="35%">
				<input id="password1" type="password" class="required"
					value="${entity.password1}"/><em>*</em></label>
					<label  width="35%"><span  id="sppassword1"></span></label>
			</div>
			<div class="">
				<label for="Name" width="25%">
					交易商名称：
				</label>
				<label  width="35%">
				<input id="name" type="text" class="required"
					name="entity.name" value="${entity.name}"/><em>*</em></label>
					<label  width="30%"><span  id="spname"></span></label>
			</div>
			<div>
				<label for="fullName" width="25%">
					交易商全称：
				</label>
				<label  width="35%">
				<input id="fullName" type="text" name="entity.fullName" value="${entity.fullName}" class="required" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="type"  width="25%">
					交易商类型：
				</label>
				<fieldset class="type" width="35%" align="left">
					<label class="label" for="sex1">
						<input value=1 type="radio" name="entity.type" checked="checked" onclick="checkCode(this.value)"/>法人
					</label><label class="label" for="sex2">
						<input value=2 type="radio" name="entity.type"  onclick="checkCode(this.value)"/>代理
					</label><label class="label" for="sex3"><input value=3 type="radio" name="entity.type"  onclick="checkCode(this.value)"/>个人</label>
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
					所属地域编码 ：
				</label>
				<label width="35%">
				<input id="zoneCode" type="text" class="required" 
					name="entity.zoneCode" value="${entity.zoneCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="industryCodes"  width="25%">
					所属行业编码：
				</label>
				<label width="35%">
				<input id="" type="text" class="required" 
					name="entity.industryCode" value="${entity.industryCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="organizationCodes" width="25%">
					组织机构代码：
				</label>
				<label width="35%">
				<input id="organizationCode" type="text" class="required" 
					name="entity.organizationCode" value="${entity.organizationCode}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
			<div>
				<label for="corporateRepresentatives" width="25%">
					法人代表：
				</label>
				<label width="35%">
				<input id="corporateRepresentative" type="text" class="required" 
					name="entity.corporateRepresentative" value="${entity.corporateRepresentative}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>	
			</div>
		</div>
			<div>
				<label for="certificateTypes" widht="25%">
					证件类型：
				</label>
				<label width="35%">
				<select id="certificateType" name="entity.certificate.code"  style="width: 133px;">
					<option value="1">居民身份证</option>
					<option value="2">士官证</option>
					<option value="3">学生证</option>
					<option value="4">驾照证</option>
					<option value="5">护照</option>
					<option value="6">港澳通行证</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="certificateNOs" width="25%">
					证件号码：
				</label>
				<label width="35%">
				<input id="certificateNO" type="text" name="entity.certificateNO" class="required" value="${entity.certificateNO}"  /><em>*</em></label>
				<label width="30%"><span  id="spcertificateNO"></span></label>
			</div>
		</fieldset>
		<!-------------------------↓填写联系方式------------------------->
		<h3>
			↓填写联系方式
		</h3>
		<fieldset>
			<legend>
				↓填写联系方式
			</legend>
			<div>
				<label for="contactMan" width="25%">
					联系人：
				</label>
				<label width="35%">
				<input id="contactMan" data-prompt-position="topLeft:0" class="required"
					type="text" name="entity.contactMan" value="${entity.contactMan}" /><em>*</em></label>
					<label width="30%"><span  id="spcontactMan"></span></label>
			</div>
			<div>
				<label for="address" width="25%">联系地址：</label>
				<label width="35%"><input id="address" type="text" name="entity.address" class="required" value="${entity.address}" /><em>*</em></label>
				<label width="30%"><span  id="spaddress"></span></label>
			</div>
			<div>
				<label for="phone" width="25%">
					电话：
				</label>
				<label width="35%"><input id="phone" data-prompt-position="centerRight:5" class="required"
					type="text" name="entity.phone" value="${entity.phone}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="phone" width="25%">
					手机号：
				</label>
				<label width="35%"><input id="mobile" data-prompt-position="centerRight:5" class="required" 
					type="text" name="entity.mobile" value="${entity.mobile}"/><em>*</em></label>
					<label width="30%"><span  id="spmobile"></span></label>
			</div>
			<div>
				<label for="fax" width="25%">
					传真：
				</label>
				<label width="35%">
				<input id="fax" type="text" name="entity.fax" value="${entity.fax}" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;</label>
			</div>
			<div>
				<label for="email" width="25%">
					电子邮箱：
				</label>
				<label width="35%">
				<input id="email" class="required"  
					type="text" name="entity.email" value="${entity.email}" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label width="30%"><span id="errorMessage"></span></label>
			</div>
			<div>
				<label for="postcode" width="25%">
					邮编：
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
					验证码：
				</label>
				<label width="35%">
				<input id="imgcode" name="imgcode" type="text" class="required" style="width: 75px;" size="8" /><em>*</em></label>
				<label  width="30%"><span id="spimgcode"></span></label>
				<script type="text/javascript">
					/**更换验证码图片*/
					function changeregistimg() {
						document.getElementById("verifycode").src = "./jsp/loginimage.jsp?"
								+ Math.random();
					}
				</script>
			</div>
			<div style="border: 0px solid red;height: 35px;width: 300px" align="center">
				<a href="#" onclick= "changeregistimg(); return false;"><img id="verifycode" src="./jsp/loginimage.jsp"	
					width="100" height="20" align="absmiddle" /></a>&nbsp;&nbsp;
				<a href="#" onclick= "changeregistimg(); return false;">看不清？</a>
			</div>
		</fieldset>
		</fieldset>
		<!-------------------------↓提交------------------------->
		<h3></h3>
		<div class="center">
			<a href="#" class="link_red_12_"> <span class="label"> <input
						id="agree" name="take2" class="take" type="checkbox"
						checked="checked" /> </span>同意服务条款及交易规则 点此阅读服务条款 </a>
		</div>
		<div class="center">
			<input id="register" type="submit" class="button" onclick="" action="/mgr/app/integrated/checkneedless/register.action"
				value="提交注册信息" />
		</div>
	</div>
</form>
<!-- ====== Footer Begin ====== -->
<!-- Footer -->
<div class="footer">
	<div class="copyright"><a href="#" class="link_gray">关于我们</a> | <a href="#" class="link_gray">客服中心</a> | <a href="#" class="link_gray">广告服务</a> | <a href="#" class="link_gray">联系我们</a> | <a href="#" class="link_gray">代理合作</a> | <a href="#" class="link_gray">法律声明</a> | <a href="#" class="link_gray">求贤纳才</a> | <a href="#" class="link_gray">服务条款</a> | <a href="#" class="link_gray">公司动态</a><br />
  建议与投诉:gnnt@gnnt.com.cn 法律顾问：**********<br />
  经营许可证编号京ICP证*****号 <a href="#"><img src="image/count.jpg" width="20" height="20" border="0" /></a>
 </div>
</div>
<!-- End Footer -->
<!-- ====== Footer End ====== -->
</div>
</body>
<script type="text/javascript">
</script>
</html>
