package com.sit.common;

import java.util.List;

import com.sit.domain.SearchCriteria;
import com.sit.exception.AuthorizationException;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;

public class CommonDialogDatatableAction extends CommonAction implements ModelDriven<CommonModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631272065836982263L;
	
	/**
	 * Concrete method for initialize criteria
	 * @return criteria
	 */
	public SearchCriteria initSearchCriteria() throws Exception {
		return null;
	}
	
	/**
	 * Concrete method for initialize combo
	 * @param conn
	 */
	public void getComboForSearch(CCTConnection conn) {
		
	}
	
	/**
	 * Concrete method for search
	 * @param conn
	 */
	public List<CommonDomain> search(CCTConnection conn) throws Exception{
		return null;
	}
	
	public List<CommonDomain> searchByIds(CCTConnection conn) throws Exception {
		return null;
	}
	
	@Override
	public CommonModel getModel() {
		return null;
	}
	
	private void initCriteriaDialogDatatable(CommonModel model) {
		
		getModel().getCriteria().setDefaultHeaderSorts();

		if (getModel().getCriteria().getHeaderSortsSelect() != null) {
			
			String[] hSelect = getModel().getCriteria().getHeaderSortsSelect().split(",");
			String order = "";
			for (String head : hSelect) {
				order += "," + getModel().getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
			}
			
			getModel().getCriteria().setOrderSortsSelect(order.substring(1));
		}
	}
	
	public String init() {
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			//INIT CRITERIA
			SearchCriteria criteria = initSearchCriteria();
			
			getModel().setCriteria(criteria);
			
			initCriteriaDialogDatatable(getModel());
			
		} catch (Exception e) {
			
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return ReturnType.SEARCH_AJAX.getResult();

	}
	
	public String searchByIds() throws AuthorizationException {
		
		CCTConnection conn 	= null;
		
		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			List<CommonDomain> lstResult = searchByIds(conn);
			
			getModel().setLstResult(lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, getModel());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return ReturnType.SEARCH_AJAX.getResult();
	}
	
	public String search() throws AuthorizationException {
		
		String result = null;
		CCTConnection conn 	= null;
		
		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), PF_CODE.getSearchFunction());
			
			List<CommonDomain> lstResult = search(conn);
			
			manageSearchResult(getModel(), lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, getModel());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}
	
	public String searchInhouse() throws AuthorizationException {
		
		String result = null;
		CCTConnection conn 	= null;
		
		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			result = manageSearchAjaxInhouse(conn, getModel(), getModel().getCriteria(), PF_CODE.getSearchFunction());
			
			List<CommonDomain> lstResult = search(conn);
			
			manageSearchResult(getModel(), lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, getModel());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

}
