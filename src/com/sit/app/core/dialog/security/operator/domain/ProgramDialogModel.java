package com.sit.app.core.dialog.security.operator.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.sit.common.CommonModel;
import com.sit.domain.TreeData;

@XmlRootElement
public class ProgramDialogModel extends CommonModel {

	private static final long serialVersionUID = 1748956191787200840L;

	private TreeData treeData = new TreeData();

	public TreeData getTreeData() {
		return treeData;
	}

	public void setTreeData(TreeData treeData) {
		this.treeData = treeData;
	}

}
