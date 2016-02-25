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
					Ʒ���޸�
				</legend>
				<input type="hidden" name="breedId" value="${breed.breedId }">
				<table align="center">
					<tr>
						<td align="right">
							��������:
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
							Ʒ������:
						</td>
						<td align="center">
							<input type="text" name="breedName" value="${breed.breedName }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							����������������:
						</td>
						<td align="center">
							<input type="text" name="deliveryMinDay" id="deliveryMinDay"
								value="${breed.deliveryMinDay }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							���������������:
						</td>
						<td align="center">
							<input type="text" name="deliveryMaxDay" id="deliveryMaxDay"
								value="${breed.deliveryMaxDay }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							���׵�λ:
						</td>
						<td align="center">
							<input type="text" name="tradeUnit" id="tradeUnit" value="${breed.tradeUnit }" />
						</td>
					</tr>
					<tr>
						<td align="right">
							��λ����:
						</td>
						<td align="center">
							<input type="text" name="unitVolume" id="unitVolume" value="<fmt:formatNumber type="number" maxIntegerDigits="3"
            				value="${breed.unitVolume }" />" />
						</td>
					</tr>
					<tr>
						<td align="right">
							����ϵ��:
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
							<input type="button" value="����" onclick="save()" />
						</td>
						<td>
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						</td>
						<td align="center">
							<input type="button" value="����" onclick="freturn()" />
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
		alert('��ѡ��Ʒ������');
		frm.breedId.focus();
		return false;
	}
	if(frm.deliveryMinDay.value == "")
	{
		
		alert('�����뽻��������������');
		frm.deliveryMinDay.focus();
		return false;
	}else if(isNaN(frm.deliveryMinDay.value)){
		alert('��������������������Ϊ���֣�');
		frm.deliveryMinDay.focus();
		return false;
	}
	if(frm.deliveryMaxDay.value == "")
	{
		
		alert('�����뽻�������������');
		frm.deliveryMaxDay.focus();
		return false;
	}else if(isNaN(frm.deliveryMaxDay.value)){
		alert('�������������������Ϊ���֣�');
		frm.deliveryMaxDay.focus();
		return false;
	}
	if(frm.tradeUnit.value == "")
	{
		
		alert('���׵�λ����Ϊ��');
		frm.tradeUnit.focus();
		return false;
	}
	var deMinDay=frm.deliveryMinDay.value;
	var deMaxDay=frm.deliveryMaxDay.value;
	if(parseFloat(deMinDay)>parseFloat(deMaxDay)){
		alert('���������������������ڵ��ڽ���������������');
		frm.deliveryMaxDay.focus();
		return false;
	}
	if(frm.unitVolume.value == "")
	{
		
		alert('��λ��������Ϊ��');
		frm.unitVolume.focus();
		return false;
	}else if(isNaN(frm.unitVolume.value)){
		alert('��λ��������Ϊ���֣�');
		frm.unitVolume.focus();
		return false;
	}else if(frm.unitVolume.value==0){
		alert('��λ��������Ϊ0��');
		frm.unitVolume.focus();
		return false;
	}	
	if(frm.deliveryRatio.value==""){
		alert('����ϵ������Ϊ��');
		frm.deliveryRatio.focus();
		return false;
	}else if(isNaN(frm.deliveryRatio.value)){
		alert('����ϵ������Ϊ���֣�');
		frm.deliveryRatio.focus();
		return false;
	}else if(frm.deliveryRatio.value==0){
		alert('����ϵ������Ϊ�գ�');
		frm.deliveryRatio.focus();
		return false;
	}	
	if(confirm("ȷ���޸�Ʒ������ô?")){
		frm.submit();
	}
	
}
function freturn(){
		frm.action = "<%=basePath%>breedController.zcjs?funcflg=list";
		frm.submit();
	}
		</script>


