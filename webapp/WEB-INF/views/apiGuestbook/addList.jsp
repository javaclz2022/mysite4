<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- css -->

<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">


<!-- js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>


</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->
	
		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
				
				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<%-- <form action="${pageContext.request.contextPath}/api/guestbook/add" method="get"> --%>
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></td>
									<td><input id="input-uname" type="text" name="name" value=""></td>
									<th><label class="form-text" for="input-pass">패스워드</label></td>
									<td><input id="input-pass"type="password" name="password" value=""></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center"><button id="btnSubmit" type="submit">등록</button></td>
								</tr>
							</tbody>
							
						</table>
						<!-- //guestWrite -->
						
					<!-- </form>	 -->
					
					
					<!-- 리스트 영역 -->
					<div id="listArea">
					
					
					</div>
					<!-- //리스트 영역 -->
					
					
					
				</div>
				<!-- //guestbook -->
			
			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->


<!-- /////////////////////////////////////////////////////////////// -->
<!-- 삭제모달창 -->
<div id="delModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">비밀번호를 입력하세요</h4>
      </div>
      <div class="modal-body">
      	
      	비밀번호<input type="text" name="password" value="">
        <br><input type="text" name="no" value="">
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
        <button id="btnModalDel" type="button" class="btn btn-danger">삭제</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- /////////////////////////////////////////////////////////////// -->




</body>
<script type="text/javascript">

/* 준비가 끝났을때 */
$(document).ready(function(){
	/* 리스트 요청+그리기 */
	fetchList()
});

/* 저장버튼을 클릭했을때 */
//jquery 요청(json)
$("#btnSubmit").on("click", function(){
	console.log("저장버튼 클릭");
	
	//데이터수집
	var name = $("[name='name']").val();
	var password = $("[name='password']").val();
	var content = $("[name=content]").val();
	
	//데이터 객체로 묶기
	var guestVo = {
		name: name,
		password: password,
		content: content
	};
	
	
	
	$.ajax({
		url : "${pageContext.request.contextPath }/api/guestbook/add2",		
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(guestVo),   //js객체를 JSON문자열로 변경

		dataType : "json",
		success : function(gVo){

			//1개데이터 리스트 추가(그리기)하기
			render(gVo, "up");
			
			//입력폼 초기화
			$("[name='name']").val("");
			$("[name='password']").val("");
			$("[name='content']").val("");
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
	

});


/*
//jquery 요청(파라미터)
$("#btnSubmit").on("click", function(){
	console.log("저장버튼 클릭");
	
	//데이터수집
	var name = $("[name='name']").val();
	var password = $("[name='password']").val();
	var content = $("[name=content]").val();
	
	//데이터 객체로 묶기
	var guestVo = {
		name: name,
		password: password,
		content: content
	};
	
	$.ajax({
		
		//url : "${pageContext.request.contextPath }/api/guestbook/add?name="+name+"&password="+password+"&content="+content
		url : "${pageContext.request.contextPath }/api/guestbook/add",		
		type : "post",
		//contentType : "application/json",
		data : guestVo,   //파라미터 정리된다

		dataType : "json",
		success : function(gVo){

			//1개데이터 리스트 추가(그리기)하기
			render(gVo, "up");
			
			//입력폼 초기화
			$("[name='name']").val("");
			$("[name='password']").val("");
			$("[name='content']").val("");
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});

});
*/

//리스트의 삭제버튼을 클릭할때
$("#listArea").on("click", ".btnDel", function(){
	console.log("리스트 삭제버튼 클릭");
	var $this = $(this);
	var no =$this.data("no");
	
	//모달창에 form 값 입력
	$('#delModal [name="password"]').val("");
	$('[name="no"]').val(no);
	
	
	//모달창 띄우기
	$("#delModal").modal("show");
	
});


/* 모달창 삭제버튼 클릭할때 */
$("#btnModalDel").on("click", function(){
	console.log("모달창 삭제버튼 클릭");
	
	//데이터 모으기
	var password = $('#delModal [name="password"]').val();
	var no = $('[name="no"]').val();
	
	var guestbookVo = {
		password: password,
		no: no
	};
	
	/*
	var guestbookVo = {};
	guestbookVo.password = password;
	guestbookVo.no = no;
	*/
	
	console.log(guestbookVo);
	
	//서버로 데이터 전송
	$.ajax({
		
		url : "${pageContext.request.contextPath }/api/guestbook/remove",		
		type : "post",
		//contentType : "application/json",
		data : guestbookVo,

		dataType : "json",
		success : function(result){
			/*성공시 처리해야될 코드 작성*/
			console.log(result);
			
			//성공이면 지우고
			if(result == "succeess"){
				$("#t"+no).remove();
				$("#delModal").modal("hide");
			
			}else {
				alert("비밀번호를 확인하세요");
			}
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
	//성공이면 리스트에서 제거하기
	
	//모달창 닫기
	
});




/* 리스트 요청 */
function fetchList(){
	$.ajax({
		
		url : "${pageContext.request.contextPath }/api/guestbook/list",		
		type : "post",
		//contentType : "application/json",
		//data : {name: ”홍길동"},
		
		dataType : "json",
		success : function(guestbookList){
			//화면 data + html 그린다
			for(var i=0; i<guestbookList.length; i++){
				render(guestbookList[i], "down");  //vo --> 화면에 그린다.
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
}


/* 리스트 그리기 1개씩*/
function render(guestbookVo, opt){
	console.log("render()");
	
	var str = '';
	str += '<table id="t'+guestbookVo.no+'" class="guestRead">' ;
	str += '    <colgroup>' ;
	str += '        <col style="width: 10%;">' ;
	str += '        <col style="width: 40%;">' ;
	str += '        <col style="width: 40%;">' ;
	str += '        <col style="width: 10%;">' ;
	str += '    </colgroup>' ;
	str += '    <tr>' ;
	str += '        <td>'+guestbookVo.no+'</td>' ;
	str += '        <td>'+guestbookVo.name+'</td>' ;
	str += '        <td>'+guestbookVo.regDate+'</td>' ;
	str += '        <td><button class="btnDel" type="button" data-no="'+ guestbookVo.no +'">삭제</button></td>' ;
	str += '    </tr>' ;
	str += '    <tr>' ;
	str += '        <td colspan=4 class="text-left">'+guestbookVo.content+'</td>' ;
	str += '    </tr>' ;
	str += '</table>' ;
	
	if(opt == "down"){
		$("#listArea").append(str);	
	
	}else if(opt == "up"){
		$("#listArea").prepend(str);
	
	}else {
		console.log("opt오류");
	}
	
	
	
}


</script>

</html>