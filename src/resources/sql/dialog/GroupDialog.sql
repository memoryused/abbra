/*----------------------------------------------------------------------------------------------------------
SQL : Popup_UserGroup_นับจำนวนข้อมูลที่ค้นหาตามเงื่อนไข_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
countGroup{
	SELECT COUNT(*) AS TOT
	FROM [OC].sec_group U
	WHERE 1=1
	AND U.active = %s  /* สถานะใช้งาน */
	AND UPPER(U.GROUP_CODE) LIKE UPPER(CONCAT('%', %s, '%')) /* รหัสกลุ่มผู้ใช้ */
	AND UPPER(U.GROUP_NAME) LIKE UPPER(CONCAT('%', %s, '%')) /* ชื่อกลุ่มผู้ใช้ */
	AND U.GROUP_ID NOT IN (%s)
}

/*----------------------------------------------------------------------------------------------------------
SQL : Popup_UserGroup_ค้นหาข้อมูลตามเงื่อนไข_SQL
Description : -
----------------------------------------------------------------------------------------------------------*/
searchGroup{
	SELECT 
	U.GROUP_ID,
	U.GROUP_CODE,
	U.GROUP_NAME,
	U.active
	FROM [OC].sec_group U
	WHERE 1=1
	AND U.active = %s  /* สถานะใช้งาน */
	AND UPPER(U.GROUP_CODE) LIKE UPPER(CONCAT('%', %s, '%')) /* รหัสกลุ่มผู้ใช้ */
	AND UPPER(U.GROUP_NAME) LIKE UPPER(CONCAT('%', %s, '%')) /* ชื่อกลุ่มผู้ใช้ */
	AND U.GROUP_ID NOT IN (%s)
	AND U.GROUP_ID IN (%s)
}