<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>拆仓单</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${basePath }/front/app/bill/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
			<script>
		jQuery(document).ready(function(){
			$("#frm").validationEngine("attach");
			var changeNum = function(){
			//	$(this).val(value.replace(/\D/g,'')) //去掉非数字的字符，设为文本框的值
				//计算剩余的商品数量
				var num = ${entity.quantity };
				$("#dismantle > input").each(function(){
					var value = $(this).val();  //获取文本框的值
					//	var val =parseFloat( $(this).attr("value"));
						var reg = /^-?\d+\.?\d{0,2}$/; //匹配整数或小数
						if(reg.test(value)){
							if( value > 0){
								num = num - parseFloat(value);
								$(this).next().text('')
								$("#flag").attr("value",true);
							}else{
								$(this).next().text('仓单数量填写错误！').css("color","red");
								$("#flag").attr("value",false);
								return false;
							}
						}else{
							$(this).next().text('仓单数量只能包含两位小数！').css("color","red");
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
					$("#surplus").text(num.toFixed(2) + "             仓单剩余数量不足！！！");
					$("#surplus").css("color","red");
				}
			}
			//注册事件
			$("input[name='amount']").keyup(changeNum);
			var num1 = ${entity.quantity };
			$("#surplus").text(num1.toFixed(2));  //设置初始值
			//提交查单
			$("#sub").click(function (){
				 changeNum();
				var num = $("#surplus").text();
				var val=document.getElementById("amount").value;
				if(num == 0 && ( $("#flag").attr("value")=='true' ||  $("#flag").attr("value")==true)){
					if(val !=0){
						if(affirm("确认拆单？")){
						frm.target="frame";
						frm.action="${frontPath}/bill/unregister/dismantleStock.action";
						frm.submit();
						}
					}else{
						alert("拆单数量错误！");
						return false;
					}
				}else{
					alert("拆单数量错误！");
						return false;
					}
				});
			var count = 1;
			//添加输入框。因为新加输入框值为0，所以同时设置不可提交。
			$("#add").click(function(){
				$('  <input type="text" id="amount'+count+++'" name="amount"  onfocus="this.select()" value="0"  class="validate[required,custom[double]]"/><span></span><br />').bind('keyup',changeNum).appendTo("#dismantle");
				$("#flag").attr("value",false);
			});
			//删除最后的输入框
			$("#reduce").click(function (){
				//文本框多余1个时可以执行删除，删除文本框之后重新计算剩余数量
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
				<div class="title font_orange_14b">温馨提示:</div>
				<div class="content">　　在此可对您的未注册仓单进行数量拆分。
您用于履约的注册仓单数量应与成交数量吻合。当仓单数量过大时，可以使用拆单功能来实现需求。

				</div>
			</div>
			<iframe  name="frame" width=0 height=0  application='yes'></iframe>
			<form id="frm" action="${frontPath}/bill/unregister/dismantleStock.action" method="post">
			<div class="form margin_10b">
					<input type="hidden" name="stockId"  value="${ entity.stockId}"/>
					<div class="column1">当前信息：</div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="17%" scope="row">当前仓单号：</th>
						<td width="83%">${entity.stockId }</td>
					</tr>
					<tr>
						<th scope="row">商品总量：</th>
						<td><fmt:formatNumber pattern="#0.00" value="${entity.quantity }"></fmt:formatNumber>(${entity.unit }) &nbsp;</td>
					</tr>
					<tr>
						<th scope="row">剩余数量：</th>
						<td><label id="surplus"></label>(${entity.unit })&nbsp; </td>
					</tr>
				</table>
				<div class="column1">拆单：<span id="flag" value="true" ></span> </div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="17%" scope="row">拆单后仓单数量：</th>
						<td width="83%" >
							<div id="dismantle" style="overflow:auto;height:220px;max-height:220px;" >
								<input type="text" id="amount" name="amount" onfocus="this.select()" value="0" class="validate[required,custom[double]]" /><span></span><br />
								<input type="text" id="amount0" name="amount" onfocus="this.select()" value="0" class="validate[required,custom[double]]"  /><span></span><br />
							</div>
						</td>
					</tr>
				</table>
			</div>
					<input type="button" id="add" class="btbg" value="加" />&nbsp;
					<input type="button" id="reduce" class="btbg" value="减 " />&nbsp;
					
					<input type="button" id="sub" class="btbg" value="提 交" />&nbsp;
					<input type="button" class="btbg" onclick="stockBack();" value="取 消 " />&nbsp;
				</div>
			</form>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>