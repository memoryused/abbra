package com.sit.app.core.security.authorization.service;

import java.util.Map;

import util.log.LogUtil;

import com.sit.domain.Operator;
import com.sit.app.core.security.authorization.domain.Authorize;
import com.sit.app.core.security.authorization.domain.PFCode;

public class AuthorizationService {

	public Authorize checkAuthorize(PFCode pfcode, Map<String, Operator> mapFunction) throws Exception {
		LogUtil.SEC.info("");

		Authorize ath = new Authorize();
		try {
			if (mapFunction.get(pfcode.getAddFunction()) != null) {
				ath.setAdd(true);
			}
			if (mapFunction.get(pfcode.getSearchFunction()) != null) {
				ath.setSearch(true);
			}
			if (mapFunction.get(pfcode.getEditFunction()) != null) {
				ath.setEdit(true);
			}
			if (mapFunction.get(pfcode.getViewFunction()) != null) {
				ath.setView(true);
			}
			if (mapFunction.get(pfcode.getChangeFunction()) != null) {
				ath.setChange(true);
			}
			if (mapFunction.get(pfcode.getDeleteFunction()) != null) {
				ath.setDelete(true);
			}
			if (mapFunction.get(pfcode.getImportFunction()) != null) {
				ath.setImport(true);
			}
			if (mapFunction.get(pfcode.getPrintFunction()) != null) {
				ath.setPrint(true);
			}
			if (mapFunction.get(pfcode.getExportFunction()) != null) {
				ath.setExport(true);
			}
		} catch (Exception e) {
			throw e;
		}
		return ath;
	}
}
