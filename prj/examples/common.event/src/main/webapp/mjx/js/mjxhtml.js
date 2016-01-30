/**
 *
 */
var persons, picIdx = 0, totalPerson = 0;

function person(uid, votes, picurl, picdate, nick, headPic, sex) {
	this.uid = uid;
	this.votes = votes;
	this.picurl = picurl;
	this.picdate = picdate;
	this.nick = nick;
	this.headPic = headPic;
	this.sex = sex;
	// this.changeName = changeName; function changeName(name) {this.lastname = name;}myMother.changeName("Ballmer");
}

function apply(action) {
	jQuery.post("/event/mjxhtml.htm", {
		act : action
	}, function(data) {
		dataShow(data);
	});
}

function dataShow(data) {
	var objs = eval("(" + data + ")");
	var msg = objs.msg;
	if (msg != null && msg != "") {//have msg
		altMsg(msg);
		return;
	}
	persons = new Array();
	picIdx = 0;
	totalPerson = 0;

	$(objs).each(function(index) {
		var obj = objs[index];
		persons[index] = new person(obj.uid, obj.votes, obj.picurl, obj.picdate, obj.nick, obj.headPic, obj.sex);
	});

	pageShow("all", "", "");
	saveData();
}

function makeData() {
	var total = $('.mjx_data p').text();
	persons = new Array();
	for (var i = 0; i < total; i++) {
		persons[i] = new person($("#uid" + i).val(), $("#votes" + i).val(), $("#picurl" + i).val(), $("#picdate" + i).val(), $("#nick" + i).val(), $("#headPic" + i).val(), $("#sex" + i).val());
	};
}

function showAll() {
	pageShow("all", "", "");
}

function getPersons(sex) {
	pageShow("sex", sex, "");
}

function nickSearch() {
	var nickName = $(".ss").val();
	if (nickName == null || nickName == "")
		return;
	pageShow("nick", "", nickName);
}

function saveData() {
	var htmlStr = "<div class=\"mjx_data\" style=\"display: none\"><p>" + persons.length + "</p>";
	$(persons).each(function(index) {
		var obj = persons[index];
		htmlStr += "<input type=\"text\" id=\"uid" + index + "\" value=\"" + obj.uid + "\"/>";
		htmlStr += "<input type=\"text\" id=\"votes" + index + "\" value=\"" + obj.votes + "\"/>";
		htmlStr += "<input type=\"text\" id=\"picurl" + index + "\" value=\"" + obj.picurl + "\"/>";
		htmlStr += "<input type=\"text\" id=\"picdate" + index + "\" value=\"" + obj.picdate + "\"/>";
		htmlStr += "<input type=\"text\" id=\"nick" + index + "\" value=\"" + obj.nick + "\"/>";
		htmlStr += "<input type=\"text\" id=\"headPic" + index + "\" value=\"" + obj.headPic + "\"/>";
		htmlStr += "<input type=\"text\" id=\"sex" + index + "\" value=\"" + obj.sex + "\"/>";
	});
	htmlStr += "</div>";
	$("body").append(htmlStr);
}

function pageShow(type, sex, nick) {
	if (persons.length < 1)
		return;

	picIdx = 0;
	totalPerson = 0;
	var objs;
	if ("all" == type) {
		objs = persons;
	} else if ("sex" == type) {
		objs = new Array();
		var j = 0;
		for (var i = 0; i < persons.length; i++) {
			if (persons[i].sex == sex) {
				objs[j++] = persons[i];
			}
		}
	} else if ("nick" == type) {
		objs = new Array();
		for (var i = 0; i < persons.length; i++) {
			if (persons[i].nick.match(nick)) {
				objs[j++] = persons[i];
			}
		}
	}
	totalPerson = objs.length;

	$('ul.slides_container').html("");
	$("#num").text("");

	var htmlStr;
	$(objs).each(function(index) {
		var obj = objs[index];
		if (index == 0)
			htmlStr = "<li><p id=tp" + index + " style=\"width:87%;height:12%; position:absolute;bottom:12%; z-index:100;left:7%;background-color:#A8671E;opacity:0.7; color:#fff\" class=\"tp\" > " + obj.votes + "票</p><img src = \"http://uyoungweb.chinacloudapp.cn:8080" + obj.picurl + "\"  alt=\"" + obj.nick + "\" class=\"imgVis\" width=\"1880\"/><p class=\"leftBg\"></p><p class=\"rightBg\"></p></li>";
		else
			htmlStr = htmlStr + "<li><p id=tp" + index + " style=\"width:87%;height:12%; position:absolute;bottom:12%; z-index:100;left:7%;background-color:#A8671E;opacity:0.7; color:#fff\" class=\"tp\" > " + obj.votes + "票</p><img src = \"http://uyoungweb.chinacloudapp.cn:8080" + obj.picurl + "\"  alt=\"" + obj.nick + "\" class=\"imgVis\" width=\"1880\"/><p class=\"leftBg\"></p><p class=\"rightBg\"></p></li>";
	});
	if (totalPerson > 0) {
		$('ul.slides_container').html(htmlStr);
		picIdx = 1;
		$("#num").text((picIdx) + "/" + totalPerson);

		$(".imgVis").click(function() {
			$(".dt img").attr("src", $(this).attr("src"));
			$(".dt").css("display", "block");
		});
	}

	$(".mjx_i").css("display", "none");
	$(".mjx_sr").css("display", "block");
	showSlide();
}

function showSlide() {
	$('#slides').slides({
		container : 'slides_container',
		preload : true,
		play : 000,
		pause : 0,
		hoverPause : true,
		effect : 'slide',
		slideSpeed : 850
	});
}

function switch2(d) {
	if (totalPerson < 1)
		return;
	if (d == "r") {
		picIdx += 1;
		if (picIdx <= totalPerson) {
			$("#num").text((picIdx) + "/" + totalPerson);
		} else {
			picIdx = 1;
			$("#num").text((picIdx) + "/" + totalPerson);
		}
		;
	}
	if (d == "l") {
		picIdx = picIdx - 1;
		if (picIdx >= 1) {
			$("#num").text((picIdx) + "/" + totalPerson);
		} else {
			picIdx = totalPerson;
			$("#num").text((picIdx) + "/" + totalPerson);
		}

	}
}


$(document).ready(function() {
	$("html").css("min-height", $(window).height());
	$("body").css("min-height", $(window).height());
	$(".mjx_sr").css("min-height", $(window).height());

	$(".mjx_i p").click(function() {
		apply("apply");
	});
	$("#pk").click(function() {
		toFly();
	});
	$("#back").click(function() {
		$(".mjx_pk").css("display", "none");
		$(".mjx_sr").css("display", "block");
	});
	$("#talk").click(function() {
		toFly();
	});
	$("#gz").click(function() {
		toFly();
	});
	$("#post").click(function() {
		toFly();
	});
	$("#boy").click(function() {
		$("#boy").attr("src", "images/5.png");
		$("#girl").attr("src", "images/02.png");
		getPersons(1);
	});
	$("#girl").click(function() {
		$("#girl").attr("src", "images/6.png");
		$("#boy").attr("src", "images/01.png");
		getPersons(0);
	});
	$("#search").click(function() {
		nickSearch();
	});
	$(".dt").click(function() {
		$(".dt").css("display", "none");
	});
});

function exit() {
	$(".tk").css("display", "none");
}

function altMsg(msg, tm) {
	if (tm == null)
		tm = 2000;
	swal({
		title : "",
		text : msg,
		timer : tm,
		showConfirmButton : false
	});
}

function toFly() {
	altMsg("您还未加入[集合]大家庭，请前往商城下载或点击链接进入页面注册，参加本次活动");
	setTimeout("$(window.location).attr('href', 'http://uyoungweb.chinacloudapp.cn:81/index.php?m=Index&a=login')", 2000);
}
