package com.sit.app.core.security.config.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;

@XmlRootElement
public class ConfigSystemModel extends CommonModel {

	private static final long serialVersionUID = 3140824996313034398L;
	
	private ConfigSystem configSystem = new ConfigSystem();
	
	private List<CommonSelectItem> listMethodUnlockAuto = new ArrayList<CommonSelectItem>();
	
	public ConfigSystem getConfigSystem() {
		return configSystem;
	}

	public void setConfigSystem(ConfigSystem configSystem) {
		this.configSystem = configSystem;
	}

	public List<CommonSelectItem> getListMethodUnlockAuto() {
		return listMethodUnlockAuto;
	}

	public void setListMethodUnlockAuto(List<CommonSelectItem> listMethodUnlockAuto) {
		this.listMethodUnlockAuto = listMethodUnlockAuto;
	}

}
