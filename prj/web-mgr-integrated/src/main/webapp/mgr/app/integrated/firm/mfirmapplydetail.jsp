<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>����������</title>
		<meta http-equiv="Pragma" content="no-cache">
		<base target="_self">
	</head>
	<body style="overflow-y: hidden" onload="getHidden()">
		<form name="frm" action="${basePath}" method="post">
		<input type="hidden" name="${entity.createTime }" value="${entity.createTime }">
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="85%" align="center">
					<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :������ע�����&nbsp;&nbsp;&nbsp;&nbsp;
								<font color="orange"><c:if test="${entity.status==0 }">�����</c:if></font>
								<font color="green"><c:if test="${entity.status==1 }">�ý��������ͨ��</c:if></font>
								<font color="red"><c:if test="${entity.status==2 }">�ý��������δͨ��</c:if>
								</font>
							</div>
						</div>
					</td>
				</tr>
					<tr>
						<td>
							<%@include file="mfirmapplycommontable.jsp"%>
							<!-- ������������Ϊ'���˵�ʱ��',���ص����š���ҵ��š���֯�ṹ����ͷ��˴��� -->
							<script type="text/javascript">
								function getHidden(){
									var type=document.getElementById("type").value;
									var content=document.getElementById("content");
									if(type =="����"){
										content.style.display = "none";
									}else{
										content.style.display = "block";
									}
								}
							</script>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							
							<c:if test="${entity.status!='1'&&entity.status!='2'}">
								<rightButton:rightButton name="ͨ��"
								onclick="pass('${entity.applyID}');"
								className="btn_sec"
								action="/trade/mfirm/forwordaddFirmDetail.action" id="auditPass"></rightButton:rightButton>
								&nbsp;&nbsp;
								<rightButton:rightButton name="��ͨ��"
									onclick="unPass('${entity.applyID}');"
									className="btn_sec"
									action="/trade/mfirm/auditUnPass.action" id="auditUnPass"></rightButton:rightButton>
									&nbsp;&nbsp;
							</c:if>
							<button class="btn_sec" onClick="window.close();">
								�ر�
							</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
		function pass(id){
			var vaild = window.confirm("��ȷ��Ҫ������");
			if (vaild == true) {
				var a=document.getElementById('auditPass').action;
				var url = "${basePath}"+a+"?entity.applyID="+id+"&date=<%=new Date()%>";
				frm.action=url;
				frm.submit();
			} else {
				return false;
			}
			
		}
		function unPass(id){
			var vaild = affirm("��ȷ��Ҫ������");
			if (vaild == true) {
			var a=document.getElementById('auditUnPass').action;
			var url = "${basePath}"+a+"?applyID="+id;
				frm.action=url;
				frm.submit();
			}
		}
</script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>