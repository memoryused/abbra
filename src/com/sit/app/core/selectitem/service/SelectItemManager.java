package com.sit.app.core.selectitem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.sit.abstracts.AbstractManager;
import com.sit.common.CommonSelectItem;
import com.sit.common.CommonUser;
import com.sit.domain.Language;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.product.domain.MailControl;

public class SelectItemManager extends AbstractManager<Object, Object, Object, Object> {

	private static Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData = new HashMap<Locale, Map<String, List<CommonSelectItem>>>();

	private SelectItemService service = null;

	public SelectItemManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new SelectItemService(conn, user, log);
	}

	/**
	 * กำหนดค่าเริ่มต้นให้กับ Combo ที่ไม่มีการเปลื่ยนบ่อย
	 *
	 * @throws Exception
	 */
	public void init() throws Exception {
		try {
			initGlobalDataSelectItem(conn);
		} catch (Exception e) {
			throw e;
		}
	}

	private void initGlobalDataSelectItem(CCTConnection conn) throws Exception {
		for (Language language : ParameterConfig.getParameter().getApplication().getSupportLanguageList()) {
			Map<String, List<CommonSelectItem>> mapSelectItem = service.searchGlobalDataSelectItem(language);
			if (mapSelectItem.size() == 0) {
				mapSelectItem = mapGlobalData.get(ParameterConfig.getParameter().getApplication().getApplicationLocale());
			}
			mapGlobalData.put(language.getLocale(), mapSelectItem);
		}
	}

	public static Map<Locale, Map<String, List<CommonSelectItem>>> getMapGlobalData() {
		return mapGlobalData;
	}

	public static void setMapGlobalData(Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData) {
		SelectItemManager.mapGlobalData = mapGlobalData;
	}

	
	/**
	 * Product [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchProductSelectItem() throws Exception {
		return service.searchProductSelectItem();
	}
	
	/**
	 * Vender [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchVenderSelectItem() throws Exception {
		return service.searchVenderSelectItem();
	}
	
	/**
	 * Document Type [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchDocumentTypeSelectItem() throws Exception {
		return service.searchDocumentTypeSelectItem();
	}
	
	/**
	 * Status [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchStatusSelectItem() throws Exception {
		return service.searchStatusSelectItem();
	}
	
	/**
	 * Standard [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchStandardSelectItem(String documentId) throws Exception {
		return service.searchStandardSelectItem(documentId);
	}
	
	/**
	 * Mail Control
	 * @return
	 * @throws Exception
	 */
	public MailControl searchMailControl() throws Exception {
		return service.searchMailControl();
	}
	
	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public List<Object> search(Object criteria) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public Object searchById(String id) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public int add(Object obj) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public int edit(Object obj) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public int delete(String ids) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		return 0;
	}
}