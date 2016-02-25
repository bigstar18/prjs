<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>注册交易商信息页面</title>
       <link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>		
	<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//执行%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("您确认要执行此操作吗？")){
					$("#subfrm").submit();
				}
				}
			});
	    });
	
		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
				$("#frm").submit();
		}
	</script>	
  </head>
  
  <body>
  <form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoFirmInfoPage.action"></form>
  <iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以进行注册交易商信息操作。</div>
			</div>
			<div class="clear"></div>
			<div id="u24" class="u24"  >						
			<div id="message" class="form margin_10b">			
						
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/gotoAddCorrPage.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
				<tr>
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">							
							<input name="firmID" type=text readonly value="${firmID}">						
					</td>
				</tr>
			    <tr>
			        <td align="right">选择银行：&nbsp;</td>
					<td align="left">
						<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;">
							<option value="">请选择</option>
							<c:forEach var="bank" items="${pageInfo.result}">
								<option value="${bank.bankID}">${bank.bankName}</option>
							</c:forEach>
						</select>
					</td>
			    </tr>
			    <tr>
					<td align="right">银行帐号：&nbsp;</td>
					<td align="left">
						<input id="account" name="account"  class="validate[required,maxSize[30]]" /><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">重复银行帐号：&nbsp;</td>
					<td align="left">
						<input id="reaccount" name="reaccount" class="validate[required,equals[account],maxSize[30]]"/><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">账户名：&nbsp;</td>
					<td align="left">
						<input id="accountName" name="accountName" class="validate[required,maxSize[30]]"><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType" class="validate[required,maxSize[30]]">
							<option value="">请选择</option>
							<option value="1">身份证</option>
							<option value="8">组织机构代码</option>
							<option value="9">营业执照</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input id="card" name="card" value="" type=text class="validate[required,maxSize[30]]"><span>*</span>
					</td>
				</tr>
				<tr>
					<td align="right">开户行名称：&nbsp;</td>
					<td align="left">
						<!--  <select  id="bankName" name="bankName">
							<option value="">--请选择--</option>
							<option value="NBCB">宁波银行</option>
							<option value="BOCOMM">交通银行</option>
							<option value="CIB">兴业银行</option>
							<option value="BOC">中国银行</option>
							<option value="CEB">光大银行</option>
							<option value="CCB">建设银行</option>
							<option value="ABC">农业银行</option>
							<option value="ICBC">工商银行</option>
							<option value="CMB">招商银行</option>
							<option value="CMBC">民生银行</option>
							<option value="CITIC">中信银行</option>
							<option value="SPDB">浦发银行</option>
							<option value="UNION">银联</option>
							<option value="SDB">深圳发展银行</option>
							<option value="PINGANBANK">平安银行</option>
							<option value="HXB">华夏银行</option>
							<option value="GDB">重庆三峡银行</option>
							<option value="CQRCB">重庆农村商业银行</option>
							<option value="PSBC">邮政储蓄银行</option>
						</select>-->
						<input id="bankName" name="bankName"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行省份：&nbsp;</td>
					<td align="left">	
						<input id="bankProvince" name="bankProvince"/>
					</td>
				</tr>
				<tr>
					<td align="right">开户行所在市：&nbsp;</td>
					<td align="left">
						<input id="bankCity" name="bankCity"/>
					</td>
				</tr>
				<tr>
					<td align="right">电话：&nbsp;</td>
					<td align="left">
						<input name="mobile" value="" type=text>
					</td>
				</tr>
				<tr>
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="Email" value="" type=text>
					</td>
				</tr>
					
			</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="确认" class="btbg"/>
							</span>
						</label>
					</div>
					
				
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
