<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>��������Ϣ</title>
    <script type="text/javascript">
      //ִ�в�ѯ�б�
	  function dolistquery() {
		frm.submit();
	  }

      // �޸�״̬
	  function updateStatus(){
			var status = document.getElementById("sta").value;
			var updateStatusUrl = "/timebargain/firmSet/tradePrivilege/updateStatusFirm.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateStatusUrl+"?status="+status;
			
			updateRMIEcside(ec.ids,url);
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
	  }

      // ��ת������Ȩ��
	  function updateForwardPrivilege(id) {
			//��ȡ����Ȩ�޵� URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//�� URL ��Ӳ���
			url += "?firmID="+id;

			parent.location.href = url;
	  }

	  // ��ת���׿ͻ�
	  function updateForwardCustomer(id) {
		  
			//��ȡ����Ȩ�޵� URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listCustomer.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//�� URL ��Ӳ���
			url += "?firmID="+id;

			parent.location.href = url;
	  }

	  // ��ת����Ա
	  function updateForwardTrader(id) {
			//��ȡ����Ȩ�޵� URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listTrader.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//�� URL ��Ӳ���
			url += "?firmID="+id;

			parent.location.href = url;
	  }
	  //�����޸�Ȩ��
	  function batchSet(){
		    //pTop("<c:url value="/mgr/app/timebargain/firmSet/batchSetPower.jsp"/>",700,500);
		    //var url = "/mgr/app/timebargain/firmSet/batchSetPower.jsp";
		    //location.href = "${basePath}"+url;

		   //��ȡ������תURL
			var url = "${basePath}"+"/timebargain/firmSet/tradePrivilege/addPrivilegeForward.action";
			//�������ҳ��
			if(showDialog(url, "", 600, 550)){
				//�����ӳɹ�����ˢ���б�
				ECSideUtil.reload("ec");
			}
	  }
	  function pTop(location, width, height)
	  {
		    return showModalDialog(location,window,'dialogWidth:'+width+'px;dialogHeight:'+height+'px;dialogLeft:'+(screen.width-width)/2+'px;dialogTop:'+(screen.height-height)/2+'px;center:yes;help:no;resizable:no;status:no;scrollbars:no');
	  }
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmInfo.action?sortColumns=order+by+createTime+desc" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							 <td class="table3_td_1" align="right">
							         �����̴���:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmID" name="${GNNT_}primary.firmID[like]" value="${oldParams['primary.firmID[like]']}" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							         ����������:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmName" name="${GNNT_}primary.firmName[like]" value="${oldParams['primary.firmName[like]']}" />
							    </label>
							  </td>
                              <td class="table3_td_1" align="right">
								״̬:&nbsp;
								<label>									
								  <select id="status" name="${GNNT_}primary.status[=][int]">
								    <option value="">ȫ��</option>
			                        <c:forEach items="${firm_statusMap}" var="map">
					                  <option value="${map.key}">${map.value}</option>
				                    </c:forEach>		
			                      </select>
								</label>
								<script >
								  frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  			</script>
					          </td>
						    <td class="table3_td_anniu" align="left">
						      <button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">����</button>
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
				    <ec:table items="pageInfo.result" var="firmInfo"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmInfo.action?sortColumns=order+by+createTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firmInfo.xls" csvFileName="firmInfo.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firmInfo.firmID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="firmID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="firmName" title="����������" width="10%" style="text-align:center;" />
						<ec:column property="status" title="״̬" width="10%" style="text-align:center;">
					      ${firm_statusMap[firmInfo.status] }
						</ec:column>
						<ec:column property="customerCounts" title="�����ͻ�����" width="10%" style="text-align:center;">
						<c:set var="status" value="${firm.status}"></c:set>
					      <c:choose>
                            <c:when test="${firmInfo.customerCounts > 0}">
                              <%-- <a href="#" onclick="updateForwardCustomer('<c:out value="${firmInfo.firmID}"/>')"><c:out value="${firmInfo.customerCounts}"/>&nbsp;&nbsp;<img title="�޸Ķ����ͻ�" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>"></a> --%>
                              <rightHyperlink:rightHyperlink text=" ${firmInfo.customerCounts}&nbsp;&nbsp;<img title='�޸Ķ����ͻ�' src='${skinPath}/image/app/timebargain/customer.gif'/>" href="#" onclick="updateForwardCustomer('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listCustomer.action"/>
                            </c:when>
                            <c:otherwise>
                              0&nbsp;<img title="�޸Ľ��׿ͻ�" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>">
                           </c:otherwise>
                         </c:choose>
                       	
						</ec:column>
						<ec:column property="tcounts" title="����Ա����" width="10%" style="text-align:center;">
											      
					       <c:choose>
                            <c:when test="${firmInfo.tcounts > 0}">
                              <%-- <a href="#" onclick="updateForwardTrader('<c:out value="${firmInfo.firmID}"/>')"><c:out value="${firmInfo.tcounts}"/>&nbsp;&nbsp;<img title="�޸Ľ���Ա" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>"></a> --%>
                              <rightHyperlink:rightHyperlink text=" ${firmInfo.tcounts}&nbsp;&nbsp;<img title='�޸Ľ���Ա' src='${skinPath}/image/app/timebargain/customer.gif'/>" href="#" onclick="updateForwardTrader('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listTrader.action"/>
                            </c:when>
                            <c:otherwise>
                              0&nbsp;<img title="�޸Ľ���Ա" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>">
                           </c:otherwise>
                         </c:choose>
					      
						</ec:column>
						<ec:column property="createTime" title="����ʱ��" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${firmInfo.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>
						<ec:column property="prop" title="������Ȩ��" width="5%" style="text-align:center;" sortable="false"  tipTitle="�鿴Ȩ��" >
						  <%-- <a href="#" onclick="updateForwardPrivilege('<c:out value="${firmInfo.firmID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action"/>
						</ec:column>
								
					  </ec:row>
					</ec:table>
				  </td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
		          <td>
			        <select id="sta">
			          <c:forEach items="${firm_statusMap}" var="map">
					    <option value="${map.key}">${map.value}</option>
				      </c:forEach>		
			        </select>
			        <input type="button" class="anniu_btn" value="�޸�״̬"  onclick="updateStatus()">
			        <input type="button" class="anniu_btn" value="��������"  onclick="batchSet()">
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
