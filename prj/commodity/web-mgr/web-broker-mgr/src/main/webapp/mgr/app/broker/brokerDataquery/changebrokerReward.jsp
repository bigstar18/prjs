<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>֧��Ӷ��</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){

						//��֤֧��Ӷ���Ƿ���ڴ���Ӷ��
						if(canReward()){
							var vaild = affirm("��ȷ��Ҫ������");
							if(vaild){
								//�����ϢURL
								var updateDemoUrl = $(this).attr("action");
								//ȫ URL ·��
								var url = "${basePath}"+updateDemoUrl;
								$("#frm").attr("action",url);
								$("#frm").submit();
							}
						}
						
					}
				});
			});

			//���������ֺ�.
			function onlyNumberInput()
			{
			  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
			  {
			    event.returnValue=false;
			  }
			}

			//��֤֧��Ӷ���Ƿ���ڴ���Ӷ��
			function canReward(){
				if(jQuery("#money").val() <= 0){
					alert("֧��Ӷ��������0������������");
					return false;
				}
				
				//var money = document.getElementByID("money");
				if(jQuery("#money").val() > ${entity.amount}){
					alert("֧��Ӷ���ܴ��ڴ���Ӷ������������");
					return false;
				}

				return true;
			}
		</script>
	</head>
<body>
		<form id="frm" method="post" >
			<div class="div_cx">
				<table border="0" width="100%" align="center">
			
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												֧��Ӷ��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
											       
											   <tr>
													<td align="right">														
														�����̱�ţ�
													</td>
													<td>
													 ${ entity.brokerId}	
																						   
													</td>
													<input type="hidden" name="entity.brokerId" value="${entity.brokerId}" />
													<input type="hidden" name="entity.moduleId" value="${entity.moduleId}" />
													<input type="hidden" name="entity.occurDate" value="<fmt:formatDate value="${entity.occurDate}" pattern="yyyy-MM-dd"/>" />								
											   </tr>
											   <tr>
													<td align="right">							
														����Ӷ��
													</td>
													<td>
													 ${entity.amount}												   
													</td>	
													<input id="amount" type="hidden" name="entity.amount" value="${ entity.amount}" />								
											   </tr>
											   <tr>
													<td align="right">														
														�Ѹ�Ӷ��
													</td>
													<td>
													 ${entity.paidAmount}												   
													</td>	
													<input type="hidden" name="entity.paidAmount" value="${ entity.paidAmount}" />								
											   </tr>
										     <tr height="35">
								                <td align="right" class="td_size" width="20%">
									                                        ֧��Ӷ�� ��
								               </td>
								               <td align="left" width="35%">
									             <input id="money" style="width: 160px;"
										            name="money" type="text" onkeypress="return onlyNumberInput()"
										                class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text" />
										              <span class="required">*</span>
								              </td>
							             </tr>
											 </table>
									   </div>
									</td>
								</tr>
						   </table>
					  </td>
				   </tr>
			  </table>
		 </div>
		 <div class="tab_pad" >
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/broker/brokerDataquery/updateBrokerReward.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�رմ���</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>