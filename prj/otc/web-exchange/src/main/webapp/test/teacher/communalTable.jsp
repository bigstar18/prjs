<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
	<s:hidden id="id" name="obj.id"></s:hidden>
	<tr height="20">
		<td align="right">
			Ãû³Æ:
		</td>
		<td>
			<s:textfield id="name" name="obj.name"></s:textfield>
		</td>
	</tr>
	<tr height="20">
		<td align="right">
			ÄêÁä:
		</td>
		<td>
			<s:if test="obj.age == 0">
				<s:textfield id="age" name="obj.age" value=""></s:textfield>
			</s:if>
			<s:else>
				<s:textfield id="age" name="obj.age"></s:textfield>
			</s:else>
		</td>
	</tr>
	<tr height="20">
		<td align="right">
			µØÖ·:
		</td>
		<td>
			<s:textfield id="address" name="obj.address"></s:textfield>
		</td>
	</tr>

</table>