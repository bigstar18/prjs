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
    <title>交易商佣金汇总</title>
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

	  function isQryHis_onclick()
	  {
	    if(frm.isQryHis.checked)
	    {
	      frm.startBreed.disabled=false;
	      frm.endBreed.disabled=false;
	      frm.isPartition.value="Y";
	    }
	    else
	    {
	      frm.startBreed.disabled=true;
	      frm.endBreed.disabled=true;
	      frm.isPartition.value="N";
	    }
	  }

	  function window_onload()
	  {
		  if('${isPartition}' == "Y"){
			  frm.isQryHis.checked = true;
		  }
	      isQryHis_onclick();
	  }

    </script>
  </head>
  
  <body onload="window_onload()">
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/brokerReward/firmRewardDetailList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0" align="center">
					      <input type="hidden" id="isPartition" name="isPartition"/>
						  <tr>
						    
						     <td class="table3_td_1" align="right">
						       <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()"/>
						       <label for="isQryHis" >分品种</label>		
							         开始品种:&nbsp;
								<label>
								  <select id="startBreed" name="startBreed">
								    <option value="">全部</option>
									<c:forEach items="${breedStartList}" var="map" >
									  <option value="${map.BREEDID}">${map.BREEDID}</option>
					                </c:forEach>
								  </select>
							    </label>
							 </td>
							 <td class="table3_td_1" align="left">
							         结束品种:&nbsp;
								<label>
								  <select id="endBreed" name="endBreed">
								    <option value="">全部</option>
									<c:forEach items="${breedEndList}" var="map" >
									  <option value="${map.BREEDID}">${map.BREEDID}</option>
					                </c:forEach>
								  </select>
							    </label>
							 </td>
							 <td class="table3_td_1" >
							         开始交易商:&nbsp;
								<label>
								  <input type="text" class="input_text" id="startFirm" name="startFirm" value="${startFirm}" />
							    </label>
							  </td>
							  <td class="table3_td_1" >
							         结束交易商:&nbsp;
								<label>
								  <input type="text" class="input_text" id="endFirm" name="endFirm" value="${endFirm}" />
							    </label>
							  </td>
							</tr>
							<tr>
                              <td class="table3_td_1" align="right">
											开始日期:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"				
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" >
											结束日期:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						    <td class="table3_td_anniu">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">重置</button>
						    </td>
					      </tr>
					    </table>
					    <script type="text/javascript">
					      frm.isPartition.value = "<c:out value='${isPartition}'/>";
					      frm.startBreed.value = "<c:out value='${startBreed}'/>";
					      frm.endBreed.value = "<c:out value='${endBreed}'/>";
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
				    <ec:table items="pageInfo.result" var="firmRewardDetail"
							  action="${basePath}/timebargain/brokerReward/firmRewardDetailList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firmRewardDetail.xls" csvFileName="firmRewardDetail.csv"
							  showPrint="true" listWidth="150%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="2%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="FIRMID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="FIRMNAME" title="交易商名称" width="5%" style="text-align:center;" />
						<ec:column property="BROKERID" title="加盟商代码" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="BROKERNAME" title="加盟商名称" width="5%" style="text-align:center;" />
						<c:if test="${isPartition == 'Y'}">
						  <ec:column property="BREEDID" title="品种代码" width="5%" style="text-align:center;" />
						</c:if>
						<ec:column property="SUMTRADEFEE" title="手续费" width="5%" style="text-align:center;" calcTitle= "合计" format="#,##0.00"  cell="number"  calc="total" />	
						<ec:column property="SUMSELFGAIN" title="加盟商手续费" width="5%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMMARHETGAIN" title="交易所手续费" width="5%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMBROKEREACHDIVIDE" title="加盟商手续费分成" width="5%" style="text-align:center;"format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMFINALMARHETGAIN" title="交易所实得手续费" width="5%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMREWARD" title="加盟商实得手续费" width="5%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />  
						<ec:column property="clearDate" title="结算日期" width="5%" style="text-align:center;" >
						  <fmt:formatDate value="${firmRewardDetail.CLEARDATE }" pattern="yyyy-MM-dd" />
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
