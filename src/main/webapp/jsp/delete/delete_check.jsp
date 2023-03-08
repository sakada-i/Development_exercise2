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
				<h3>社員削除確認画面</h3>
				<p></p>
				<%
					// エラーメッセージの有無判定
					if (null != request.getAttribute("errMsg")) {
				%>
					<div class="errormessage">${ errMsg }</div>
				<%
					}
				%>
				<div class="update">
					<div class="form">
						<div class="label">パスワード：</div>
						<div class="input">※非表示</div>
					</div>
					<div class="form">
						<div class="label">社員名：</div>
						<div class="input">${ employee.empName }</div>
					</div>
					<div class="form">
						<div class="label">性別：</div>
						<div class="input"">${ employee.gender }</div>
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
						<%
							String condition = "よい";
							// if ("1" == request.getParameter("deptId")) {
							if (condition == "よい") {
						%>
							<div class="input">${ employee.deptId }</div>
						<%
							}
						%>
					</div>
					<form action="<%=request.getContextPath() %>/delete" method="GET">
						<div class="form">
							<div class="label"></div>
							<div class="input">
								<input type="hidden" name="empId" value="${ employee.empId }">
								<input type="submit" value="削除">
							</div>
						</div>
					</form>
					<form action="<%=request.getContextPath() %>/list" method="GET">
						<div class="form">
							<div class="label"></div>
							<div class="input">
								<!-- <input type="hidden" name="empId" value="4"> -->
								<input type="submit" value="戻る">
							</div>
						</div>
						<input type="hidden" name="emp_id" value="${ employee.empId }" />
						<input type="hidden" name="emp_name" value="${ employee.empName }" />
						<input type="hidden" name="gender" value="${ employee.gender }" />
						<input type="hidden" name="address" value="${ employee.address }" />
						<input type="hidden" name="birthday" value="${ employee.birthday }" />
						<input type="hidden" name="authority" value="${ employee.authority }" />
						<input type="hidden" name="dept_id" value="${ employee.deptId }" />
					</form>
				</div>
			</article>
		</div>	
		<%@include file="/jsp/common/footer.jsp" %>
	</body>
</html>