<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交易客户权限</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		frm.submit();
	  }

	 
	  // 详细信息跳转
	  function viewById(id, result){
			//获取配置权限的 URL
			var viewUrl = "/timebargain/settle/matchDispose/viewMatchDispose.action";
			//获取完整跳转URL
			var url = "${basePath}" + viewUrl;
			//给 URL 添加参数
			url += "?entity.matchID=" + id + "&entity.result=" + result;
			
			document.location.href = url;
	  }

    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		    
		    <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/settle/matchDispose/matchDisposeList.action?sortColumns=order+by+createTime+desc" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							 <td class="table4_td_1" align="right">
							         买方交易商代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmID_B" name="${GNNT_}primary.firmID_B[like]" value="${oldParams['primary.firmID_B[like]']}" />
							    </label>
							  </td>&nbsp;&nbsp;
							  <td class="table4_td_1" align="right" >
							         卖方交易商代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmID_S" name="${GNNT_}primary.firmID_S[like]" value="${oldParams['primary.firmID_S[like]']}" />
							    </label>
							  </td>

							  <td class="table3_td_1" align="right">
							          执行结果:&nbsp;
								<label>
									<select id="result" name="${GNNT_}primary.result[=][int]"  class="normal" style="width: 120px">
									  <option value="">全部</option>
									  <c:forEach items="${settleMatch_resultMap}" var="map">
									    <option value="${map.key }">${map.value }</option>
									  </c:forEach>
									</select>
								</label>  
							  </td>
							  <td class="table3_td_1" align="right">
							          状态:&nbsp;
								<label>
									<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
									  <option value="">全部</option>
									  <c:forEach items="${settleMatch_statusMap}" var="map">
									    <option value="${map.key }">${map.value }</option>
									  </c:forEach>
									</select>
								</label>  
							  </td>
                            <script type="text/javascript">
								  frm.result.value = "<c:out value='${oldParams["primary.result[=][int]"] }'/>";
								  frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  		</script>
					  		
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">重置</button>
						    </td>
					      </tr>
					    </table>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
            </div>
	                    
            <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="matchDispose"
							  action="${basePath}/timebargain/settle/matchDispose/matchDisposeList.action?sortColumns=order+by+createTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="matchDispose.xls" csvFileName="matchDispose.csv"
							  showPrint="true" listWidth="100%" 
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="matchID" title="配对编号" width="10%" style="text-align:center;" >
						 <%--  <a href="#" class="blank_a" onclick="viewById('<c:out value="${matchDispose.matchID}"/>', '<c:out value="${matchDispose.result}"/>')">
						    <font color="#880000">${matchDispose.matchID }</font>
						  </a>--%>
						  <rightHyperlink:rightHyperlink text="<font color='#880000'>${matchDispose.matchID}</font>" className="blank_a" onclick="viewById('${matchDispose.matchID}','${matchDispose.result}');" action="/timebargain/settle/matchDispose/viewMatchDispose.action" />
						</ec:column>
						<ec:column property="commodityID" title="商品代码" width="10%" style="text-align:center;" />
						<ec:column property="quantity" title="交收数量" width="10%" style="text-align:center;" />
						<ec:column property="firmID_B" title="买方交易商代码" width="10%" ellipsis="true" style="text-align:center;" />		
						<ec:column property="firmID_S" title="卖方交易商代码" width="10%" ellipsis="true" style="text-align:center;" />		
						<ec:column property="createTime" title="创建时间" width="10%" style="text-align:center;">
						  <fmt:formatDate value="${matchDispose.createTime}" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>		
						<ec:column property="result" title="执行结果" width="10%" style="text-align:center;" >
						  ${settleMatch_resultMap[matchDispose.result] }
						</ec:column>		
						<ec:column property="status" title="交收状态" width="10%" style="text-align:center;" >
						  ${settleMatch_statusMap[matchDispose.status] }
						</ec:column>							
					  </ec:row>
					  
					</ec:table>
				  </td>
				</tr>
			  </table>
		
            </div>		
	      </td>
	    </tr>
      </table>
       
    </div>
  </body>
</html>
