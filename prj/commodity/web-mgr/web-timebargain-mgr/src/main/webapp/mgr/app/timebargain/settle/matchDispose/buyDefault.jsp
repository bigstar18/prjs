<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交收配对买方违约信息详情</title>
    
    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
    <script type="text/javascript">
    
      // 返回配对处理列表
	  function goback() {
		 var backUrl = "/timebargain/settle/matchDispose/matchDisposeList.action?sortColumns=order+by+createTime+desc";
		 //获取完整跳转URL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }


	  function event(v){
		  var url = "#";
		  switch(v)
			{	
		  	case 1:
			  	url = "${basePath}/timebargain/settle/matchDispose/viewMatch.action?flag=21&entity.matchID=${entity.matchID}";
				break;
			case 2:
				url = "${basePath}/timebargain/settle/matchDispose/viewMatch.action?flag=22&entity.matchID=${entity.matchID}";
				break;
			case 3:
				url = "${basePath}/timebargain/settle/matchDispose/viewMatch.action?flag=23&entity.matchID=${entity.matchID}";
				break;
			case 4:
				url = "${basePath}/timebargain/settle/matchDispose/viewMatch.action?flag=24&entity.matchID=${entity.matchID}";
				break;
			case 20:
				url = "${basePath}/timebargain/settle/matchDispose/settleMatchLogList.action?matchID=${entity.matchID}";
				break;
			case 21:
				url = "${basePath}/timebargain/settle/matchDispose/billList.action?matchID=${entity.matchID}";
				break;
			}
			if(url=="#"){
				if(v==5){
					var vaild = affirm("买方代码： ${entity.firmID_B },卖方代码： ${entity.firmID_S }\n商品代码：${entity.commodityID},交收数量：${entity.quantity }\n已收买方违约金：${entity.takePenalty_B},\n已付卖方违约金：${entity.payPenalty_S},\n您确定要处理完成吗？");
					if (vaild == true) {
						var url = "${basePath}/timebargain/settle/matchDispose/settleFinish_default.action?entity.matchID=${entity.matchID}";
						document.location.href = url;
					}
				}else if(v==6){
					var vaild = affirm("您确定要撤销吗？\n如果选择确定，交收数据将作废，金额变动自动回滚！");
					if (vaild == true) {
						var url = "${basePath}/timebargain/settle/matchDispose/settleCancel.action?entity.matchID=${entity.matchID}&entity.result=${entity.result}";
						document.location.href = url;
					}
				}
			}else{
				if(showDialog(url, "", 500, 480)){
					var url = "${basePath}/timebargain/settle/matchDispose/viewMatchDispose.action?entity.matchID=${entity.matchID}";
					document.location.href = url;
				};
			}
	  }

	  function viewPrices(bs_Flag){
		  if(showDialog("${basePath}/timebargain/settle/matchDispose/viewPrices.action?matchID=${entity.matchID}&bs_Flag="+bs_Flag, "", 50, 400)){
				var url = "${basePath}/timebargain/settle/matchDispose/viewMatchDispose.action?entity.matchID=${entity.matchID}";
				document.location.href = url;
			};
	  }	  
	  
    </script>
    
  </head>
  <body>
    <div class="div_cx">
	  <table border="0" width="100%" align="center">
	    <tr>
		  <td>
		    <div class="warning">
			  <div class="content">
			          温馨提示 :交收配对【${entity.matchID}】信息
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
					     基本信息
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">配对编号：</td>
						<td>
						  ${entity.matchID}&nbsp;
						</td>
						<td align="right">执行结果：</td>
						<td>
							${settleMatch_resultMap[entity.result] }&nbsp;
						</td>
						<td align="right">交收状态：</td>
						<td>
							${settleMatch_statusMap[entity.status] }&nbsp;
						</td>
						<td>
						  <span onclick="event(20)"  style="cursor:hand;color:blue">查看所有操作记录->></span>
						</td>
					  </tr>
					  <tr>
			            <td align="right">商品代码：</td>
						<td>
						  ${entity.commodityID}&nbsp;
						</td>
						<td align="right">交收数量：</td>
						<td>
							${entity.quantity }&nbsp;
						</td>
						<td colspan="3">&nbsp;</td>					
					  </tr>
					</table>
				  </div>
				</td>
			  </tr>
			</table>
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
					     买方信息
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
					    <td align="right">
						  <b>买方可用资金：</b>
						</td>
						<td >
						  <fmt:formatNumber value="${buyBalance }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">买方交收价：</td>
						<td >
						<c:if test="${entity.settleType!=2 }">
						  <fmt:formatNumber value="${entity.buyPrice }" pattern="#,##0.00"/>&nbsp;
						</c:if>
						<c:if test="${entity.settleType==2 }">
						  <a href="javaScript:viewPrices(1)">价格详细</a>
						</c:if>
						</td>
					  </tr>
					  <tr>
			            <td align="right">买方基准货款：</td>
						<td >
						  <fmt:formatNumber value="${entity.buyPayout_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">
						  <b>买方升贴水后货款：</b>
						</td>
						<td >
						  <c:choose>
						    <c:when test="${(entity.buyPayout_Ref + entity.HL_Amount) > buyBalance }">
							  <font color="red"><fmt:formatNumber value="${entity.buyPayout_Ref + entity.HL_Amount }" pattern="#,##0.00"/></font>
						    </c:when>
						    <c:otherwise>
							  <fmt:formatNumber value="${entity.buyPayout_Ref + entity.HL_Amount }" pattern="#,##0.00"/>
						    </c:otherwise>
					      </c:choose>
					      &nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">
						  <b>已收买方货款：</b>
						</td>
						<td >
						  <fmt:formatNumber value="${entity.buyPayout }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">已收买方保证金：</td>
						<td>
						  <fmt:formatNumber value="${entity.buyMargin }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">收买方违约金：</td>
						<td>
						  <fmt:formatNumber value="${entity.takePenalty_B }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					   <tr>
			            <td align="right">买方交收盈亏：</td>
						<td>
						  <fmt:formatNumber value="${entity.settlePL_B }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					</table>
				  </div>
				</td>
				
				<td>
				  <div class="div_cxtj">
				    <div class="div_cxtjL"></div>
					<div class="div_cxtjC">
					     卖方信息
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="8" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">卖方交易商代码：</td>
						<td>
						  ${entity.firmID_S }&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">
						  <b>卖方可用资金：</b>
						</td>
						<td >
						  <fmt:formatNumber value="${sellBalance }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">卖方交收价：</td>
						<td >
						<c:if test="${entity.settleType!=2 }">
						  <fmt:formatNumber value="${entity.sellPrice }" pattern="#,##0.00"/>&nbsp;
						</c:if>
						<c:if test="${entity.settleType==2 }">
						  <a href="javaScript:viewPrices(2)">价格详细</a>
						</c:if>
						</td>
					  </tr>
					  <tr>
			            <td align="right">卖方基准货款：</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">
						  <b>卖方升贴水后货款：</b>
						</td>
						<td> 
						  <fmt:formatNumber value="${entity.sellIncome_Ref + entity.HL_Amount }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
					    <td align="right">
						  <b>已付卖方货款：</b>
						</td>
						<td >
						  <fmt:formatNumber value="${entity.sellIncome }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">已收卖方保证金：</td>
						<td>
						  <fmt:formatNumber value="${entity.sellMargin }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">付卖方违约金：</td>
						<td>
						  <fmt:formatNumber value="${entity.payPenalty_S }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>
					   <tr>
			            <td align="right">卖方交收盈亏：</td>
						<td>
						  <fmt:formatNumber value="${entity.settlePL_S }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>		
					  
					</table>
				  </div>
				</td>
				
				<td>
				  <div class="div_cxtj">
				    <div class="div_cxtjL"></div>
					<div class="div_cxtjC">
					     其他信息
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="8" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">升贴水：</td>
						<td>
						  <fmt:formatNumber value="${entity.HL_Amount }" pattern="#,##0.00" />&nbsp;
						</td>
					  </tr>
					  <tr>
			            <td align="right">市场货款：</td>
						<td>
						  <fmt:formatNumber value="${entity.sellIncome_Ref - entity.buyPayout_Ref }" pattern="#,##0.00"/>&nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">最近操作员：</td>
						<td>
						  <c:choose>
						    <c:when test="${operator != '' }">  
							  ${operator }
						    </c:when>
						    <c:otherwise>
							    暂无人员操作
						    </c:otherwise>
					      </c:choose>&nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>	
					   <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>	
					  <tr>
			            <td align="right">&nbsp;</td>
						<td>
						  &nbsp;
						</td>
					  </tr>			
					</table>
				  </div>
				</td>
				
			  </tr>
			</table>
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
					     创建修改时间
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				  <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="8" width="100%" align="center" class="table2_style">
					  <tr>
			            <td align="right">创建时间：</td>
						<td>
						  <fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />&nbsp;
						</td>
						<td align="right">修改时间：</td>
						<td>
						  <fmt:formatDate value="${entity.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />&nbsp;
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
	
	<div >
	  <table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
	    <tr align="center">   
	    <%-- 交收状态：未处理、处理中可见，处理完成、作废不可见 --%>  
	    <c:if test="${entity.status <= 1 }">
		  <td>
			<button class="btn_sec1" onClick="event(1)">收买方违约金</button>
		  </td>
		  <td>
			<button class="btn_sec1" onClick="event(2)">付卖方违约金</button>
		  </td>
		  <c:if test="${priceType==2}">	
			  <td>
				<button class="btn_sec1" onClick="event(3)">买方交收盈亏</button>
			  </td>
			  <td>
				<button class="btn_sec1" onClick="event(4)">卖方交收盈亏</button>
			  </td>
		  </c:if>
		  <td>
			<button class="btn_sec" onClick="event(5)">处理完成</button>
		  </td>
		  <td>
			<button class="btn_sec" onClick="event(6)">撤销</button>
		  </td>
		</c:if>
		  
		  <td>
			<button class="btn_sec" onClick="goback()">返回</button>
		  </td>
		  <td width="100">
			&nbsp;
		  </td>
		  <td width="100">
			&nbsp;
		  </td>
		  <td width="100">
			&nbsp;
		  </td>
	    </tr>
	    <tr>
	      <td colspan="9">
	        <font color="red">
			  注：金额精度为分(0.01元)，超过精度部分将被四舍五入。<br/>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收取违约金时会自动返还交收保证金。
			</font>		
	      </td>
	    </tr>
	  </table>
    </div>
		
  </body>
</html>