package com.sit.app.core.security.authorization.domain;

import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import util.log.LogUtil;

public enum PFCode {
	// ---------: Program code and Function code :----------
	PRODUCTHOME("10300100", "{'" + FunctionType.SEARCH + "':'10300101', '" + FunctionType.EXPORT + "':'10300110'}"),
	PRODUCTINFO("10300200", "{'" + FunctionType.SEARCH + "':'10300201', '" 
			+ FunctionType.ADD + "':'10300202','" 
			+ FunctionType.EDIT + "':'10300203','" 
			+ FunctionType.VIEW + "':'10300204','" 
			+ FunctionType.DELETE + "':'10300205','" 
			+ FunctionType.CHANGE + "':'10300206','"
			+ FunctionType.EXPORT + "':'10300210'}"),
	PRODUCT("10200100", "{'" + FunctionType.SEARCH + "':'10200101', '" 
			+ FunctionType.ADD + "':'10200102','" 
			+ FunctionType.EDIT + "':'10200103','" 
			+ FunctionType.VIEW + "':'10200104','" 
			+ FunctionType.DELETE + "':'10200105','" 
			+ FunctionType.CHANGE + "':'10200106'}"
			),
	VENDOR("10200200", "{'" + FunctionType.SEARCH + "':'10200201', '" 
			+ FunctionType.ADD + "':'10200202','" 
			+ FunctionType.EDIT + "':'10200203','" 
			+ FunctionType.VIEW + "':'10200204','" 
			+ FunctionType.DELETE + "':'10200205','" 
			+ FunctionType.CHANGE + "':'10200206'}"
			),
	PRODUCTMAPPING("10200300", "{'" + FunctionType.SEARCH + "':'10200301', '" 
			+ FunctionType.ADD + "':'10200302','" 
			+ FunctionType.EDIT + "':'10200303','" 
			+ FunctionType.VIEW + "':'10200304','" 
			+ FunctionType.CHANGE + "':'10200306'}"
			),
	MEMBER("10100400", "{'" + FunctionType.SEARCH + "':'10100401', '" 
			+ FunctionType.ADD + "':'10100402','" 
			+ FunctionType.EDIT + "':'10100403','" 
			+ FunctionType.VIEW + "':'10100404','" 
			+ FunctionType.RESETPASSWORD + "':'10100408','" 
			+ FunctionType.CHANGE + "':'10100406'}"
			),
	MEMBER_GROUP("10100500", "{'" + FunctionType.SEARCH + "':'10100501', '" 
			+ FunctionType.ADD + "':'10100502','" 
			+ FunctionType.EDIT + "':'10100503','" 
			+ FunctionType.VIEW + "':'10100504','" 
			+ FunctionType.DELETE + "':'10100505'}" 
			)
	;
	// ---------: Program code and Function code :-----------
	// ---------: Fucntion code :-----------
	private String searchFunction;
	private String addFunction;
	private String editFunction;
	private String viewFunction;
	private String deleteFunction;
	private String changeFunction;
	private String configFunction;
	private String importFunction;
	private String exportFunction;
	private String printFunction;
	private String resetPasswordFunction;

	private String confirmAddFunction;
	private String confirmEditFunction;
	// ---------: Fucntion code :-----------

	private String programCode;
	private Map<String, String> funcs = null;

	@SuppressWarnings("unchecked")
	private PFCode(String programCode, String functions) {
		this.programCode = programCode;
		try {
			this.funcs = (Map<String, String>) JSONUtil.deserialize(functions);
			searchFunction = funcs.get(FunctionType.SEARCH);
			addFunction = funcs.get(FunctionType.ADD);
			editFunction = funcs.get(FunctionType.EDIT);
			viewFunction = funcs.get(FunctionType.VIEW);
			deleteFunction = funcs.get(FunctionType.DELETE);
			changeFunction = funcs.get(FunctionType.CHANGE);
			configFunction = funcs.get(FunctionType.CONFIG);
			importFunction = funcs.get(FunctionType.IMPORT);
			exportFunction = funcs.get(FunctionType.EXPORT);
			printFunction = funcs.get(FunctionType.PRINT);
			resetPasswordFunction = funcs.get(FunctionType.RESETPASSWORD);

			confirmAddFunction = funcs.get(FunctionType.CONFIRM_ADD);
			confirmEditFunction = funcs.get(FunctionType.CONFIRM_EDIT);
		} catch (Exception e) {
			LogUtil.SEC.error("", e);
		}
	}

	public String getProgramCode() {
		return programCode;
	}

	public Map<String, String> getFuncs() {
		return funcs;
	}

	public String getFunctionCode(String programCode, String functionType) {
		String functionCode = null;
		for (PFCode code : PFCode.values()) {
			if (code.getProgramCode().equals(programCode)) {
				functionCode = code.getFuncs().get(functionType);
				break;
			}
		}
		return functionCode;
	}

	public String getSearchFunction() {
		return searchFunction;
	}

	public String getSearchFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.SEARCH);
	}

	public String getAddFunction() {
		return addFunction;
	}

	public String getAddFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.ADD);
	}

	public String getEditFunction() {
		return editFunction;
	}

	public String getEditFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.EDIT);
	}

	public String getViewFunction() {
		return viewFunction;
	}

	public String getViewFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.VIEW);
	}

	public String getDeleteFunction() {
		return deleteFunction;
	}

	public String getDeleteFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.DELETE);
	}

	public String getChangeFunction() {
		return changeFunction;
	}

	public String getChangeFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CHANGE);
	}

	public String getConfigFunction() {
		return configFunction;
	}

	public String getConfigFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CONFIG);
	}

	public String getImportFunction() {
		return importFunction;
	}

	public String getImportFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.IMPORT);
	}

	public String getExportFunction() {
		return exportFunction;
	}

	public String getExportFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.EXPORT);
	}

	public String getPrintFunction() {
		return printFunction;
	}

	public String getPrintFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.PRINT);
	}

	public String getResetPasswordFunction() {
		return resetPasswordFunction;
	}

	public String getResetPasswordFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.RESETPASSWORD);
	}

	public String getConfirmAddFunction() {
		return confirmAddFunction;
	}

	public String getConfirmAddFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CONFIRM_ADD);
	}
	
	public String getConfirmEditFunction() {
		return confirmEditFunction;
	}

	public String getConfirmEditFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CONFIRM_EDIT);
	}
	
}