package jp.co.sss.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.dao.EmployeeDao;
import jp.co.sss.dto.Employee;

@WebServlet("/empList")
public class EmpList extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// hiddenで保持している値をインスタンスに詰めて一覧画面へ返却する
//		employee.setEmpId(Integer.parseInt(request.getParameter("empId")));
//		employee.setEmpName(request.getParameter("empName"));
//		employee.setGender(Integer.parseInt(request.getParameter("gender")));
//		employee.setAddress(request.getParameter("address"));
//		employee.setBirthday(Date.valueOf(request.getParameter("birthday")));
//		employee.setAuthority(Integer.parseInt(request.getParameter("authority")));
//		employee.setDeptId(Integer.parseInt(request.getParameter("deptId")));
//		request.setAttribute("employee", employee);

		EmployeeDao empDao = new EmployeeDao();
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			// 全職員を取得するメソッドを呼び出す
		 	employeeList = empDao.findAll();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		request.setAttribute("employeeList", employeeList);
		request.getRequestDispatcher("/jsp/emplist.jsp").forward(request, response);
	}
}