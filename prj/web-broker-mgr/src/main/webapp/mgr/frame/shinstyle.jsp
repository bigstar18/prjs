<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.statictools.ApplicationContextInit"%>
<%@include file="/mgr/public/includefiles/taglib.jsp"%>
<%@include file="/mgr/public/includefiles/path.jsp"%>
<%@include file="/mgr/public/includefiles/jsinclude.jsp"%>
<%@include file="/mgr/public/includefiles/cssinclude.jsp"%>
<%@include file="/mgr/public/jsp/session.jsp"%>
<%
	Map<String,String> com_styleNameMap = (Map<String,String>) ApplicationContextInit.getBean("com_styleNameMap");
	request.setAttribute("com_styleNameMap", com_styleNameMap);
%>

<body>
	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/user/saveShinStyle.action" targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="div_cxtj">
    	<div class="div_cxtjL"></div>
        <div class="div_cxtjC">�������</div>
        <div class="div_cxtjR"></div>
    </div>
			<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="35%">
									�û����� ��
								</td>
								<td align="left">
									<input name="entity.userId" type="text" class="input_text_pwd"
										value="${CurrentUser.userId }" readonly="readonly">
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									ѡ���� ��
								</td>
								<td align="left">
									<select id="shinStyle" name="entity.skin">
										<option value="">
											��ѡ��
										</option>
										<c:forEach items="${com_styleNameMap }" var="snMap">
											<option value="${snMap.key }">
												${snMap.value }
											</option>
										</c:forEach>
									</select>
									<script>
	document.getElementById("shinStyle").value = "<c:out value='${CurrentUser.skin }'/>";
</script>
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
				<tr height="35">
					<td align="center">
						<button class="btn_sec" onClick=
	frmChk();
>
							����
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick=
	window.close();
>
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	function frmChk() {
		if (Trim(document.getElementById("shinStyle").value) == "") {
			alert("Ĭ�Ϸ�����ã�");
		}else{
			frm.submit();
		}
				//return true;
	}
	//-->
</SCRIPT>
<%@include file="../public/jsp/footinc.jsp"%>