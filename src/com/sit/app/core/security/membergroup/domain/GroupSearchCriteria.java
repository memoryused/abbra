package com.sit.app.core.security.membergroup.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class GroupSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = 3207442983330990416L;

	public static final int TABLE_HEADER_SORTS_TYPE_1 = 1;
	
	private Group group = new Group();

	public GroupSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("GROUP_CODE", HeaderSorts.ASC, "0")
				, new HeaderSorts("GROUP_NAME", HeaderSorts.ASC, "0")
				, new HeaderSorts("ACTIVE", HeaderSorts.ASC, "0")
			};
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	private static final HeaderSorts[] HEADER_SORTS_1 = { 
			new HeaderSorts("", HeaderSorts.ASC),
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC)
	};
	
	public void setDefaultHeaderSorts() {
		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:
				setHeaderSortsSelect("");
				break;
			default:{
				for (int i = 0; i < getHeaderSortsSize(); i++) {
					getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
					getHeaderSorts()[i].setSeq("");
				}
				getHeaderSorts()[0].setSeq("0");
				setHeaderSortsSelect("3");
			}
		}
	}

	public void initHeaderSorts() {
		HeaderSorts[] arrayHeaderSorts = null;

		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:	{
				arrayHeaderSorts = HEADER_SORTS_1;
				setHeaderSorts(arrayHeaderSorts);
				setHeaderSortsSize(arrayHeaderSorts.length);
				
				break;
			}
			default:
				break;
		}
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
