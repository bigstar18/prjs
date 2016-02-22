<%
	/*skinstyle*/
	String skinName = null;
	String skin = (String)request.getSession().getAttribute("skinstyle");
	if(skin == null){
		skinName = "default";
	}else{
		skinName = skin;
	}
	/*path*/
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"
						+request.getServerPort()+path;
	/*path used in jsp*/
	String skinPath = basePath + "/common/skinstyle/" + skinName +"/common/commoncss";	
	String escideskinPath = basePath + "/common/skinstyle/" + skinName +"/special/ecside/css";
	String picPath = basePath+ "/common/skinstyle/" + skinName +"/common/ico";
	String menuPicPath = basePath + "/common/public/menuicon/";

	String escideskinPathForTimebargain = request.getScheme()+"://"+request.getServerName()+":"
						+request.getServerPort()+path + "/common/skinstyle/" + skinName +"/special/ecside/css";
	String skinPathForTimebargain	= request.getScheme()+"://"+request.getServerName()+":"
						+request.getServerPort()+path + "/common/skinstyle/" + skinName +"/common/commoncss";	
%>