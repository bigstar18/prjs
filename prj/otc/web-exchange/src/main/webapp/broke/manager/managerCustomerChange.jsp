<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/managerChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scro">
				<table border="0" height="300" width="90%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;�ͻ�����ת��
							</div>
							<table width="421" border="0">
						<tr height="35">
							<td align="right">
								�ͻ��������:
							</td>
							<td colspan="3">
								<input type="text" id="managerNo" name="obj.managerNo" value="${obj.managerNo }" style="width: 300" readonly=true >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								�ͻ���������:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									style="width: 300">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								ת�ƿͻ���������:
							</td>
							<td colspan="3">
								<select name="obj.managerNoChange" id="managerNoChange">
									<option  value="">��ѡ��</option>
									<c:forEach items="${resultList}" var="manager" >
										<option value="${manager.managerNo }">${manager.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="changeManager()" id="update">
							ת�ƿͻ�����
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
		function changeManager(){
	    	if(frm.managerNoChange.value==""){
	    		alert('��ѡ����Ҫת�Ƶľ����ˣ�');
	    		return false;
	    	}else if(frm.managerNoChange.value==frm.managerNo.value){
	    		alert('����ת�Ƹ��Լ���������ѡ��');
	    		return false;
	    	}
			frm.submit();
		}
	</script>
<%@ include file="/public/footInc.jsp"%>