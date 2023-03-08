package jp.co.sss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import jp.co.sss.dao.EmployeeDao;
import jp.co.sss.dto.Employee;

public class Validation {

	/**
	* 未入力チェック
	* 	※チェックは必要最低限のみ実施する。
	* 	
	* 	呼び出し元でチェック項目をListに詰めて渡す。
	* 	リスト数チェックを繰り返す
	* 
	* @param dispStatus
	* @param value
	* @return errMsg
	* 
	*/
	public String blank(String dispStatus, List<String> value) {

		String errMsg = "";
		
		List<String> checkContentsList = new ArrayList<String>();
		checkContentsList.add("社員ID");
		checkContentsList.add("パスワード");
		checkContentsList.add("社員名");
		checkContentsList.add("住所");
		checkContentsList.add("生年月日");
		
		// index
		//  チェック項目
		//   0: パスワード
		//   1: 社員ID
		if ("index".equals(dispStatus)) {
			// パスワードだけ未入力時
			if ("".equals(value.get(0)) && !"".equals(value.get(1))) {
				errMsg = "社員IDを入力して下さい。";
			// 社員IDだけ未入力時
			} else if (!"".equals(value.get(0)) && "".equals(value.get(1))) {
				errMsg = "パスワードを入力して下さい。";
			// 社員IDとパスワード共に未入力時
			} else if ("".equals(value.get(0)) && "".equals(value.get(1))) {
				errMsg = "社員IDとパスワードを入力して下さい。";
			}
		}

		// regist または update
		//  チェック項目
		//  0: パスワード
		//  1: 社員名
		//  3: 住所
		//  4: 生年月日
		if ("regist".equals(dispStatus) || "update".equals(dispStatus)) {

			// リストサイズ分繰返し空白チェック
			for (int i = 0; i < value.size() && "".equals(errMsg); i++) {
				if ("".equals(value.get(i))) {
					switch (i) {
					case 0: // パスワード
						errMsg = "0";
						break;
					case 1: // 社員名
						errMsg = "1";
						break;
					case 2: // 住所
						errMsg = "2";
						break;
					case 3: // 生年月日
						errMsg = "3";
						break;
					}
				}
			}
			Properties properties = new Properties();
			String file1 = "C:/Users/kki25/git/Development_exercise/Development_exercise/src/main/java/message.properties";
			try {
				FileInputStream fis = new FileInputStream(file1);
				try {
					properties.load(fis);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			errMsg = MessageFormat.format(
					properties.getProperty("org.hibernate.validator.constraints.NotBlank.message")
							, checkContentsList.get(Integer.parseInt(errMsg))
						);
		}
		
		return errMsg;
	}

	/**
	 * 文字数チェック
	 * 
	 * @param dispStatus
	 * @param value
	 * @return errMsg
	 * 
	 */
	public String digits(String dispStatus, List<String> value) {

		String errMsg = "";

		List<String> checkContentsList = new ArrayList<String>();
		checkContentsList.add("社員ID");
		checkContentsList.add("パスワード");
		checkContentsList.add("社員名");
		checkContentsList.add("住所");
		
		// チェックする桁数
		Map<String, Integer> numberDigits = new HashMap<String, Integer>();
		numberDigits.put("社員ID", 5);
		numberDigits.put("パスワード", 16);
		numberDigits.put("社員名", 30);
		numberDigits.put("住所", 60);

		// index
		// チェック項目
		//  0: 社員ID
		if ("index".equals(dispStatus)) {
//			if (5 <= value.get(0).length()) {
			if (numberDigits.get("社員ID") <= value.get(0).length()) {
				errMsg = checkContentsList.get(0) + "は5桁以内で入力して下さい。";
			}
		}
		
		// regist または update
		// チェック項目
		//  0: パスワード
		//  1: 社員名
		//  2: 住所
		if ("regist".equals(dispStatus) || "update".equals(dispStatus)) {
			errMsg = numberDigits.get("パスワード") <= value.get(0).length()
					? checkContentsList.get(1) + "は16桁以内で入力して下さい。" : "";
			errMsg = "".equals(errMsg) && numberDigits.get("社員名") <= value.get(1).length()
					? checkContentsList.get(2) + "は30桁以内で入力して下さい。" : "";
			errMsg = "".equals(errMsg) && numberDigits.get("住所") <= value.get(3).length()
					? checkContentsList.get(3) + "は60桁以内で入力して下さい。" : "";
		}

		return errMsg;
	}
	
	/**
	 * 整数値形式チェック
	 * 
	 * @param dispStatus
	 * @param value
	 * @return errMsg
	 */
	public String format(String dispStatus, List<String> value) {

		String errMsg = "";

		Pattern pattern = Pattern.compile("^[0-9]+$");

		// index
		if ("index".equals(dispStatus)) {
			if (!pattern.matcher(value.get(0)).matches()) {
				errMsg = "社員IDは整数値で入力して下さい。";
			}
		}

		// regist
		if ("regist".equals(dispStatus)) {
			
		}

		return errMsg;
	}

	/**
	 * ログインユーザチェック
	 * 
	 * @param dispStatus
	 * @param value
	 * @return errMsg
	 * 
	 */
	public String userCheck(String dipStatus, List<String> value) {

	 	String errMsg = "職員が存在しません。";

		Employee employee = new Employee();
		EmployeeDao employeeDao = new EmployeeDao();

		try {
			errMsg = "success".equals(employeeDao.findEmp(value.get(0), value.get(1)))
					? "" : errMsg;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return errMsg;
	}

	// TODO DateTimeFormatter使って日付の妥当性をチェックする
	/**
	 * 日付の妥当性チェック
	 * 
	 * @param
	 * @param
	 * @return
	 * 
	 */
	public String DateFormatter() {

		String errMsg = "";

		return errMsg;
	}
}
