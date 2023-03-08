package jp.co.sss.servlet.delete;

import java.io.IOException;
import java.text.ParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.dao.EmployeeDao;

@WebServlet("/delete_input")
public class DeleteInput extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String errMsg = "職員が存在しません。";

		// 削除対象のIDを取得
		String empId = request.getParameter("emp_id");
		String empPass = "notpass";
		
		try {
			errMsg = EmployeeDao.findEmp(empId, empPass);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		request.setAttribute("employee", errMsg);
		request.getRequestDispatcher("/jsp/delete/delete_check.jsp").forward(request, response);;
	}
}
