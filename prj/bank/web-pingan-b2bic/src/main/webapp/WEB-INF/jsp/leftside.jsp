<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp" %>

<div id="leftside">
	<div id="sidebar_s">
		<div class="collapse">
			<div class="toggleCollapse"><div></div></div>
		</div>
	</div>
	<div id="sidebar">
		<div class="toggleCollapse"><h2>Menu</h2><div>collapse</div></div>
	
		<div class="accordion" fillSpace="sideBar">
			<s:iterator value="funcList.children">
				<div class="accordionHeader">
					<h2><span>Folder</span>${ nodeName }</h2>
				</div>
				<div class="accordionContent">
					<ul class="tree treeFolder">
						<s:iterator value="children">
							<s:if test="!children.isEmpty()">
								<li>
									<a>${ nodeName }</a>
									<ul>
										<s:iterator value="children">
											<li><a href="${ nodeAddress }<s:property value="nodeAddress.indexOf('?') > 1 ? '&' : '?'"/>navTabId=func_${ nodeId }&menu=true" target="navTab" rel="func_${ nodeId }">${ nodeName }</a></li>
										</s:iterator>
									</ul>
								</li>
							</s:if>
							<s:elseif test="!\"\".equals(nodeAddress)">
								<li>
									<a href="${ nodeAddress }<s:property value="nodeAddress.indexOf('?') > 1 ? '&' : '?'"/>navTabId=func_${ nodeId }&menu=true" target="navTab" rel="func_${ nodeId }">${ nodeName }</a>
								</li>
							</s:elseif>
						</s:iterator>
					</ul>
				</div>
			</s:iterator>
		</div>
	</div>
</div>