<%@ page pageEncoding="GBK" %>
<%@ taglib uri="/tld/forEcSide" prefix="forEcside"%>       
             <!-- 查询数据 -->
             <div style="overflow:auto;width:100%;height:500px"  class="bodyZone"  id="ec_bodyZone" >
               <table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;"  width="100%"    >
	              <tbody id="ec_table_body" >
			<c:forEach items="${resultList}" var="result" varStatus="status">
				<tr class="<c:if test='${status.count%2==0}'>odd</c:if>"
					onclick="ECSideUtil.selectRow(this,'ec');"
					ondblclick="return update(${result.id});"
					onmouseover="ECSideUtil.lightRow(this,'ec');"
					onmouseout="ECSideUtil.unlightRow(this,'ec');"
					recordKey="${result.id}">
					<td width="22" cellValue="${result.id}">
						<input type="checkbox" name="checkboxId" value="${result.id}"
							class="checkbox" />
					</td>
					<td style="text-align: center;" width="20%"
						cellValue="${result.id}">
						${result.id}
					</td>
					<td style="text-align: center;" width="20%"
						cellValue="${result.name}">
						${result.name}
					</td>
					<td style="text-align: center;" width="20%"
						cellValue="${result.age}">
						${result.age}
					</td>
					<td style="text-align: center;" width="20%"
						cellValue="${result.grade}">
						${result.grade}
					</td>
					<td style="text-align: center;" width="20%"
						cellValue="${result.speciality}">
						${result.speciality}
					</td>
				</tr>
			</c:forEach>
		</tbody>
               </table> 
               <iframe style="border:0px;" marginwidth="0" marginheight="0" frameborder="0" border="0" width="0" height="0" id="ec_ecs_export_iframe"
                        name="ec_ecs_export_iframe" >
               </iframe>
           </div>
           <!-- 查询数据 -->          
    