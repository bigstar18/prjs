<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html> 
  <body>
    <table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="80%">
				<tHead>
					<tr>
						<td class="panel_tHead_LB">
							&nbsp;
						</td>
						<td class="panel_tHead_MB">
							<input type="checkbox" id="checkAll"
								onclick="selectAll(tableList,'delCheck')">
						</td>
						<td class="panel_tHead_MB" abbr="traderId" align="center">
							交易商代码
						</td>
						<td class="panel_tHead_MB" abbr="loginDate" align="center">
							登录时间
						</td>
						<td class="panel_tHead_MB" abbr="counts" align="center">
							登录次数
						</td>
						<td class="panel_tHead_MB" abbr="type" align="center">
							查看详情
						</td>
						<td class="panel_tHead_RB">
							&nbsp;
						</td>
					</tr>
				</tHead>
				<tBody>
					<c:forEach items="${resultList}" var="result">
						<tr onclick="selectTr();">
							<td class="panel_tBody_LB">
								&nbsp;
							</td>
							<td class="underLine">
								<input name="delCheck" type="checkbox"
									value="<c:out value="${result.traderID}"/>">
							</td>
							<td class="underLine" align="center">
								<c:out value="${result.traderID}" />
								&nbsp;
							</td>
							<td class="underLine" align="center">
							<fmt:formatDate value="${result.loginDate}" pattern="yyyy-MM-dd"/>
								&nbsp;
							</td>
							<td class="underLine" align="center">
								<c:out value="${result.counts}" />
								&nbsp;
							</td>
							<td class="underLine" align="center">
							   <span onclick="fmod('<c:out value="${result.traderID}"/>')" style="cursor:hand;color:blue">进入->>
							    </span>
							</td>
							<td class="panel_tBody_RB">
								&nbsp;
							</td>
						</tr>
					</c:forEach>
				</tBody>
				<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td colspan="5">
							&nbsp;
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>

					<tr height="22">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td class="pager" colspan="5">
							<%@ include file="../public/pagerInc.jsp"%>
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>

					<tr height="22">
						<td class="panel_tFoot_LB">
							&nbsp;
						</td>
						<td class="panel_tFoot_MB" colspan="5"></td>
						<td class="panel_tFoot_RB">
							&nbsp;
						</td>
					</tr>
				</tFoot>
			</table>	
  </body>
</html>
