$(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	$(document).ajaxError(function(event,xhr,options,exc){
		if (!xhr.responseJSON) {
			xhr.responseJSON = $.parseJSON(xhr.responseText);
		}
		if (xhr.responseJSON.message && xhr.responseJSON.title) {
			if (xhr.status == 401) {
				swal({
	                title: xhr.responseJSON.title,
	                text: xhr.responseJSON.message,
	                type: "warning",
	                showCancelButton: true,
	                confirmButtonColor: "#DD6B55",
	                confirmButtonText: "确定",
	                cancelButtonText:"取消",
	                closeOnConfirm: false
	            }, function () {
	                window.location.href = getRealPath() + '/expired';
	            });
			}else if (xhr.status == 403) {
				swal({
					title: "操作失败了",
					text: "没有访问权限或登录超时",
					type: "warning",
					showCancelButton: true,
					confirmButtonColor: "#DD6B55",
					confirmButtonText: "确定",
					cancelButtonText:"取消",
					closeOnConfirm: false
				}, function () {
					window.location.href = getRealPath() + '/index';
				});
			}
			else {
				swal(xhr.responseJSON.title, xhr.responseJSON.message);
			}
		} else {
			swal('未知错误', '服务器访问超时.');
		}
	});
	
	$(document).ajaxStart(function(){
		showLoading();
    }).ajaxStop(function(){
    	hideLoading(); 
    });
	
});

function logout() {
	swal({
		title: "确定注销吗？",   
		text: "您将注销CRM系统！",   
		type: "warning",   
		showCancelButton: true,
		cancelButtonText:"取消",
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "确定",
		closeOnConfirm: false 
	}, function(){
		$.ajax({
	        type: "POST",
	        url: getRealPath() + "/logout",
	        dataType: "text",
	        success: function(data){
	        	if (data == 'SUCCESS')
	        		window.location.href = getRealPath() + '/expired';
	        	else
	        		swal('Unkown Error', 'Server Responsed Time Out.');
	        }
		});
	});
}

function toSiteMessagePage() {
    window.location.href = getRealPath() + "/fhsms/toFhsmsListPage"
}

var i = 1;

function showtoDoList() {
    if(location.pathname!=getRealPath() + "/index"){
        window.location.href = getRealPath() + "/index";
    }else{
        window.location.href = getRealPath() + "/index";
        // if (i % 2 == 1) {
        //     document.getElementById("toDoList").style.display = '';
        //     i++;
        // } else {
        //     document.getElementById("toDoList").style.display = 'none';
        //     i++;
        // }
    }

}

function showLoading() {
	$("#global-loading")
		.css("width", document.body.scrollWidth + "px")
		.css("height", document.body.scrollHeight + "px")
		.show();
}

function hideLoading() {
	$("#global-loading").hide();
}