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
    <title>��Ʒ��Ӷ�����</title>
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
			  <form name="frm" action="${basePath}/timebargain/brokerReward/breedRewardSumList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0" align="center">
						  <tr>  
						     <td class="table3_td_1" align="right">
							         ��ʼƷ��:&nbsp;
								<label>
								  <select id="startBreed" name="startBreed">
								    <option value="">ȫ��</option>
									<c:forEach items="${breedStartList}" var="map" >
									  <option value="${map.BREEDID}">${map.BREEDID}</option>
					                </c:forEach>
								  </select>
							    </label>
							 </td>
							 <td class="table3_td_1" align="right">
							         ����Ʒ��:&nbsp;
								<label>
								  <select id="endBreed" name="endBreed">
								    <option value="">ȫ��</option>
									<c:forEach items="${breedEndList}" var="map" >
									  <option value="${map.BREEDID}">${map.BREEDID}</option>
					                </c:forEach>
								  </select>
							    </label>
							 </td>
							
                              <td class="table3_td_1" align="right">
											��ʼ����:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"				
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											��������:
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
				    <ec:table items="pageInfo.result" var="breedRewardSum"
							  action="${basePath}/timebargain/brokerReward/breedRewardSumList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedRewardSum.xls" csvFileName="breedRewardSum.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="BREEDID" title="Ʒ�ִ���" width="10%" style="text-align:center;" />
					    <ec:column property="BREEDNAME" title="Ʒ������" width="10%" style="text-align:center;" />
						<ec:column property="SUMTRADEFEE" title="������" width="10%" style="text-align:center;" calcTitle= "�ϼ�" format="#,##0.00"  cell="number"  calc="total" />	
						<ec:column property="SUMSELFGAIN" title="������������" width="10%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMMARHETGAIN" title="������������" width="10%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMBROKEREACHDIVIDE" title="�����������ѷֳ�" width="10%" style="text-align:center;"format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMFINALMARHETGAIN" title="������ʵ��������" width="10%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />
						<ec:column property="SUMREWARD" title="������ʵ��������" width="10%" style="text-align:center;" format="#,##0.00"  cell="number"  calc="total" />  
						<ec:column property="clearDate" title="��������" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${breedRewardSum.CLEARDATE }" pattern="yyyy-MM-dd" />
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
