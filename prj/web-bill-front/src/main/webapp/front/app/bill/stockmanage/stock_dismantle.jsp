<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��ֵ�</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${basePath }/front/app/bill/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
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
						var reg = /^-?\d+\.?\d{0,2}$/; //ƥ��������С��
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
			var num1 = ${entity.quantity };
			$("#surplus").text(num1.toFixed(2));  //���ó�ʼֵ
			//�ύ�鵥
			$("#sub").click(function (){
				 changeNum();
				var num = $("#surplus").text();
				var val=document.getElementById("amount").value;
				if(num == 0 && ( $("#flag").attr("value")=='true' ||  $("#flag").attr("value")==true)){
					if(val !=0){
						if(affirm("ȷ�ϲ𵥣�")){
						frm.target="frame";
						frm.action="${frontPath}/bill/unregister/dismantleStock.action";
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
				$('  <input type="text" id="amount'+count+++'" name="amount"  onfocus="this.select()" value="0"  class="validate[required,custom[double]]"/><span></span><br />').bind('keyup',changeNum).appendTo("#dismantle");
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

		function stockBack(){
			frm.target="";
			frm.action="${frontPath}/bill/unregister/list.action?sortColumns=order+by+stockId+desc";
			frm.submit();
		}
		function showMsgBoxCallbak(result,msg){
			if(result==1){
				stockBack();
			}else{
				clearSubmitCount();
			}
		}
		</script>		
	</head>
	<body>
		 <div id="main">
		 	<div class="warning">
				<div class="title font_orange_14b">��ܰ��ʾ:</div>
				<div class="content">�����ڴ˿ɶ�����δע��ֵ�����������֡�
��������Լ��ע��ֵ�����Ӧ��ɽ������Ǻϡ����ֵ���������ʱ������ʹ�ò𵥹�����ʵ������

				</div>
			</div>
			<iframe  name="frame" width=0 height=0  application='yes'></iframe>
			<form id="frm" action="${frontPath}/bill/unregister/dismantleStock.action" method="post">
			<div class="form margin_10b">
					<input type="hidden" name="stockId"  value="${ entity.stockId}"/>
					<div class="column1">��ǰ��Ϣ��</div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="17%" scope="row">��ǰ�ֵ��ţ�</th>
						<td width="83%">${entity.stockId }</td>
					</tr>
					<tr>
						<th scope="row">��Ʒ������</th>
						<td><fmt:formatNumber pattern="#0.00" value="${entity.quantity }"></fmt:formatNumber>(${entity.unit }) &nbsp;</td>
					</tr>
					<tr>
						<th scope="row">ʣ��������</th>
						<td><label id="surplus"></label>(${entity.unit })&nbsp; </td>
					</tr>
				</table>
				<div class="column1">�𵥣�<span id="flag" value="true" ></span> </div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="17%" scope="row">�𵥺�ֵ�������</th>
						<td width="83%" >
							<div id="dismantle" style="overflow:auto;height:220px;max-height:220px;" >
								<input type="text" id="amount" name="amount" onfocus="this.select()" value="0" class="validate[required,custom[double]]" /><span></span><br />
								<input type="text" id="amount0" name="amount" onfocus="this.select()" value="0" class="validate[required,custom[double]]"  /><span></span><br />
							</div>
						</td>
					</tr>
				</table>
			</div>
					<input type="button" id="add" class="btbg" value="��" />&nbsp;
					<input type="button" id="reduce" class="btbg" value="�� " />&nbsp;
					
					<input type="button" id="sub" class="btbg" value="�� ��" />&nbsp;
					<input type="button" class="btbg" onclick="stockBack();" value="ȡ �� " />&nbsp;
				</div>
			</form>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>