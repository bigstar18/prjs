<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%//��EL���ʽ���ǻ�ȡ���� list ���ϵĳ��ȵģ����ñ���ǩ���������·�ʽ��ȡ���ؼ��ϵĳ��ȣ�${fn:length(list)}%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%//��ʽ���õ��ı�ǩ��<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"/> %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%//�����г���Ϣ��ǩ%>
<fmt:setBundle basename="MarketMessage" var="MarketMessage" />
<%//��������򳤶ȱ�ǩ%>
<fmt:setBundle basename="property_Length" var="proplength" />