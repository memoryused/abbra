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
	(Item_code, item_short_name, active, create_date, create_user) 
	values 
	( %s 
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
	where item_id IN (%s)
}