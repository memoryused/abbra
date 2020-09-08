searchCount{
	select count(1) as TOT
	from [OC].m_item_h ih
	where 1=1
	and ih.deleted = 'N'
	and ih.item_id = %s
	and ih.item_code like CONCAT('%', %s, '%') 
	and ih.active = %s
}

search{
	select ih.item_id, ih.item_code, ih.item_short_name, ih.active
	from [OC].m_item_h ih
	where 1=1
	and ih.deleted = 'N'
	and ih.item_id = %s
	and ih.item_code like CONCAT('%', %s, '%') 
	and ih.active = %s
	order by 
	%s
	limit %s,
	%s
}

setActiveStatus{
	update [OC].m_item_h
	set active = %s
	, update_date = sysdate()
	, update_user = %s 
	where item_id IN (%s)
}

searchById{
	select ih.item_id, ih.item_code, ih.item_short_name, ih.active
	from [OC].m_item_h ih
	where ih.item_id = %s
	and ih.deleted = 'N'
}

checkDup{
	select count(1) as cnt 
	from [OC].m_item_h
	where deleted = 'N' 
	and item_code = %s
	and item_short_name = %s
	and item_id <> %s
}

insertItem{
	insert into [OC].m_item_h
	(item_id, Item_code, item_short_name, active, create_date, create_user) 
	values 
	( %s 
	, %s 
	, %s 
	, %s 
	, sysdate() 
	, %s
	)
}

updateItem{
	update [OC].m_item_h 
	set Item_code = %s 
	, item_short_name = %s
	, active = %s
	, update_date = sysdate()
	, update_user = %s 
	where item_id = %s
}

deleteItem{
	update [OC].m_item_h
	set deleted = 'Y'
	, update_date = sysdate()
	, update_user = %s 
	where item_id IN (%s)
}

getItemSEQ{
	SELECT (MAX(item_id)+1) as ITEM_ID_SEQ FROM [OC].m_item_h
}

insertVendorItemMap{
	INSERT INTO [OC].doc_vendor_item_map
	(vendor_id, Item_id, Active, deleted) 
	VALUES (
		%s, 
		%s, 
		'Y', 
		'N')
}

searchVenderByItemId{
	SELECT dm.Vendor_item_id, dm.vendor_id, mh.vendor_code, mh.vendor_name, dm.Item_id, dm.Active 
	FROM [OC].doc_vendor_item_map dm
	inner join [OC].m_vendor_h mh on dm.vendor_id = mh.vendor_id
	where dm.deleted = 'N'
	and dm.Item_id = %s
}

checkDupVendorItemMap{
	select count(1) as CNT 
	from [OC].doc_vendor_item_map
	where deleted = 'N'
	and vendor_id = %s
	and Item_id = %s
}

deleteVendorItemMapById{
	UPDATE [OC].doc_vendor_item_map 
	SET deleted = 'Y' 
	WHERE vendor_id = %s
	and Item_id = %s
}