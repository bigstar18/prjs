<%@ page pageEncoding="GBK" %>
<div id="ec_toolbar"  class="toolbar"  style="width:100%;" >
	          <table id="ec_toolbarTable"  class="toolbarTable"  cellpadding="0"  cellspacing="0">
	            <tr>
	              <td class="pageNavigationTool"  nowrap="nowrap" >
	                 <input type="button" <c:if test="${pageInfo.pageNo==1}">disabled="disabled"</c:if>   class="pageNav <c:if test="${pageInfo.pageNo==1}">firstPageD</c:if><c:if test="${pageInfo.pageNo!=1}">firstPage</c:if>"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="��һҳ" />
	                 <input type="button"  <c:if test="${pageInfo.pageNo==1}">disabled="disabled"</c:if>  class="pageNav <c:if test="${pageInfo.pageNo==1}">prevPageD</c:if><c:if test="${pageInfo.pageNo!=1}">prevPage</c:if>"  onclick="ECSideUtil.gotoPage(${pageInfo.pageNo-1},'ec');"  title="��һҳ" />
	              </td>
	              <td class="pageNavigationTool"  nowrap="nowrap" >
	                 <input type="button" <c:if test="${pageInfo.pageNo==pageInfo.totalPages}">disabled="disabled"</c:if>  class="pageNav <c:if test="${pageInfo.pageNo==pageInfo.totalPages}">nextPageD</c:if><c:if test="${pageInfo.pageNo!=pageInfo.totalPages}">nextPage</c:if>"  onclick="ECSideUtil.gotoPage(${pageInfo.pageNo+1},'ec');"  title="��һҳ" />
	                 <input type="button" <c:if test="${pageInfo.pageNo==pageInfo.totalPages}">disabled="disabled"</c:if> class="pageNav <c:if test="${pageInfo.pageNo==pageInfo.totalPages}">lastPageD</c:if><c:if test="${pageInfo.pageNo!=pageInfo.totalPages}">lastPage</c:if>"  onclick="ECSideUtil.gotoPage(${pageInfo.totalPages},'ec');"  title="��ĩҳ" />
	              </td>
                  <td class="separatorTool" >&#160;</td>
                  <td class="pageJumpTool"  nowrap="nowrap"  onmouseover="ECSideUtil.NearPagesBar.showMe(this,'ec');"  
                       onmouseout="ECSideUtil.NearPagesBar.hideMe(this,'ec');" >
                       <nobr>
                       <input type="button"  class="pageNav jumpPage"  onclick="ECSideUtil.gotoPageByInput(this,'ec');" />
                       <input type="text"  name="ec_pg"  value="<c:out value="${pageInfo.pageNo}" />"  class="jumpPageInput"  
                           onkeydown="if (event.keyCode && event.keyCode==13 ) {ECSideUtil.gotoPageByInput(this,'ec');;return false; } " />/<c:out value="${pageInfo.totalPages}" />ҳ</nobr>
                  </td>
                  <td class="separatorTool" >&#160;</td>
                  <td class="pageSizeTool"  nowrap="nowrap" >ÿҳ<select name="ec_rd"  onchange="ECSideUtil.changeRowsDisplayed('ec',this);" >
				       <option value="5" >5</option>
				       <option value="10">10</option>
				       <option value="20" >20</option>
				       <option value="50" >50</option>
				       <option value="100" >100</option>
				       <option value="1000" >1000</option>
				       <option value="21" >ȫ��</option>
				   </select>��
				   <script type="text/javascript" >
				       ec.ec_rd.value=<c:out value="${pageInfo.pageSize}" />;
				   </script>
				   </td>
                   <td class="separatorTool" >&#160;</td>
                   <td nowrap="nowrap"  class="refreshTool" >
                     <nobr>
                       <input type="button"  class="toolButton girdRefresh"  onclick="ECSideUtil.reload('ec');" />
                     </nobr>
                   </td>
                   <td class="separatorTool" >&#160;</td>
                   <td nowrap="nowrap"  class="exportTool" >
                     <nobr>
                       <input type="button"  class="toolButton exportPrint"  onclick="ECSideUtil.doExport('print','_print_','','ec');"  alt="��ӡ"  title="��ӡ" />
                       <input type="button"  class="toolButton exportXls"  onclick="ECSideUtil.doExport('xls','xls.xls','','ec');"  alt="����xls"  title="����xls"/>
                       <input type="button"  class="toolButton exportPdf"  onclick="ECSideUtil.doExport('pdf','pdf.pdf','','ec');" alt="����pdf"  title="����pdf"/>
                     </nobr>
                   </td>
                  <%--  
                   <td class="extendTool" >
                      <button id='addTest' name='addTest' style="color='red'" onclick="add()" >���</button>
                      <button id='delete' style="color='red'" onclick="deleteByCheckBox()">ɾ��</button>
                   </td>
                    --%>
                   <td class="separatorTool" >&#160;</td>
                   <c:set value="${pageInfo.pageSize*(pageInfo.pageNo-1)+1}" var="currentStart"></c:set>
                   <c:set value="${pageInfo.pageSize*pageInfo.pageNo}" var="currentEnd"></c:set>
                   <td nowrap="nowrap"  class="statusTool" ><nobr>��<c:out value="${pageInfo.totalRecords}"/>����¼,��ʾ<c:out value="${currentStart}"/>��<c:out value="${currentEnd}"/></nobr></td>
                 </tr>
	          </table>
	       </div>