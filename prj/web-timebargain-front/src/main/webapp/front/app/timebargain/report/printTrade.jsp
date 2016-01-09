<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>成交记录表页面</title>
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
			jQuery(document).ready(function(){
				//设置表单验证
				$("#frm").validationEngine("attach");
				$("#view").click(function(){
					if ($("#frm").validationEngine('validate')) {
						$("#frm").submit();
					}
				});
			});

			function checkBeginDate(){
				var beginTime=document.getElementById("d1").value;
				var endTime=document.getElementById("d2").value;
				if(!Util.CompareDate(beginTime,endTime)){
						return "*开始日期不能大于结束日期";
				}else{
					jQuery('#endTime').validationEngine('hide')
				}
			}
			
			function checkEndDate(){
					var beginTime=document.getElementById("d1").value;
					var endTime=document.getElementById("d2").value;
					if(!Util.CompareDate(beginTime,endTime)){
							return "*结束日期不能小于开始日期";
					}else{
						jQuery('#beginTime').validationEngine('hide')
					}
				}
		</script>
	</head>
	

<!-------------------------Body Begin------------------------->
<body>
 <div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">温馨提示 :</div>
		<div class="content">在此展示您的成交明细信息。</div>
	</div>
 <div class="main_copy">
	<form id="frm" action="${frontPath}/app/timebargain/report/tradesQuery.action" method="post">
		<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="250">
						<label>开始日期：
							<input id="d1" class="Wdate validate[custom[date],past[${today}],funcCall[checkBeginDate]] datepicker" name="d1" value="<c:out value="${d1 }" />" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>结束日期：
							<input id="d2"  class="Wdate validate[custom[date],past[${today}],funcCall[checkEndDate]] datepicker" name="d2" value="<c:out value="${d2 }" />" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td width="110" height="30" align="center" valign="middle">
						<a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a>
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
									<td width="10%" align="center"><div class="ordercol" id="noticeId">客户代码</div></td>
									<td width="12%" align="center"><div class="ordercol" id="noticeId">成交序号</div></td>
									<td width="10%" align="center"><div class="ordercol" id="createTime">商品代码</div></td>
									<td width="10%" align="center"><div class="ordercol" id="noticeId">交易类型</div></td>
									<td width="10%" align="center"><div class="ordercol" id="noticeId">成交价格</div></td>
									<td width="12%" align="center"><div class="ordercol" id="noticeId">数量(批)</div></td>
									<td width="12%" align="center"><div class="ordercol" id="noticeId">委托时间</div></td>
									<td width="10%" align="center"><div class="ordercol" id="noticeId">成交时间</div></td>
									<td width="14%" align="center"><div class="ordercol" id="noticeId">交易手续费</div></td>									
								</tr>
								  <c:set var="listCount" value="0" />
									<c:forEach items="${tradesList}" var="trade">
									      <c:set var="listCount" value="1" />
								          <tr>
								            <td class="main_tbw02" width="10%"><c:out value="${trade.CUSTOMERID}"/></td>
								            <td class="main_tbw01" width="12%"><c:out value="${trade.A_TRADENO}"/></td>
								            <td class="main_tbw01" width="10%"><c:out value="${trade.COMMODITYID}"/></td>
								            <td class="main_tbw01" width="10%"><c:out value="${trade.BSORDERTYPE}"/></td>
								            <td class="main_tbw01" width="10%" align="right">
								              <fmt:formatNumber value="${trade.PRICE}" pattern="#,##0.00"/>
								            </td>
								            <td class="main_tbw01" width="12%">&nbsp;<c:out value="${trade.QUANTITY}"/></td>
								            <td class="main_tbw01" width="12%">
								              <fmt:formatDate value="${trade.ORDERTIME}" pattern="yyyy-MM-dd HH:mm:ss" />
								            </td>
								            <td class="main_tbw01" width="12%">
								               <fmt:formatDate value="${trade.TRADETIME}" pattern="yyyy-MM-dd HH:mm:ss" />
								            </td>
								            <td class="main_tbw01" width="10%" align="right">
								              <fmt:formatNumber value="${trade.TRADEFEE}" pattern="#,##0.00"/>
								            </td>
								        
								          </tr>
								           
									</c:forEach>
										<c:if test="${listCount == 1}">
										  <tr>
								            <td class="main_tbw02" width="10%">合计</td>
								            <td class="main_tbw01" width="12%">&nbsp;</td>
								            <td class="main_tbw01" width="10%">&nbsp;</td>
								            <td class="main_tbw01" width="10%">&nbsp;</td>
								            <td class="main_tbw01" width="10%">&nbsp;</td>
								            <td class="main_tbw01" width="12%">
								              <fmt:formatNumber value="${quantitySum}" />
								            </td>
								            <td class="main_tbw01" width="12%">&nbsp;</td>
								            <td class="main_tbw01" width="12%">&nbsp;</td>
								            <td class="main_tbw01" width="10%" align="right">
								             <fmt:formatNumber value="${tradefeeSum}" pattern="#,##0.00"/>
								            </td>
								          </tr>
								       </c:if>								
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
					<c:if test="${pageInfo != null}">
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