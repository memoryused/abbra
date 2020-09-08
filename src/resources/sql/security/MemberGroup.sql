/*----------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลที่ค้นพบทั้งหมด_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchCountGroup{
  	SELECT COUNT(*) AS TOT
	FROM [OC].SEC_GROUP U
	WHERE 1=1
	  AND U.ACTIVE = %s
	  AND UPPER(U.GROUP_CODE) LIKE UPPER(CONCAT('%', %s, '%'))
	  AND UPPER(U.GROUP_NAME) LIKE UPPER(CONCAT('%', %s, '%'))
}

/*----------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูล_SQL
Description : &f_OrderBy default : U.GROUP_CODE
----------------------------------------------------------------------------------------------------------*/
searchGroup{
  SELECT
	U.GROUP_ID,
	U.GROUP_CODE,
	U.GROUP_NAME,
	U.ACTIVE
  FROM [OC].SEC_GROUP U
  WHERE U.deleted = 'N'
	AND U.ACTIVE = %s
	AND UPPER(U.GROUP_CODE) LIKE UPPER(CONCAT('%', %s, '%'))
	AND UPPER(U.GROUP_NAME) LIKE UPPER(CONCAT('%', %s, '%'))
  order by 
	%s
	limit %s,
	%s
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกการเปลี่ยนสถานะ_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
setActiveStatus{
	UPDATE [OC].SEC_GROUP
	SET ACTIVE 		= %s, 		/* สถานะใช้งานที่เลือกเปลี่ยนสถานะ จากหน้าจอค้นหา (ใช้งาน = 'Y' , ยกเลิก = 'N') */
	UPDATE_DATE 	= sysdate(),  /* วันที่แก้ไขข้อมูล */
	UPDATE_USER 	= %s		/* IP เครื่องลูกข่ายที่แก้ไขข้อมูลล่าสุด รับค่าจาก Login */
	WHERE GROUP_ID IN (%s)
}

/*----------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบข้อมูลเพิ่มซ้ำ_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
checkDup{
	SELECT COUNT(GROUP_ID) AS TOT  /* ถ้าค่า <> 0 แสดงว่าซ้ำ */
	FROM [OC].SEC_GROUP
	WHERE (UPPER(GROUP_CODE) = UPPER(%s) /* ตรวจสอบจากรหัสกลุ่มผู้ใช้ */
		OR UPPER(GROUP_NAME) = UPPER(%s) /* ตรวจสอบจากชื่อกลุ่มผู้ใช้ */
	)
	AND GROUP_ID <> %s /* รหัสอ้างอิงข้อมูลกลุ่มผู้ใช้ที่เลือกแก้ไข */
}

/*----------------------------------------------------------------------------------------------------------
SQL : Select User_Group_SEQ_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
getUserSEQ{
	SELECT (MAX(GROUP_ID)+1) as GROUP_ID_SEQ FROM [OC].SEC_GROUP
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกเพิ่มกลุ่มผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
insertGroup{
	INSERT INTO [OC].sec_group(
		GROUP_ID
		, GROUP_CODE
		, GROUP_NAME
		, active
		, create_date
		, create_user
		, update_date
		, update_user
		, deleted
	) VALUES ( 
		%s,		/*seq*/
		%s,
		%s,
		%s,
		sysdate(),
		%s,
		null,
		null,
		'N'
	)
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้_SQL
Description : วนลูปบันทึก
----------------------------------------------------------------------------------------------------------*/
insertGroupOperator{
	INSERT INTO [OC].sec_group_operator(
		OPERATOR_ID
		, GROUP_ID
		, create_date
		, create_user
	) VALUES (
		%s, 
		%s, 
		sysdate(), 
		%s
	)
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกเพิ่มรายการผู้ใช้ที่อ้างอิงกับกลุ่มผู้ใช้_SQL
Description : วนลูปบันทึก
----------------------------------------------------------------------------------------------------------*/
insertGroupUser{
	INSERT INTO [OC].sec_user_group(
		GROUP_ID
		, USER_ID
		, create_date
		, create_user
	) VALUES (
		%s, 
		%s, 
		sysdate(), 
		%s
	)
}

/*----------------------------------------------------------------------------------------------------------
SQL : แสดงข้อมูลกลุ่มผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchByIdGroup{
	SELECT GROUP_ID, GROUP_CODE, GROUP_NAME, active, create_date, create_user, update_date, update_user, deleted 
	FROM [OC].sec_group
	where GROUP_ID = %s 	/* &GROUP_ID ข้อมูลที่เลือก */
}

/*----------------------------------------------------------------------------------------------------------
SQL : แสดงรายการข้อมูลสิทธิ์โปรแกรมหรืออ้างอิง_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
/* หาค่า Min-Max OPERATOR_LEVEL */
searchProgramLevel{
	SELECT
	MIN(SO.OPERATOR_LEVEL) AS MIN_OPERATOR_LEVEL,
	MAX(SO.OPERATOR_LEVEL) AS MAX_OPERATOR_LEVEL
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN('P')
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลกลุ่มผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_GROUP_OPERATOR  WHERE GROUP_ID = %s)
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_USER_OPERATOR  WHERE USER_ID = %s)
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/* แสดงรายการข้อมูลสิทธิ์โปรแกรมหรือรายงานอ้างอิง_SQL */
searchProgramByGroupId{
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
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลกลุ่มผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_GROUP_OPERATOR  WHERE GROUP_ID = %s)
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_USER_OPERATOR  WHERE USER_ID = %s)
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/*----------------------------------------------------------------------------------------------------------
SQL : แสดงรายการข้อมูลผู้ใช้อ้างอิง_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchUserByGroupId{
	SELECT
	U.USER_ID,
	U.USER_CODE,
	U.USERNAME,
	CONCAT(P.PREFIX_NAME, " ", U.FORENAME, " ", U.SURNAME) as fullname,
	U.ORGANIZATION_ID, O.ORGANIZATION_NAME,
	U.POSITION_NAME,
	U.LOCK_STATUS,
	U.ACTIVE
	FROM [OC].SEC_USER U
	LEFT OUTER JOIN [OC].M_ORGANIZATION O ON (U.ORGANIZATION_ID = O.ORGANIZATION_ID)
	LEFT OUTER JOIN [OC].M_PREFIX P ON (U.PREFIX_ID = P.PREFIX_ID)
	WHERE U.USER_ID IN (SELECT USER_ID FROM [OC].SEC_USER_GROUP WHERE GROUP_ID = %s) 	/* &GROUP_ID ข้อมูลที่เลือก */
	AND U.USER_ID > 0
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขกลุ่มผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
updateGroupUser{
	UPDATE [OC].sec_group 
	SET GROUP_CODE = %s, 
	    GROUP_NAME = %s, 
	    active = %s, 
	    update_date = sysdate(), 
	    update_user = %s 
	WHERE GROUP_ID = %s	
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกลบรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
deleteOperator{
	DELETE FROM [OC].SEC_GROUP_OPERATOR
	WHERE GROUP_ID = %s /* รหัสอ้างอิงข้อมูลกลุ่มผู้ใช้ที่เลือกแก้ไข */
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกลบรายการผู้ใช้ที่อ้างอิงกับกลุ่มผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
deleteUser{
	DELETE FROM [OC].SEC_USER_GROUP
	WHERE GROUP_ID = %s /* รหัสอ้างอิงข้อมูลกลุ่มผู้ใช้ที่เลือกแก้ไข */
}