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
			action="<%=basePath%>breedController.zcjs?funcflg=saveBreed">
			<fieldset width="60%">
				<legend> 
					Ʒ�����
				</legend>
				<table align="center">
					<tr>
						<td align="right">
							�������ࣺ
						</td>
						<td align="center">
							<select name="sortId">
								<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<c:forEach items="${sortList}" var="result">
									<option value="${result.sortId }">
										<c:out value="${result.sortName}"></c:out>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							Ʒ������:
						</td>
						<td align="center">
							<select name="breedId">
								<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<c:forEach items="${breedList}" var="result">
									<option value="${result.breedId }">
										<c:out value="${result.breedName}"></c:out>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							����������������:
						</td>
						<td align="center">
							<input type="text" name="deliveryMinDay" id="deliveryMinDay"
								value="" />
						</td>
					</tr>
					<tr>
						<td align="right">
							���������������:
						</td>
						<td align="center">
							<input type="text" name="deliveryMaxDay" id="deliveryMaxDay"
								value="" />
						</td>
					</tr>
					<tr>
						<td align="right">
							���׵�λ:
						</td>
						<td align="center">
							<input type="text" name="tradeUnit" id="tradeUnit" value="" />
						</td>
					</tr>
					<tr>
						<td align="right">
							��λ����:
						</td>
						<td align="center">
							<input type="text" name="unitVolume" id="unitVolume" value="" />
						</td>
					</tr>
					<tr>
						<td align="right">
							����ϵ��:
						</td>
						<td align="center">
							<input type="text" name="deliveryRatio" id="deliveryRatio" value="" />
						</td>
					</tr>
				</table>
				<table align="center">
					<tr>
						<td align="center">
							<input type="button" value="����" onclick="save()" />
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;
						</td>
						<td align="center">
							<input type="reset" value="����" />
						</td> 
						<td>
							&nbsp;&nbsp;&nbsp;
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
	if(frm.sortId.value == "")
	{	
		alert('��ѡ��Ʒ���������࣡');
		frm.sortId.focus();
		return false;
	}
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
	var deMinDay=frm.deliveryMinDay.value;
	var deMaxDay=frm.deliveryMaxDay.value;
	if(parseFloat(deMinDay)>parseFloat(deMaxDay)){
		alert('���������������������ڵ��ڽ���������������');
		frm.deliveryMaxDay.focus();
		return false;
	}
	if(frm.tradeUnit.value == "")
	{
		
		alert('���׵�λ����Ϊ��');
		frm.tradeUnit.focus();
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
	if(confirm("ȷ����Ӵ�Ʒ����Ϣô?")){
		frm.submit();
	}
}
function freturn(){
	frm.action = "<%=basePath%>breedController.zcjs?funcflg=list";
	frm.submit();
}
</script>


