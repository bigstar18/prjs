<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="../public/headInc.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body><div id="sroll" style="width:100%;height:90%;overflow-x:hidden;overflow-y:scroll">
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%"
			height="300">
			<tHead>
				<tr height="25" align=center>
					<td class="panel_tHead_LB">
						&nbsp;
					</td>
					<td class="panel_tHead_MB" align=left>
						ģ��
					</td>
					<td class="panel_tHead_MB" align=left>
						��¼ʱ��
					</td>
					<td class="panel_tHead_MB" align=left>
						��¼IP
					</td>
					<td class="panel_tHead_RB">
						&nbsp;
					</td>
				</tr>
			</tHead>
			<tBody>
				<c:forEach items="${resultList }" var="resultList">
					<tr onclick="selectTr();" align=center height="25">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td class="underLine" align=left>
							<c:if test="${resultList.moduleId=='1' }">����</c:if>
							<c:if test="${resultList.moduleId=='2' }">����</c:if>
							<c:if test="${resultList.moduleId=='3' }">�ֻ�����</c:if>
							<c:if test="${resultList.moduleId=='4' }">����</c:if>
							<c:if test="${resultList.moduleId=='5' }">���нӿ�</c:if>
							<c:if test="${resultList.moduleId=='5' }">�ֿ�</c:if>
						</td>
						<td class="underLine" align=left>
							${resultList.loginDate }
						</td>
						<td class="underLine" align=left>
							${resultList.ip }
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
				</c:forEach>
			</tBody>
			<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">
						&nbsp;
					</td>
					<td colspan="3">
						&nbsp;
					</td>
					<td class="panel_tBody_RB">
						&nbsp;
					</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">
						&nbsp;
					</td>
					<td class="panel_tFoot_MB" colspan="3"></td>
					<td class="panel_tFoot_RB">
						&nbsp;
					</td>
				</tr>
			</tFoot>
		</table></div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr height="10">
				<td width="40%">
					<div align="center">
						<input class="mdlbtn" type="button" onclick="return window.close();" class="btn"
							value="�ر�">
						&nbsp;&nbsp;
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">

      function sc()
     {
         document.getElementById("sroll").scrollTop =10;
       }
 </script>
	</body>
	</html:html>