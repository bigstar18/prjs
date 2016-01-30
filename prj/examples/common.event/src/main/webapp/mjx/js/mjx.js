/**
 *
 */

var applied;
var phone, eventId, signid;

var persons, picIdx = 0, totalPerson = 0;
var pkPersons;

function person(uid, votes, picurl, picdate, nick, headPic) {
	this.uid = uid;
	this.votes = votes;
	this.picurl = picurl;
	this.picdate = picdate;
	this.nick = nick;
	this.headPic = headPic;
	// this.changeName = changeName; function changeName(name) {this.lastname = name;}myMother.changeName("Ballmer");
}

function pkPperson(uid, headPic, pics, dates) {
	this.uid = uid;
	this.headPic = headPic;
	this.pics = pics;
	this.dates = dates;
}

function apply(action) {
	if (action == null) {
		return;
	}
	jQuery.post("/event/mjxApply.htm", {
		phone : phone,
		eventId : eventId,
		signId : signid,
		act : action
	}, function(data) {
		if ("notApplied" == data) {//没有报过
			$(".mjx_i").css("display", "block");
			$(".mjx_sr").css("display", "none");
		} else {
			pageShow(data);
		}
	});
}

function pageShow(data) {
	var objs = eval("(" + data + ")");
	var msg = objs.msg;
	if (msg != null && msg != "") {//have msg
		altMsg(msg);
		return;
	}
	persons = new Array();
	picIdx = 0;
	totalPerson = 0;
	$('ul.slides_container').html("");
	$("#num").text("");

	var htmlStr;
	$(objs).each(function(index) {
		// if (index < 10) {
			var obj = objs[index];
			persons[index] = new person(obj.uid, obj.votes, obj.picurl, obj.picdate, obj.nick, obj.headPic);
			totalPerson = index + 1;
			if (index == 0)
				htmlStr = "<li><p id=tp" + index + " style=\"width:87%;height:12%; position:absolute;bottom:12%; z-index:100;left:7%;background-color:#A8671E;opacity:0.7; color:#fff\" class=\"tp\" > " + obj.votes + "票</p><img src = \"http://uyoungweb.chinacloudapp.cn:8080" + obj.picurl + "\"  alt=\"" + obj.nick + "\" class=\"imgVis\" width=\"1880\"/><p class=\"leftBg\"></p><p class=\"rightBg\"></p></li>";
			else
				htmlStr = htmlStr + "<li><p id=tp" + index + " style=\"width:87%;height:12%; position:absolute;bottom:12%; z-index:100;left:7%;background-color:#A8671E;opacity:0.7; color:#fff\" class=\"tp\" > " + obj.votes + "票</p><img src = \"http://uyoungweb.chinacloudapp.cn:8080" + obj.picurl + "\"  alt=\"" + obj.nick + "\" class=\"imgVis\" width=\"1880\"/><p class=\"leftBg\"></p><p class=\"rightBg\"></p></li>";
		// }
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

function talk() {
	if (totalPerson < 1)
		return;
	window.android.chat(persons[picIdx - 1].uid, persons[picIdx - 1].nick, "http://uyoungweb.chinacloudapp.cn:8080/" + persons[picIdx - 1].headPic);
}

function attention() {
	if (totalPerson < 1)
		return;
	jQuery.post("/event/proxy.htm", {
		phone : phone,
		eventId : eventId,
		signId : signid,
		AttentionUserUID : persons[picIdx - 1].uid
	}, function(data) {
		altMsg(data);
	});
}

function vote() {
	if (totalPerson < 1)
		return;
	jQuery.post("/event/vote.htm", {
		voterPhone : phone,
		picOwnerId : persons[picIdx - 1].uid,
		eventId : eventId,
	}, function(data) {
		var objs = eval("(" + data + ")");
		var ticket = objs.tickets;
		if (ticket == null || ticket == "") {
			altMsg(objs.msg);
		} else {
			persons[picIdx - 1].votes = ticket;
			$("#tp" + (picIdx - 1)).html(ticket);
		}
	});
}

function getPersons(sex) {
	jQuery.post("/event/show.htm", {
		sexType : sex,
		eventId : eventId
	}, function(data) {
		pageShow(data);
	});
}

function nickSearch() {
	var nickName = $(".ss").val();
	if (nickName == null || nickName == "")
		return;
	jQuery.post("/event/search.htm", {
		nickname : nickName,
		eventId : eventId
	}, function(data) {
		pageShow(data);
	});
}

function pk() {
	if (totalPerson < 1)
		return;
	jQuery.post("/event/pk.htm", {
		myPhone : phone,
		otherUid : persons[picIdx - 1].uid,
		eventId : eventId
	}, function(data) {
		pkShow(data);
	});
}

function pkShow(data) {
	var objs = eval("(" + data + ")");
	var msg = objs.msg;
	if (msg != null && msg != "") {//have msg
		altMsg(msg);
		return;
	}

	generateMixed();

	pkPersons = new Array();
	$("#pk_girl").remove();
	$("#pk_boy").remove();
	$(".of_girl").remove();
	$(".of_boy").remove();

	var headStr, imgStr;
	$(objs).each(function(index) {
		var obj = objs[index];
		// pkPersons[index] = new pkPperson(obj.uid, obj.headPic, obj.pics);
		if (index == 0) {
			headStr = "<div class=\"round\" style=\"left:3%\"><img src=\"http://uyoungweb.chinacloudapp.cn:8080" + obj.headPic + "\" alt=\"\" id=\"pk_girl\"></div>";
			imgStr = "<div class=\"of_girl\">";
			$(obj.pics).each(function(idx2) {
				imgStr += "<div class=\"k\"><p class=\"date\">" + obj.dates[idx2].date + "</p><p class=\"line\"></p><img src=\"http://uyoungweb.chinacloudapp.cn:8080" + obj.pics[idx2].pic + "\" alt=\"\"></div>";
			});
			imgStr += "</div>";
		} else {
			headStr = headStr + "<div class=\"round\" style=\"right:3%\"><img src=\"http://uyoungweb.chinacloudapp.cn:8080" + obj.headPic + "\" alt=\"\" id=\"pk_boy\"></div>";
			imgStr += "<div class=\"of_boy\">";
			$(obj.pics).each(function(idx2) {
				imgStr += "<div class=\"k\"><p class=\"date\">" + obj.dates[idx2].date + "</p><p class=\"line\"></p><img src=\"http://uyoungweb.chinacloudapp.cn:8080" + obj.pics[idx2].pic + "\" alt=\"\"></div>";
			});
			imgStr += "</div>";
		}
	});
	if (headStr != null && headStr.length > 0) {
		$(".mjx_pk").append(headStr + imgStr);
	}

	$(".mjx_sr").css("display", "none");
	$(".mjx_pk").css("display", "block");
}


$(document).ready(function() {
	$("html").css("min-height", $(window).height());
	$("body").css("min-height", $(window).height());
	$(".mjx_sr").css("min-height", $(window).height());

	$(".mjx_i p").click(function() {
		apply("apply");
	});
	$("#pk").click(function() {
		pk();
	});
	$("#back").click(function() {
		$(".mjx_pk").css("display", "none");
		$(".mjx_sr").css("display", "block");
	});
	$("#talk").click(function() {
		talk();
	});
	$("#gz").click(function() {
		attention();
	});
	$("#post").click(function() {
		vote();
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

var chars = ["images/tan1.png", "images/tan2.png", "images/tan3.png"];
function generateMixed() {
	var id = Math.ceil(Math.random() * 2);

	res = chars[id - 1];
	$(".tk").css("background-image", "url(" + res + ")");
}

function exit() {
	$(".tk").css("display", "none");
}

function altMsg(msg) {
	swal({
		title : "",
		text : msg,
		timer : 2000,
		showConfirmButton : false
	});
}
