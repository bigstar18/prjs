<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>�������</title>
    <script type="text/javascript">
      //ִ�в�ѯ�б�
	  function dolistquery() {
		frm.submit();
	  }

      // ��ϸ��Ϣ��ת
	  function viewById(id){
		
		//��ȡ����Ȩ�޵� URL
		var viewUrl = "/timebargain/firmSet/risk/viewFirm.action";
		//��ȡ������תURL
		var url = "${basePath}"+viewUrl;
		//�� URL ��Ӳ���
		url += "?entity.firmID=" + id;
		// �����޸�ҳ��
		if(showDialog(url, "", 600, 450)){
			// ����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
	  }
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/risk/riskList.action" method="post">
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
				    <ec:table items="pageInfo.result" var="risk"
							  action="${basePath}/timebargain/firmSet/risk/riskList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="risk.xls" csvFileName="risk.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="firmID" title="�����̴���" width="20%" ellipsis="true" style="text-align:center;" >
						 <%--  <a href="#" class="blank_a" onclick="viewById('<c:out value="${risk.firmID}"/>')">
						    <font color="#880000">${risk.firmID}</font>
						  </a> --%>
						  <rightHyperlink:rightHyperlink href="#" text="<font color='#880000'>${risk.firmID}</font>" className="blank_a" onclick="viewById('${risk.firmID}');" action="/timebargain/firmSet/risk/viewFirm.action" />
						</ec:column>
						<ec:column property="maxHoldQty" title="��󶩻�����" width="20%" style="text-align:center;">
					      <c:if test="${risk.maxHoldQty == -1}">
					                    ������
					      </c:if>
					      <c:if test="${risk.maxHoldQty != -1}">
					          ${risk.maxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="minClearDeposit" title="��ͽ���׼����" width="20%" style="text-align:center;" >
						  <fmt:formatNumber value="${risk.minClearDeposit }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="maxOverdraft" title="��Ѻ�ʽ�" width="20%" style="text-align:center;" >
						  <fmt:formatNumber value="${risk.maxOverdraft }" pattern="#,##0.00" />
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
