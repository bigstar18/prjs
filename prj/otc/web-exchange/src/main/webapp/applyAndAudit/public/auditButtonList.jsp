<%@ page pageEncoding="GBK"%>
<input type="hidden" name="buttonClick" value="">
<div class="tab_pad">
<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center">
<tr>
	<c:forEach items="${buttonList}" var="result" varStatus="status">
		<c:if test="${status.count%2==1}">
		
		</c:if>
			<td align="center">
				<button class="btn_secmid" name="button" onclick="audit('${result.operateKey}')" id="${apply.applyType}Audit/audits.">
					${result.operateName}
				</button>
			</td>
		<c:if test="${status.count%2==0}">
		
		</c:if>
	</c:forEach>
	<td align="center">
			<button class="btn_secmid" onClick="window.close()">
					¹Ø&nbsp;&nbsp;&nbsp;&nbsp;±Õ
				</button>
	</td>
</tr>
</table>
</div>