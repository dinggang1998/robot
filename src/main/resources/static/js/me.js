var allrole=[];
$(document).ready(function(){
	$.ajax({
	    type: "GET",
	    url: getRealPath() + "/me",
	    async:false,
	    data: {},
	    dataType: "json",
	    success: function(data){
	    	for(var i=0;i<data.authorities.length;i++){
	    		allrole.push(data.authorities[i].authority);
	    	}
	    }
	});
});
function exportExcelSafe(url,values) {
	if(allrole.indexOf("ROLE_SUPER")!=-1){//超级管理员
		window.location.href=url + values;
	}else{
		swal({
			title: "<h>导出申请</h>",
			text: "<p>批量导出数据需进行人工授权，授权人Philip（wangcz@chinatelecomglobal.com），确认导出将自动发送审核邮件给授权人，请确认操作。</p><p>申请原因 <input id='remark' name='remark'  maxlength='120'></p>",
			html: true,
			showCancelButton: true,
			closeOnConfirm: true,
			cancelButtonText: "取消",
			confirmButtonText: "确认",
			type: "input",
		}, function (remark) {
			console.log(remark);
			if(remark!=null&&remark!=''){
				values = values+"&remark="+remark;
				$.ajax({
					type: "GET",
					url: url + values,
					data: {},
					success: function(data){
						if(data=='0'){
							swal("申请成功！");
						}else{
							swal("申请失败！");
						}
					}
				});
			}else{
				swal("申请原因不可为空");
			}
		})
	}
}

function exportExcelSafes(url) {
	if(allrole.indexOf("ROLE_SUPER")!=-1){//超级管理员
		window.location.href=url;
	}else{
		swal({
			title: "<h>导出申请</h>",
			text: "<p>批量导出数据需进行人工授权，授权人Philip（wangcz@chinatelecomglobal.com），确认导出将自动发送审核邮件给授权人，请确认操作。</p><p>申请原因 <input id='remark' name='remark'  maxlength='120'></p>",
			html: true,
			showCancelButton: true,
			closeOnConfirm: true,
			cancelButtonText: "取消",
			confirmButtonText: "确认",
			type: "input",
		}, function (remark) {
			console.log(remark);
			if(remark!=null&&remark!=''){
				var values = "?remark"+remark;
				$.ajax({
					type: "GET",
					url: url + values,
					data: {},
					success: function(data){
						if(data=='0'){
							swal("申请成功！");
						}else{
							swal("申请失败！");
						}
					}
				});
			}else{
				swal("申请原因不可为空");
			}
		})
	}
}
