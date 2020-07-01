package com.sit.app.core.selectitem.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.product.domain.MailControl;
import com.sit.common.CommonSelectItem;
import com.sit.common.CommonUser;
import com.sit.domain.Language;

import util.database.CCTConnection;

public class SelectItemService extends AbstractService {

	private SelectItemDAO dao = null;

	public SelectItemService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new SelectItemDAO(log);
		this.dao.setSqlPath(SQLPath.SELECT_ITEM_SQL);
	}

	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(Language language) throws Exception {
		return dao.searchGlobalDataSelectItem(conn, language.getLocale());
	}
	
	
	/**
	 * Product [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchProductSelectItem() throws Exception {
		return dao.searchProductSelectItem(conn);
	}
	
	/**
	 * Vender [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchVenderSelectItem() throws Exception {
		return dao.searchVenderSelectItem(conn);
	}
	
	/**
	 * Document Type [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDocumentTypeSelectItem() throws Exception {
		return dao.searchDocumentTypeSelectItem(conn);
	}
	
	/**
	 * Status [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchStatusSelectItem() throws Exception {
		return dao.searchStatusSelectItem();
	}
	
	/**
	 * Standard [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchStandardSelectItem(String documentId) throws Exception {
		return dao.searchStandardSelectItem(conn, documentId);
	}
	
	/**
	 * Mail Control
	 * @return
	 * @throws Exception
	 */
	protected MailControl searchMailControl() throws Exception {
		return dao.searchMailControl(conn);
	}
}