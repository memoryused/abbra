searchCount{
	select count(1) as TOT
	FROM [OC].sec_user su
	left outer join [OC].m_prefix mp on su.PREFIX_ID = mp.prefix_id
	left outer join [OC].m_organization mo on su.ORGANIZATION_ID = mo.organization_id
	where su.deleted = 'N'
	and su.USER_CODE like CONCAT('%', %s, '%')
	and su.USERNAME like CONCAT('%', %s, '%')
	and su.FORENAME like CONCAT('%', %s, '%')
	and su.SURNAME like CONCAT('%', %s, '%')
	and su.ORGANIZATION_ID = %s
	and su.POSITION_NAME like CONCAT('%', %s, '%')
	and su.LOCK_STATUS = %s
	and su.ACTIVE = %s
}

search{
	SELECT su.USER_ID, su.USER_CODE, CONCAT(mp.prefix_name, " ", su.FORENAME, " ", su.SURNAME) as fullname
	    , mo.organization_id, mo.organization_name, su.POSITION_NAME
		, su.EMAIL, su.CELL_PHONE, su.USERNAME
		, su.LOCK_STATUS, su.ACTIVE 
	FROM [OC].sec_user su
	left outer join [OC].m_prefix mp on su.PREFIX_ID = mp.prefix_id
	left outer join [OC].m_organization mo on su.ORGANIZATION_ID = mo.organization_id
	where su.deleted = 'N'
	and su.USER_CODE like CONCAT('%', %s, '%')
	and su.USERNAME like CONCAT('%', %s, '%')
	and su.FORENAME like CONCAT('%', %s, '%')
	and su.SURNAME like CONCAT('%', %s, '%')
	and su.ORGANIZATION_ID = %s
	and su.POSITION_NAME like CONCAT('%', %s, '%')
	and su.LOCK_STATUS = %s
	and su.ACTIVE = %s
	order by 
	%s
	limit %s,
	%s
}

setActiveStatus{
	update [OC].sec_user
	set active = %s
	, update_date = sysdate()
	, update_user = %s 
	where USER_ID IN (%s)
}

searchById{
	select 
	su.USER_ID
	, su.USER_CODE
	, su.PREFIX_ID, mp.prefix_name
	, su.SURNAME, su.FORENAME
	, su.ORGANIZATION_ID, mo.organization_name
	, su.POSITION_NAME
	, su.EMAIL, su.CELL_PHONE
	, DATE_FORMAT(su.START_DATE, '%d/%m/%Y') as START_DATE
	, DATE_FORMAT(su.END_DATE, '%d/%m/%Y') as END_DATE
	, su.USERNAME, su.PASSWORD
	, su.PASSWORD_DATE, su.PASSWORD_EXP
	, su.QUESTION, su.ANSWER
	, su.LOCK_STATUS, su.RESET_PASSWORD_STATUS
	, su.LOCK_DATE, su.ACTIVE
	, su.create_date, su.create_user
	, su.update_date, su.update_user
	from  [OC].sec_user su
	inner join [OC].m_prefix mp on su.PREFIX_ID = mp.prefix_id
	left join [OC].m_organization mo on su.ORGANIZATION_ID = mo.organization_id 
	where su.USER_ID = %s
}

/* หาค่า Min-Max OPERATOR_LEVEL */
searchLevel{
	SELECT
		MIN(SO.OPERATOR_LEVEL) AS MIN_OPERATOR_LEVEL,
		MAX(SO.OPERATOR_LEVEL) AS MAX_OPERATOR_LEVEL
		FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN(%s)
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_USER_OPERATOR  WHERE USER_ID = %s)
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/*----------------------------------------------------------------------------------------------------------
SQL : แสดงรายการข้อมูลสิทธิ์โปรแกรมหรือรายงานอ้างอิง_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchProgramByUserId{
	SELECT SO.OPERATOR_ID,
	SO.PARENT_ID,
	SO.LABEL,
	SO.OPERATOR_TYPE,
	SO.OPERATOR_LEVEL,
	SO.URL,
	SO.ACTIVE
	FROM [OC].SEC_OPERATOR SO
	WHERE SO.ACTIVE = 'Y'
	AND SO.REPORT_TYPE IN(%s)
	/* เงื่อนไข กรณีเรียกใช้จากข้อมูลผู้ใช้ */
	AND SO.OPERATOR_ID IN (SELECT OPERATOR_ID FROM [OC].SEC_USER_OPERATOR  WHERE USER_ID = %s)
	ORDER BY SO.OPERATOR_LEVEL ASC, SO.LIST_NO ASC
}

/*----------------------------------------------------------------------------------------------------------
SQL : แสดงรายการข้อมูลกลุ่มผู้ใช้อ้างอิง_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchGroupByUserId{
	SELECT
	U.GROUP_ID,
	U.GROUP_CODE,
	U.GROUP_NAME,
	U.ACTIVE
	FROM [OC].SEC_GROUP U
	WHERE U.ACTIVE = 'Y'
	/*อ้างอิงกับผู้ใช้  &USER_ID รหัสอ้างอิงข้อมูลผู้ใช้*/
	AND U.GROUP_ID IN (SELECT GROUP_ID FROM [OC].SEC_USER_GROUP WHERE USER_ID = %s)     /* &USER_ID ข้อมูลที่เลือก */
    ORDER BY U.GROUP_CODE
}

checkDup{
	SELECT COUNT(USER_ID) AS TOT  /* ถ้าค่า <> 0 แสดงว่าซ้ำ */
	FROM [OC].SEC_USER
	WHERE USER_ID IS NOT NULL
	AND (
		UPPER(USERNAME) = UPPER(%s)		/* ตรวจสอบจากรหัสผู้ใช้ไม่ซ้ำ */
	)
	AND USER_ID <> %s /* รหัสอ้างอิงข้อมูลผู้ใช้ที่เลือกแก้ไข */
}

getUserSEQ{
	SELECT (MAX(USER_ID)+1) as USER_ID_SEQ FROM [OC].SEC_USER
}

insertMember{
	INSERT INTO [OC].sec_user( 
	USER_ID
	, USER_CODE
	, PREFIX_ID
	, SURNAME
	, FORENAME
	, ORGANIZATION_ID
	, POSITION_NAME
	, EMAIL
	, CELL_PHONE
	, START_DATE
	, END_DATE
	, USERNAME
	, PASSWORD
	, PASSWORD_DATE
	, PASSWORD_EXP
	, QUESTION
	, ANSWER
	, LOCK_STATUS
	, RESET_PASSWORD_STATUS
	, LOCK_DATE
	, ACTIVE
	, create_date, create_user
	, update_date, update_user
	, deleted
	) 
	VALUES ( 
	%s, 
	%s, 
	%s, 
	%s, 
	%s, 
	%s, 
	%s, 
	%s, 
	%s, 
	STR_TO_DATE(%s, '%d/%m/%Y'), 
	STR_TO_DATE(%s, '%d/%m/%Y'), 
	%s, 
	%s,		/* รหัสผ่านที่ผ่านการ Encrypt แล้ว */
	sysdate(), 	/*ครั้งแรกจะเป็นวันที่ปัจจุบัน เพื่อให้ผู้ใช้เข้ามาเปลี่ยน password เป็นของตัวเอง*/
	null, 
	null, 
	null, 
	%s, 
	null, 
	null, 
	%s, 
	sysdate(), %s,
	null, null, 
	'N')
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้_SQL
Description : วนลูปบันทึก
----------------------------------------------------------------------------------------------------------*/
insertUserOperator{
	INSERT INTO [OC].SEC_USER_OPERATOR (
		USER_ID,
		OPERATOR_ID,
		CREATE_USER,
		CREATE_DATE
	)
	VALUES (
		%s,  			/* รหัสอ้างอิงผู้ใช้ ซึ่งค่าจะได้จาก Select User SEQ_SQL */
		%s, 			/* OPERATOR_ID ในหน้าจอ */
		%s, 			/* รหัสอ้างอิงผู้ใช้ที่สร้างข้อมูล รับค่าจาก Login */
		sysdate() 		/* วันที่สร้างข้อมูล */
	)
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกเพิ่มรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้_SQL
Description : วนลูปบันทึก
----------------------------------------------------------------------------------------------------------*/
insertUserGroup{
	INSERT INTO [OC].SEC_USER_GROUP (
		GROUP_ID,
		USER_ID,
		CREATE_USER,
		CREATE_DATE
	)
	VALUES (
		%s, 		/* &GROUP_ID ในหน้าจอ */
		%s,  		/* รหัสอ้างอิงผู้ใช้ ซึ่งค่าจะได้จาก Select User SEQ_SQL */
		%s, 		/* รหัสอ้างอิงผู้ใช้ที่สร้างข้อมูล รับค่าจาก Login */
		sysdate() 		/* วันที่สร้างข้อมูล */
	)
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
setMember{
	UPDATE [OC].SEC_USER
	SET  USER_CODE               = %s,  	/*รหัสเจ้าหน้าที่*/
		   PREFIX_ID             = %s,  		/*รหัสอ้างอิงคำนำหน้าชื่อได้จาก Combo*/
		   SURNAME               = %s,  		/*นามสกุล (ภาษาไทย)*/
		   FORENAME              = %s,  	/*ชื่อ (ภาษาไทย)*/
		   ORGANIZATION_ID       = %s,    /*รหัสอ้างอิงหน่วยงานได้จาก Combo*/
		   POSITION_NAME         = %s,    /*ตำแหน่ง*/
		   EMAIL                 = %s,    		/*อีเมล*/
		   CELL_PHONE            = %s,     /*หมายเลขโทรศัพท์ 1*/
		   START_DATE            = STR_TO_DATE(%s, '%d/%m/%Y'), 	/*วันที่อนุญาตให้ใช้งานระบบ ตั้งแต่*/
		   END_DATE              = STR_TO_DATE(%s, '%d/%m/%Y'),   	/*สิ้นสุด*/
		   USERNAME              = %s, 	/*ชื่อผู้ใช้*/

		   /* ---------- กรณีที่มีการกด Reset Password ในหน้าจอแก้ไข ---------- */

		   PASSWORD              = %s, 	/*รหัสผ่านที่ผ่านการ Encrypt แล้ว*/ PASSWORD_DATE         = sysdate(),  	/*วันที่เปลี่ยนรหัสผ่านล่าสุด*/

		   /* ----------------------------------------- */
		   ACTIVE                = %s,   	/*สถานะใช้งาน*/
		   LOCK_STATUS           = %s,  /*สถานะล็อกอิน*/
		   UPDATE_USER           = %s,    /*รหัสอ้างอิงผู้ใช้ที่แก้ไขข้อมูลล่าสุด รับค่าจาก Login*/
		   UPDATE_DATE           = sysdate()   	/*วันที่แก้ไขข้อมูลล่าสุด*/
	WHERE  USER_ID             	 = %s
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกลบรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
deleteOperator{
	DELETE FROM [OC].SEC_USER_OPERATOR
	WHERE USER_ID = %s /* รหัสอ้างอิงข้อมูลผู้ใช้ที่เลือกแก้ไข */
}

/*----------------------------------------------------------------------------------------------------------
SQL : บันทึกลบรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
deleteGroup{
	DELETE FROM [OC].SEC_USER_GROUP
	WHERE USER_ID = %s /* รหัสอ้างอิงข้อมูลผู้ใช้ที่เลือกแก้ไข */
}

deleteMember{
	update [OC].sec_user
	set deleted = 'Y'
	, update_date = sysdate()
	, update_user = %s 
	where USER_ID IN (%s)
}