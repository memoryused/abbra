searchUserLogin{
	SELECT SU.USER_ID,  SU.PREFIX_ID
    , SU.FORENAME, SU.SURNAME
    , CONCAT(SP.PREFIX_NAME, SU.FORENAME, '  ',SU.SURNAME) as FULLNAME
    , SU.ORGANIZATION_ID , SU.POSITION_NAME, SU.EMAIL
    , date_format(SU.START_DATE, '%d/%m/%Y %H:%i:%S') as START_DATE
    , date_format(SU.END_DATE, '%d/%m/%Y %H:%i:%S') as END_DATE
    , SU.USERNAME, SU.PASSWORD
    , date_format(SU.PASSWORD_DATE, '%d/%m/%Y %H:%i:%S') as PASSWORD_DATE
    , date_format(SU.PASSWORD_EXP, '%d/%m/%Y %H:%i:%S') as PASSWORD_EXP
    , SU.LOCK_STATUS, SU.RESET_PASSWORD_STATUS
    , date_format(SU.LOCK_DATE, '%d/%m/%Y %H:%i:%S') as LOCK_DATE
    , SU.ACTIVE
	FROM [OC].sec_user SU
	 LEFT JOIN [OC].m_prefix SP ON SU.PREFIX_ID = SP.PREFIX_ID
   WHERE SU.USERNAME = %s
}

insertLoginWrong{
	INSERT INTO [OC].sec_login_wrong (
	   USERNAME, PASSWORD,
	   LOCK_DATE, ACTIVE, create_date,
	   create_user)
	VALUES ( 
	 %s,
	 %s,
	 sysdate(),
	 'Y',
	 sysdate(),
	 %s)
}

searchCountLoginWrong{
	SELECT COUNT(*) WRONG_NUM FROM [OC].sec_login_wrong
	WHERE create_user = %s
	AND ACTIVE = 'Y' 
    AND LOCK_DATE IS NOT NULL
	AND create_date >= sysdate() - interval %s MINUTE
}

updatelockUser{
	UPDATE [OC].sec_user
	SET LOCK_STATUS = %s       /*-- 1= ใช้งานปกติ, 2=สถานะ Lock*/
	, LOCK_DATE = sysdate() 		
	, update_user = %s		
	, update_date = sysdate()
	WHERE USER_ID = %s
}

calculateLockedMinutes{
	select 
	case WHEN (DATE_ADD(MAX(create_date), INTERVAL 5 MINUTE) < sysdate()) 
	  	THEN 1 
	  	ELSE 2 
	END as LOCKED_STATUS
	from [OC].sec_login_wrong
	where ACTIVE = 'Y'
	and create_user = %s 
	and LOCK_DATE IS NOT NULL 
}

updateLoginWrong{
	UPDATE [OC].sec_login_wrong
	SET ACTIVE  = 'N'
	WHERE create_user = %s
	AND ACTIVE  = 'Y'
}

searchOperatorId{
	SELECT DISTINCT OPERATOR_ID
	FROM
	(
	     SELECT SU.OPERATOR_ID
	     FROM [OC].sec_user_operator SU, [OC].sec_operator SO 
	     WHERE  SU.OPERATOR_ID = SO.OPERATOR_ID
	     AND SU.USER_ID = %s     
	
	     UNION ALL
	
	     SELECT OPERATOR_ID
	     FROM [OC].sec_group_operator
	     WHERE GROUP_ID IN
	     (
	          SELECT G.GROUP_ID
	          FROM [OC].sec_user_group G, [OC].sec_user U, [OC].sec_group S
	            WHERE  U.USER_ID =G.USER_ID
	            AND S.GROUP_ID = G.GROUP_ID 
	            AND S.ACTIVE = 'Y'
	            AND U.USER_ID = %s
	      )
	)A
}

searchOperatorLevel{
	SELECT MIN(SO.OPERATOR_LEVEL) as MIN_LEVEL, MAX(SO.OPERATOR_LEVEL) as MAX_LEVEL
	FROM [OC].sec_operator SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('P','R')
	AND SO.OPERATOR_ID IN (%s)
}

searchOperator{
	SELECT SO.OPERATOR_ID, SO.PARENT_ID
  	, SO.LABEL, SO.OPERATOR_TYPE, SO.OPERATOR_LEVEL, SO.URL, SO.ACTIVE
	FROM [OC].sec_operator SO
	WHERE SO.ACTIVE = 'Y'
    AND SO.REPORT_TYPE IN('P','R')
 	AND SO.OPERATOR_ID IN (%s)
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

