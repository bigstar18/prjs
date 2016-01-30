<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%--������Ʒ�Ƿ�˰ ������������ֱ��ʹ�� 1Ϊ����˰ 0Ϊ��˰ --%>
<c:set value="${entity.taxIncluesive}" var="WT" scope="page"/>
<html>
  <head>
    <title>����ˮ����</title>
    
    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
    <script type="text/javascript">
    

	  function checkNum(){
			var val= frm.hl_Amount.value
			if(val.indexOf(".") != -1 && val.substring(val.indexOf(".")+1,val.length).length>2){
				return "* С�������Ϊ2λ";
			}
			if(${entity.buyPayout_Ref }+val*1<0){
				return "������ˮ������С��0��";
			}
			if(${entity.sellIncome_Ref }+val*1<0){
				return "��������ˮ������С��0��";
			}
	  }
	  jQuery(document).ready(function() {
			
		  $("#frm").validationEngine('attach');
			
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("��ȷ��Ҫ������");
					if (vaild == true) {
						$("#frm").validationEngine('attach');
						//$('#frm').attr('action', 'action');
					    $("#frm").submit();
						document.getElementById("update").disabled=true;
				}
			}})
	});
    </script>
    
  </head>
  <body>
  <form id="frm" enctype="multipart/form-data" method="post" targetType="hidden" action="${basePath}/timebargain/settle/matchDispose/updateHL_Amount.action?entity.matchID=${entity.matchID}" >
    <div class="div_cx">
	  <table border="0" width="100%" align="center">
	    <tr>
		  <td>
		    <div class="warning">
			  <div class="content">
			          ��ܰ��ʾ :������ԡ�${entity.matchID}������ˮ��Ϣ
			  </div>
			</div>
		  </td>
		</tr>
		<tr>
		  <td>
		    <table border="0" width="100%" align="center">
			  <tr>
			  
			    <td>
				  <div class="div_cxtj">
				    <div class="div_cxtjL"></div>
					<div class="div_cxtjC">
					     ����ˮ��Ϣ
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="8" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">�򷽽����̴��룺</td>
						<td >
						  ${entity.firmID_B }&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">���������̴��룺</td>
						<td>
						  ${entity.firmID_S }&nbsp;
						</td>
					  </tr>
					  <tr>
			             <td align="right">��Ʒ���룺</td>
						<td>
						  ${entity.commodityID}&nbsp;
						</td>
					  </tr>
					  <tr>
					  <td align="right">����������</td>
						<td>
							${entity.quantity }&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">�򷽻�׼���</td>
						<td >
						  <fmt:formatNumber value="${entity.buyPayout_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">�����򷽻��</td>
						<td>
						  <fmt:formatNumber value="${entity.buyPayout + entity.buyTax }" pattern="#,##0.00"/>
						&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">������׼���</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					   <tr>
					    <td align="right">�Ѹ��������</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome+entity.buyTax }" pattern="#,##0.00"/>
						&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">��ǰ����ˮ��</td>
						<td>
						  <fmt:formatNumber value="${entity.HL_Amount }" pattern="#,##0.00" />&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">�޸�����ˮΪ��</td>
						<td>
						 <input class="validate[required,custom[number],funcCall[checkNum]]" id="hl_Amount" name="entity.HL_Amount" size="12" />
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
	
	<div class="tab_pad">
	  <table border="0" cellspacing="0" cellpadding="10" width="100%" align="center">
	    <tr align="center">   
		  <td>
			
						<rightButton:rightButton name="����" onclick="" className="btn_sec"
							action="/timebargain/settle/matchDispose/updateHL_Amount.action" id="update" ></rightButton:rightButton>
		  </td>
	    </tr>
	  </table>
    </div>
	</form>	
  </body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>