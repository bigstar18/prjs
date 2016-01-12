<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
Date sysdate = new Date();
SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
String nowDate = df.format(sysdate);
%>
<html>
  <head>
    <title>手续费明细</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		  var startDate = frm.beginDate.value;
		  var endDate = frm.endDate.value;
		  
		 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		 {
		  if(frm.beginDate.value=="")
		  {
		    alert("开始日期不能为空！");
		    frm.beginDate.focus();
		    return false;
		  }
		  if(frm.endDate.value=="")
		  {
		    alert("结束日期不能为空！");
		    frm.endDate.focus();
		    return false;
		  }
		  if ( startDate > '<%=nowDate%>' ) { 
		      alert("开始日期不能大于当天日期!"); 
		      frm.beginDate.focus();
		      return false; 
		  } 
		  if ( startDate > endDate ) { 
		      alert("开始日期不能大于结束日期!"); 
		      return false; 
		  } 
		 }  
		 frm.submit();
	  }
	  
    </script>
  </head>
  
  <body >
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/brokerReward/hisDealDetailList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0" align="center">
						  <tr>  
						      <td class="table3_td_1" align="left">
									加盟商编号：
									<label>
										<input type="text" class="input_text" id="brokerId" name="brokerId" maxlength="16"/>
									</label>
								</td>
				
								<td class="table3_td_1" align="right">
								         品种:&nbsp;
									<label>
									  <select id="breedId" name="breedId">
									    <option value="">全部</option>
										<c:forEach items="${breedStartList}" var="map" >
										  <option value="${map.BREEDID}">${map.BREEDNAME}</option>
						                </c:forEach>
									  </select>
								    </label>
							 </td>
								<td class="table3_td_1" align="left">
									交易商代码：
									<label>
										<input type="text" class="input_text" id="firmId"   name="firmId" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
									</label>
								</td>
							</tr>
							<tr>
							   <td class="table3_td_1" align="right">
									&nbsp;&nbsp;成交类型：
									<label>
										<select id="bsFlag" name="bsFlag"  class="normal" style="width: 120px">
											<option value="">全部</option>
								          <option value="1">买成交</option>
								          <option value="2">卖成交</option>		
										</select>
									</label>
								</td>
                              <td class="table3_td_1" align="right">
											结算开始日期:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"				
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											结算结束日期:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">重置</button>
						    </td>
					      </tr>
					    </table>
					    <script type="text/javascript">
					      frm.brokerId.value = "<c:out value='${brokerId}'/>";
					      frm.breedId.value = "<c:out value='${breedId}'/>";
					      frm.firmId.value = "<c:out value='${firmId}'/>";
					      frm.bsFlag.value = "<c:out value='${bsFlag}'/>";
					      frm.beginDate.value = "<c:out value='${beginDate}'/>";
					      frm.endDate.value = "<c:out value='${endDate}'/>";
					    </script>
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
				    <ec:table items="pageInfo.result" var="dealDetailList"
							  action="${basePath}/timebargain/brokerReward/hisDealDetailList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedRewardSum.xls" csvFileName="breedRewardSum.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					  
					     <ec:column property="BROKERID" title="加盟商编号"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="FIRMID" title="交易商代码"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="CLEARDATE" title="结算日期"  width="10%" style="text-align:center;" >
						     <fmt:formatDate value="${dealDetailList.CLEARDATE}" pattern="yyyy-MM-dd"/>
						  </ec:column>
						  <ec:column property="TRADENO" title="成交号"  width="10%" style="text-align:center;" />
						  <ec:column property="BREEDNAME" title="品种"  width="10%" style="text-align:center;" />
						  <ec:column property="BSFLAG" title="成交类型"  width="10%" style="text-align:right;" >
						   
						    <c:if test="${dealDetailList.BSFLAG==1}">
								<c:out value="买成交"/>
							</c:if>
							<c:if test="${dealDetailList.BSFLAG==2}">
								<c:out value="卖成交"/>
							</c:if>
						  </ec:column>
						  <ec:column property="QUANTITY" title="成交量"  width="10%" style="text-align:right;" calcTitle="合计" format="#,###" calc="total">
						    <fmt:formatNumber value="${dealDetailList.QUANTITY}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="TRADEFEE" title="交易手续费(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${dealDetailList.TRADEFEE}" pattern="#,##0.00"/>
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
