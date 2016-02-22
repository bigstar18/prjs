<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
<input type="hidden" name="obj.id" value="${obj.id}">
	<tr height="20">
		<td align="right">
			姓名:
		</td>
		<td>
		  <input type="text" id="name" name="obj.name" value="${obj.name}">
		</td>
	</tr>
	<tr height="20">
		<td align="right">
		年龄:
		</td>
		<td>
			<c:if test="${obj.age == 0}">
			    <input type="text" id="age" name="obj.age">
			</c:if>
			<c:if test="${obj.age != 0}">
			    <input type="text" id="age" name="obj.age" value="${obj.age} ">
			</c:if>
		</td>
	</tr>
	<tr height="20">
		<td align="right">
		班级:
		</td>
		<td>
		  <input type="text" id="grade" name="obj.grade" value="${obj.grade }">
		</td>
	</tr>
	<tr height="20">
		<td align="right">
		专业:
		</td>
		<td>
		  <input type="text" id="speciality" name="obj.speciality" value="${obj.speciality }">
			</td>
		</tr>
</table>