package util.web;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;

import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;

public class MenuSideBarUtil {
	

	public static final String DELIMITER_FUNCTION = ",";
	public static final String OPERATOR_TYPE_MENU = "M";
	public static final String OPERATOR_TYPE_FUNCTION = "F";
	
	private static final int OPERATOR_LEVEL_1 = 1;
	
	private static final String DIVIDER = "<li class='dropdown-divider'></li>";
	
	private static final String BEGIN_UL = "<ul class='menu-level%s collapse list-unstyled' id='%s'>";
	private static final String END_UL = "</ul>";
	private static final String BEGIN_LI = "<li>";
	private static final String LI_FOR_MENU_ITEM = "<li id='%s'><a href='%s'>%s</a></li>";
	private static final String END_LI = "</li>";
	
	private static final String NEW_LINE = "\n";
	
	private Object[] operators;
	private Map<String, Operator> mapMenu;
	private Map<String, Operator> mapMenuLevel = new LinkedHashMap<String, Operator>();
	private String context;
	private CommonUser user;
	
	public MenuSideBarUtil(String context, Map<String, Operator> mapMenu, CommonUser user) {
		this.context = context + "/";
		this.mapMenu = mapMenu;
		this.operators = (Object[]) (mapMenu.values().toArray());
		this.user = user;
	}
	
	public String genarateMenuBar() {
		String html = "";
		if(user != null){
			genarateItem();
//			html = NEW_LINE + generateUserProfile() + NEW_LINE + "<ul class='sidebar-nav'>";
			html = NEW_LINE + "<ul class='sidebar-nav'>";
			if (mapMenuLevel.size() > 0) {
				html += displayOperator(mapMenuLevel);
			}
			html += NEW_LINE + END_UL;
		}
		return html;
	}
	
	public String generateUserProfile(){
		String html = "<div class='row'>"
				+ NEW_LINE + "<div style='width: 280px;'>"
				+ NEW_LINE + "<div class='well well-sm' style='background-color: #EDEDED;'>"
				+ NEW_LINE + "<div class='media userProfile'>"
				+ NEW_LINE + "<a class='pull-left' href='#'>"
				+ NEW_LINE + "<img class='media-object' src='" + context + "images/icon/i_passenger.png'>"
				+ NEW_LINE + "</a>"
				+ NEW_LINE + "<div class='media-body'>"
				+ NEW_LINE + "<span style='color:#120585;'>" + user.getFirstName() + " " + user.getLastName() + "</span><br/>"
				+ NEW_LINE + "<a class='label label-info' href='#'>Profile</a>"
				+ NEW_LINE + "<a class='label label-warning' href='#'>Sign out</a>"
				+ NEW_LINE + "</div>"
				+ NEW_LINE + "</div>"
				+ NEW_LINE + "</div>"
				+ NEW_LINE + "</div>"
				+ NEW_LINE + "</div>";
		return html;
	}
	

	public String genarateItem() {
		String html = "";

		if (operators.length > 0) {
			int minLevel = ((Operator) operators[0]).getMinLevel();
			int maxLevel = ((Operator) operators[0]).getMaxLevel();

			for (int currentLevel = minLevel; currentLevel <= maxLevel; currentLevel++) {

//				for (int index = operators.length - 1; index >= 0; index--) {
				for (int index = 0; index < operators.length; index++) {

					Operator operator = (Operator) operators[index];
					if (operator.getOperatorType().equals(OPERATOR_TYPE_MENU) == false) {
						continue;
					}

					if (operator.getCurrentLevel() == currentLevel) {

						if (operator.getCurrentLevel() == OPERATOR_LEVEL_1) {
							mapMenuLevel.put(operator.getOperatorId(), operator);
						} else {
							String parentOperatorIds = searchParentOperator(operator.getParentId(), operators);
							parentOperatorIds += "," + operator.getOperatorId();
							updateOperator(mapMenu, mapMenuLevel, parentOperatorIds, parentOperatorIds);
						}
					}
				}
			}
		}
		return html;
	}

	public String searchParentOperator(String parentId, Object[] listOperator) {
		String parentIds = "";

		if ((parentId != null) && (parentId.trim().length() > 0)) {

			for (Object operatorObject : listOperator) {

				Operator operator = (Operator) operatorObject;
				if (operator.getOperatorType().equals(OPERATOR_TYPE_MENU) == false) {
					continue;
				}

				if (operator.getOperatorId().equals(parentId)) {
					parentIds = parentIds + ",";
					parentIds = operator.getOperatorId() + parentIds;
					String pppId = searchParentOperator(operator.getParentId(), listOperator);
					if (pppId.length() > 0) {
						parentIds = pppId + "," + parentIds;
					}
				}
			}
			if (parentIds.length() > 0) {
				parentIds = parentIds.substring(0, parentIds.length() - 1);
			}

		}
		return parentIds;
	}

	public void updateOperator(Map<String, Operator> mapMenu, Map<String, Operator> mapMenuLevel, String parentIds, String groupParentIds) {

		if (parentIds.indexOf(",") > -1) {
			String[] operatorIds = parentIds.split(",");
			String parentOperatorId = operatorIds[0];
			String currentOperatorId = operatorIds[1];
			String groupOperatorId = groupParentIds.substring(0, groupParentIds.indexOf(currentOperatorId) + currentOperatorId.length());

			Operator operator = mapMenu.get(currentOperatorId);
			operator.setParentOperatorIds(groupOperatorId);
			if (mapMenuLevel.get(parentOperatorId) != null) {
				mapMenuLevel.get(parentOperatorId).getMapOperator().put(operator.getOperatorId(), operator);
			}

			parentIds = parentIds.substring(parentIds.indexOf(",") + 1, parentIds.length());

			if (mapMenuLevel.get(parentOperatorId) != null) {
				updateOperator(mapMenu, mapMenuLevel.get(parentOperatorId).getMapOperator(), parentIds, groupParentIds);
			}
		}
	}

	public String displayOperator(Map<String, Operator> mapMenuLevel) {

		String html = "";
		if (mapMenuLevel.size() > 0) {

			for (String operatorId : mapMenuLevel.keySet()) {

				Operator operator = mapMenuLevel.get(operatorId);
				if (operator.getVisible().equals("N")) {
					continue;
				}

				if(operator.getCurrentLevel() == 1){
					html += NEW_LINE + DIVIDER;
				}
			
				if (operator.getMapOperator().size() > 0) {
//					html += NEW_LINE + "<li data-toggle='collapse' data-target='#" + operator.getSystemName().replaceAll(" ", "") + "' class='collapsed menu-level" + operator.getCurrentLevel() + "'>";
					html += NEW_LINE + "<li data-toggle='collapse' data-target='#" + operator.getOperatorId().replaceAll(" ", "") + "' class='collapsed menu-level" + operator.getCurrentLevel() + "'>";
					html += NEW_LINE + "<a href='javascript:void(0);' class='dropdown-toggle'>" + operator.getLabel() + "</a>";
					html += NEW_LINE + "</li>";
					html += NEW_LINE + BEGIN_LI;
//					html += NEW_LINE + String.format(BEGIN_UL, String.valueOf(operator.getCurrentLevel() + 1), operator.getSystemName().replaceAll(" ", ""));
					html += NEW_LINE + String.format(BEGIN_UL, String.valueOf(operator.getCurrentLevel() + 1), operator.getOperatorId().replaceAll(" ", ""));
					html += displayOperator(operator.getMapOperator());
					html += NEW_LINE + END_UL;
					html += NEW_LINE + END_LI;
				}else{
					
					String operatorIds = operator.getParentOperatorIds();
					if (operatorIds == null) {
						operatorIds = operator.getOperatorId();
					}
					
					html += NEW_LINE + String.format(LI_FOR_MENU_ITEM, operatorIds, context + operator.getUrl(), operator.getLabel());
				}
			}
		}
		return html;
	}
	
	public static void main(String[] args) {
		try{
			MenuSideBarUtil menu = new MenuSideBarUtil(null, null, null);
			Map<String, Operator> operators = menu.searchDetailOperatorByOperatorId(null, Locale.ENGLISH, null);
			
			menu.context = ServletActionContext.getRequest().getContextPath();
			menu.mapMenu = operators;
			menu.operators = (Object[]) (operators.values().toArray());
			
			String menuStr = menu.genarateMenuBar();
			
			System.out.println(menuStr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ค้นหารายละเอียดเมนูและสิทธิ์
	 * @param operatorIds
	 * @param locale
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchDetailOperatorByOperatorId(String operatorIds, Locale locale, Long siteId) throws Exception {

		CCTConnection conn = null;
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();

		try {
			
			conn = new CCTConnection();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn.setConn(DriverManager.getConnection(
					"jdbc:oracle:thin:@10.100.70.59:1521:ORCL", "APPS_IMM",
					"imm2015"));
			
			Operator operatorLevel = searchOperatorLevel(conn, operatorIds, locale, siteId);
			mapOperator = searchOperator(conn, operatorIds, locale, siteId);
			for (String key : mapOperator.keySet()) {
				mapOperator.get(key).setMinLevel(operatorLevel.getMinLevel());
				mapOperator.get(key).setMaxLevel(operatorLevel.getMaxLevel());
				break;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			CCTConnectionUtil.close(conn);
		}

		return mapOperator;
	}
	
	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MIN, MAX MENU LEVEL)
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Operator searchOperatorLevel(CCTConnection conn, String operatorIds,  Locale locale, Long siteId) throws Exception {

		Operator operatorLevel = null;


		String sql = "SELECT MIN(SO.OPERATOR_LEVEL) as MIN_LEVEL, MAX(SO.OPERATOR_LEVEL) as MAX_LEVEL"
					+ "\n FROM SEC_OPERATOR SO"
					+ "\n WHERE SO.ACTIVE = 'Y'"
					+ "\n AND SO.HELP = 'Y'"
				    + "\n AND SO.REPORT_TYPE IN('P','R')"
					+ "\n AND SO.SITE_ID LIKE '%[' || '3' || ']%'";
		
		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
			 operatorLevel = new Operator();
			 operatorLevel.setMinLevel(rst.getInt("MIN_LEVEL"));
			 operatorLevel.setMaxLevel(rst.getInt("MAX_LEVEL"));
			}

		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return operatorLevel;
	}
	
	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MENU)
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchOperator(CCTConnection conn, String operatorIds,  Locale locale, Long siteId) throws Exception {
		Map<String, Operator> mapOperator = new LinkedHashMap<>();
		
		String sql = "SELECT SO.OPERATOR_ID, SO.PARENT_ID"
			  	+ "\n, SO.LABEL_TH, SO.LABEL_EN"
			  	+ "\n, SO.OPERATOR_TYPE, SO.OPERATOR_LEVEL, SO.URL, SO.ACTIVE, SO.HELP"
				+ "\n FROM SEC_OPERATOR SO"
				+ "\n WHERE SO.ACTIVE = 'Y'"
			    + "\n AND SO.HELP = 'Y'"
			    + "\n AND SO.REPORT_TYPE IN('P','R')"
				+ "\n AND SO.SITE_ID LIKE '%[' || '3' || ']%'"
				+ "\n ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC";
		
		Statement stmt = null;
		ResultSet rst = null;

		try {
			 stmt = conn.createStatement();
			 rst = stmt.executeQuery(sql);
			 while (rst.next()) {
			 Operator operator = new Operator();
			 operator.setOperatorId(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
			 if (operator.getOperatorId().equals("0")) {
			 operator.setOperatorId(null);
			 continue;
			 }

			 operator.setOperatorType(rst.getString("OPERATOR_TYPE"));
			 operator.setUrl(rst.getString("URL"));
			 operator.setVisible(GlobalVariable.FLAG_ACTIVE);

			 // extend from com.cct.domain.Tree สำหรับวาด tree
			 operator.setParentId(StringUtil.nullToString(rst.getString("PARENT_ID")));
			 if (operator.getParentId().equals("0")) {
			 operator.setParentId(null);
			 }
			 operator.setCurrentId(operator.getOperatorId());

			 operator.setLabel(rst.getString(SQLUtil.getColumnByLocale("LABEL", locale)));
			 operator.setSystemName(rst.getString("LABEL_EN"));
			 
			 operator.setCurrentLevel(rst.getInt("OPERATOR_LEVEL"));
			 if (operator.getCurrentLevel() <= 0) {
			 continue;
			 }
			 mapOperator.put(operator.getOperatorId(), operator);
			 }

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return mapOperator;
	}
}
