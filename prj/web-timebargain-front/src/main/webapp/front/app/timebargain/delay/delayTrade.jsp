<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�ʽ�����ҳ��</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link href="${skinPath}/css/app/timebargain/delay/delayTrade.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
			
		<script type="text/javascript">
			$(function() {
	
				/**************************** ����ΪprintDelayQuotaion.jsp���õ��Ľű� ****************************/
				
				//ˢ�����ڽ�������
				function refreshData(){
					$.ajax( {
						url : "${frontPath }/app/timebargain/delayAjax/delayRealTimeInfo.action",
						type : 'post',
						cache : false,
						success : function(msg) {
							//���������ñ�־  ����:true δ����:false   
							//var neutralFlag = $("#neutral").html() == null || $("#neutral").html() == "" ? false : true;
							$.each(msg, function(i, n) {
								var _commodityId = n['COMMODITYID'];
								var _buysettleqty = $("#" + _commodityId + " div[name='buysettleqty']");
								var _sellsettleqty = $("#" + _commodityId + " div[name='sellsettleqty']");
								var _totalamount = $("#" + _commodityId + " div[name='totalamount']");
								var _reservecount = $("#" + _commodityId + " div[name='reservecount']");
								var _price = $("#" + _commodityId + " div[name='price']");
								var _neutralQty = $("#" + _commodityId + " div[name='neutralQty']");
	
								_buysettleqty.html(n['BUYSETTLEQTY']);
								_sellsettleqty.html(n['SELLSETTLEQTY']);
								_totalamount.html(n['TOTALAMOUNT']);
								_reservecount.html(n['RESERVECOUNT']);
								_price.html(n['PRICE']);
								
								//�������������
								if(_neutralQty.html() != null){
									var _neutralside = n['NEUTRALSIDE'];
									var qty = '-';
									if(_neutralside == '��'){
										qty = n['BUYNEUTRALQTY'];
									}else if(_neutralside == '��'){
										qty = n['SELLNEUTRALQTY'];
									}
									_neutralQty.html(qty);
								}
									
							});
							
						}
					});
				}
	
				window.setInterval(function(){
					refreshData();
				},1000);
				
				
				/**************************** ����ΪprintDelayOrder.jsp���õ��Ľű� ****************************/
				
				
				//Ĭ����ʾ��ѡ� �����걨��ѯѡ� JQuery����
				var defaultShow = $('#clicktab .tabbtn li').eq(0);
				//��ǰ��ʾ��ѡ�, ��ʼֵΪĬ��ѡ�JQuery����, 
				//��Ϊȫ�ֱ�����¼��ǰѡ��ڽ�������ɹ��������ǰѡ�����¼�
				var currentShow = defaultShow;
				//ѡ���껬���
				$('#clicktab .tabbtn li').click(function(){
					//��ȡ����ʽ   ---  declaration �걨��ѯ ��  capital �ʽ��ѯ ��   order ������ѯ
					var processMode = $(this).attr('processMode');
					//alert(processMode);
					//ȡ�ö�Ӧ�����ַ���������
					var currAction = $(this).attr('action');
					var commodityId = $("#commodityId_" + processMode).val();
					//��������
					sendRequest(currAction, commodityId, processMode);
					currentShow = $(this);
					//����չʾ
					TabSelect("#clicktab .tabbtn li", "#clicktab .tabcon", "current", $(this))
				});
				
				//��������걨��ѯ�¼�
				defaultShow.trigger("click");
	
				//�л�ѡ�
				function TabSelect(tab,con,addClass,obj){
					var $_self = obj;
					var $_nav = $(tab);
					$_nav.removeClass(addClass),
					$_self.addClass(addClass);
					var $_index = $_nav.index($_self);
					var $_con = $(con);
					$_con.hide(),
					$_con.eq($_index).show();
				}
	
				//Ϊ������ѯ��ťע�����¼�
				$("input[type='button'][name='delayOrderQuery']").click(function(){
					//ȡ�ö�Ӧ�����ַ������ʽ���������
					var currAction = $(this).attr('action');
					var processMode = $(this).attr("processMode");
					var commodityId = $("#commodityId_" + processMode).val();
	
					sendRequest(currAction,commodityId, processMode);
				});
				
				//�������󷽷�
				function sendRequest(currAction,commodityId, processMode){
				
					$.ajax({
						url : currAction,
						type : 'post',
						data:{"commodityId" : commodityId},
						cache : false,
						dataType : "json",
						success : function(msg) {
							processData(msg,processMode);
						},
						error : function(errormsg){
							alert("���ڹ���ʱ��δ�����������µ�¼��");
						}
					});
				}
				//�������ݷ���
				function processData(msg,processMode){
					switch(processMode) {
						case "declaration":
							processDeclaration(msg);
						break;
						case "capital" :
							processCapital(msg);
						break; 
						case "order" :
							processOrder(msg);
						break; 
					}
				}
	
				var infoTagStart = "<td><div class='ordercol'>";
				var infoTagEnd = "</div></td>";
				
				//�����걨��ѯ
				function processDeclaration(msg){
	
					$("#sellectAll").removeAttr("checked");
					
					var infoTag = [];
					
					$.each(msg, function(i, n) {
						infoTag.push("<tr class='font_b tr_bg'>");
						infoTag.push(infoTagStart);
						infoTag.push("<input type='checkbox' name='orderNo' value = '" + n['A_ORDERNO'] + "'/>");
						infoTag.push(infoTagEnd);
						//�걨��
						infoTag = dataWrap(infoTag, n['A_ORDERNO']);
						//�걨ʱ��
						infoTag = dataWrap(infoTag, n['TIME']);
						//������
						infoTag = dataWrap(infoTag, n['CUSTOMERID']);
						//��Ʒ����
						infoTag = dataWrap(infoTag, n['COMMODITYID']);
						//��/�� 
						infoTag = dataWrap(infoTag, n['FLAG']);
						//����
						infoTag = dataWrap(infoTag, n['QUANTITY']);
						//δ�ɽ�
						infoTag = dataWrap(infoTag, n['NOTRADEQTY']);
						//״̬
						var status = n['STATUS'];
						switch (status) {
						case 1:
							status = "���걨";
							break;
						case 2:
							status = "���ֳɽ�";
							break;
						case 3:
							status = "ȫ���ɽ�";
							break;
						case 5:
							status = "�ѳ���";
							break;
						case 6:
							status = "���ֳɽ��󳷵�";
							break;
	
						default:
							status = "δ֪";
							break;
						}
						infoTag = dataWrap(infoTag, status);
						//�걨����
						infoTag = dataWrap(infoTag, n['TYPE']);
	
						infoTag.push("</tr>");
					});
					
					var infoStr = infoTag.join('')
					$('#delayOrderInfo tr:not(#declarationFirstTr)').remove();
					$('#delayOrderInfo tr:last-child').after(infoStr);
				}
				
				//�����ʽ��ѯ
				function processCapital(msg){
					var usableCapital = $("span[id='usableCapital']");
					var unusableCapital = $("span[id='unusableCapital']");
					if(msg.length != 0){
						usableCapital.html("��" + msg[0]['USEFULFUND']);
						unusableCapital.html("��" + msg[0]['FROZENFUNDS']);
					}else{
						usableCapital.html("��Ǹ������������ܳ������⣬�����µ�¼��ѯ��");
					}
				}
				
				//��������ѯ
				function processOrder(msg){
					var infoTag = [];
	
					$.each(msg, function(i, n) {
						infoTag.push("<tr class='font_b tr_bg'>");
						
						//�����̴���
						infoTag = dataWrap(infoTag, n['CUSTOMERID']);
						//��Ʒ����
						infoTag = dataWrap(infoTag, n['COMMODITYID']);
						//��/�� 
						infoTag = dataWrap(infoTag, n['FLAG']);
						//�ֲ�����
						infoTag = dataWrap(infoTag, n['HOLDQTY']);
						//��������
						infoTag = dataWrap(infoTag, n['FROZENQTY']);
	
						infoTag.push("</tr>");
					});
					var infoStr = infoTag.join('')
					$('#orderInfo tr:not(#orderFirstTr)').remove();
					$('#orderInfo tr:last-child').after(infoStr);
				}
	
				//ƴװhtml��ǩ����
				function dataWrap(infoTag, nodeData){
					infoTag.push(infoTagStart);
					infoTag.push(nodeData);
					infoTag.push(infoTagEnd);
					return infoTag;
				}
	
				//ȫѡ�л�
				$("#sellectAll").change(function(){
					var isChecked = !!($(this).attr('checked'));
					var allCheckBox = $("input[type='checkbox'][name='orderNo']");
					if(isChecked){
						allCheckBox.attr("checked","checked");
					}else{
						allCheckBox.removeAttr("checked");
					}
				});
	
				//����ѡ��
				$("#cancel").click(function(){
					var allChecked = $("input[type='checkbox'][name='orderNo']:checked");
					if(allChecked.length == 0){
						alert("��ѡ��Ҫ���������ݣ�");
						return;
					}
					if(!confirm("ȷ��Ҫ����ô��")){
						return;
					}
					var currAction = $(this).attr("action");
					var orderNos = [];
					allChecked.each(function(){
						orderNos.push($(this).val());
					});
					$.ajax({
						url : currAction,
						type : 'post',
						data:{"orderNos" : orderNos},
						cache : false,
						dataType : "json",
						success : function(msg) {
							alert(msg);
							$("#sellectAll").removeAttr("checked");
							$('#clicktab .tabbtn li').eq(0).trigger("click");
							
						},
						error : function(errormsg){
							alert("���ڹ���ʱ��δ�����������µ�¼��");
						}
					});
				});
	
	
				/**************************** ����ΪdelayOrder.jsp���õ��Ľű� ****************************/
	
				$("#applayBtn").click(function(){
					if(!$(":radio[name='bs_flag']").attr("disabled") && !($(":radio:checked[name='bs_flag']").val())){
						alert("��ѡ����������");
						return;
					}
					var quantity = $.trim($("#settleDeclare_quantity").val());
					if(!isNumber(quantity)){
						alert("������������Ϊ����0��ͷ������");
						$("#settleDeclare_quantity").val("");
						return;
					}
					if(quantity.length > 10){
						alert("�����������ܳ���10λ��");
						$("#settleDeclare_quantity").val("");
						return;
					}
					if(!confirm("ȷ���µ���?")){
						return;
					}
					var currAction = $(this).attr('action');
					var commodityId = $("#settleDeclare_commodityId").val();
					var delayOrderType = $(":radio[name='delayOrderType']:checked").val();
					var bsFlag = $(":radio[name='bs_flag']:checked").val();
					
					$.ajax( {
						url : currAction,
						type : 'post',
						data:{"commodityId" : commodityId, "delayOrderType" : delayOrderType, "bsFlag" : bsFlag, "quantity" : quantity},
						cache : false,
						dataType : "json",
						success : function(msg) {
							alert(msg);
							currentShow.trigger("click");
						},
						error : function(errormsg){
							alert("���ڹ���ʱ��δ�����������µ�¼��");
						}
					});
				});
				//�ж��Ƿ�Ϊ�������
				function isNumber(str){
					if(""==str){
						return false;
					}
					var reg = /^[1-9]\d*$/;
					return str.match(reg)!=null;
				}        
				$(":radio[name='delayOrderType']").change(function(){
					setBsFlagVisibility($(this).val());
				});
				//�������������Ŀ����� ----  1 : ����    2 : ������
				function setBsFlagVisibility(s){
					var _bsFlag = $(":radio[name='bs_flag']");
					if(s == 1){
						_bsFlag.removeAttr("disabled");
					}else if(s == 2){
						_bsFlag.attr("disabled","disabled");
					}
				}
			});
				
		</script>
		<style type="text/css">
		.btn{cursor:pointer;}
		</style>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div style="margin-left: 14px;" >
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
		</div>
		<!-------------------------Body End------------------------->
		<div>
			<jsp:include page="/front/app/timebargain/delay/printDelayQuotation.jsp"></jsp:include>
		</div>
		<div>
			<!-- ��ѯDIV -->
			<div  style="width: 70%; float: left;">
				<jsp:include page="/front/app/timebargain/delay/printDelayOrder.jsp"></jsp:include>
			</div>
			<!-- �µ�DIV -->
			<div style="width : 30%; float: left;">
				<jsp:include page="/front/app/timebargain/delay/delayOrder.jsp"></jsp:include>
			</div>
		</div>
	</body>
</html>