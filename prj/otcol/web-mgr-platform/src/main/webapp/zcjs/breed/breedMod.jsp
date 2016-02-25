<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<script type="text/javascript" src="javascript/common.js"></script>
		<%@ include file="../public/session.jsp"%>
	</head>
	<body>
		<form name="frm" method="post"
			action="<%=basePath%>breedController.zcjs?funcflg=updateBreed">
			<fieldset width="60%">
				<legend> 
					品种修改
				</legend>
				<input type="hidden" name="breedId" value="${breed.breedId }">
				<table align="center">
					<tr>
						<td align="right">
							所属分类:
						</td>
						<td align="left">
						<c:set value="${breed.sortId}" var="sortId"/> 
							<select name="sortId">
								<c:forEach items="${sortList}" var="result">
									<option value="${result.sortId }" <c:if test="${result.sortId==sortId }">selected</c:if> >
										<c:out value="${result.sortName}"></c:out>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
					<tr>
						<td align="right">
							品种名称:
						</td>
						<td align="center">
							<input type="text" name="breedName" value="${breed.breedName }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							交割周期最少天数:
						</td>
						<td align="center">
							<input type="text" name="deliveryMinDay" id="deliveryMinDay"
								value="${breed.deliveryMinDay }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							交割周期最多天数:
						</td>
						<td align="center">
							<input type="text" name="deliveryMaxDay" id="deliveryMaxDay"
								value="${breed.deliveryMaxDay }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							交易单位:
						</td>
						<td align="center">
							<input type="text" name="tradeUnit" id="tradeUnit" value="${breed.tradeUnit }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位数量:
						</td>
						<td align="center">
							<input type="text" name="unitVolume" id="unitVolume" value="<fmt:formatNumber type="number" maxIntegerDigits="3"
            				value="${breed.unitVolume }" />" />
						</td>
					</tr>
					<tr>
						<td align="right">
							交收系数:
						</td>
						
						
						<td align="center">
							<input type="text" name="deliveryRatio" id="deliveryRatio" value="<fmt:formatNumber type="number" maxIntegerDigits="3"
            				value="${breed.deliveryRatio }" />" />
						</td>
					</tr>
				</table>
				<table align="center">
					<tr>
						<td align="center">
							<input type="button" value="保存" onclick="save()" />
						</td>
						<td>
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						</td>
						<td align="center">
							<input type="button" value="返回" onclick="freturn()" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>

	</body>
</html>
<script type="text/javascript">
function save(){
	if(frm.breedId.value == "")
	{	
		alert('请选择品种名称');
		frm.breedId.focus();
		return false;
	}
	if(frm.deliveryMinDay.value == "")
	{
		
		alert('请输入交割周期最少天数');
		frm.deliveryMinDay.focus();
		return false;
	}else if(isNaN(frm.deliveryMinDay.value)){
		alert('交割周期最少天数必须为数字！');
		frm.deliveryMinDay.focus();
		return false;
	}
	if(frm.deliveryMaxDay.value == "")
	{
		
		alert('请输入交割周期最多天数');
		frm.deliveryMaxDay.focus();
		return false;
	}else if(isNaN(frm.deliveryMaxDay.value)){
		alert('交割周期最多天数必须为数字！');
		frm.deliveryMaxDay.focus();
		return false;
	}
	if(frm.tradeUnit.value == "")
	{
		
		alert('交易单位不能为空');
		frm.tradeUnit.focus();
		return false;
	}
	var deMinDay=frm.deliveryMinDay.value;
	var deMaxDay=frm.deliveryMaxDay.value;
	if(parseFloat(deMinDay)>parseFloat(deMaxDay)){
		alert('交割周期最多天数必须大于等于交割周期最少天数');
		frm.deliveryMaxDay.focus();
		return false;
	}
	if(frm.unitVolume.value == "")
	{
		
		alert('单位数量不能为空');
		frm.unitVolume.focus();
		return false;
	}else if(isNaN(frm.unitVolume.value)){
		alert('单位数量必须为数字！');
		frm.unitVolume.focus();
		return false;
	}else if(frm.unitVolume.value==0){
		alert('单位数量不能为0！');
		frm.unitVolume.focus();
		return false;
	}	
	if(frm.deliveryRatio.value==""){
		alert('交收系数不能为空');
		frm.deliveryRatio.focus();
		return false;
	}else if(isNaN(frm.deliveryRatio.value)){
		alert('交收系数必须为数字！');
		frm.deliveryRatio.focus();
		return false;
	}else if(frm.deliveryRatio.value==0){
		alert('交收系数不能为空！');
		frm.deliveryRatio.focus();
		return false;
	}	
	if(confirm("确定修改品种设置么?")){
		frm.submit();
	}
	
}
function freturn(){
		frm.action = "<%=basePath%>breedController.zcjs?funcflg=list";
		frm.submit();
	}
		</script>


