<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" />
		<title>社員管理システム</title>
	</head>
	<body>
		<%@include file="/jsp/common/header.jsp" %>
		<div class="content">
			<aside>
				<div class="title">社員名検索</div>
				<div class="form">
					<form action="">
						<input type="text" name="empId">
						<input type="submit" value="検索">
					</form>
				</div>
				<div class="title">部署名検索</div>
				<div class="form">
					<form action="">
						<select name="deptId">
							<option value="1">営業部</option>
							<option value="2">経理部</option>
							<option value="3">総務部</option>
						</select>
						<input type="submit" value="検索">
					</form>
				</div>
			</aside>
			<article>
				<h3>社員登録確認画面</h3>
				<div class="update">
					<div class="form">
						<div class="label">パスワード：</div>
						<div class="input">${ employee.empPass }</div>
					</div>
					<div class="form">
						<div class="label">社員名：</div>
						<div class="input">${ employee.empName }</div>
					</div>
					<div class="form">
						<div class="label">性別：</div>
						<div class="input">${ employee.gender }</div>
					</div>
					<div class="form">
						<div class="label">住所：</div>
						<div class="input">${ employee.address }</div>
					</div>
					<div class="form">
						<div class="label">生年月日：</div>
						<div class="input">${ employee.birthday }</div>
					</div>
					<div class="form">
						<div class="label">権限：</div>
						<div class="input">${ employee.authority }</div>
					</div>
					<div class="form">
						<div class="label">部署名：</div>
						<div class="input">${ employee.department.deptName }</div>
					</div>
					<form action="<%=request.getContextPath() %>/regist" method="GET">
						<div class="form">
							<div class="label"></div>
							<div class="input">
								<input type="submit" value="登録">
							</div>
						</div>
						<!-- ステータス保持用(hiddenタグ) -->
						<input type="hidden" name="emp_pass" value="${ employee.empPass }">
						<input type="hidden" name="emp_name" value="${ employee.empName }">
						<input type="hidden" name="gender" value="${ employee.gender }">
						<input type="hidden" name="address" value="${ employee.address }">
						<input type="hidden" name="birthday" value="${ employee.birthday }">
						<input type="hidden" name="authority" value="${ employee.authority }">
						<input type="hidden" name="dept_id" value="${ employee.department.deptId }">
					</form>
					<form action="">
						<div class="form">
							<div class="label"></div>
							<div class="input">
								<input type="submit" value="戻る">
							</div>
						</div>
					</form>
				</div>
			</article>
		</div>	
		<%@include file="/jsp/common/footer.jsp" %>
	</body>
</html>