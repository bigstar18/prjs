<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
<head>
  <title>Dynamic, Database-driven Menu</title>
    <script type="text/javascript" src="<c:url value="/timebargain/scripts/xtree_customerGroup.js"/>"></script>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/timebargain/styles/xtree.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
</head>

<body leftmargin="0" topmargin="0">
<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="3"></td>
    <td width="36"><a href="<c:url value="/timebargain/baseinfo/customerGroup.do?funcflg=groupTree"/>" id="customer" class="common" ><font color="blue"><u>Ë¢ÐÂ</u></font></a></td>      
  </tr>
</table>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="160" dwcopytype="CopyTableRow">
  <TR>
    <TD WIDTH="90%" NOWRAP="true" VALIGN="TOP">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top">
            <div id="tree" style="position: relative;left: 0;padding: 10px;">
              <script type="text/javascript">
                 <menu:useMenuDisplayer name="Velocity" config="/timebargain/templates/xtree.html" repository="repository">
                 <c:forEach var="menu" items="${repository.topMenus}">
                   <menu-el:displayMenu name="${menu.name}" />
                 </c:forEach>
                 </menu:useMenuDisplayer>
             </script>
           </div>
          </td>
        </tr>
      </table>
    </TD>
  </TR>
</TABLE>
</body>
</html>
