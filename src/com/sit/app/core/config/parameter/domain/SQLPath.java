package com.sit.app.core.config.parameter.domain;

import java.io.Serializable;
import resources.sql.selectitem.SelectItemSQL;
import resources.sql.security.SecuritySQL;
import resources.sql.product.ProductSQL;

public enum SQLPath implements Serializable {
	/**
	 * @Description: Class enum for data base lookup
	 */
	SELECT_ITEM_SQL(SelectItemSQL.class, "resources/sql/selectitem/SelectItem.sql")
	, LOGIN_SQL(SecuritySQL.class, "resources/sql/security/Login.sql")
	, PRODUCT_HOME(ProductSQL.class, "resources/sql/product/ProductHome.sql")
	, PRODUCT_INFO(ProductSQL.class, "resources/sql/product/ProductInfo.sql")
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