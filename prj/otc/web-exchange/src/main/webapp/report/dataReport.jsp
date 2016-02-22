<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
	<div align="center"><font style="font-size: 30;"><s:property value="reportType"/></font></div>
		<s:iterator value="completeDataList">
		    <s:set var="primaryMap" value="primaryTable.primaryMsg" />
		    <s:set value="addPrimaryMap(#primaryMap)" />
			<s:include value="%{primaryTable.template}">
			</s:include>
			<s:iterator value="dataTableList">
			    <s:set var="dataListFor" value="dataList" />
			    <s:set value="addDataList(#dataListFor)" />
				<s:include value="%{template}" />
			</s:iterator>
		</s:iterator>

