searchCount{
	select count(1) as TOT
	from [OC].m_vendor_h vh
	where vh.deleted = 'N'
	and vh.vendor_name like UPPER(CONCAT('%', %s, '%'))
	and vh.vendor_code like UPPER(CONCAT('%', %s, '%'))
	and vh.active = %s
	and vh.vendor_id NOT IN (%s)
}

search{
	select vh.vendor_id, vh.vendor_code, vh.vendor_name, vh.vendor_short_name, vh.active
	from [OC].m_vendor_h vh
	where vh.deleted = 'N'
	and vh.vendor_name like UPPER(CONCAT('%', %s, '%'))
	and vh.vendor_code like UPPER(CONCAT('%', %s, '%'))
	and vh.active = %s
	and vh.vendor_id NOT IN (%s)
	and vh.vendor_id IN (%s)
}