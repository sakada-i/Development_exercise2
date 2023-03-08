package jp.co.sss.servlet.regist;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.Validation;
import jp.co.sss.dao.DepartmentDao;
import jp.co.sss.dto.Department;
import jp.co.sss.dto.Employee;

@WebServlet("/regist_input")
public class RegistInput extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

		Validation validator = new Validation();

		// エラーメッセージ
		String errMsg = "";

		// 画面タイプ
		String displayType = "regist";

	
		// 未入力チェック
		List<String> blankCheckList = new ArrayList<String>();
		blankCheckList.add(request.getParameter("emp_pass"));
		blankCheckList.add(request.getParameter("emp_name"));
		blankCheckList.add(request.getParameter("address"));
		blankCheckList.add(request.getParameter("birthday"));
//		errMsg = validator.blank(displayType, paramList);
		errMsg = validator.blank(displayType, blankCheckList);

		// 文字数チェック
		List<String> digitsCheckList = new ArrayList<String>();
		blankCheckList.add(request.getParameter("emp_id"));
		errMsg = "".equals(errMsg) ? validator.digits(displayType, digitsCheckList) : errMsg;

		// 日付の妥当性チェック
		// TODO DateTimeFormatterを使って日付の妥当性をチェック

		/**
		 * 0: emp_pass(パスワード)
		 * 1: emp_name(社員名)
		 * 2: gender(性別)
		 * 3: address(住所)
		 * 4: birthday(誕生日)
		 * 5: authority(権限)
		 * 6: deptId(部署ID)
		 */
		List<String> paramList = new ArrayList<String>();
		paramList.add(request.getParameter("emp_pass"));
		paramList.add(request.getParameter("emp_name"));
		paramList.add(request.getParameter("gender"));
		paramList.add(request.getParameter("address"));
		paramList.add(request.getParameter("birthday"));
		paramList.add(request.getParameter("authority"));
		paramList.add(request.getParameter("dept_id"));
		if ("".equals(errMsg)) {
			Employee employee = new Employee();
			employee.setEmpPass(paramList.get(0));
			employee.setEmpName(paramList.get(1));
			employee.setGender(
					Integer.parseInt(paramList.get(2))
				);
			employee.setAddress(paramList.get(3));
			employee.setBirthday(
					Date.valueOf(paramList.get(4))
				);
			employee.setAuthority(
					Integer.parseInt(paramList.get(5))
				);
			Department department = DepartmentDao.findById(Integer.parseInt(paramList.get(6)));
			employee.setDepartment(department);
			// 遷移先の画面を指定
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/jsp/regist/regist_check.jsp").forward(request, response);
		} else {
			request.setAttribute("errMsg", errMsg);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/regist/regist_input.jsp");
			dispatcher.forward(request, response);
		}
	}
}
