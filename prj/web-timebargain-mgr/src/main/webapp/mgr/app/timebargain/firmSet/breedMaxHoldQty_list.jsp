<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>����Ʒ�ֶ������б�</title>
    <script type="text/javascript">
   
      // �����Ϣ��ת
	  function forwardAdd(){
			// ��ȡ����Ȩ�޵� URL
			var addUrl=document.getElementById('add').action;
			// ��ȡ������תURL
			var url = "${basePath}"+addUrl;
			// �������ҳ��
			if(showDialog(url, "", 800, 600)){
				// �����ӳɹ�����ˢ���б�
				ECSideUtil.reload("ec");
			}
	  }
	  
	  // ����ɾ����Ϣ
	  function deleteQty(){
			// ��ȡ����Ȩ�޵� URL
			var deleteUrl = document.getElementById('delete').action;
			// ��ȡ������תURL
			var url = "${basePath}"+deleteUrl;
			// ִ��ɾ������
			updateRMIEcside(ec.ids,url);
			
	  }

	  // ��ϸ��Ϣ��ת
	  function viewById(firmID, breedID){
			//��ȡ����Ȩ�޵� URL
			var viewUrl = "/timebargain/firmSet/breedSpecial/viewMaxHoldQty.action";
			//��ȡ������תURL
			var url = "${basePath}"+viewUrl;
			//�� URL ��Ӳ���
			url += "?entity.firmID="+firmID + "&entity.breedID=" + breedID;
			// �����޸�ҳ��
			if(showDialog(url, "", 800, 600)){
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
		  
		    <div class="div_gn">
			  <rightButton:rightButton name="���" onclick="forwardAdd();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/addMaxHoldQtyFoward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="ɾ��" onclick="deleteQty();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/deleteMaxHoldQty.action" id="delete"></rightButton:rightButton>
			</div>     
		       
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="breedQty"
							  action="${basePath}/timebargain/firmSet/breedSpecial/listMaxHoldQty.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedQty.xls" csvFileName="breedQty.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${breedQty.firmID},${breedQty.breedID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    
					    <ec:column property="_1" title="�޸�" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="viewById('<c:out value="${breedQty.firmID}"/>','<c:out value="${breedQty.breedID}"/>')"><img title="�����޸���Ϣ" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a> --%>
                          <rightHyperlink:rightHyperlink text="<img title='�����޸���Ϣ' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="viewById('${breedQty.firmID}','${breedQty.breedID}')" action="/timebargain/firmSet/breedSpecial/viewMaxHoldQty.action"/>
                        </ec:column>
						<ec:column property="firmID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="breedID" title="Ʒ�ִ���" width="10%" style="text-align:center;" />
						<ec:column property="maxHoldQty" title="��󶩻���" width="10%" style="text-align:center;">
					      <c:if test="${breedQty.maxHoldQty == -1}">
					                    ������
					      </c:if>
					      <c:if test="${breedQty.maxHoldQty != -1}">
					          ${breedQty.maxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="cleanMaxHoldQty" title="��󾻶�����" width="10%" style="text-align:center;">
					      <c:if test="${breedQty.cleanMaxHoldQty == -1}">
					                    ������
					      </c:if>
					      <c:if test="${breedQty.cleanMaxHoldQty != -1}">
					          ${breedQty.cleanMaxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="modifyTime" title="�޸�ʱ��" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${breedQty.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
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
