<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%//在EL表达式中是获取不到 list 集合的长度的，引用本标签可以用以下方式获取返回集合的长度：${fn:length(list)}%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%//格式化用到的标签：<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"/> %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%//定义市场信息标签%>
<fmt:setBundle basename="MarketMessage" var="MarketMessage" />
<%//定义输入框长度标签%>
<fmt:setBundle basename="property_Length" var="proplength" />