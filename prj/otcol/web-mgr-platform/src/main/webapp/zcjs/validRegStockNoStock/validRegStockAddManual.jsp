<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<form name="frm" action="" method="post">
			<fieldset width="95%">
				<legend>
					��Ч�ֵ����
				</legend>
				<br>
				<table width="96%" border="1" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse: collapse;">
					<tr height="36px">
						<td align="right" class="cd_bt" width="25%">
							�ֵ��ţ�
						</td>
						<td colspan="3" class="cd_list1">
							&nbsp;
							<input id="regstockId" name="regstockId" type="text" value=""
								class="form_kr" style="width: 120">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr height="36px">
						<td align="right" class="cd_bt">
							�����̴��룺
						</td>
						<td width="35%" colspan="3" class="cd_list1">
							&nbsp;
							<input id="firmId" name="firmId" type="text" value=""
								class="form_kr" style="width: 120">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr height="36px" id="breed">
						<td align="right" class="cd_bt">
							Ʒ�֣�
						</td>
						<td class="cd_list1" colspan="3">
							<div align="left">
								&nbsp;
								<select name="breedId" id="breedId" style="width: 120"
									class="form_k">
									<option value="">
										��ѡ��
									</option>
									<c:forEach items="${breedList}" var="result">
										<option value="${result.breedId}">
											${result.breedName}
										</option>
									</c:forEach>
								</select>
								&nbsp;
								<font color="red">*</font>
							</div>
						</td>
					</tr>
				</table>
				<br>
				<br>
				<div align="center">
					<button class="smlbtn" type="button" onClick="doSubmit();">
						��һ��
					</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="smlbtn" type="button" onClick="freturn();">
						����
					</button>
				</div>

			</fieldset>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	
	function freturn(){
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=getvalidRegStockList";
		frm.submit();
	}
	
	function doSubmit(){
		if(frm.regstockId.value == "") {
			alert ("�ֵ��Ų���Ϊ�գ�");
			return false;
		}
		if(frm.firmId.value == "") {
			alert ("�����̴��벻��Ϊ�գ�");
			return false;
		}
		if(frm.breedId.value == "") {
			alert ("Ʒ�ֲ���Ϊ�գ�");
			return false;
		}
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=addSaleNextForward";
		frm.submit();
	}
		
</SCRIPT>