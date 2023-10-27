<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Page</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<form action="/board/register" method="post" enctype="multipart/form-data">
<div class="mb-3">
  <label for="exampleFormControlInput1" class="form-label">title</label>
  <input type="text" class="form-control" name="title" id="exampleFormControlInput1" placeholder="title">
</div>
<div class="mb-3">
  <label for="exampleFormControlInput1" class="form-label">writer</label>
  <input type="text" class="form-control" name="writer" id="exampleFormControlInput1" placeholder="writer">
</div>
<div class="mb-3">
  <label for="exampleFormControlTextarea1" class="form-label">Content</label>
  <textarea class="form-control" name="content" id="exampleFormControlTextarea1" rows="3"></textarea>
</div>
<div class="mb-3">
  <input type="file" class="form-control" name="files" id="files" style="display: none;" multiple="multiple">
  <!-- input button trigger 용도의 button -->
  <button type="button" id=trigger class="btn btn-dark">File Upload</button>
</div>
<div class="mb-3" id="fileZone">
<!-- 첨부파일  표시될 영역 -->
</div>
<button type="submit" class="btn btn-dark" id="regBtn">등록</button>
</form>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
<jsp:include page="../common/footer.jsp" />
</body>
</html>