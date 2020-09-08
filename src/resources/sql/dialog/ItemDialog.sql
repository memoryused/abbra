searchCount{
	select count(1) as TOT
	from [OC].m_item_h mh
	where mh.deleted = 'N'
	and mh.item_short_name like UPPER(CONCAT('%', %s, '%'))
	and mh.Item_code like UPPER(CONCAT('%', %s, '%'))
	and mh.active = %s
	and mh.Item_id NOT IN (%s)
}

search{
	select mh.Item_id, mh.Item_code, mh.item_short_name, mh.active
	from [OC].m_item_h mh
	where mh.deleted = 'N'
	and mh.item_short_name like UPPER(CONCAT('%', %s, '%'))
	and mh.Item_code like UPPER(CONCAT('%', %s, '%'))
	and mh.active = %s
	and mh.Item_id NOT IN (%s)
	and mh.Item_id IN (%s)
}