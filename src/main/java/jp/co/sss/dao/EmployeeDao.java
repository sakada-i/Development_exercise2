package jp.co.sss.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sss.dto.DBManager;
import jp.co.sss.dto.Department;
import jp.co.sss.dto.Employee;

public class EmployeeDao {
	
	/** 画面タイプ */
	private String displayType = "";

	/**
	 * 画面タイプを返します
	 * @return
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * 画面タイプを設定します
	 * @param displayType
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * 職員検索するメソッド
	 * @param empId   社員ID
	 * @param empPass パスワード
	 * @return result
	 * @throws ParseException 
	 */
	public static String findEmp(String empId, String empPass) throws ParseException {

		Connection con = null;
		PreparedStatement ps = null;
		Employee employee = new Employee();
		String sql = "SELECT * FROM employee WHERE emp_id = ? AND emp_pass = ?";
		String status = "failure";

		try {
			// DB接続内容記述
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(empId));
			ps.setString(2, empPass);
	
			// 取得データ格納内容記述
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				employee.setEmpId(rs.getInt("emp_id"));
				employee.setEmpPass(rs.getString("emp_pass"));
				employee.setEmpName(rs.getString("emp_name"));
				employee.setGender(rs.getInt("gender"));
				employee.setAddress(rs.getString("address"));
				employee.setBirthday(Date.valueOf(rs.getString("birthday")));
				employee.setAuthority(rs.getInt("authority"));
				employee.setDeptId(rs.getInt("dept_id"));
				status = "success";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(ps, con);
		}

		return status;
	}

	/**
	 * 社員全件取得するメソッド
	 * @return
	 * @throws ParseException 
	 */
	public static List<Employee> findAll() throws ParseException {

		Connection con = null;
		PreparedStatement ps = null;

		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			
			con = DBManager.getConnection();
			
			ps = con.prepareStatement(""
					+ "SELECT * "
					+ "FROM employee e "
					+ "INNER join department d "
					+ "ON e.dept_id = d.dept_id"
					);
			
			ResultSet rs = ps.executeQuery();
		
			// 誕生日はString型⇒Date型へ変換する必要有
			Date birthday = null;
			while(rs.next()) {

				Employee employee = new Employee();
				employee.setEmpId(rs.getInt("emp_id"));
				employee.setEmpPass(rs.getString("emp_pass"));
				employee.setEmpName(rs.getString("emp_name"));
				// 【性別】 1:男性 2:女性
				// TODO 性別：int型で受け取るためjsp側で表示を切り替える
				employee.setGender(rs.getInt("gender"));
				employee.setAddress(rs.getString("address"));
				employee.setBirthday(Date.valueOf(rs.getString("birthday")));
				// 【権限】 1:一般 2:管理者
				// TODO intで受け取りjsp側で表示を切り替える
				employee.setAuthority(rs.getInt("authority"));

				Department department = new Department();
				department.setDeptId(rs.getInt("dept_id"));
				department.setDeptName(rs.getString("dept_name"));
				
				employee.setDepartment(department);
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(ps, con);
		}

		return employeeList;
	}
	
	public static void insert(Employee employee) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DBManager.getConnection();
			// 職員登録SQL発行
			ps = con.prepareStatement("INSERT INTO employee VALUES (nextval('seq_emp'),?,?,?,?,?,?,?)");
			
			// SQLへ渡すパラメータを設定
			ps.setString(1, employee.getEmpPass());
			ps.setString(2, employee.getEmpName());
			ps.setInt(3, employee.getGender());
			ps.setString(4, employee.getAddress());
			ps.setDate(5, (Date) employee.getBirthday());
			ps.setInt(6, employee.getAuthority());
			ps.setInt(7, employee.getDepartment().getDeptId());
			// SQL実行
			int result = ps.executeUpdate();
			// 登録件数をコンソールへ出力
			System.out.println(result);
			// コミット実行※これやらないとDBへ反映されない
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// DBコネクションを閉じる
			DBManager.close(ps, con);
		}
	}
	
	/**
	 * 職員削除を実行するメソッド
	 * @param empId
	 */
	public static void delete(String empId) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DBManager.getConnection();
			// deleteSQL発行
			ps = con.prepareStatement("DELETE FROM employee WHERE emp_id = ?");
			// SQLへ渡すパラメータを設定
			ps.setInt(1, Integer.parseInt(empId));
			// SQL実行
			int result = ps.executeUpdate();
			// 削除件数をコンソールへ出力
			System.out.println(result);
			// コミット実行※これやらないとDBへ反映されない
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// DBコネクションを閉じる
			DBManager.close(ps, con);
		}
	}
}
