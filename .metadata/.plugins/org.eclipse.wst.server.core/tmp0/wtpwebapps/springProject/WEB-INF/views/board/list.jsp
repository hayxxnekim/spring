<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List Page</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<!-- 검색 라인 -->
<!-- 검색값이 모든 페이지에 동일하게 적용하기 위해 cset -->
<!-- 아무것도 선택하지 않으면(typed==null) 기본값 : choose -->
<!-- "Title"을 선택하면 폼의 type에 t를 저장
	 => pagingVO 객체의 type 속성으로 설정
	 cset 태그를 사용하여 jsp 페이지에서 ph.pgvo.type 값을 변수 typed에 설정
	 
	 => type와 typed는 서로 연결되어 있음
 -->
<div class="col-sm-12 col-md-6">
	<form action="/board/list" method="get">
		<div class="input-group mb-3">
		<c:set value="${ph.pgvo.type }" var="typed"></c:set>
		  <select class="form-select" name="type" id="inputGroupSelect01">
		    <option ${typed eq null ? 'selected' : '' }>Choose...</option>
		    <option value="t" ${typed eq 't' ? 'selected' : '' }>Title</option>
		    <option value="w" ${typed eq 'w' ? 'selected' : '' }>Writer</option>
		    <option value="c" ${typed eq 'c' ? 'selected' : '' }>Content</option>
		    <option value="tw" ${typed eq 'tw' ? 'selected' : '' }>Title or Writer</option>
		    <option value="tc" ${typed eq 'tc' ? 'selected' : '' }>Title or Content</option>
		    <option value="cw" ${typed eq 'cw' ? 'selected' : '' }>Content or Writer</option>
		    <option value="twc" ${typed eq 'twc' ? 'selected' : '' }>All</option>
		  </select>
		  	<!-- 기본값 설정을 위해 value 설정 -->
		  	<!-- 검색하면 무조건 첫번째 페이지로 돌아감 -->
			<input class="form-control me-2" name="keyword" value="${ph.pgvo.keyword }" type="search" placeholder="Search" aria-label="Search">
			<input type="hidden" name="pageNo" value="1">
			<input type="hidden" name="qty" value="${ph.pgvo.qty }">
			<button class="btn btn-outline-success" type="submit">
			Search
			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
				${ph.totalCount }	
			<span class="visually-hidden">unread messages</span>
			</span>
			</button>
		</div> 
	</form>
</div> 
     
<table class="table table-hover">
<thead>
<tr>
	<th>#</th>
	<th>제목</th>
	<th>작성자</th>
	<th>조회수</th>
	<th>댓글수</th>
	<th>파일수</th>
</tr>
</thead>
<tbody>
<c:forEach items="${list }" var="bvo">
	<tr>
		<td>${bvo.bno }</td>
		<!-- 컨트롤러의 board/detail로 이동 -->
		<td><a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a></td>
		<td>${bvo.writer }</td>
		<td>${bvo.readCount }</td>
		<td>${bvo.cmtQty }</td>
		<td>${bvo.hasFile }</td>
	</tr>
</c:forEach>
</tbody>
</table>

<!-- 가운데 정렬하고 싶으면 div로 감싸고 스타일 주기  -->
<!-- 페이징 라인 -->
<nav aria-label="Page navigation example">
  <ul class="pagination">
  	<!-- 이전 -->
    <li class="page-item ${(ph.prev eq false) ? 'disabled' : ''}" >
      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
	<c:forEach begin="${ph.startPage}" end="${ph.endPage}" var="i">
    <li class="page-item"><a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i}</a></li>
    </c:forEach>
    <!-- 다음 -->
    <li class="page-item ${(ph.next eq false) ? 'disabled' : ''}">
      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

<script type="text/javascript">
let isUp = `<c:out value="${isUp}" />`;
let isDel = `<c:out value="${isDel}" />`;
if(parseInt(isUp)) {
	alert('게시글 등록 성공');
}
if(parseInt(isDel)) {
	alert('게시글 삭제 성공');
}
</script>
<jsp:include page="../common/footer.jsp" />
</body>
</html>