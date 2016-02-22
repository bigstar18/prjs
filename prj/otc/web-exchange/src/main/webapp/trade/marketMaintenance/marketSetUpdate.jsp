<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<%
	String quoprice = "";
	String minQuoprice="";
	String maxQuoprice="";
%>
<html>
	<head>
		<title>行情补录信息</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/marketMaintenanceSet/marketLogSet/update.action" method="post" targetType="hidden">
			<div style="overflow:auto;height:510px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;行情补录信息修改
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="st_bor">
								<tr height="20">
									<td align="right" width="25%">
										商品代码:
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
										最小行情价格:
									</td>
									<td width="30%">
										<input type="text" id="minQuoprice" name="minQuoprice" class="input_text"  onblur="minPrice('minQuoprice')" 
										value="${obj.minquoprice }" />	
										<font color="red">(美元/盎司)</font>
									</td>
									<td width="45%" align="left" >
									  <div id="minQuoprice_vTip" ><%=minQuoprice%></div>
									</td>
								</tr>
								<tr height="20">
									<td align="right" width="25%">
										最大行情价格:
									</td>
									<td width="30%">
										<input type="text" id="maxQuoprice" name="maxQuoprice" class="input_text" onblur="maxPrice('maxQuoprice')" 
										value="${obj.maxquoprice }" />	
										<font color="red">(美元/盎司)</font>
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
								修改
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>

</html>
<<script type="text/javascript">
// 验证最小价格必须小于最大价格
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
				alert("没有修改内容");
				return false;
			}
			validatePrice("minQuoprice","maxQuoprice");
			if (!validatePrice("minQuoprice","maxQuoprice")){
				var vTip = document.getElementById("minQuoprice_vTip");
				vTip.innerHTML = "最小行情价格必须小于最大行情价格";
				vTip.className = "onError";
				return false;
			}
			var vaild = affirm("您确定要操作吗？");
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
		var str = "最小行情价格";
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		if (isEmpty(user.value)) {
			innerHTML = str + "不能为空";
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "不是非负数";	
		}else if(intByNum(user.value,12)){
			innerHTML = "小数点前数字最多12位";	
		}else if(!flote(user.value,4)){
			innerHTML = "最多4位小数的数字";	
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
		var str = "最大行情价格";
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		if (isEmpty(user.value)) {
			innerHTML = str + "不能为空";
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "不是非负数";	
		}else if(intByNum(user.value,12)){
			innerHTML = "小数点前数字最多12位";	
		}else if(!flote(user.value,4)){
			innerHTML = "最多4位小数的数字";	
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

 
  //判断是否为非负数（包括整数，浮点数）
	function IsIntOrFloat(num){
	  var reNum=/(^\d)\d*(\.?)\d*$/;
	  return (reNum.test(num));
	}
  //判断整数位
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

