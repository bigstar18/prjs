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
    <title>��������ϸ</title>
    <script type="text/javascript">
      //ִ�в�ѯ�б�
	  function dolistquery() {
		  var startDate = frm.beginDate.value;
		  var endDate = frm.endDate.value;
		  
		 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		 {
		  if(frm.beginDate.value=="")
		  {
		    alert("��ʼ���ڲ���Ϊ�գ�");
		    frm.beginDate.focus();
		    return false;
		  }
		  if(frm.endDate.value=="")
		  {
		    alert("�������ڲ���Ϊ�գ�");
		    frm.endDate.focus();
		    return false;
		  }
		  if ( startDate > '<%=nowDate%>' ) { 
		      alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
		      frm.beginDate.focus();
		      return false; 
		  } 
		  if ( startDate > endDate ) { 
		      alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
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
									�����̱�ţ�
									<label>
										<input type="text" class="input_text" id="brokerId" name="brokerId" maxlength="16"/>
									</label>
								</td>
				
								<td class="table3_td_1" align="right">
								         Ʒ��:&nbsp;
									<label>
									  <select id="breedId" name="breedId">
									    <option value="">ȫ��</option>
										<c:forEach items="${breedStartList}" var="map" >
										  <option value="${map.BREEDID}">${map.BREEDNAME}</option>
						                </c:forEach>
									  </select>
								    </label>
							 </td>
								<td class="table3_td_1" align="left">
									�����̴��룺
									<label>
										<input type="text" class="input_text" id="firmId"   name="firmId" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
									</label>
								</td>
							</tr>
							<tr>
							   <td class="table3_td_1" align="right">
									&nbsp;&nbsp;�ɽ����ͣ�
									<label>
										<select id="bsFlag" name="bsFlag"  class="normal" style="width: 120px">
											<option value="">ȫ��</option>
								          <option value="1">��ɽ�</option>
								          <option value="2">���ɽ�</option>		
										</select>
									</label>
								</td>
                              <td class="table3_td_1" align="right">
											���㿪ʼ����:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"				
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											�����������:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">����</button>
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
					  
					     <ec:column property="BROKERID" title="�����̱��"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="FIRMID" title="�����̴���"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="CLEARDATE" title="��������"  width="10%" style="text-align:center;" >
						     <fmt:formatDate value="${dealDetailList.CLEARDATE}" pattern="yyyy-MM-dd"/>
						  </ec:column>
						  <ec:column property="TRADENO" title="�ɽ���"  width="10%" style="text-align:center;" />
						  <ec:column property="BREEDNAME" title="Ʒ��"  width="10%" style="text-align:center;" />
						  <ec:column property="BSFLAG" title="�ɽ�����"  width="10%" style="text-align:right;" >
						   
						    <c:if test="${dealDetailList.BSFLAG==1}">
								<c:out value="��ɽ�"/>
							</c:if>
							<c:if test="${dealDetailList.BSFLAG==2}">
								<c:out value="���ɽ�"/>
							</c:if>
						  </ec:column>
						  <ec:column property="QUANTITY" title="�ɽ���"  width="10%" style="text-align:right;" calcTitle="�ϼ�" format="#,###" calc="total">
						    <fmt:formatNumber value="${dealDetailList.QUANTITY}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="TRADEFEE" title="����������(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
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
