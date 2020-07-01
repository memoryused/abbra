package com.sit.app.core.security.config.domain;

import com.sit.common.CommonDomain;

public class ConfigIP extends CommonDomain {

	private static final long serialVersionUID = 510546283371492812L;

	private String ipv;
	private String ipNumber;
	private String remark;
	private String checksum;
	private String configSystemId;			// เพิ่มตาม SQL
	private String createStationIp;			// เพิ่มตาม SQL
	
	public String getIpv() {
		return ipv;
	}

	public void setIpv(String ipv) {
		this.ipv = ipv;
	}

	public String getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getConfigSystemId() {
		return configSystemId;
	}

	public void setConfigSystemId(String configSystemId) {
		this.configSystemId = configSystemId;
	}

	public String getCreateStationIp() {
		return createStationIp;
	}

	public void setCreateStationIp(String createStationIp) {
		this.createStationIp = createStationIp;
	}

}
