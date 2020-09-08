package com.sit.app.core.config.parameter.domain;

import java.io.Serializable;
import resources.sql.selectitem.SelectItemSQL;
import resources.sql.security.SecuritySQL;
import resources.sql.product.ProductSQL;
import resources.sql.master.MasterSQL;
import resources.sql.dialog.DialogSQL;

public enum SQLPath implements Serializable {
	/**
	 * @Description: Class enum for data base lookup
	 */
	SELECT_ITEM_SQL(SelectItemSQL.class, "resources/sql/selectitem/SelectItem.sql")
	, LOGIN_SQL(SecuritySQL.class, "resources/sql/security/Login.sql")
	, PRODUCT_HOME(ProductSQL.class, "resources/sql/product/ProductHome.sql")
	, PRODUCT_INFO(ProductSQL.class, "resources/sql/product/ProductInfo.sql")
	, ITEM(MasterSQL.class, "resources/sql/master/Item.sql")
	, VENDOR(MasterSQL.class, "resources/sql/master/Vendor.sql")
	, MEMBER(SecuritySQL.class, "resources/sql/security/Member.sql")
	, MEMBER_GROUP(SecuritySQL.class, "resources/sql/security/MemberGroup.sql")
	
	, DIALOG_GROUP_SQL(DialogSQL.class, "resources/sql/dialog/GroupDialog.sql")
	, DIALOG_OPERATOR_SQL(DialogSQL.class, "resources/sql/dialog/Operator.sql")
	, DIALOG_USER_SQL(DialogSQL.class, "resources/sql/dialog/UserDialog.sql")
	, DIALOG_VENDOR_SQL(DialogSQL.class, "resources/sql/dialog/VendorDialog.sql")
	, DIALOG_ITEM_SQL(DialogSQL.class, "resources/sql/dialog/ItemDialog.sql")
	;

	private String path;
	private Class<?> className;

	private SQLPath(Class<?> className, String path) {
		this.path = path;
		this.className = className;
	}

	public String getPath() {
		return ParameterConfig.getParameter().getApplication().getSqlPath() + path;
	}

	public Class<?> getClassName() {
		return className;
	}
}