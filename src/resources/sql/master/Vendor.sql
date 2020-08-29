searchCount{
	select count(1) as TOT
	from [OC].m_vendor_h vh
	where vh.deleted = 'N'
	and vh.vendor_id = %s
	and vh.vendor_code like CONCAT('%', %s, '%')
	and vh.active = %s
}

search{
	select vh.vendor_id, vh.vendor_code, vh.vendor_name, vh.vendor_short_name, vh.active
	from [OC].m_vendor_h vh
	where vh.deleted = 'N'
	and vh.vendor_id = %s
	and vh.vendor_code like CONCAT('%', %s, '%')
	and vh.active = %s
	order by 
	%s
	limit %s,
	%s
}

setActiveStatus{
	update [OC].m_vendor_h
	set active = %s
	where vendor_id IN (%s)
}

searchById{
	select vh.vendor_id, vh.vendor_code, vh.vendor_name, vh.vendor_short_name, vh.active
	from [OC].m_vendor_h vh
	where vh.vendor_id = %s
	and vh.deleted = 'N'
}

checkDup{
	select count(1) as cnt 
	from [OC].m_vendor_h
	where deleted = 'N' 
	and vendor_code = %s
	and vendor_name = %s
	and vendor_short_name = %s
	and vendor_id <> %s
}

insertVendor{
	insert into [OC].m_vendor_h
	(vendor_code, vendor_name, vendor_short_name, active, create_date, create_user) 
	values 
	( %s 
	, %s 
	, %s 
	, %s 
	, sysdate() 
	, %s
	)
}

updateVendor{
	update [OC].m_vendor_h 
	set vendor_code = %s 
	, vendor_name = %s
	, vendor_short_name = %s
	, active = %s
	, update_date = sysdate()
	, update_user = %s 
	where vendor_id = %s
}

deleteVendor{
	update [OC].m_vendor_h
	set deleted = 'Y'
	where vendor_id IN (%s)
}