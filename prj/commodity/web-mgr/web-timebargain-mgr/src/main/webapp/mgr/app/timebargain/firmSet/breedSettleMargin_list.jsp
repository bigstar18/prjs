<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>����Ʒ�ֽ��ձ�֤���б�</title>
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
	  function deleteSettleMargin(){
		  
			// ��ȡ����Ȩ�޵� URL
			var deleteUrl = document.getElementById('delete').action;
			// ��ȡ������תURL
			var url = "${basePath}"+deleteUrl;
			// ִ��ɾ������
			updateRMIEcside(ec.ids,url);
	  }
   
	  // ��ϸ��Ϣ��ת
	  function viewById(firmID, breedID){
			// ��ȡ����Ȩ�޵� URL
			var viewUrl = "/timebargain/firmSet/breedSpecial/viewSettleMargin.action";
			// ��ȡ������תURL
			var url = "${basePath}"+viewUrl;
			// �� URL ��Ӳ���
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
			  <rightButton:rightButton name="���" onclick="forwardAdd();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/addSettleMarginFoward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="ɾ��" onclick="deleteSettleMargin();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/deleteSettleMargin.action" id="delete"></rightButton:rightButton>
			</div>     
		       
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="breedSettleMargin"  
							  action="${basePath}/timebargain/firmSet/breedSpecial/listSettleMargin.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedSettleMargin.xls" csvFileName="breedSettleMargin.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${breedSettleMargin.firmID},${breedSettleMargin.breedID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    
					    <ec:column property="_1" title="�޸�" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="viewById('<c:out value="${breedSettleMargin.firmID}"/>','<c:out value="${breedSettleMargin.breedID}"/>')"><img title="�����޸���Ϣ" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a> --%>
                          <rightHyperlink:rightHyperlink text="<img title='�����޸���Ϣ' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="viewById('${breedSettleMargin.firmID}','${breedSettleMargin.breedID}')" action="/timebargain/firmSet/breedSpecial/viewSettleMargin.action"/>
                        </ec:column>
						<ec:column property="firmID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="breedID" title="Ʒ�ִ���" width="10%" style="text-align:center;" />
						<ec:column property="settleMarginAlgr_B" title="���ձ�֤���㷨" width="10%" style="text-align:center;">
					      ${algrMap[breedSettleMargin.settleMarginAlgr_B] }
						</ec:column>
						<ec:column property="settleMarginAlgr_S" title="�����ձ�֤���㷨" width="10%" style="text-align:center;">
					      ${algrMap[breedSettleMargin.settleMarginAlgr_S] }
						</ec:column>
						<ec:column property="payoutAlgr" title="���ջ����㷨" width="10%" style="text-align:center;">
					      ${algrMap[breedSettleMargin.payoutAlgr] }
						</ec:column>
						<ec:column property="modifyTime" title="�޸�ʱ��" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${breedSettleMargin.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
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
