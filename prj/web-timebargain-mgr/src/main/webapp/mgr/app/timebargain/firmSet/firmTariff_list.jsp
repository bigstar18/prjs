<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>�������ײ�ѡ���б�</title>
    <script type="text/javascript">
      //ִ�в�ѯ�б�
	  function dolistquery() {
		frm.submit();
	  }

      // �޸���Ϣ��ת
	  function updateForward(id){
		
		//��ȡ����Ȩ�޵� URL
		var updateUrl = "/timebargain/firmSet/firmTariff/updateFirmTariffForward.action";
		//��ȡ������תURL
		var url = "${basePath}" + updateUrl;
		//�� URL ��Ӳ���
		url += "?entity.firmID="+id;
		//�����޸�ҳ��
		if(showDialog(url, "", 600, 450)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
	  }

	   // ����ȡ���ײ���Ϣ
	   function closeTariff(){
			//��ȡ����Ȩ�޵� URL
			var updateUrl = document.getElementById('update').action;
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//ִ���޸Ĳ���
			updateRMIEcside(ec.ids,url);
		}
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/firmTariff/firmTariffList.action?sortColumns=order+by+modifyTime+desc" method="post">
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
							         �ײʹ���:&nbsp;
								<label>
								  <input type="text" class="input_text" id="tariffID" name="${GNNT_}primary.tariffID[like]" value="${oldParams['primary.tariffID[like]']}" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							         �Ƿ������ײ�:&nbsp;
								<label>
								  <select id="isTariff" name="isTariff"  class="normal" >
									<option value="">ȫ��</option>
									<option value="Y">��</option>							
									<option value="N">��</option>								
								  </select>
							    </label>
							     <script >
									frm.isTariff.value = "${isTariff}";
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
            
            <div class="div_gn">		
				<rightButton:rightButton name="ȡ���ײ�" onclick="closeTariff();" className="anniu_btn" action="/timebargain/firmSet/firmTariff/updateFirmTariffClose.action" id="update"></rightButton:rightButton>
			</div>
            
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="firm"
							  action="${basePath}/timebargain/firmSet/firmTariff/firmTariffList.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firm.xls" csvFileName="firm.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firm.firmID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />

						<ec:column property="firmID" title="�����̴���" width="20%" ellipsis="true" style="text-align:center;" />
						<ec:column property="status" title="״̬" width="20%" style="text-align:center;">
					      ${firm_statusMap[firm.status] }
						</ec:column>
						<ec:column property="modifyTime" title="�޸�ʱ��" width="20%" style="text-align:center;" >
						  <fmt:formatDate value="${firm.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>		
						 <ec:column property="tariffI" title="�ײʹ���" width="20%" style="text-align:center;" sortable="false" filterable="false">
						  <c:if test="${firm.tariffID != 'none' }">
						   <%--  <a href="<c:out value="${basePath}"/>/timebargain/firmSet/firmTariff/viewTariff.action?tariffID=${firm.tariffID }&sortColumns=order+by+commodityID+asc" title="�鿴�ײ�">
						      <c:out value="${firm.tariffID }"/>
						    </a> --%>
						    <rightHyperlink:rightHyperlink text="${firm.tariffID }" href="${basePath}/timebargain/firmSet/firmTariff/viewTariff.action?tariffID=${firm.tariffID }&sortColumns=order+by+commodityID+asc" title="�鿴�ײ�" action="/timebargain/firmSet/firmTariff/viewTariff.action"/>
						  </c:if>
						</ec:column>	
						<ec:column property="_1" title="�����ײ�" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="updateForward('<c:out value="${firm.firmID}"/>')"><img title="�����ײ�" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a> --%>
                          <rightHyperlink:rightHyperlink text="<img title='�����ײ�' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="updateForward('${firm.firmID}')" action="/timebargain/firmSet/firmTariff/updateFirmTariffForward.action"/>
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
