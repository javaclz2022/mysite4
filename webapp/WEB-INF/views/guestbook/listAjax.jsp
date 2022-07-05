<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/guestAside.jsp"></c:import>
		<!-- //aside -->


		<div id="content">
			
			<div id="content-head">
            	<h3>ajax방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">ajax방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<%-- <form action="${pageContext.request.contextPath }/guestbook/write" method="get"> --%>
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th><label class="form-text" for="input-uname">이름</label></th>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label></th>
								<td><input id="input-pass"type="password" name="password"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea id="input-content" name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button type="submit" id="btnSubmit">등록</button></td>
							</tr>
						</tbody>
						
					</table>
					<!-- //guestWrite -->
					
				<%-- </form>	--%>
				
				<ul id="guestbook-list">

				</ul>	
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->
	
	
	<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="delPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" id="modalPassword"><br>	
					<input type="text" name="modalNo" value="" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btnDel">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	

</body>

<script type="text/javascript">
$(document).ready(function() {
	
	//페이지가 준비될때
	fetchList();
		
});	



//저장하기
$("#btnSubmit").on("click", function(){
	console.log("저장버튼 클릭");
	
	var name = $("[name=name]").val();
	var password = $("[name=password").val();
	var content = $("[name=content]").val();

	var guestbookVo = {
		name: name,
		password: password,
		content: content
	};
	
	console.log(guestbookVo);
	
	//${pageContext.request.contextPath }/guestbook/write
	$.ajax({
		url : "${pageContext.request.contextPath }/api/gb/write",
		type : "post",
		/* contentType : "application/json", */
		data : guestbookVo,
		dataType : "json",
		success : function(guestbookVo) {
			console.log(guestbookVo);
			render(guestbookVo, "up");
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
	
});


//삭제팝업창 띄우기
$("#guestbook-list").on("click", "a", function() {
	event.preventDefault();
	
	var no = $(this).data("delno");
	
	$("#modalPassword").val("");
	$("#modalNo").val("");
	$("#modalNo").val(no);
    $("#delPop").modal();
    
});


//삭제
$("#btnDel").on("click", function() {
	/* event.preventDefault(); */
	
	var no = $("#modalNo").val();
   	var password = $("#modalPassword").val();
   	
	
	$.ajax({
		url : "${pageContext.request.contextPath }/api/gb/delete",	
		type : "post",
		contentType : "application/json",
		data : JSON.stringify({no: no, password: password}),
		dataType : "json",
		success : function(count) {
			console.log(count);
			if(count == 1){
				$("#li-" + no).remove();	
				$("#delPop").modal("hide")	
			}else {
				$("#delPop").modal("hide")	
			}
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
	
});

//리스트 요청후 출력하는 함수
function fetchList(){

	$.ajax({
		url : "${pageContext.request.contextPath }/api/gb/list",
		type : "post",
		/* contentType : "application/json", */
		//data : {lastNo: lastNo},
		dataType : "json",
		success : function(list) {
			for(var i=0; i<list.length; i++){
				render(list[i], "down");
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});

}



//방명록 글을 html과 조합하여 화면에 출력합니다.
function render(guestbookVo, updown) {
	
	var str = "";
	str += "<li id='li-"+guestbookVo.no+"' >";
	str += "	<table>";
	str += "		<tr>";
	str += "			<td>[" + guestbookVo.no + "]</td>";
	str += "			<td>" + guestbookVo.name + "</td>";
	str += "			<td>" + guestbookVo.regDate + "</td>";
	str += "			<td><a href='#' data-delno='" + guestbookVo.no + "' >삭제</a></td>";
	str += "		</tr>";
	str += "		<tr>";
	str += "			<td colspan=4>"+ guestbookVo.content + "</td>";
	str += "		</tr>";
	str += "	</table>";
	str += "<br/>";
		

	if(updown == "up"){
		$("#guestbook-list").prepend(str); 
	}else if(updown == "down"){
		$("#guestbook-list").append(str); 
		lastNo = guestbookVo.no;
	}else{
		console.log("updown 오류")
	}
}



</script>


</html>
