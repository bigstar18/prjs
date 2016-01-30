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
			//�޸İ�ťע�����¼�
			$("#update").click(function(){
				//��֤��Ϣ
				if(jQuery("#frm").validationEngine('validateform')){

					var flag = false;

				    flag = save_onclick();
				    if(flag){
				    	var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}	
				}
			});
			$("#delete").click(function(){
				
		    	var vaild = affirm("��ȷ��Ҫ������");
				if(vaild){
					//�����ϢURL
					var updateDemoUrl = $(this).attr("action");
					//ȫ URL ·��
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

		if (document.forms(0).startTime.value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		if (document.forms(0).endTime.value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		
		if (!isTime(document.forms(0).startTime.value)) {
			alert("���տ�ʼʱ���ʽ����ȷ��");
			document.forms(0).startTime.focus();
			return false;
		}
		if (!isTime(document.forms(0).endTime.value)) {
			alert("���ս���ʱ���ʽ����ȷ��");
			document.forms(0).endTime.focus();
			return false;
		}
		
		if (document.forms(0).endTime.value <= document.forms(0).startTime.value) {
			alert("���ս���ʱ��Ӧ���ڽ��տ�ʼʱ�䣡");
			return false;
		}
		
		var inputs = document.getElementsByTagName("input");
		for(var i = 0; i < inputs.length; i++){
			if(inputs[i].type == "checkbox" && inputs[i].checked){
				if (document.forms(0).startMiddleTime.value == "") {
					alert("�����ֿ�ʼʱ�䲻��Ϊ�գ�");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endMiddleTime.value == "") {
					alert("�����ֽ���ʱ�䲻��Ϊ�գ�");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).startMiddleTime.value.indexOf("��") != "-1") {
					alert("ʱ�䲻����������ð�ţ�");
					return false;
				}
				if (document.forms(0).endMiddleTime.value.indexOf("��") != "-1") {
					alert("ʱ�䲻����������ð�ţ�");
					return false;
				}
				
				if (!isTime(document.forms(0).startMiddleTime.value)) {
					alert("�����ֿ�ʼʱ���ʽ����ȷ��");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (!isTime(document.forms(0).endMiddleTime.value)) {
					alert("�����ֽ���ʱ���ʽ����ȷ��");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endTime.value >= document.forms(0).startMiddleTime.value) {
					alert("�����ֿ�ʼʱ��Ӧ���ڽ��ս���ʱ�䣡");
					return false;
				}
				if (document.forms(0).endMiddleTime.value <= document.forms(0).startMiddleTime.value) {
					alert("�����ֽ���ʱ��Ӧ���������ֿ�ʼʱ�䣡");
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
								<b>���ڽ��ս���Ϣ
								</b>
							</legend>

			  <table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
				  <!-- ������Ϣ -->
				  <tr class="common">
				     <td colspan="4">
							              
						<span id="baseinfo">
							<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>
									<td align="right" width="40%">
											���տ�ʼʱ�䣺
									</td>
									<td>
										<input type="text" id="startTime" name="entity.startTime" maxlength="8" style="ime-mode:disabled" value="${entity.startTime}"
											 class="validate[required] input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											���ս���ʱ�䣺 
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
											�Ƿ����������ֽ��׽ڣ�
									</td>
									<td>
										<input id="isNotDo" type="checkbox" onclick="doIt();" />
									
									</td>
								</tr>
								<tr id="one" style="display:none;">
									<td align="right" width="40%">
											�����ֿ�ʼʱ�䣺
									</td>
									<td>
										<input type="text" id="startMiddleTime" name="entity.startMiddleTime" maxlength="8" style="ime-mode:disabled" value="${entity.startMiddleTime}" 
											class="input_text" onkeypress="return suffixNamePress()"/>
										<span class="required">* HH:MM:SS</span>
									</td>
								</tr>
								<tr id = "two" style="display:none;">
									<td align="right">
											�����ֽ���ʱ�䣺 
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
										<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/timebargain/delay/updateSection.action" id="update"></rightButton:rightButton>
										&nbsp;&nbsp;
										<rightButton:rightButton name="ɾ��" onclick="" className="btn_sec" action="/timebargain/delay/deleteSection.action" id="delete"></rightButton:rightButton>
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


