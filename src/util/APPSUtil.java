package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.security.config.domain.ConfigSystem;
import com.sit.domain.FileMeta;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;

import util.bundle.BundleUtil;
import util.calendar.CalendarUtil;
import util.cryptography.EncryptionUtil;
import util.cryptography.AES256.AESCipherHybridService;
import util.database.CCTConnectionUtil;
import util.date.DateUtil;
import util.file.FileManagerUtil;
import util.string.RandomUtil;
import util.string.StringUtil;
import util.type.CrytographyType.EncType;
import util.type.DatabaseType.DbType;
import util.web.MenuTreeUtil;
import util.web.MenuUtil;

public class APPSUtil {

	public static String HTML_BR = "<br>";
	public static String HTML_RSAQUO = "&nbsp;&rsaquo;&nbsp;";

	public enum DefaultSecound {
		START(":00"), END(":59"), NONE(""), NOW(":" + Calendar.getInstance(ParameterConfig.getParameter().getApplication().getDatabaseLocale()).get(Calendar.SECOND));

		private String value;

		private DefaultSecound(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 > 31/12/2558
	 *
	 * @param dateValue
	 * @param locale
	 *            (get from session)
	 * @return
	 * @throws Exception
	 */
	public static String convertDateForDisplay(String dateValue, Locale locale) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplay();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, locale);
	}

	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59:59 > 31/12/2558 23:59:59
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplay(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * 
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 2016-06-20 23:59:59 > 31/12/2015 23:59:59
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(String dateValue) throws Exception {
		String fromFormat = "YYYY-MM-DD HH:mm:ss";
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = "YYYY-MM-DD";
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = "YYYY-MM-DD HH:mm:ss";
			}
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * 
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59:59 > 2015123123595959
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayYYYYMMDDHHMMSS(String dateValue) throws Exception {
		String toFormat = "YYYYMMDDHHmmss";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * 
	 * แปลงวันที่ที่ได้รับจาก datepicker เพื่อ insert 31/12/2015 > 20151231
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForInsertYYYYMMDD(String dateValue) throws Exception {
		String toFormat = "YYYYMMDD";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่จาก 20151231 > 31/12/2015
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFromYYYYMMDDToDDslMMslYYYY(String dateValue) throws Exception {
		String toFormat = "DD/MM/YYYY";
		String fromFormat = "YYYYMMDD";
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่จาก 20151231220201 > 31/12/2015 10:00:00
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFromYYYYMMDDHHMMSSToDDslMMslYYYYHhMmSs(String dateValue) throws Exception {
		String toFormat = "DD/MM/YYYY HH:mm:ss";
		String fromFormat = "YYYYMMDDHHmmss";
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่จาก 201512312202 > 31/12/2015 10:00
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFromYYYYMMDDHHMMToDDslMMslYYYYHhMm(String dateValue) throws Exception {
		String toFormat = "DD/MM/YYYY HH:mm";
		String fromFormat = "YYYYMMDDHHmm";
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59 > 20151231235959
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayYYYYMMDDHHMM(String dateValue) throws Exception {
		String toFormat = "YYYYMMDDHHmm";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();

			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59:00 > 20151231235959
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForYYYYMMDDHHMMSS(String dateValue) throws Exception {
		String toFormat = "YYYYMMDDHHmmss";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
			fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();

		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59 > 31/12/2558 23:59
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayHHMM(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMM();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * ใช้สำหรับแปลงค่า String of long ให้เป็น Long เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Long convertLongValue(String value) {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Long.parseLong(value);
		}
	}

	/**
	 * ใช้สำหรับแปลงค่า String of double ให้เป็น Double เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Double convertDoubleValue(String value) {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Double.parseDouble(value);
		}
	}

	/**
	 * get Current Date(dd/mm/yyyy) From Database
	 *
	 *
	 * */
	public static String getCurrentDateDB(Connection conn) throws Exception {
		String curDate = "";
		try {
			curDate = DateUtil.getCurrentDateDB(conn, ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect(), DbType.ORA);
			return curDate;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * get Current DateTime(dd/mm/yyyy hh:mi:sss) From Database
	 *
	 *
	 * */
	public static String getCurrentDateTimeDB(Connection conn) throws Exception {
		String curDate = "";
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery("select date_format(sysdate(), '%d/%m/%Y %H:%i:%S') as CUR_DATE from dual");

			if (rst.next()) {
				curDate = StringUtil.nullToString(rst.getString("CUR_DATE"));
			}

		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return curDate;
	}

	/**
	 * ตัด Format "," ออก
	 *
	 *
	 * */
	public static String unformatCurrency(String value) {
		String returnValue = null;
		if ((value != null) && !value.trim().isEmpty()) {
			returnValue = value.replace(",", "");
		}
		return returnValue;
	}

	/**
	 * Format ที่เป็นค่า ทศนิยม
	 *
	 * param : 100000 , 2 return : 100000.00
	 *
	 * */

	public static String formatDouble(String value, int digit) {

		String returnValue = "";
		if ((value != null) && !value.trim().isEmpty()) {
			String patten = "###0";
			if (digit > 0) {
				patten += ".";
			}

			for (int i = 0; i < digit; i++) {
				patten += "0";
			}

			returnValue = new DecimalFormat(patten).format(Double.parseDouble(value));
		}
		return returnValue;
	}

	/**
	 * Format ที่เป็นค่าเงิน
	 *
	 * param : 100000 , 2 return : 100,000.00
	 *
	 * */
	public static String formatCurrency(String value, int digit) {

		String returnValue = "";
		if ((value != null) && !value.trim().isEmpty()) {
			String patten = "#,##0";
			if (digit > 0) {
				patten += ".";
			}

			for (int i = 0; i < digit; i++) {
				patten += "0";
			}

			returnValue = new DecimalFormat(patten).format(Double.parseDouble(value));
		}
		return returnValue;
	}

	/**
	 * ดึงค่าเดือน เช่น มกราคม, กุมภาพันธ์ ...
	 * 
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String getMonthName(String month) throws Exception {
		String monthReturn = "";
		try {

			ResourceBundle bundle = BundleUtil.getBundle("resources.bundle.common.Message", ParameterConfig.getParameter().getApplication().getApplicationLocale());
			int numMonth = Integer.parseInt(month);
			switch (numMonth) {
			case 1:
				monthReturn = bundle.getString("month01");
				break;
			case 2:
				monthReturn = bundle.getString("month02");
				break;
			case 3:
				monthReturn = bundle.getString("month03");
				break;
			case 4:
				monthReturn = bundle.getString("month04");
				break;
			case 5:
				monthReturn = bundle.getString("month05");
				break;
			case 6:
				monthReturn = bundle.getString("month06");
				break;
			case 7:
				monthReturn = bundle.getString("month07");
				break;
			case 8:
				monthReturn = bundle.getString("month08");
				break;
			case 9:
				monthReturn = bundle.getString("month09");
				break;
			case 10:
				monthReturn = bundle.getString("month10");
				break;
			case 11:
				monthReturn = bundle.getString("month11");
				break;
			case 12:
				monthReturn = bundle.getString("month12");
				break;

			default:
				break;
			}

		} catch (Exception e) {
			throw e;
		}
		return monthReturn;
	}

	/**
	 * แปลงเลขอารบิกเป็นเลขไทย => 1 > ๑
	 */
	public static String getThaiNumber(String value) {
		String result = null;
		try {
			if (value != null) {
				result = value.replaceAll("1", "๑").replaceAll("2", "๒").replaceAll("3", "๓").replaceAll("4", "๔").replaceAll("5", "๕").replaceAll("6", "๖").replaceAll("7", "๗").replaceAll("8", "๘").replaceAll("9", "๙").replaceAll("0", "๐");
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public static String randomPassword(ConfigSystem config) throws Exception {

		int pwLength = config.getPwLength();
		boolean pwFormatNum = config.getPwFormatNum().equals(GlobalVariable.FLAG_ACTIVE);
		boolean pwFormatLitChar = config.getPwFormatLitChar().equals(GlobalVariable.FLAG_ACTIVE);
		boolean pwFormatBigChar = config.getPwFormatBigChar().equals(GlobalVariable.FLAG_ACTIVE);
		boolean pwFormatSpecial = config.getPwFormatSpecial().equals(GlobalVariable.FLAG_ACTIVE);
		return RandomUtil.getRandomString(pwLength, pwFormatNum, pwFormatLitChar, pwFormatBigChar, pwFormatSpecial);
	}

	public static String getIPAddress() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		try {
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_CLIENT_ipAddress");
			}
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			throw e;
		}

		return ipAddress;
	}

	/**
	 * converse form 2558 >> 2015
	 * 
	 * @param engYYYY
	 * @return thaiYYYY
	 */
	public static String converseYearYYYY(String formatYYYY) throws Exception {
		String yyyy = "";
		yyyy = CalendarUtil.convertDateString(formatYYYY, "YYYY", ParameterConfig.getParameter().getApplication().getDatetimeLocale(), "YYYY", ParameterConfig.getParameter().getApplication().getDatabaseLocale());
		return yyyy;
	}

	/**
	 * converse form 2016 >> 2559 (ตาม Locale)
	 * 
	 * @param engYYYY
	 * @return thaiYYYY
	 */
	public static String converseYear(String formatYYYY, Locale locale) throws Exception {
		String yyyy = "";
		yyyy = CalendarUtil.convertDateString(formatYYYY, "YYYY", ParameterConfig.getParameter().getApplication().getDatabaseLocale(), "YYYY", locale);
		return yyyy;
	}

	public static String currentDateTest() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String passwordEncrypt(String password) throws Exception {
		AESCipherHybridService aes = new AESCipherHybridService("Isb5Plse$%!dsl0[sd{sd,xpW>ztF.;e");
		return aes.encrypt(password);
	}

	public static String passwordDecrypt(String password) throws Exception {
		AESCipherHybridService aes = new AESCipherHybridService("Isb5Plse$%!dsl0[sd{sd,xpW>ztF.;e");
		return aes.decrypt(password);
	}

	/**
	 * ONE-Way encrypt (decrypt ไม่ได้)
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String passwordEncryptOneWay(String password) throws Exception {
		return EncryptionUtil.doEncrypt(password, EncType.SHA256);
	}

	/**
	 * ดึงค่าเดือน เช่น ม.ค., ก.พ ...
	 * 
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String getMonthMMName(String month) throws Exception {
		String monthReturn = "";
		try {

			ResourceBundle bundle = BundleUtil.getBundle("resources.bundle.common.Message", ParameterConfig.getParameter().getApplication().getApplicationLocale());
			int numMonth = Integer.parseInt(month);
			switch (numMonth) {
			case 1:
				monthReturn = bundle.getString("mon01");
				break;
			case 2:
				monthReturn = bundle.getString("mon02");
				break;
			case 3:
				monthReturn = bundle.getString("mon03");
				break;
			case 4:
				monthReturn = bundle.getString("mon04");
				break;
			case 5:
				monthReturn = bundle.getString("mon05");
				break;
			case 6:
				monthReturn = bundle.getString("mon06");
				break;
			case 7:
				monthReturn = bundle.getString("mon07");
				break;
			case 8:
				monthReturn = bundle.getString("mon08");
				break;
			case 9:
				monthReturn = bundle.getString("mon09");
				break;
			case 10:
				monthReturn = bundle.getString("mon10");
				break;
			case 11:
				monthReturn = bundle.getString("mon11");
				break;
			case 12:
				monthReturn = bundle.getString("mon12");
				break;

			default:
				break;
			}

		} catch (Exception e) {
			throw e;
		}
		return monthReturn;
	}

	/*
	 * dateCompare คือ วันที่ - ย้อนหลัง x วัน 
	 * dateCheck คือ ตัวที่นำมาเปรียบเทียบ (วันที่ file) 
	 * เช่น return true หมายถึง dateCheck น้อยกว่าเท่ากับ dateCompare
	 */
	public static boolean compareLessEqualDate(String dateCheck) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		Date dd = new Date();
		Calendar cc = Calendar.getInstance(Locale.ENGLISH);
		cc.setTime(dd);
		cc.add(Calendar.DATE, -2);
		dd.setTime(cc.getTime().getTime());

		String dateCompare = sdf.format(dd);

		return Integer.parseInt(dateCheck) <= Integer.parseInt(dateCompare);

	}

	/*
	 * dateCompare คือ วันที่ - ย้อนหลัง x วัน 
	 * dateCheck คือ ตัวที่นำมาเปรียบเทียบ (วันที่ file) 
	 * เช่น return true หมายถึง dateCheck น้อยกว่าเท่ากับ dateCompare
	 */
	public static void checkFileForDelete(String pathFile, String fileName) throws Exception {

		File[] files = FileManagerUtil.listFiles(pathFile, fileName + "*.xlsx");

		for (File f : files) {
			String ff = f.getName();
			String fff[] = ff.split("_");
			if (fff.length > 1) {

				String fDate = fff[1].substring(0, 8);

				if (APPSUtil.compareLessEqualDate(fDate)) {
					FileManagerUtil.deleteQuietly(f);
				}
			} // end if length
		} // end for

	}

	/**
	 * Format from 00YYMMDD -> DD/MM/YYYY
	 * @param dob
	 * @return
	 */
	public static String formatDOB(String dob) {
		String result = "";

		if (dob != null && dob.trim().length() == 8) {
			result = dob.substring(6) + "/" + dob.substring(4, 6) + "/" + dob.substring(0, 4);
		}

		return result;
	}

	/**
	 * get Current Date(dd/mm/yyyy) From Database 
	 * year : จำนวนปีที่ต้องการ บวกเพิ่ม 
	 * return String Date (dd/mm/yyyy)
	 *
	 * */
	public static String getCurrentDateDBPlusYear(Connection conn, int year) throws Exception {
		String curDate = "";
		try {
			curDate = DateUtil.getCurrentDateDB(conn, ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect(), DbType.ORA);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", ParameterConfig.getParameter().getApplication().getDatabaseLocale());
			Date date = sdf.parse(curDate);
			Calendar cal = DateUtils.toCalendar(date);
			cal.add(Calendar.YEAR, year);
			return CalendarUtil.convertDateString(sdf.format(cal.getTime()), ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect(), ParameterConfig.getParameter().getApplication().getDatabaseLocale(), ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect(), ParameterConfig.getParameter().getApplication().getDatabaseLocale());
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * replace /r ด้วย ค่าว่าง 
	 * str : ตัวที่นำมา replace
	 * @param str
	 * @return
	 */
	public static String replaceSlashRWithBlank(String str) {
		if (str != null) {
			return str.replaceAll("(\\r)+", "");

		} else {
			return str;
		}
	}

	/**
	 * ลบไฟล์ย้อนหลังตามเวลาจริงที่แก้ไขไฟล์
	 * @param pathFile: path ที่จะไป loop ลบ
	 * @param daysBack: วันที่ย้อนหลังว่าจะย้อนไปลบกี่วัน 
	 * ตัวอย่างการใช้งาน : pathfile = D:/tmp/ACCESS/ และ daysBack = 1
	 */
	public static void deleteFileFromLastModifiedDate(String pathFile, int daysBack) {

		File directory = new File(pathFile);
		File[] listFiles = directory.listFiles();

		long purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);
		for (File listFile : listFiles) {
			if (listFile.lastModified() < purgeTime) {
				listFile.delete();
			}
		}

	}

	/**
	 * DD/MM/YYYY HH:mm:ss
	 * @param dateStr: 22/03/2017 12:00:00
	 * @return
	 * @throws Exception
	 */
	public static long convertDateTsringToMillisecound(String dateStr) throws Exception {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS());// "DD/MM/YYYY HH:mm:ss"
			Date date = sdf.parse(dateStr);
			return date.getTime();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * 
	 * สำหรับแปลงค่า date from 
	 * กรณีไม่มีเวลาจะใส่ default ให้ 
	 * 10/02/2017 10:20
	 * @param date
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String formateDateFromDDslMMslYYYYHhMm(String date, String time) throws Exception {
		String dateTime = null;
		if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) != null) {
			dateTime = StringUtil.nullToString(date) + " " + (StringUtil.nullToString(time));
		} else if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) == null) {
			dateTime = StringUtil.nullToString(date) + " " + GlobalVariable.TimeDefault.START_HHMM.getValue();
		}
		return dateTime;
	}

	/**
	 * สำหรับแปลงค่า date to 
	 * กรณีไม่มีเวลาจะใส่ default ให้ 
	 * 10/02/2017 10:20
	 * @param date
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String formatDateToDDslMMslYYYYHhMm(String date, String time) throws Exception {
		String dateTime = null;
		if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) != null) {
			dateTime = StringUtil.nullToString(date) + " " + (StringUtil.nullToString(time));
		} else if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) == null) {
			dateTime = StringUtil.nullToString(date) + " " + GlobalVariable.TimeDefault.END_HHMM.getValue();
		}
		return dateTime;
	}

	/**
	 * 
	 * สำหรับแปลงค่า date from 
	 * กรณีไม่มีเวลาจะใส่ default ให้ 
	 * 10/02/2017 10:20 >> 201702101020
	 * @param date
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String formateDateFromYYYYMMDDHHMM(String date, String time) throws Exception {
		String dateTime = null;
		if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) != null) {
			dateTime = APPSUtil.convertDateTimeForDisplayYYYYMMDDHHMM(StringUtil.nullToString(date) + " " + (StringUtil.nullToString(time)));
		} else if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) == null) {
			dateTime = APPSUtil.convertDateTimeForDisplayYYYYMMDDHHMM(StringUtil.nullToString(date) + " " + GlobalVariable.TimeDefault.START_HHMM.getValue());
		}
		return dateTime;
	}

	/**
	 * สำหรับแปลงค่า date to 
	 * กรณีไม่มีเวลาจะใส่ default ให้ 
	 * 10/02/2017 10:20 >> 201702101020
	 * @param date
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String formatDateToYYYYMMDDHHMM(String date, String time) throws Exception {
		String dateTime = null;
		if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) != null) {
			dateTime = APPSUtil.convertDateTimeForDisplayYYYYMMDDHHMM(StringUtil.nullToString(date) + " " + (StringUtil.nullToString(time)));
		} else if (StringUtil.stringToNull(date) != null && StringUtil.stringToNull(time) == null) {
			dateTime = APPSUtil.convertDateTimeForDisplayYYYYMMDDHHMM(StringUtil.nullToString(date) + " " + GlobalVariable.TimeDefault.END_HHMM.getValue());
		}
		return dateTime;
	}

	/**
	 * สำหรับระบบ stoplistHitSearch, blacklistHitSearch ใช้ในการแปลงค่า id
	 * สำหรับการ where เงื่อนไขการ export by selection
	 * 
	 * @param selectedIds: 1,2,3,4
	 * @param journeyInfos: a,b,c,d
	 * @return : (1,a),(2,b),(3,c),(4,d)
	 * @throws Exception
	 */
	public static String fotmatIdHitSearch(String selectedIds, String journeyInfos) throws Exception {

		String strs = null;
		try {

			String ids[] = selectedIds.split(",");
			String jos[] = journeyInfos.split(",");

			StringBuilder str = new StringBuilder();
			for (int i = 0; i < ids.length; i++) {
				str.append(",(" + ids[i] + "," + jos[i] + ")");
			}

			if (!str.toString().equals("")) {
				strs = str.toString().substring(1, str.toString().length());
			}

		} catch (Exception e) {
			throw e;
		}

		return strs;

	}

	/**
	 * คำนวณ หา จำนวนแถวจาก length ของข้อความ / จำนวนข้อความต่อแถว 
	 * lengthTotal : length ของข้อความทั้งหมด 
	 * lengthLimit : length ของข้อความ ต่อแถว
	 * 
	 * @param lengthTotal
	 * @param lenghtLimit
	 * 4.0 rounds to 4 
	 * 4.1 rounds to 5
	 * @return
	 */
	public static int calculateTotalRow(double lengthTotal, double lengthLimit) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setRoundingMode(RoundingMode.CEILING);
		int row = 1;
		if (lengthTotal > 0) {
			double countRow = lengthTotal / lengthLimit;
			String cRow = nf.format(countRow);
			row = Integer.valueOf(cRow);

			if (row == 0) {
				row = 1;
			}
		}
		return row;
	}

	/**
	 * get Current Date(dd/mm/yyyy) 
	 * year : จำนวนปีที่ต้องการ บวกเพิ่ม 
	 * return String Date (dd/mm/yyyy)
	 *
	 */
	public static String getCurrentDatePlusDay(int day) throws Exception {
		try {
			Calendar cal = Calendar.getInstance(ParameterConfig.getParameter().getApplication().getDatetimeLocale());
			cal.add(Calendar.DATE, day);
			return CalendarUtil.getDDslMMslYYYY(cal);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * get Current Date(dd/mm/yyyy) 
	 * return String Date (dd/mm/yyyy)
	 *
	 */
	public static String getCurrentDate() throws Exception {
		try {
			Calendar cal = Calendar.getInstance(ParameterConfig.getParameter().getApplication().getDatetimeLocale());
			return CalendarUtil.getDDslMMslYYYY(cal);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * แปลงวันที่จาก 12/05/2017 10:00:00 > 12/05/2017 10:00
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeMMForDisplayHHMM(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMM();
		String fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	public static String convertDateTimeForDisplayDDMMYYYY(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplay();
		String fromFormat = "YYYY-MM-DD HH:mm:ss";
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	public static String convertDateTimeMMForDisplayDDMMYYYY(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplay();
		String fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * แปลงวันที่จาก 23/06/2017 103900 > 23/06/2017 10:39:00
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateToDDslMMslYYYYHhMmSs(String dateValue) throws Exception {
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}

		String[] arr = dateValue.split(" ");
		String HH = arr[1].substring(0, 2);
		String mm = arr[1].substring(2, 4);
		String ss = arr[1].substring(4, 6);

		return String.format("%s %s:%s:%s", arr[0], HH, mm, ss);
	}

	public static void main(String[] args) throws IOException {

		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		
		try {
			//String srcTxtPath = "C:/opt/data/temp/1592630476253.tmp";
			//String descTxtPath = "C:/opt/data/pdf/20200614/mk.pdf";
			//FileManagerUtil.moveFile(srcTxtPath, descTxtPath);
			
			String [] str = new String[]{"test"};
			FileMeta a = new FileMeta();
			a.setFileUploadFileName(str);
			System.out.println(a.getFileUploadFileName()[0]);
					
			a.setFileUploadFileName(new String[]{"mkyoung"});
			System.out.println(a.getFileUploadFileName()[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * แปลงวันที่จาก 23/06/2017 10:39:00 > 20170623103900
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeToYYYYMMDDHHmmss(String inputDateTime) throws Exception {

		String outputDateTime = "";
		if (StringUtil.stringToNull(inputDateTime) != null && inputDateTime.length() == 19) {
			String[] date = inputDateTime.split(" ")[0].split("\\/");
			String newDate = date[2] + date[1] + date[0];
			String time = inputDateTime.split(" ")[1].replaceAll(":", "");
			outputDateTime = newDate + time;
		}
		return outputDateTime;
	}
	
	
	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น 31/12/2015 23:59:59 > 31/12/2558 23:59:59
	 *
	 * @param dateValue
	 * @param locale
	 *            (get from session)
	 * @return
	 * @throws Exception
	 */
	public static String convertDateForDisplayHHMMSS(String dateValue, Locale locale) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat, locale);
	}
	
	public static  byte[] decryptFile(File file, String keyStr) throws Exception{
		byte[] decryptData = null;
		FileInputStream fis = null;
		try {
			
			byte[] fileBytesArray = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			fis.read(fileBytesArray);
			AESCipherHybridService aes = new AESCipherHybridService(keyStr);
			decryptData = aes.decrypt(fileBytesArray);
			
		} catch (Exception e) {
			throw e;
		}finally {
			try {
				if(fis != null) {
					fis.close();
				}
			}catch (Exception e) {}
		}
		
		return decryptData;
	}
	
	/**
	 * สำหรับคำนวณ จำนวนแถว แบบรองรับ กรณีที่ เป็นการ Enter หลายๆ บรรทัด
	 * @param strResult
	 * @param lengthLimit
	 * @return
	 * @throws Exception
	 */
	public static int calculateLine(String strResult , int lengthLimit) throws Exception {
		int row = 0;
		if(StringUtil.stringToNull(strResult) != null){
			String[] strLine = strResult.split("\n");
			int  line = strLine.length;
			  for(int i=0;i< line;i++){
				  int totalRow = calculateTotalRow(strLine[i].length(), lengthLimit);
				  row += totalRow;	
			  }
		}
		return row;
	}
	
	/*
	 * 20190510:channarong.i: เพิ่มการหาวันที่เริ่มต้นถึงวันที่สิ้นสุดจะได้ diffDate กี่วัน?
	 * dateStart คือ วันที่เริ่มต้น เช่น 20191201
	 * dateEnd คือ วันที่สิ้นสุด เช่น 20191231
	 * return จำนวนวันเท่ากับกี่วัน เช่น 30
	 */
	public static int getDiffDate(String dateStart, String dateEnd) throws Exception {
		int dayDiff = -1;
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH); 
		
		Date startdate = df.parse(dateStart);
		Date enddate = df.parse(dateEnd);
		// 20191231 -  20191201 = 30
		long diff = enddate.getTime() - startdate.getTime();
		
		dayDiff = (int) (diff / (24 * 60 * 60 * 1000));
	
		return dayDiff;
	}

	/**
	 * ดึงค่าวันที่เวลาปัจจุบัน จาก database.
	 * <p>
	 * [Example : String curdateTime =
	 * DateUtil.getCurrentDateDB(conn,"dd/mm/yyyy",DateUtil.DbType.ORA)]
	 *
	 * @param conn : Connection
	 * @param format Format Date Time
	 * @param dbType : Database oracle
	 * @return วันที่เวลาปัจจุบัน จาก database
	 * @throws Exception the exception
	 */
	public static String getCurrentDateTimeDB(Connection conn, String format) throws Exception {
		String curDateTime = "";

		ResultSet rst = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rst = stmt.executeQuery("SELECT TO_CHAR(SYSTIMESTAMP, '" + format + "') AS currentDateTime FROM DUAL");
			if (rst.next()) {
				curDateTime = rst.getString("currentDateTime");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rst != null)
					rst.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
		return curDateTime;
	}
	
	/*
	 * 20191010:channarong.i: เพิ่มการหาวันที่เริ่มต้นถึงวันที่สิ้นสุดจะได้ diffDate เท่าไร?
	 * dateStart คือ วันที่เริ่มต้น เช่น 20191201000000
	 * dateEnd คือ วันที่สิ้นสุด เช่น 20191231235959
	 * format คือ รูปแบบformatของวันที่เช่น yyyyMMddHHmmss
	 * unit คือ หน่วยเวลาที่ส่งเข้ามาแปลง
	 * return ตามหน่วยเวลาที่ต้องการ
	 */
	public static int getDiffDateTime(String dateStart, String dateEnd, String format, int unit) throws Exception {
		int dayDiff = -1;
		DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH); 
		
		Date startdate = df.parse(dateStart);
		Date enddate = df.parse(dateEnd);
		// 20191231 -  20191201 = 30
		long diff = enddate.getTime() - startdate.getTime();
		
		dayDiff = (int) (diff / unit);
	
		return dayDiff;
	}
	
	public static Object[] getListOperatorIdFromListOperator(List<Operator> listOperator) {
		Map<String, String> mapOperator = new LinkedHashMap<String, String>();
		if (listOperator != null) {
			for (Operator operator : listOperator) {
				String[] parentIdArray = operator.getParentIds().split(MenuTreeUtil.DELIMITER_FUNCTION);
				if (operator.getDeleteFlag().equals(GlobalVariable.FLAG_DELETED)) {
					continue;
				}
				for (String id : parentIdArray) {
					mapOperator.put(id, id);
				}
			}
		}
		return (Object[]) (mapOperator.values().toArray());
	}
	
	public static List<Operator> generateOperatorResult(Map<String, Operator> mapProgram) {
		Object[] operators = (Object[]) (mapProgram.values().toArray());

		Map<String, Operator> mapMenuLevel = new LinkedHashMap<String, Operator>();
		if (operators.length > 0) {
			int minLevel = ((Operator) operators[0]).getMinLevel();
			int maxLevel = ((Operator) operators[0]).getMaxLevel();
			for (int currentLevel = minLevel; currentLevel <= maxLevel; currentLevel++) {
				for (int index = 0; index <= operators.length - 1; index++) {
					Operator operator = (Operator) operators[index];

					if (operator.getCurrentLevel() == currentLevel) {
						if (operator.getCurrentLevel() == 1) {
							mapMenuLevel.put(operator.getOperatorId(), operator);
						} else {
							String parentOperatorIds = searchParentOperator(operator.getParentId(), operators);
							parentOperatorIds += MenuUtil.DELIMITER_FUNCTION + operator.getOperatorId();
							if (operator.getOperatorType().equals(MenuUtil.OPERATOR_TYPE_FUNCTION)) {
								operator.setParentIds(parentOperatorIds);
								String fullPath = MenuUtil.searchLabel(mapProgram, operator.getCurrentId());
								operator.setSystemName(fullPath.substring(0, fullPath.indexOf(HTML_BR)));
								// LogUtil.SEC.debug("fullPath: " + fullPath);
								if (fullPath.lastIndexOf(HTML_RSAQUO) > -1) {
									operator.setMenuName(fullPath.substring(fullPath.indexOf(HTML_BR) + HTML_BR.length(), fullPath.lastIndexOf(HTML_RSAQUO)));
									operator.setFunctionName(fullPath.substring(fullPath.lastIndexOf(HTML_RSAQUO) + HTML_RSAQUO.length(), fullPath.length()));
								} else {
									operator.setMenuName("");
									operator.setFunctionName(fullPath.substring(fullPath.lastIndexOf(HTML_BR) + HTML_BR.length(), fullPath.length()));
								}
							}
							updateOperator(mapProgram, mapMenuLevel, parentOperatorIds, parentOperatorIds);
						}
					}
				}
			}
		}

		return convertMapOperatorToListOperator(mapMenuLevel);
	}
	
	private static List<Operator> convertMapOperatorToListOperator(Map<String, Operator> mapOperator) {

		List<Operator> listOperator = new ArrayList<Operator>();
		for (String key : mapOperator.keySet()) {
			if (mapOperator.get(key).getOperatorType().equals(MenuUtil.OPERATOR_TYPE_FUNCTION)) {
				Operator operator = mapOperator.get(key);
				operator.setParentIds(operator.getParentIds().replaceAll(MenuUtil.DELIMITER_FUNCTION, MenuTreeUtil.DELIMITER_FUNCTION));
				listOperator.add(operator);
			}
			if (mapOperator.get(key).getMapOperator().size() > 0) {
				listOperator.addAll(convertMapOperatorToListOperator(mapOperator.get(key).getMapOperator()));
			}
		}
		return listOperator;
	}

	private static void updateOperator(Map<String, Operator> mapMenu, Map<String, Operator> mapMenuLevel, String parentIds, String groupParentIds) {
		if (parentIds.indexOf(MenuUtil.DELIMITER_FUNCTION) > -1) {
			String[] operatorIds = parentIds.split(MenuUtil.DELIMITER_FUNCTION);
			String parentOperatorId = operatorIds[0];
			String currentOperatorId = operatorIds[1];
			String groupOperatorId = groupParentIds.substring(0, groupParentIds.indexOf(currentOperatorId) + currentOperatorId.length());

			Operator operator = mapMenu.get(currentOperatorId);
			operator.setParentOperatorIds(groupOperatorId);
			if (mapMenuLevel.get(parentOperatorId) != null) {
				mapMenuLevel.get(parentOperatorId).getMapOperator().put(operator.getOperatorId(), operator);
			}

			parentIds = parentIds.substring(parentIds.indexOf(MenuUtil.DELIMITER_FUNCTION) + MenuUtil.DELIMITER_FUNCTION.length(), parentIds.length());

			if (mapMenuLevel.get(parentOperatorId) != null) {
				updateOperator(mapMenu, mapMenuLevel.get(parentOperatorId).getMapOperator(), parentIds, groupParentIds);
			}
		}
	}

	private static String searchParentOperator(String parentId, Object[] listOperator) {
		String parentIds = "";

		if ((parentId != null) && (parentId.trim().length() > 0)) {

			for (Object operatorObject : listOperator) {

				Operator operator = (Operator) operatorObject;

				if (operator.getOperatorId().equals(parentId)) {
					parentIds = parentIds + MenuUtil.DELIMITER_FUNCTION;
					parentIds = operator.getOperatorId() + parentIds;
					String pppId = searchParentOperator(operator.getParentId(), listOperator);
					if (pppId.length() > 0) {
						parentIds = pppId + MenuUtil.DELIMITER_FUNCTION + parentIds;
					}
				}
			}
			if (parentIds.length() > 0) {
				parentIds = parentIds.substring(0, parentIds.length() - 1);
			}

		}
		return parentIds;
	}
}
