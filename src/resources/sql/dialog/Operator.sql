/* หาค่า Min-Max OPERATOR_LEVEL */
searchLevelProgram{
	SELECT 
	MIN(SO.OPERATOR_LEVEL) AS MIN_OPERATOR_LEVEL,
	MAX(SO.OPERATOR_LEVEL) AS MAX_OPERATOR_LEVEL
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('P')
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/* Popup : Menu */
/*----------------------------------------------------------------------------------------------------------
SQL : Popup_ข้อมูลเมนู_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchOperatorProgram{
	SELECT SO.OPERATOR_ID, 
	SO.PARENT_ID, 
	SO.LABEL,  
	SO.OPERATOR_TYPE, 
	SO.OPERATOR_LEVEL, 
	SO.URL, 
	SO.ACTIVE
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('P') 
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/* หาค่า Min-Max OPERATOR_LEVEL */
searchLevelReport{
	SELECT 
	MIN(SO.OPERATOR_LEVEL) AS MIN_OPERATOR_LEVEL,
	MAX(SO.OPERATOR_LEVEL) AS MAX_OPERATOR_LEVEL
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('R')
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/* Popup : Menu */
/*----------------------------------------------------------------------------------------------------------
SQL : Popup_ข้อมูลเมนู_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchOperatorReport{
	SELECT SO.OPERATOR_ID, 
	SO.PARENT_ID, 
	SO.LABEL,  
	SO.OPERATOR_TYPE, 
	SO.OPERATOR_LEVEL, 
	SO.URL, 
	SO.ACTIVE
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('R') 
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}