<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<h4>Input your ID & PASSWORD</h4>
<div class="container">
<form action="/member/login" method="post">
	<div class="mb-3">
	  <label for="e" class="form-label">email</label>
	  <input type="email" class="form-control" name="email" id="e" placeholder="example@OOO.com">
	</div>
	<div class="mb-3">
	  <label for="p" class="form-label">pwd</label>
	  <input type="password" class="form-control" name="pwd" id="p" placeholder="password">
	</div>
	<button type="submit" class="btn btn-dark">LogIn</button>
</form>
</div>
<jsp:include page="../common/footer.jsp" />
</body>
</html>