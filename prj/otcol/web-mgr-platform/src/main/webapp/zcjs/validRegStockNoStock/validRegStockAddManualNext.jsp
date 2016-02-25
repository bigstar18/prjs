<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="gnnt.MEBS.zcjs.model.Quality"  %>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>

	</head>
	<body>
		<form name="frm" action="" method="post">
			<fieldset width="95%">
				<legend>
					有效仓单添加
				</legend>
				<br>
				<table width="96%" border="1" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse: collapse;">
					<tr height="36px">
						<td align="right" class="cd_bt" width="25%">
							仓单号：
						</td>
						<td colspan="3" class="cd_list1">
							&nbsp;
							<input id="regstockId" name="regstockId" type="text" value="c_${validRegstock.regstockId}"
								class="form_kr" style="width: 120" readonly="readonly">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr height="36px">
						<td align="right" class="cd_bt">
							交易商代码：
						</td>
						<td width="35%" colspan="3" class="cd_list1">
							&nbsp;
							<input id="firmId" name="firmId" type="text" value="${validRegstock.firmId }"
								class="form_kr" style="width: 120" readonly="readonly">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr height="36px" id="breed">
						<td align="right" class="cd_bt">
							品种：
						</td>
						<td class="cd_list1" colspan="3">
							<div align="left">
								&nbsp;
								<select name="breedId" id="breedId" style="width: 120"
									class="form_k">
									<option value="${validRegstock.breedId }">
										${breedName }
									</option>
								</select>
								&nbsp;
								<font color="red">*</font>
							</div>
						</td>
					</tr>
					<c:forEach items="${resultList}" var="result">
						<tr height="36px">
							<td align="right" class="cd_bt">
								${result.key.propertyName }：
							</td>
							<td class="cd_list1" colspan="3">
								<div align="left">
									&nbsp;
									<select name="cp_${result.key.key }" id="cp_${result.key.key }" style="width: 120"
										class="form_k">
										<c:forEach items="${result.value}" var="parameterResult">
											<option value="${parameterResult.parameterName }">
												${parameterResult.parameterName }
											</option>
										</c:forEach>
									</select>
									&nbsp;
									<font color="red">*</font>
								</div>
							</td>
						</tr>
					</c:forEach>
					<% int i = 0; 
						List<Quality> tempList = (List<Quality>)request.getAttribute("qualityList");
					%>
					<c:forEach items="${qualityList}" var="result">
						<tr height="36px">
							<td align="right" class="cd_bt" id="tempName_<%=i %>">
								${result.qualityName }：
							</td>
							<td width="35%" colspan="3" class="cd_list1">
								&nbsp;
								<input id="qu_<%= i %>" name="qu_${result.qualityId }" type="text" value="" class="form_kr" style="width: 120">
								&nbsp;
							   <font color="red">*</font>
								<% i++; %>
							</td>
						</tr>
					</c:forEach>
					<tr height="36px">
						<td align="right" class="cd_bt">
							数量：
						</td>
						<td width="35%" colspan="3" class="cd_list1">
							&nbsp;
							<input id="quantity" name="quantity" type="text" value=""
								class="form_kr" style="width: 120">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
				</table>
				<br>
				<br>
				<div align="center">
					<button class="smlbtn" type="button" onClick="doSubmit();">
						确定
					</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="smlbtn" type="button" onClick="freturn();">
						返回
					</button>
				</div>

			</fieldset>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">

	function freturn(){
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=addSaleForward";
		frm.submit();
	}
	
		
	function doSubmit(){
		var quantity = frm.quantity.value;
		//添加对质量指标的判断。
		for(var j=0;j<<%= tempList.size()%>;j++){
			var tempId = "qu_"+j;
			var tempName = "tempName_"+j;
			var qualityName = document.getElementById(tempName).innerText;
			if(document.getElementById(tempId).value == ""){
				alert(qualityName.substr(0,qualityName.length-2)+"不能为空");
				document.getElementById(tempId).focus();
				return false;
			}
			
		}
		
		if(frm.quantity.value == ""){
			document.getElementById('quantity').value="";
			alert ("数量不能为空！");
			document.getElementById('quantity').focus();
			return false;
		}
		if(isNaN(quantity)){
			document.getElementById('quantity').value="";
			alert ("数量必须为数字！");
			document.getElementById('quantity').focus();
			return false;
		}
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=addSaleStock";
		frm.submit();
	}
</SCRIPT>