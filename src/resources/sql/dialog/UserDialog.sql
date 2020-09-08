/*----------------------------------------------------------------------------------------------------------
SQL : Popup_User_นับจำนวนข้อมูลที่ค้นหาตามเงื่อนไข_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
countUser{
	SELECT COUNT(*) AS TOT
	FROM [OC].SEC_USER U
	LEFT OUTER JOIN [OC].M_ORGANIZATION O ON (U.ORGANIZATION_ID = O.ORGANIZATION_ID AND O.ACTIVE = 'Y')
	LEFT OUTER JOIN [OC].M_PREFIX P ON (U.PREFIX_ID = P.PREFIX_ID AND P.ACTIVE = 'Y')
	WHERE 1 = 1
	AND U.USER_ID > 0
	AND UPPER(U.USER_CODE) LIKE UPPER(CONCAT('%', %s, '%')) /* รหัสพนักงาน */
	AND UPPER(U.USERNAME) LIKE UPPER(CONCAT('%', %s, '%'))  /* รหัสผู้ใช้ */
	AND UPPER(U.FORENAME) LIKE UPPER(CONCAT('%', %s, '%'))
	AND UPPER(U.SURNAME) LIKE UPPER(CONCAT('%', %s, '%'))
	AND U.ORGANIZATION_ID = %s   /* ID หน่วยงาน */
	AND U.LOCK_STATUS = %s       /* สถานะรหัสผ่าน */
	AND U.ACTIVE = 'Y'            /* Fix สถานะใช้งาน */
	AND U.USER_ID NOT IN (%s)
	AND U.USER_ID IN (%s)
}

/*----------------------------------------------------------------------------------------------------------
SQL : Popup_User_ค้นหาข้อมูลตามเงื่อนไข_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchUser{
	SELECT 
	U.USER_ID,
	U.USER_CODE,
	U.USERNAME,
	CONCAT(P.PREFIX_NAME, " ", U.FORENAME, " ", U.SURNAME) as fullname ,
	O.ORGANIZATION_NAME,
	U.POSITION_NAME,
	U.LOCK_STATUS,
	U.ACTIVE,
	U.EMAIL
	FROM [OC].SEC_USER U
	LEFT OUTER JOIN [OC].M_ORGANIZATION O ON (U.ORGANIZATION_ID = O.ORGANIZATION_ID AND O.ACTIVE = 'Y')
	LEFT OUTER JOIN [OC].M_PREFIX P ON (U.PREFIX_ID = P.PREFIX_ID AND P.ACTIVE = 'Y')
	WHERE 1 = 1
	AND U.USER_ID > 0
	AND UPPER(U.USER_CODE) LIKE UPPER(CONCAT('%', %s, '%')) /* รหัสพนักงาน */
	AND UPPER(U.USERNAME) LIKE UPPER(CONCAT('%', %s, '%'))  /* รหัสผู้ใช้ */
	AND UPPER(U.FORENAME) LIKE UPPER(CONCAT('%', %s, '%'))
	AND UPPER(U.SURNAME) LIKE UPPER(CONCAT('%', %s, '%'))
	AND U.ORGANIZATION_ID = %s   /* ID หน่วยงาน */
	AND U.LOCK_STATUS = %s       /* สถานะรหัสผ่าน */
	AND U.ACTIVE = 'Y'            /* Fix สถานะใช้งาน */
	AND U.USER_ID NOT IN (%s)
	AND U.USER_ID IN (%s)
}

