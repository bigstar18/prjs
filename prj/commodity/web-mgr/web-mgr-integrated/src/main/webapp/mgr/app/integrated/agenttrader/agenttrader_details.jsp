<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		$("#frm").validationEngine('attach');
		$("#update1").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					$("#frm").attr("action",'${basePath}'+$(this).attr("action"));
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$('#frm').submit();
				}
			}
		});
	});
</script>
<body onload="getSelect()">
	<head>
		<title>代为交易员信息详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
    <form name="frm" id="frm" method="post" targetType="hidden">
    <input name="entity.agentTraderId" value="${entity.agentTraderId}" type="hidden"/>
		<div class="st_title">
			<table border="0" width="90%" align="center">
				<tr>
			        <td>
			            <div class="warning">
			                <div class="content">温馨提示 :代为交易员信息详情</div>
			            </div>
			        </td>
			   </tr>
			   <tr>
					<td>
						<div class="div_cxtj">
				    	<div class="div_cxtjL"></div>
				        <div class="div_cxtjC">代为交易员信息</div>
				        <div class="div_cxtjR"></div>
				        </div>
			            <div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
						
							<tr height="35">
								<td align="right"  width="20%">代为交易员代码 ：</td>
								<td align="left" width="30%" >${entity.agentTraderId}</td>
								<td align="right"  width="20%">代为交易员名称 ：</td>
								<td align="left"  width="30%" >${entity.name}</td>
							</tr>
							<tr height="35">
								<td align="right">代为交易员类型 ：</td>
								<td align="left">
								    <select id="type" name="entity.type" class="normal" style="width: 120px">
		                				<c:forEach var="type" items="${agentTraderTypeMap}">
		                				    <option value="${type.key}" <c:if test="${type.key==entity.type}">selected="selected"</c:if>>${type.value}</option>
		                				</c:forEach>
									</select>
								</td>
		                		<td align="right">代为交易员状态 ：</td>
								<td align="left">
		                			<select id="status" name="entity.status" class="normal" style="width: 120px">
		                				<c:forEach var="status" items="${agentTraderStatusMap}">
		                				<option value="${status.key}" <c:if test="${status.key==entity.status}">selected="selected"</c:if>>${status.value}</option>
		                				</c:forEach>
									</select>
		                		</td>
		              		</tr>
		              		<tr height="35">
		              			<td align="right">创建时间 ：</td>
		              			<td align="left"><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		              			<td align="right">修改时间 ：</td>
		              			<td align="left"><fmt:formatDate value="${entity.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		              		</tr>
						
							
							<tr height="35">
							<td align="right">可操作交易商 ：</td>
								<td align="left" width="" class="td_0size" colspan="4">
								<input type="hidden" id="viewOperateFirm" name="viewOperateFirm" value="${entity.operateFirm}" />
								<script type="text/javascript">
								function getSelect(){
									var ofirms = $("#viewOperateFirm").val();
									var ofirm=ofirms.split(",");
									document.all.operateFirm.options.remove(0); //删除空的object
									for(var i=0;i<ofirm.length;i++){
								  		var oOption=document.createElement("Option");
										oOption.text=ofirm[i]; //option的文本值
										oOption.value=ofirm[i]; //option的value
										oOption.title=ofirm[i]; //option的title
										document.all.operateFirm.add(oOption);
									}
								}
								</script>
						                <select id="operateFirm" name="operateFirm" class="normal" size=5 style="text-align:left;width:150px;">
							            	<option></option>
							            </select>
							            </td>
							</tr>
						</table>
						<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<tr height="35">
				    <td align="right" id="tdId">
						<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/trade/agenttrader/updateAgentTrader.action" id="update1"></rightButton:rightButton>
					    &nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close()">
							关闭
						</button>
					</td>
				</tr>
			</table>
		</div>
						</table>
			   </div>
		
	</form>
</body>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>