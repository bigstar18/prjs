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
    <title>�����ѻ���</title>
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

	  function initSummarizingWay(){
			//���ܷ�ʽvalue
			var summarizingWay = document.getElementById("summarizingWay");

			//������/Ʒ�� ������
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//�ֽ�����/���� ������
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");
			
			
			//Ʒ��
			var breed = document.getElementById("breedId");
			
			if(${summarizingWay1 == 'summarizingFirm'}){
				//������
				summarizingWayInpt1.value = "summarizingFirm";
				if(${summarizingWay2 == 'Summarizing'}){
					//����
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 1);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "Summarizing";

					breed.disabled = "disabled";
					breed.style.cssText = "background-color: gray;";
				}else if(${summarizingWay2 == 'SettleDay'}){
					//�ֽ�����
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 3);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "SettleDay";

					breed.disabled = "disabled";
					breed.style.cssText = "background-color: gray;";
				}
			}else if(${summarizingWay1 == 'summarizingBreed'}){
				//Ʒ��
				summarizingWayInpt1.value = "summarizingBreed";
				if(${summarizingWay2 == 'Summarizing'}){
					//����
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 2);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "Summarizing";
				}else if(${summarizingWay2 == 'SettleDay'}){
					//�ֽ�����
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 4);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "SettleDay";
				}
			}
			
			/*�������ܷ�ʽ,����val����valָ����option����*/
			function eachSummarizingWay(way, val){
				wayChildren = way.childNodes;
				for(var i in wayChildren){
					if(wayChildren[i].value == val){
						return wayChildren[i];
					}
				}
			}

		}

	  /*�ı���ܷ�ʽ*/
		function summarizingWayChange(){
			var summarizingWayVal = document.getElementById("summarizingWay").value;
			//������/Ʒ�� ������
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//�ֽ�����/���� ������
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");

			var breed = document.getElementById("breedId");
			if(summarizingWayVal == '1'){
				//�����̻���
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "Summarizing";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '2'){
				//Ʒ�ֻ���
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "Summarizing";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}else if(summarizingWayVal == '3'){
				//�����̽�����
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "SettleDay";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '4'){
				//Ʒ�ֽ�����
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "SettleDay";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}
		}
    </script>
  </head>
  
  <body onload="initSummarizingWay();">
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/brokerReward/brokerUserFeeList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0" align="center">
						  <tr>  
						      <td class="table3_td_1" align="right">
									�����̿�ʼ��
									<label>
										<input type="text" class="input_text" id="startFirm" name="startFirm" maxlength="16"/>
									</label>
								</td>
								<td class="table3_td_1" align="left">
									�����̽�����
									<label>
										<input type="text" class="input_text" id="endFirm"   name="endFirm" maxlength="16"/>
									</label>
								</td>
								<td class="table3_td_1" align="left">
									�����̱�ţ�
									<label>
										<input type="text" class="input_text" id="brokerid"   name="brokerid" maxlength="16"/>
									</label>
								</td>
							</tr>
							<tr>
								<td class="table3_td_1" align="right" style="width: 250px">
										���ܷ�ʽ��
										<label>
											<input type = "hidden" id = "summarizingWayInpt1" name = "summarizingWay1" value = ""/>
									        <input type = "hidden" id = "summarizingWayInpt2"name = "summarizingWay2" value = ""/>
											<select id="summarizingWay" name="summarizingWay" onchange="summarizingWayChange()" class="normal">
												<option value="1">�ֽ����̻���</option>
												<option value="2">�ֽ�����Ʒ�ֻ���</option>
												<option value="3">�ֽ����̽����ջ���</option>
												<option value="4">�ֽ�����Ʒ�ֽ����ջ���</option>
											</select>
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
							</tr>
							<tr>
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
					      frm.startFirm.value = "<c:out value='${startFirm}'/>";
					      frm.endFirm.value = "<c:out value='${endFirm}'/>";
					      frm.brokerid.value = "<c:out value='${brokerid}'/>";
					      var summarizingWayInput3 = "<c:out value='${summarizingWay}'/>";
					      if(summarizingWayInput3 =="summarizingFirmAndSummarizing"){
							frm.summarizingWay.value=1;
						  }else if(summarizingWayInput3 == "summarizingBreedAndSummarizing"){
							  frm.summarizingWay.value=2;
						  }else if(summarizingWayInput3 == "summarizingFirmAndSettleDay"){
							  frm.summarizingWay.value=3;
						   }else if(summarizingWayInput3 == "summarizingBreedAndSettleDay"){
							   frm.summarizingWay.value=4;
						  };
					      frm.breedId.value = "<c:out value='${breedId}'/>";
					      frm.beginDate.value = "<c:out value='${beginDate}'/>";
					      frm.endDate.value = "<c:out value='${endDate}'/>";
					      summarizingWayChange();
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
				    <ec:table items="pageInfo.result" var="brokerFeeSum"
							  action="${basePath}/timebargain/brokerReward/brokerUserFeeList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedRewardSum.xls" csvFileName="breedRewardSum.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					 
					     <ec:column property="BROKERID" title="����������"  width="10%" style="text-align:center;" />					 
						  <ec:column property="FIRMID" title="�����̴���"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="FIRMNAME" title="����������"  width="10%" style="text-align:center;" />
						  <c:if test="${summarizingWay1 == 'summarizingBreed'}">
							<ec:column property="BREEDNAME" title="Ʒ������"  width="10%" style="text-align:center;" />
						  </c:if>
						  <c:if test="${summarizingWay1 == 'summarizingFirm'}">
							<ec:column property="_1" title="Ʒ������" value="ȫ��" width="10%" style="text-align:center;" />
						  </c:if>
						  <ec:column property="QUANTITY" title="�ɽ���"  width="10%" style="text-align:right;" calcTitle="�ϼ�" format="#,###" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.QUANTITY}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="TRADEFEE" title="����������(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.TRADEFEE}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="FIRSTPAY" title="Ӷ���׿�(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.FIRSTPAY}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="SECONDPAY" title="Ӷ��β��(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.SECONDPAY}" pattern="#,##0.00"/>
						  </ec:column>
						  <c:if test="${summarizingWay2 == 'SettleDay'}">
							<ec:column property="CLEARDATE" title="����ʱ��" cell="date" format="date" width="10%" style="text-align:center;"/>
						  </c:if>
					
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
