<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ʽ�ת</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript"></script>
		<script>
		
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//ִ��ת��%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ��ת����")){
						subfrm.action="${basePath}/bank/bank/bankTransferMoneyGFB.action";
						$("#subfrm").submit();
					}
				}
			});
	    });
		
		</script>
	</head>
	<body>
		<form id="subfrm" action="" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="3"/>
		    <table border="0" width="100%" align="center">
			<tr>
				<td>
					<div class="warning">
						<div class="content">
							��ܰ��ʾ :�г��ڹ������е������[G����ͨ�˻�]ת��[������˻�]�������ʵ������������л�ת��
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="div_cxtj">
					    <div class="div_cxtjL"></div>
					    <div class="div_cxtjC">ִ�в���</div>
					    <div class="div_cxtjR"></div>
		   			</div>
					<div style="clear: both;"></div>
					<div class="div_tj">
						<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
							<tr>
								<td align="right" width="40%">
									ѡ�����У�
								</td>
								<td>
									<select id="bankID" name="bankID" class="validate[required] input_text">
										<option value="">��ѡ��</option>
										
										<option value="66" selected >������</option>
										
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">
								���
								</td>
								<td>
									<input type="text" id="money" name="money" value="" class="validate[required,custom[doubleCus],maxSize[9]]" />
								</td>
							</tr>
							<tr>
								<td align="right">
								</td>
								<td>
									<input type="button" id="performBtn"  value="ȷ��" class="btn_sec"/>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			
			</table>
		</form>
	</body>
</html>
