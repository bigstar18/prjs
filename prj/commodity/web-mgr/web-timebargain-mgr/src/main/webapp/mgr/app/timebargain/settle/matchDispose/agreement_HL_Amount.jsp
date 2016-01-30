<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%--设置商品是否含税 后续操作可以直接使用 1为不含税 0为含税 --%>
<c:set value="${entity.taxIncluesive}" var="WT" scope="page"/>
<html>
  <head>
    <title>升贴水设置</title>
    
    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
    <script type="text/javascript">
    

	  function checkNum(){
			var val= frm.hl_Amount.value
			if(val.indexOf(".") != -1 && val.substring(val.indexOf(".")+1,val.length).length>2){
				return "* 小数点后面为2位";
			}
			if(${entity.buyPayout_Ref }+val*1<0){
				return "买方升贴水后货款不能小于0！";
			}
			if(${entity.sellIncome_Ref }+val*1<0){
				return "卖方升贴水后货款不能小于0！";
			}
	  }
	  jQuery(document).ready(function() {
			
		  $("#frm").validationEngine('attach');
			
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
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
			          温馨提示 :交收配对【${entity.matchID}】升贴水信息
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
					     升贴水信息
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="8" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">买方交易商代码：</td>
						<td >
						  ${entity.firmID_B }&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">卖方交易商代码：</td>
						<td>
						  ${entity.firmID_S }&nbsp;
						</td>
					  </tr>
					  <tr>
			             <td align="right">商品代码：</td>
						<td>
						  ${entity.commodityID}&nbsp;
						</td>
					  </tr>
					  <tr>
					  <td align="right">交收数量：</td>
						<td>
							${entity.quantity }&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">买方基准货款：</td>
						<td >
						  <fmt:formatNumber value="${entity.buyPayout_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">已收买方货款：</td>
						<td>
						  <fmt:formatNumber value="${entity.buyPayout + entity.buyTax }" pattern="#,##0.00"/>
						&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">卖方基准货款：</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					   <tr>
					    <td align="right">已付卖方货款：</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome+entity.buyTax }" pattern="#,##0.00"/>
						&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">当前升贴水：</td>
						<td>
						  <fmt:formatNumber value="${entity.HL_Amount }" pattern="#,##0.00" />&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">修改升贴水为：</td>
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
			
						<rightButton:rightButton name="保存" onclick="" className="btn_sec"
							action="/timebargain/settle/matchDispose/updateHL_Amount.action" id="update" ></rightButton:rightButton>
		  </td>
	    </tr>
	  </table>
    </div>
	</form>	
  </body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>