package com.sit.app.core.dialog.security.operator.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.sit.common.CommonModel;
import com.sit.domain.TreeData;

@XmlRootElement
public class ReportDialogModel extends CommonModel {

	private static final long serialVersionUID = -8720009821091377859L;

	private TreeData treeData = new TreeData();

	public TreeData getTreeData() {
		return treeData;
	}

	public void setTreeData(TreeData treeData) {
		this.treeData = treeData;
	}

}
