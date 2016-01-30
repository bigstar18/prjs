<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	
		<title>
		</title>
		<style type="text/css">
		<!--
		.yin {
			visibility:hidden;
			position:absolute;
			
		}
		.xian{
			visibility:visible;
		}
		-->
		</style>
		
		<script type="text/javascript"> 
		function isTime(val) {
			var str=val;
		    
	    	if(str.length == 8) {
	         	var j=str.split(":");
	         	if(j.length == 3) {
	         		var a = j[0].match(/^(\d{2})$/);
			   		if (a == null) {
						return false;
					}
					a = j[1].match(/^(\d{2})$/);
			   		if (a == null) {
						return false;
					}
			   		a = j[2].match(/^(\d{2})$/);
			   		if (a == null) {
						return false;
					}
					
					if (j[0]>24||j[1]>60||j[2]>60) {
			           	return false;
			    	}
		        } else {
					return false;
			    } 	
	      	} else {
	           	return false;
	       	}
	        return true;
		}
		function window_onload()
		{
		}
		$(document).ready(function() {
	    	jQuery("#frm").validationEngine('attach');
			//修改按钮注册点击事件
			$("#update").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validateform')){

					var flag = false;

				    flag = save_onclick();
				    if(flag){
				    	var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}	
				}
			});
			$("#delete").click(function(){
				
		    	var vaild = affirm("您确定要操作吗？");
				if(vaild){
					//添加信息URL
					var updateDemoUrl = $(this).attr("action");
					//全 URL 路径
					var url = "${basePath}"+updateDemoUrl;
					$("#frm").attr("action",url);
					$("#frm").submit();
					$(this).attr("disabled",true);
				}
			});
	 	});
//save
function save_onclick()
{		

		if (document.forms(0).startTime.value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}
		if (document.forms(0).endTime.value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}
		
		if (!isTime(document.forms(0).startTime.value)) {
			alert("交收开始时间格式不正确！");
			document.forms(0).startTime.focus();
			return false;
		}
		if (!isTime(document.forms(0).endTime.value)) {
			alert("交收结束时间格式不正确！");
			document.forms(0).endTime.focus();
			return false;
		}
		
		if (document.forms(0).endTime.value <= document.forms(0).startTime.value) {
			alert("交收结束时间应大于交收开始时间！");
			return false;
		}
		
		var inputs = document.getElementsByTagName("input");
		for(var i = 0; i < inputs.length; i++){
			if(inputs[i].type == "checkbox" && inputs[i].checked){
				if (document.forms(0).startMiddleTime.value == "") {
					alert("中立仓开始时间不能为空！");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endMiddleTime.value == "") {
					alert("中立仓结束时间不能为空！");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).startMiddleTime.value.indexOf("：") != "-1") {
					alert("时间不能输入中文冒号！");
					return false;
				}
				if (document.forms(0).endMiddleTime.value.indexOf("：") != "-1") {
					alert("时间不能输入中文冒号！");
					return false;
				}
				
				if (!isTime(document.forms(0).startMiddleTime.value)) {
					alert("中立仓开始时间格式不正确！");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (!isTime(document.forms(0).endMiddleTime.value)) {
					alert("中立仓结束时间格式不正确！");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endTime.value >= document.forms(0).startMiddleTime.value) {
					alert("中立仓开始时间应大于交收结束时间！");
					return false;
				}
				if (document.forms(0).endMiddleTime.value <= document.forms(0).startMiddleTime.value) {
					alert("中立仓结束时间应大于中立仓开始时间！");
					return false;
				}
			}
		}
		return true;
 }

 function suffixNamePress()
{
	
  if (event.keyCode<=47 || event.keyCode>58)
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}
function doIt(){
	var s = false;
	var inputs = document.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].type == "checkbox" && inputs[i].checked){
			s = true;
		}
	} 	
	if(s){
		document.getElementById("one").style.display = "";
		document.getElementById("two").style.display = "";
		document.getElementById("typeS").value = "1";
	}else{
		document.getElementById("one").style.display = "none";
		document.getElementById("two").style.display = "none";
		document.getElementById("typeS").value = "0";
	}
}

</script>
</head>
	<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
		<table border="0" height="80%" width="100%" align="center">
			<tr>
				<td>
					<form id="frm" action="/timebargain/delay/updateSection.action" method="POST">
						
						<fieldset class="pickList" style="width:70%" align="center">
							<legend class="common">
								<b>延期交收节信息
								</b>
							</legend>

			  <table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
				  <!-- 基本信息 -->
				  <tr class="common">
				     <td colspan="4">
							              
						<span id="baseinfo">
							<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>
									<td align="right" width="40%">
											交收开始时间：
									</td>
									<td>
										<input type="text" id="startTime" name="entity.startTime" maxlength="8" style="ime-mode:disabled" value="${entity.startTime}"
											 class="validate[required] input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											交收结束时间： 
									</td>
									<td>
										<input type="text" id="endTime" name="entity.endTime" maxlength="8" style="ime-mode:disabled" value="${entity.endTime}" 
											class="validate[required] input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>
								<input id="typeS" type="hidden" name="entity.type" value="${entity.type }" />
							  <c:if test="${neutralflag == '1'}">
								<tr>
									<td align="right" width="40%">
											是否启用中立仓交易节：
									</td>
									<td>
										<input id="isNotDo" type="checkbox" onclick="doIt();" />
									
									</td>
								</tr>
								<tr id="one" style="display:none;">
									<td align="right" width="40%">
											中立仓开始时间：
									</td>
									<td>
										<input type="text" id="startMiddleTime" name="entity.startMiddleTime" maxlength="8" style="ime-mode:disabled" value="${entity.startMiddleTime}" 
											class="input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>
								<tr id = "two" style="display:none;">
									<td align="right">
											中立仓结束时间： 
									</td>
									<td>
										<input type="text" id="endMiddleTime" name="entity.endMiddleTime" maxlength="8" style="ime-mode:disabled" value="${entity.endMiddleTime}" 
											class="input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>	
							</c:if>					
							</table >
							<script type="text/javascript"> 
							    
							    if ( "${neutralflag}" == "1" && "${entity.type}" == "1") {
   
							    	document.getElementById("isNotDo").checked=true;
									document.getElementById("one").style.display = "";
									document.getElementById("two").style.display = "";
								}
							</script>
						</span>
					 </td>
				  </tr>					
								<tr>
									<td colspan="4" height="3">	
								    </td>
								</tr>																																										
								<tr>
									<td colspan="4" align="center">
										<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/timebargain/delay/updateSection.action" id="update"></rightButton:rightButton>
										&nbsp;&nbsp;
										<rightButton:rightButton name="删除" onclick="" className="btn_sec" action="/timebargain/delay/deleteSection.action" id="delete"></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="opr" value="${opr }"/>
						<input type="hidden" id="sectionID" name="entity.sectionID" value="${entity.sectionID }" />
						<input type="hidden" name="entity.status" value="${entity.status }" />
					</form>
				</td>
			</tr>
			
		</table>
		
	</body>
</html>


