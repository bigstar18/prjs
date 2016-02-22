<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<%
	String quoprice = "";
	String minQuoprice="";
	String maxQuoprice="";
%>
<html>
	<head>
		<title>���鲹¼��Ϣ</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/marketMaintenanceSet/marketLogSet/update.action" method="post" targetType="hidden">
			<div style="overflow:auto;height:510px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;���鲹¼��Ϣ�޸�
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="st_bor">
								<tr height="20">
									<td align="right" width="25%">
										��Ʒ����:
									</td>
									<td width="30%">
										<input type="text" id="commodityId" class="input_text" name="commodityId"
											value="${obj.commodityId}" style="background-color: #bebebe"
											readonly="readonly">
									</td>
									<td width="45%">&nbsp;</td>
								</tr>
								
								<tr height="20">
									<td align="right" width="25%">
										��С����۸�:
									</td>
									<td width="30%">
										<input type="text" id="minQuoprice" name="minQuoprice" class="input_text"  onblur="minPrice('minQuoprice')" 
										value="${obj.minquoprice }" />	
										<font color="red">(��Ԫ/��˾)</font>
									</td>
									<td width="45%" align="left" >
									  <div id="minQuoprice_vTip" ><%=minQuoprice%></div>
									</td>
								</tr>
								<tr height="20">
									<td align="right" width="25%">
										�������۸�:
									</td>
									<td width="30%">
										<input type="text" id="maxQuoprice" name="maxQuoprice" class="input_text" onblur="maxPrice('maxQuoprice')" 
										value="${obj.maxquoprice }" />	
										<font color="red">(��Ԫ/��˾)</font>
									</td>
									<td width="45%" align="left" >
									  <div id="maxQuoprice_vTip" ><%=maxQuoprice%></div>
									</td>
								</tr>
									
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button id="updateLogSet" class="btn_sec"
								onclick="updateMarket()">
								�޸�
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								�ر�
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>

</html>
<<script type="text/javascript">
// ��֤��С�۸����С�����۸�
function validatePrice(min,max){
	var minPrice = document.getElementById(min).value;
	var maxPrice = document.getElementById(max).value;
	
	var flag = false;
	if(minPrice < maxPrice){
		flag = true;
	}

	return flag;
}

  function updateMarket(){
	  var flag = true;
		flag = myblur("all");
		if (flag) {
			if(!isFormChanged(null,null)){
				alert("û���޸�����");
				return false;
			}
			validatePrice("minQuoprice","maxQuoprice");
			if (!validatePrice("minQuoprice","maxQuoprice")){
				var vTip = document.getElementById("minQuoprice_vTip");
				vTip.innerHTML = "��С����۸����С���������۸�";
				vTip.className = "onError";
				return false;
			}
			var vaild = affirm("��ȷ��Ҫ������");
			if (vaild == true) {
				frm.submit();
			} else {
				return false;
			}
		}
  }
  function myblur(userID) {
		var flag = true;
	    if ("minQuoprice" == userID) {
			flag = minPrice(userID);
		} else if ("maxQuoprice" == userID) {
			flag = maxPrice(userID);
		}else {
			if (!minPrice("minQuoprice"))
				flag = false;
			if (!maxPrice("maxQuoprice")){
				flag = false;
			}
		}
		return flag;
	}
 
  function minPrice(userID){
		var flag = false;
		var str = "��С����۸�";
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		if (isEmpty(user.value)) {
			innerHTML = str + "����Ϊ��";
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "���ǷǸ���";	
		}else if(intByNum(user.value,12)){
			innerHTML = "С����ǰ�������12λ";	
		}else if(!flote(user.value,4)){
			innerHTML = "���4λС��������";	
		} else {
			innerHTML = "<%=minQuoprice%>";
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		return flag;
	}
  function maxPrice(userID){
		var flag = false;
		var str = "�������۸�";
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		if (isEmpty(user.value)) {
			innerHTML = str + "����Ϊ��";
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "���ǷǸ���";	
		}else if(intByNum(user.value,12)){
			innerHTML = "С����ǰ�������12λ";	
		}else if(!flote(user.value,4)){
			innerHTML = "���4λС��������";	
		} else {
			innerHTML = "<%=maxQuoprice%>";
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		return flag;
	}

 
  //�ж��Ƿ�Ϊ�Ǹ�����������������������
	function IsIntOrFloat(num){
	  var reNum=/(^\d)\d*(\.?)\d*$/;
	  return (reNum.test(num));
	}
  //�ж�����λ
	function intByNum(str,n){
		var flag=false;
		if(str.length>0){
			var strs=new Array();
			strs=str.split(".");
			if(strs.length==1){
				if(str.length>n){
					flag=true;
				}
			}else if(strs.length==2){
				var s=strs[0];
				if(s.length>n){
					flag=true;
				}
			}
		}
		return flag;
	}
	
</script>
<%@ include file="/public/footInc.jsp"%>

