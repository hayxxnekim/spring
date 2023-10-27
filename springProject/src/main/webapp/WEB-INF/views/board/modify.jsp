<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<c:set value="${bdto.bvo }" var="bvo"></c:set>
<form action="/board/modify" method="post" enctype="multipart/form-data">
<table class="table table-hover">
	<thead>
		<tr>
			<th>#</th>
			<td><input type="text" name="bno" value="${bvo.bno }" readonly="readonly" class="form-control"></td>
		</tr>
		<tr>
			<th>title</th>
			<td><input type="text" name="title" value="${bvo.title }" class="form-control"></td>
		</tr>
		<tr>
			<th>writer</th>
			<td><input type="text" name="writer" value="${bvo.writer }" readonly="readonly" class="form-control"></td>
		</tr>
		<tr>
			<th>registerDate</th>
			<td><input type="text" name="registerdate" value="${bvo.regAt }" readonly="readonly" class="form-control"></td>
		</tr>		
		<tr>
			<th>content</th>
			<td><textarea name="content" class="form-control">${bvo.content }</textarea></td>
		</tr>
	</thead>
</table>
<!-- 파일 표시란 -->
<!-- when : 그림 파일 외 파일이 존재하는 경우 가정 -->
<c:set value="${bdto.flist }" var="flist"></c:set>
<div class="col-12">
	<ul class="list-group list-group-flush">
			<c:forEach items="${flist }" var="fvo">
				<li>
					<c:choose>
						<c:when test="${fvo.fileType > 0 }">
							<div>
								<!-- fn : \\을 /로 대체, / 추가후 파일명 넣어주기  -->
								<img alt="없음." src="/upload/${fn: replace(fvo.saveDir,'\\', '/')}/
								${fvo.uuid}_th_${fvo.fileName}">
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<!-- file 아이콘 추가 -->
							</div>
						</c:otherwise>
					</c:choose>
					<div class="ms-2 me-auto">
						<div class="fw-bold">${fvo.fileName }</div>
					<span class="badge round-pill text-bg-secondary">${fvo.fileSize }Byte</span>
					<button type="button" class="file-x btn btn-dark" data-uuid="${fvo.uuid}">X</button>
					</div>
				</li>
			</c:forEach>
	</ul>
</div>

<!-- 새파일 등록 라인 -->
<div class="mb-3">
  <input type="file" class="form-control" name="files" id="files" style="display: none;" multiple="multiple">
  <!-- input button trigger 용도의 button -->
  <button type="button" id=trigger class="btn btn-dark">File Upload</button>
</div>
<div class="mb-3" id="fileZone">
<!-- 첨부파일  표시될 영역 -->
</div>
<button type="submit" class="btn btn-dark" id="regBtn">수정</button>
</form>

<a href="/board/list">
	<button type="button" class="btn btn-dark">리스트</button>
</a>
<script type="text/javascript" src="/resources/js/boardModify.js"></script>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
<jsp:include page="../common/footer.jsp" />
</body>
</html>