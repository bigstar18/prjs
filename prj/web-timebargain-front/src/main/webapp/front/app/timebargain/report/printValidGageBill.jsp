<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>当前仓单抵顶数量表页面</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${frontPath }/app/timebargain/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript"
			src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
	jQuery(document).ready(function() {
		//设置表单验证
			$("#frm").validationEngine("attach");
			$("#view").click(function() {
				if ($("#frm").validationEngine('validate')) {
					$("#frm").submit();
				}
			});
			
			if($("#commodityID").val()!=""){
					$("#pagechanel").html('');
					
			}
		});
	
	function condition_reset() {
			$("#commodityID").val('');
	}
	

</script>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示 :
				</div>
				<div class="content">
					在此展示您的当前有效仓单抵顶数量表信息。
				</div>
			</div>
			<div class="main_copy">
				<form id="frm" action="${frontPath}/app/timebargain/report/validGageBillQuery.action" method="post">
					<div class="sort">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" width="250">
									<label>
										商品代码：
										<input id="commodityID" name="commodityID" type="text" value="${commodityID}" onblur="commodityID_check()"/>
									</label>
								</td>
								<td align="right">
									<input type="button" value="重置" onclick="condition_reset()">
								</td>
								<td width="110" height="30" align="center" valign="middle">
									<a href="#" id="view"><img
											src="${skinPath }/image/memberadmin/searchbt1.gif" width="93"
											height="23" border="0" /> </a>
								</td>
							</tr>
						</table>
					</div>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div class="tb_bg">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content" >
											<tr class="font_b tr_bg">
												<td width="25%" align="center"><div class="ordercol" id="noticeId">商品代码</div></td>
												<td width="25%" align="center"><div class="ordercol" id="noticeId">当前仓单数量</div></td>
												<td width="25%" align="center"><div class="ordercol" id="noticeId">可用仓单数量</div></td>
												<td width="25%" align="center"><div class="ordercol" id="noticeId">抵顶冻结仓单数量</div></td>
											</tr>
											
											<c:if test="${validGageBillList != null }">
											
											  <c:set var="listCount" value="0"></c:set>
											  <c:forEach items="${validGageBillList}" var="validGageBill">		
											      <c:set var="listCount" value="1"></c:set>		
										          <tr>
										            <td class="main_tbw01" width="25%"><c:out value="${validGageBill.COMMODITYID}"/></td>
										            <td class="main_tbw01" width="25%"><c:out value="${validGageBill.QUANTITY}"/></td>
										            <td class="main_tbw01" width="25%"><c:out value="${validGageBill.QUANTITY-validGageBill.FROZENQTY}"/></td>
										            <td class="main_tbw01" width="25%"><c:out value="${validGageBill.FROZENQTY}"/></td>
										          </tr>
										         
											  </c:forEach>
											  
											
											 <c:if test="${listCount == 0 }">
												  <tr>
										            <tr>
										               <td colspan="4">没有记录</td>
										            </tr>
										          </tr>
										     </c:if>
										  
										   </c:if>	
										</table>
									</div>
								</td>
							</tr>
							<tr id="pagechanel">
								<td>
								   <c:if test="${pageInfo !=null}">
									  <jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
		       </form>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>