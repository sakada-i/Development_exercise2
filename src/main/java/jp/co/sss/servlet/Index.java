package jp.co.sss.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.Validation;

@WebServlet("/index")
public class Index extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Validation validator = new Validation();

		// エラーメッセージ
		String errMsg = "";
		
		// 画面タイプ
		String displayType = "index";

		// テキストボックスの入力値(ユーザ入力)
		List<String> paramList = new ArrayList<String>();
		paramList.add(request.getParameter("emp_id"));    // 0: 社員ID
		paramList.add(request.getParameter("emp_pass"));  // 1: パスワード

		// 未入力チェック呼び出し
		errMsg = validator.blank("index", paramList);
		// 桁数チェック呼び出し
		errMsg = "".equals(errMsg) ? validator.digits(displayType, paramList) : errMsg;
		// 整数値形式チェック呼び出し
		errMsg = "".equals(errMsg) ? validator.format(displayType, paramList) : errMsg;
		// ユーザーチェック
		errMsg = "".equals(errMsg) ? validator.userCheck(displayType, paramList) : errMsg;

		if ("".equals(errMsg)) {
			response.sendRedirect("empList");
		} else {
			request.setAttribute("errMsg", errMsg);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}
	}
}