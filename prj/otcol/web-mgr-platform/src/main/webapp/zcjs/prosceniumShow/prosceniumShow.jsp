<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<body>
<form name="frm" method="post">
		<fieldset width="100%">
		<legend>设置前台展示列表</legend>
		

	<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<c:forEach items="${resultList}" var="result">
						<tr>
						<c:choose>
							<c:when test="${result.showProperty=='BorSorRegStore'}">
								<td align="right" width="50%">
								<input type="checkbox" name="showProperty" id="showProperty" value="${result.showProperty }" style="display:none" <c:if test="${result.isShow =='Y'}">checked</c:if>/> 
								</td>
							</c:when>
							<c:when test="${result.showProperty=='BusinessDirection'}">
								<td align="right" width="50%">
								<input type="checkbox" name="showProperty" id="showProperty" value="${result.showProperty }" style="display:none" <c:if test="${result.isShow =='Y'}">checked</c:if>/> 
								</td>
							</c:when>
							<c:when test="${result.showProperty=='sortId'}">
								<td align="right" width="50%">
								<input type="checkbox" name="showProperty" id="showProperty" value="${result.showProperty }" style="display:none" <c:if test="${result.isShow =='Y'}">checked</c:if>/> 
								</td>
							</c:when>
							<c:otherwise>
								<td align="right" width="50%">
									<input type="checkbox" name="showProperty" id="showProperty" value="${result.showProperty }" <c:if test="${result.isShow =='Y'}">checked</c:if>/> 
								</td>
								<td align="left" width="50%">
									${result.showName }
								</td>
							</c:otherwise>
						</c:choose>
						
						</tr>				
						</c:forEach>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr height="35">
						
		<td colspan="2" align="center">
		  <input type="button" name="btn" onclick="frmChk()" class="btn" value="保存">&nbsp;&nbsp;
		</td>
	  </tr>
								 
	</table>  
	
</fieldset>
</form>
</body>
<script type="text/javascript">
	function frmChk(){
		frm.action="<%=basePath%>/prosceniumShowController.zcjs?funcflg=mod";
		frm.submit();
	}
</script>
