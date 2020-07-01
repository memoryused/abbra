package com.sit.app.core.config.parameter.service;

import com.sit.app.core.config.parameter.domain.Parameter;

import util.file.FileManagerUtil;
import util.log.LogUtil;
import util.xml.XMLUtil;

public class ParameterService {

	protected void create(String filePath) throws Exception {
		try {

		} catch (Exception e) {
			throw e;
		}
	}

	protected Parameter load(String filePath) throws Exception {
		Parameter parameter = new Parameter();
		try {
			LogUtil.INITIAL.debug("path :- " + filePath);
			parameter = (Parameter) XMLUtil.xmlToObject(filePath, parameter);

			LogUtil.INITIAL.debug("Title :- " + parameter.getApplication().getTitle());
			LogUtil.INITIAL.debug("Support Locale :- " + parameter.getApplication().getSupportLocaleString());
			LogUtil.INITIAL.debug("Application Locale :- " + parameter.getApplication().getApplicationLocale());

			FileManagerUtil.crateDirectoryWithoutOverwrite(parameter.getAttachFile().getTmpPath());
			FileManagerUtil.crateDirectoryWithoutOverwrite(parameter.getAttachFile().getServerPath());

		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}

}
