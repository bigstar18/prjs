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
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function(){
		$("#frm").validationEngine("attach");
		var changeNum = function(){
		//	$(this).val(value.replace(/\D/g,'')) //ȥ�������ֵ��ַ�����Ϊ�ı����ֵ
			//����ʣ�����Ʒ����
			var num = ${entity.quantity };
			$("#dismantle > input").each(function(){
				var value = $(this).val();  //��ȡ�ı����ֵ
				//	var val =parseFloat( $(this).attr("value"));
					var reg = /^\d+\.?\d{0,2}$/; //ƥ��������С��
					if(reg.test(value)){
						if( value > 0){
							num = num - parseFloat(value);
							$(this).next().text('')
							$("#flag").attr("value",true);
						}else{
							$(this).next().text('�ֵ�������д����').css("color","red");
							$("#flag").attr("value",false);
							return false;
						}
					}else{
						$(this).next().text('�ֵ�����ֻ�ܰ�����λС����').css("color","red");
						$("#flag").attr("value",false);
						return false;
					}
					
			});
			if(num.toFixed(2)==0){
				$("#surplus").text('0.00');
				}else{
					$("#surplus").text(num.toFixed(2));
					}
			if(num.toFixed(2) >= 0){
				$("#surplus").css("color","green");	
			}else{
				$("#surplus").text(num.toFixed(2) + "             �ֵ�ʣ���������㣡����");
				$("#surplus").css("color","red");
			}
		}
		//ע���¼�
		$("input[name='amount']").keyup(changeNum);
		var num2=${entity.quantity };  //���ó�ʼֵ
		$("#surplus").text(num2.toFixed(2)); 
		//�ύ�鵥
		$("#sub").click(function (){
			 changeNum();
			var num = $("#surplus").text();
			var val=document.getElementById("amount").value;
			if(num == 0 && ( $("#flag").attr("value")=='true' ||  $("#flag").attr("value")==true)){
				if(val !=0){
					if(affirm("ȷ�ϲ𵥣�")){
						frm.submit();
					}
				}else{
					alert("����������");
					return false;
				}
			}else{
				alert("����������");
					return false;
				}
			});
		var count = 1;
		//����������Ϊ�¼������ֵΪ0������ͬʱ���ò����ύ��
		$("#add").click(function(){
			$('  <input type="text" id="amount'+count+++'" name="amount"  onfocus="this.select()"  value="0"  class="validate[required,custom[double]]"/><span></span><br />').bind('keyup',changeNum).appendTo("#dismantle");
			$("#flag").attr("value",false);
		});
		//ɾ�����������
		$("#reduce").click(function (){
			//�ı������1��ʱ����ִ��ɾ����ɾ���ı���֮�����¼���ʣ������
			if($("#dismantle input").size() > 2){
			 $("#dismantle > input:last").remove();
			 $("#dismantle > span:last").remove();
			 $("#dismantle > br:last").remove();
			 count--;
			 changeNum();
			}
			
		});
	});
</script>
<html>
	<head>
		<title>�𵥲���</title>
	</head>
	<body style="overflow-y: auto">
		<form id="frm" method="post"
			action="${basePath}/stock/list/dismantleStock.action"
			targetType="hidden">
			<input type="hidden" name="stockID"  value="${ entity.stockId}"/>
			<div class="div_cx">
				<table border="0" width="80%" align="center" class="table_style">
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :�𵥲���
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
													<tr height="20">
														<td align="right" width="25%">
															��ǰ�ֵ��ţ�
														</td>
														<td width="*">
															${entity.stockId }
														</td>
													</tr>
													<tr>
														<td align="right">��Ʒ������</td>
														<td><fmt:formatNumber value="${entity.quantity }" pattern="###0.00"/>(${entity.unit })&nbsp;</td>
													</tr>
													<tr>
														<td align="right">ʣ��������</td>
														<td><label id="surplus"></label>(${entity.unit })&nbsp; </td>
													</tr>
													<tr>
														<td align="right">
															<div class="column1">�𵥣�<span id="flag" value="true" ></span> </div>
														</td>
														<td>
															<table border="0" cellspacing="0" cellpadding="0" width="100%" class="table2_style">
																<tr>
																	<td width="25%" align="right">�𵥺�ֵ�������</td>
																	<td width="*" >
																		<div id="dismantle" style="overflow:auto;height:150px;max-height:100px;" >
																			<input type="text" id="amount" name="amount"  onfocus="this.select()" value="0" class="validate[required,custom[double]]" /><span></span><br />
																			<input type="text" id="amount0" name="amount"  onfocus="this.select()" value="0" class="validate[required,custom[double]]"  /><span></span><br />
																		</div>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td align="right" >
											<input type="button" id="add" class="btn_sec" value="��" />&nbsp;
															<input type="button" id="reduce" class="btn_sec" value="�� " />&nbsp;&nbsp;
											<rightButton:rightButton name="��" className="btn_sec"
												action="/stock/list/dismantleStock.action" id="sub"
												onclick=""></rightButton:rightButton>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="btn_sec" onClick="window.close();">�ر�</button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
 function apend(){
	 var vaild = affirm("��ȷ��Ҫ������");
		if(vaild){
			frm.submit();
		}
	 }
</script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>