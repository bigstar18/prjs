<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商详情</title>
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
								温馨提示 :交易商注册审核&nbsp;&nbsp;&nbsp;&nbsp;
								<font color="orange"><c:if test="${entity.status==0 }">待审核</c:if></font>
								<font color="green"><c:if test="${entity.status==1 }">该交易商审核通过</c:if></font>
								<font color="red"><c:if test="${entity.status==2 }">该交易商审核未通过</c:if>
								</font>
							</div>
						</div>
					</td>
				</tr>
					<tr>
						<td>
							<%@include file="mfirmapplycommontable.jsp"%>
							<!-- 当交易商类型为'个人的时候',隐藏地域编号、行业编号、组织结构代码和法人代表 -->
							<script type="text/javascript">
								function getHidden(){
									var type=document.getElementById("type").value;
									var content=document.getElementById("content");
									if(type =="个人"){
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
								<rightButton:rightButton name="通过"
								onclick="pass('${entity.applyID}');"
								className="btn_sec"
								action="/trade/mfirm/forwordaddFirmDetail.action" id="auditPass"></rightButton:rightButton>
								&nbsp;&nbsp;
								<rightButton:rightButton name="不通过"
									onclick="unPass('${entity.applyID}');"
									className="btn_sec"
									action="/trade/mfirm/auditUnPass.action" id="auditUnPass"></rightButton:rightButton>
									&nbsp;&nbsp;
							</c:if>
							<button class="btn_sec" onClick="window.close();">
								关闭
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
			var vaild = window.confirm("您确定要操作吗？");
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
			var vaild = affirm("您确定要操作吗？");
			if (vaild == true) {
			var a=document.getElementById('auditUnPass').action;
			var url = "${basePath}"+a+"?applyID="+id;
				frm.action=url;
				frm.submit();
			}
		}
</script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>