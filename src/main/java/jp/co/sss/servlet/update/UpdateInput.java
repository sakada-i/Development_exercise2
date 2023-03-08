package jp.co.sss.servlet.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.Validation;
import jp.co.sss.dto.Employee;

@WebServlet("/update_input")
public class UpdateInput extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// エラーメッセージ
		String errMsg = "";
		// 画面タイプ
		String displayType = "update";

		Employee employee = null;
		Validation validator = new Validation();

		// 未入力チェック呼び出し
		List<String> blankCheckList = new ArrayList<String>();
		blankCheckList.add(request.getParameter("emp_pass"));	// 0: パスワード
		blankCheckList.add(request.getParameter("emp_name"));	// 1: 社員名
		blankCheckList.add(request.getParameter("address"));	// 2: 住所
		blankCheckList.add(request.getParameter("birthday"));	// 3: 生年月日
//		errMsg = "".equals(errMsg) ? validator.blank(displayType, paramList) : errMsg;
		errMsg = "".equals(errMsg) ? validator.blank(displayType, blankCheckList) : errMsg;

		// 文字数チェック呼び出し
		List<String> digitsCheckList = new ArrayList<String>();
		digitsCheckList.add(request.getParameter("emp_pass"));	// 0: パスワード
		digitsCheckList.add(request.getParameter("emp_name"));	// 1: 社員名
		digitsCheckList.add(request.getParameter("addres"));	// 2: 住所 
//		errMsg = "".equals(errMsg) ? validator.digits(displayType, paramList) : errMsg;
		errMsg = "".equals(errMsg) ? validator.digits(displayType, digitsCheckList) : errMsg;

		// ユーザーチェックメソッド呼び出し
//		errMsg = "".equals(errMsg) ? validator.userCheck(displayType, paramList) : errMsg;
		List<String> userCheckList = new ArrayList<String>();
		userCheckList.add(request.getParameter("addres"));	 // 0: 社員ID
		userCheckList.add(request.getParameter("password")); // 1: パスワード
		errMsg = "".equals(errMsg) ? validator.userCheck(displayType, userCheckList) : errMsg;

		// テキストボックスの入力値（ユーザ入力）
		List<String> paramList = new ArrayList<String>();
		paramList.add(request.getParameter("emp_id"));		// 0: 社員ID
		paramList.add(request.getParameter("emp_pass"));	// 1: パスワード
		paramList.add(request.getParameter("emp_name"));	// 2: 社員名
		paramList.add(request.getParameter("gender"));		// 3: 性別
		paramList.add(request.getParameter("address"));		// 4: 住所
		paramList.add(request.getParameter("birthday"));	// 5: 生年月日
		paramList.add(request.getParameter("authority"));	// 6: 権限
		paramList.add(request.getParameter("dept_id"));		// 7: 所属ID

		request.setAttribute("employee", employee);
		request.getRequestDispatcher("/jsp/update/update_input.jsp").forward(request, response);
	}

}
