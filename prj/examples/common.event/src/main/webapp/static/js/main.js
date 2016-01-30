// $.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
// $.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
// $.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
// $.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());

/*
 * =Main INIT Function
 * --------------------------------------------------------------
 */
function initializeForm() {

	"use strict";

	// IE9 RECOGNITION
	if (/msie/.test(navigator.userAgent.toLowerCase()) && jQuery.browser.version == 9) {

		jQuery('html').addClass('ie9');
	}

	// NAVIGATION DETECT
	(function() {
		function navDetect() {

			var windowWidth = $(window).width();

			if (windowWidth > 1199) {
				$('nav, header').removeClass('mobile');
				$('nav, header').addClass('desktop');
			} else {
				$('nav, header').removeClass('desktop');
				$('nav, header').addClass('mobile');
			}

		}


		$(window).on("resize", navDetect);
		$(document).on("ready", navDetect);
	})();

	// NAVIGATION CUSTOM FUNCTION
	(function() {
		function navigationInit() {

			// MOBILE BUTTON
			$('.nav-button').click(function() {
				if ($('nav').is(":visible")) {
					$('nav').slideUp();
					$('.nav-button').removeClass('open');
				} else {
					$('nav').slideDown();
					$('.nav-button').addClass('open');
				}
			});

			// DROPDOWNS
			$('li.drop a.drop-btn').click(function() {
				$('.drop-list').slideUp();
				$('li.drop a.open').removeClass('open');

				if ($(this).next('ul.drop-list').is(':visible')) {
					$(this).next('ul.drop-list').slideUp();
					$(this).removeClass('open');
				} else {
					$(this).next('ul.drop-list').slideDown();
					$(this).addClass('open');
				}

				return false;

			});

		}


		$(document).on("ready", navigationInit);
	})();

	(function() {
		function navigationSticky() {
			var scrollTop = $(window).scrollTop();

			if (scrollTop > 550) {
				$('nav, header').addClass('sticky');
				$('.sticky-head').slideDown();
				$('header.desktop.sticky, nav.desktop.sticky').stop().animate({
					top : 15
				});
			} else {
				$('header.desktop.sticky, nav.desktop.sticky').stop().animate({
					top : 0,
				}, 1, function() {
					$('nav, header').removeClass('sticky');
					$('.sticky-head').fadeOut('slow');
				});
			}

		}


		$(window).on("scroll", navigationSticky);
		$(window).on("resize", navigationSticky);
	})();

	// HERO DIMENSTION AND CENTER
	(function() {
		function heroInit() {

			var heroContent = $('.hero-content'), contentHeight = heroContent.height(), parentHeight = $('.hero').height(), topMargin = (parentHeight - contentHeight) / 2, heroContentSmall = $('.hero.small .hero-content'), contentSmallHeight = heroContentSmall.height(), topMagrinSmall = (parentHeight - contentSmallHeight) / 2;

			heroContent.css({
				"margin-top" : topMargin + "px"
			});

			if ($(window).width() > 767) {

				heroContentSmall.css({
					"margin-top" : topMagrinSmall + 75 + "px"
				});

			} else {

				heroContentSmall.css({
					"margin-top" : topMagrinSmall + 35 + "px"
				});
			}
		}


		$(window).on("resize", heroInit);
		$(document).on("ready", heroInit);
	})();

	// CONTACT-FORM
	jQuery('#contact-open').click(function(e) {
		e.preventDefault();
		if (jQuery('#contact-form').is(':hidden')) {
			jQuery('#contact-form').slideDown();
			jQuery('html, body').delay(200).animate({
				scrollTop : jQuery('#contact-form').offset().top
			}, 1000);
		} else {
			jQuery('#contact-form').slideUp();
		}
	});

	// jQuery('#contactform').submit();

	// RESPONSIVE VIDEO
	// jQuery(".container").fitVids();

};
/* END ------------------------------------------------------- */
$(document).ready(function validateFrom(argument) {
	$("#contactform").validate({
		submitHandler : function(form) {
			var action = jQuery("#contactform").attr('action');

			jQuery("#alert").slideUp(750, function() {
				jQuery('#alert').hide();

				jQuery('#submit').after('<img src="./images/ajax-loader.gif" class="loader" />').attr('disabled', 'disabled');

				jQuery.post(action, {
					contacts : jQuery('#contacts').val(),
					title : jQuery('#title').val(),
					message : jQuery('#message').val(),
					phone : jQuery("#phone").val(),
					eventId : jQuery("#eventId").val(),
					signId : jQuery("#signId").val()
				}, function(data) {
					document.getElementById('alert').innerHTML = data;
					jQuery('#alert').slideDown('slow');
					jQuery('#contactform img.loader').fadeOut('slow', function() {
						jQuery(this).remove();
					});
					jQuery('#submit').removeAttr('disabled');
					if (data.match('success') != null) {
						jQuery('#contacts').val('');
						jQuery('#title').val('');
						jQuery('#message').val('');
					}
				});

			});

			return false;
		},
		rules : {
			contacts : {
				required : true,
				minlength : 2,
				maxlength : 10
			},
			title : {
				required : true,
				minlength : 2,
				maxlength : 40
			},
			message : {
				required : true,
				minlength : 10,
				maxlength : 200
			}
		},
		messages : {
			contacts : {
				required : "联系人不能为空哦",
				minlength : "最小为2位",
				maxlength : "最大为10位"
			},
			title : {
				required : "主题不能为空哦",
				minlength : "最小为2位",
				maxlength : "最大为40位"
			},
			message : {
				required : "内容不能为空哦",
				minlength : "最小为10位",
				maxlength : "最大为200位"
			}
		}
	});
});
/*
 * =Document Ready Trigger
 * --------------------------------------------------------------
 */
$(document).ready(function() {

	initializeForm();

});
/* END ------------------------------------------------------- */
