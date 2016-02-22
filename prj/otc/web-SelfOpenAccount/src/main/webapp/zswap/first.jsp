<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"
	import="gnnt.MEBS.firm.common.MessageProperties" errorPage="error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sessionid = session.getId();
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, user-scalable=yes">
	<head>
		<style type="text/css">
		<!--
		body {
			background-color: #eeeeee; margin:0px; font-size:12px; color:#b2b2b2; font-family:"微软雅黑";POSITION: relative; MIN-HEIGHT: 100%;text-rendering: optimizelegibility;
		}
		.font1{color:#FF0000;}
		.font2{color:#4577a1; font-size:14px; font-weight:bold;}
		.input1{background:#ffffff; border:0px;border-bottom:1px solid #d5d5d5; color:#7a7a7a; height:26px; padding-left:20px; width:80%; font-size:14px;}
		-->
		</style>
		<title>金网安泰微信在线开户</title>
		<script type='text/javascript' src='/SelfOpenAccount/dwr/engine.js'></script>
		<script type='text/javascript' src='/SelfOpenAccount/dwr/interface/FirmManager.js'></script>
		<script type="text/javascript">
		<%Object msg = request.getAttribute("msg");
			if (msg != null) {
				out.print("alert('" + (String) msg + "')");
			}%>	 
			function notnull(id,name){
				var ent=document.getElementById(id);
				var entmsg=document.getElementById(id+"msg");
				if(ent.value==""){
					ent.focus();
					entmsg.innerHTML="*</br>"+name+"不能为空";
					return false;
				}				

				entmsg.innerHTML="*";
			}
		//刷新验证码
		function refreshValidateCode() {
			document.getElementById("validateCodeImg").src = "./image.jsp;jsessionid=<%=sessionid%>?"+new Date();
		}
		function checkpassword(){
			if(form.password1.value==""){
				document.getElementById("password1msg").innerHTML="*<br>密码不能为空";
				form.password1.focus();
				return false;
			}
			if(form.password2.value!=""&&form.password1.value!=form.password2.value){
				document.getElementById("password2msg").innerHTML="*<br>两次密码不相同";
				form.password2.value="";
				form.password2.focus();
				return false;
			}

			document.getElementById("password1msg").innerHTML="*";
			document.getElementById("password2msg").innerHTML="*";
			return true;
		}
	 
		
		 //身份证验证
	    function checkCardNum(){
	   		if(form.papersName.value=="" ){
	   			document.getElementById("papersNamemsg").innerHTML="*</br>身份证号不能为空"
	      		form.papersName.focus();
		    	return false;
		    }
	   		
	    	FirmManager.checkedCardNumber(form.papersName.value,<%=request.getParameter("memberno")%>,function(data){
    			if(!data){
							form.papersName.value = "";
							document.getElementById("papersNamemsg").innerHTML="*</br>身份证号已被使用";
							form.papersName.focus();
							return false;
						} else {
						}
					});
	    	document.getElementById("papersNamemsg").innerHTML="*";
			return true;
		}
	
		//仅输入数字和字母
		function onlyNumberAndCharInput() {
			if ((event.keyCode >= 48 && event.keyCode <= 57)
					|| (event.keyCode >= 65 && event.keyCode <= 90)
					|| (event.keyCode >= 97 && event.keyCode <= 122)) {
				event.returnValue = true;
			} else {
				event.returnValue = false;
			}
		}
		var a = 1;
		function getVerifyCode() {
			if (document.getElementById("phone").value == "") {
				document.getElementById("phone").focus();
				document.getElementById("phonemsg").innerHTML="*</br>手机号码不能为空！";
				return false;
			}
			if (document.getElementById("phone").value.length != 11) {
				document.getElementById("phone").focus();
				document.getElementById("phonemsg").innerHTML="*</br>手机号码长度只能为11位！";
				return false;
			}
			document.getElementById("getCode").disabled = true;
			document.getElementById("verifyCode").disabled = false;
			var registeredPhoneNo = document.getElementById("phone").value;
			FirmManager.sendSMS(
							registeredPhoneNo,
							function(data) {
								if (data == 0) {
									document.getElementById("phonemsg").innerHTML = "*</br>验证码已经发到您的手机";
								} else {
									if (data == 1) {
										document.getElementById("phonemsg").innerHTML = "*</br>请输入正确的手机号码";
									} else if (data == 2) {
										document.getElementById("phonemsg").innerHTML = "*</br>对不起您的手机号已经注册";
									} else {
										document.getElementById("phonemsg").innerHTML = "*</br>短信发送失败，请稍后再试";
									}
									document.getElementById("getCode").value = "获取验证码";
									refresh();
									a = -1;
								}
							});
			countDown(180);
		}
		function countDown(secs) {
			if (a < 0) {
				a = 1;
				return false;
			}
			document.getElementById("getCode").value = secs + "秒后可重发";
			if (--secs > 0) {
				setTimeout("countDown(" + secs + " )", 1000);
			} else {
				document.getElementById("getCode").value = "获取验证码";
				refresh();
			}
		}
		function refresh() {
			document.getElementById("getCode").disabled = false;
		}
	
		function clicktrue() {
			var Code = document.getElementById("getCode");
			Code.disabled = false;
		}
		//仅输入数字
		function onlyNumberInput() {
			if (event.keyCode__tag_151$20_57 || event.keyCode == 47
					|| event.keyCode == 46) {
				event.returnValue = false;
			}
		}
		//表单提交
		function Subm() {
			var now = new Date();
			now.toLocaleDateString();
			var sum = document.getElementById("sub");
			if (ckall()) {
				sum.disabled = true;
				form.submit();
			}
		}
	
		//表单验证
		function ckall() {
			var temp = form.memberno.value;
			if (form.memberno.value == "-1") {s
				alert("请选择会员单位");
	
				return false;
			}
			return true;
		}
	
		//获得会员机构介绍
		function getNotes() {
			var notes = document.getElementById("notes");
			var memberNo = document.getElementById("memberno").value;
			notes.innerHTML = "";
			FirmManager.getMemberInfor(memberNo, function(date) {
				if (date == null) {
					notes.innerHTML = "无";
				} else {
					notes.innerHTML = date;
				}
			});
		}
	</script>
	</head>
	<body>
		<form id="form" name="form" action="./weixinAdd.fir?funcflg=eidtFirm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td height="80" colspan="3" align="center" style="background:url(/SelfOpenAccount/images/top.gif) no-repeat center;">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35" align="right"><label></label></td>
			    <td align="left"><input name="name" type="text" class="input1" id="name" onblur="notnull('name','用户名')" placeholder="用户名" />
			    <span class="font1" style="padding-left:5px;" id="namemsg">*</td>
			    <td width="1%" class="font1" style="padding-left:5px;">&nbsp; </td>
			  </tr>
			  <tr>
			    <td width="6%" height="35" align="right">&nbsp;</td>
			    <td height="35" align="left"><input  class="input1" type="password" onblur="checkpassword()" name="password1" id="password1"  placeholder="密码" />
			    <span class="font1" style="padding-left:5px;" id="password1msg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35" align="right">&nbsp;</td>
			    <td height="35" align="left"><input  type="password" name="password2" id="password2" onblur="checkpassword()"   placeholder="确认密码" class="input1"  />
			    <span class="font1" style="padding-left:5px;" id="password2msg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><span class="font2">证 件 类 型：<img src="/SelfOpenAccount/images/k.gif" width="2" height="15" />
			      <select  style="width:58%;" name="papersType" id="papersType">
			        <option value="2">身份证</option>
			      </select>
			      <span class="font1" style="padding-left:5px;" id="papersTypemsg">*</span></span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input  type="text" name="papersName" placeholder="证件号码" id="papersName"  onblur="checkCardNum()"  class="input1" />
			    <span class="font1" style="padding-left:5px;" id="papersNamemsg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left" class="font2">填写联系方式：</td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input   name="address" id="address" onblur="notnull('address','联系地址')"  placeholder="联系地址"  class="input1" />
			    <span class="font1" style="padding-left:5px;" id="addressmsg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input  name="email" type="text" id="email" onblur="notnull('email','邮箱')"  placeholder="邮箱" class="input1"  />
			    <span class="font1" style="padding-left:5px;" id="emailmsg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input  name="phone" type="text" id="phone" placeholder="手机号码"  class="input1"/>
			     <span class="font1" style="padding-left:5px;" id="phonemsg">*</span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left">
			    	<input id="verifyCode" class="input1" style="width: 50%" type="text" name="verifyCode" placeholder="手机验证码" />
			   		<input name="getCode" type="button" class=""	id="getCode"   onclick=getVerifyCode(); value="获取验证码"/>
			     	<span class="font1" style="padding-left:5px;" id="getCodemsg">*</span></td></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input  name="postCode" type="text" id="postCode" placeholder="邮编" class="input1"  /></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			   <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><input name="textfield14" type="text" class="input1" id="textfield14" placeholder="传真" /></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			   <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><span class="font2">开 户 机 构：<img src="/SelfOpenAccount/images/k.gif" width="2" height="15" />
			      <select  style="width:58%;"  name="memberno" id="memberno" onchange="getNotes()">
			      <OPTION  value ="${memberno}">${memberName}(${memberno}号会员)</OPTION>
			      </select>
			      <span class="font1" style="padding-left:5px;" id="membernomsg">*</span></span></td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="6%" height="35">&nbsp;</td>
			    <td height="35" align="left"><span class="font2">会员单位介绍：<img src="/SelfOpenAccount/images/k.gif" width="2" height="15" />
				<textarea rows="5" cols="25" name="notes" id="notes">空</textarea> </td>
			    <td width="1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td height="65" colspan="3" align="center" ><img id="sub" onclick="Subm()" src="/SelfOpenAccount/images/btn.gif" /></td>
			  </tr> 
			</table>
		</form>
	</body>
</html>
