<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<!--[if IE 8 ]><html class="ie no-js" xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn" lang="zh-cn"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js" xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn" lang="zh-cn"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn" lang="zh-cn">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<!--[if IE]><meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'><![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>proposal</title>
<meta name="description" content="">
<meta name="keywords" content="">

<!-- <link rel="shortcut icon" href="images/favicon.png"> -->
<link rel="apple-touch-icon" href="images/apple-touch-icon.png">
<link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">

<!--[if lt IE 9]>
		<script src="js/html5.js"></script>
		<![endif]-->

<!-- jQuery -->
<script src="js/jquery-1.11.3.min.js" charset="utf-8"></script>
<script src="js/jquery.validate.min.js" charset="utf-8"></script>
<script type="text/javascript">
	var phone, eventId, signid;		
    phone ="<%=request.getParameter("phone")%>";
    eventId ="<%=request.getParameter("eventId")%>";
    signid ="<%=request.getParameter("signId")%>";
    if (phone != "null" && phone != "")
    {
	    getUserInfo (phone, eventId, signid);
    }
    function getUserInfo (userAccount, sourceUID, signId)
    {
	    $ ("#phone").val (userAccount);
	    $ ("#eventId").val (sourceUID);
	    $ ("#signId").val (signId);
    }
</script>

<!-- Fonts -->

<!-- Stylesheets -->
<link rel="stylesheet" type="text/css" href="css/framework.css">
<link rel="stylesheet" type="text/css" href="css/style.css">

</head>

<body>
	<!-- Begin Small Hero Block -->
	<section class="hero small accent parallax" style="background-image: url(images/bg04.jpg);"></section>
	<!-- End Small Hero Block -->

	<!-- Begin Contact Form Block -->
	<section class="container content">

		<!-- Title -->
		<div class="row">
			<div class="center col-sm-12">
				<h2>产品优化意见有奖活动开始啦</h2>
			</div>
			<div class="col-sm-12">
				<p>
					即日起凡是通过本活动页面提交出你对我们产品改进的想法，创意，一经采纳，我们将提供丰富的奖品。 <br> 极具建设性的建议更有机会获得高额的奖金和一线互联网企业就职机会。
				</p>
			</div>
		</div>
		<!-- END-->

		<!-- Form -->
		<form method="post" action="/event/bug" name="contactform" id="contactform" class="row">
			<fieldset>
				<div class="form-field col-sm-6">
					<label for="contacts">联系人</label><span> <input type="text" name="contacts" id="contacts" />
					</span>
				</div>
				<div class="form-field col-sm-6">
					<label for="title">主 题</label><span> <input type="text" name="title" id="title" />
					</span>
				</div>
				<div id="alert" class="center col-sm-12"></div>
				<div class="form-field col-sm-12">
					<label for="message">内 容</label><span> <textarea name="message" id="message"></textarea></span>
				</div>
			</fieldset>
			<div class="form-click center col-sm-12">
				<span> <input type="submit" name="submit" value="提  交 " id="submit" />
				</span>
			</div>
			<input type="hidden" id="phone" name="phone"> <input type="hidden" name="eventId" id="eventId"> <input type="hidden" name="signId" id="signId">
		</form>

	</section>
	<!-- End Contact Form Block -->

	<!-- Javascript -->
	<script src="js/main.js"></script>
</body>

</html>