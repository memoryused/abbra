searchCount{
	select count(1) as TOT 
	from [OC].m_vendor_h vh
	      inner join [OC].doc_vendor_item_map vi on vh.vendor_id = vi.vendor_id
	      inner join [OC].m_item_h ih on vi.item_id = ih.item_id
	where 1=1
	and ih.item_id = %s
	and vh.vendor_id = %s
	and vi.active = %s
	and vi.deleted = 'N'
}

search{
	select vi.Vendor_item_id, ih.item_short_name, vh.vendor_short_name, vi.active 
	from [OC].m_vendor_h vh
	      inner join [OC].doc_vendor_item_map vi on vh.vendor_id = vi.vendor_id
	      inner join [OC].m_item_h ih on vi.item_id = ih.item_id
	where 1=1
	and ih.item_id = %s
	and vh.vendor_id = %s
	and vi.active = %s
	and vi.deleted = 'N'
	order by 
	%s
	limit %s,
	%s
}

searchById{
  select vi.Vendor_item_id, vh.vendor_code, vh.vendor_short_name, vh.vendor_name,
  ih.Item_code, ih.item_short_name 
  from [OC].m_vendor_h vh
	      inner join [OC].doc_vendor_item_map vi on vh.vendor_id = vi.vendor_id
	      inner join [OC].m_item_h ih on vi.item_id = ih.item_id
  where vi.Vendor_item_id = %s
}

searchByStandard{
	select td.Doc_TranD_ID, td.DOCUMENT_ID, cs.CERTIFICATE_ID, cs.CERTIFICATE_NAME, 
		DATE_FORMAT(td.Doc_expire_date, '%d/%m/%Y') as Doc_expire_date, td.Doc_expire_date as docExOrder, 
		CONCAT_WS(', ', td.Doc_mail_1, td.Doc_mail_2, td.Doc_mail_3) as contract_email,
		CASE 
	      WHEN td.Doc_expire_date >= SYSDATE() THEN 'Y' ELSE 'N' 
		END as PDF_Path ,
		CASE 
	      WHEN (td.Text_Path IS NULL or td.Text_Path = '') THEN 'X' 
	      WHEN (td.Doc_expire_date >= SYSDATE()) THEN 'Y' 
		  ELSE 'N' 
		END as Text_Path 
	from [OC].m_certificate_std cs
	  inner join [OC].doc_transaction_d td on td.CERTIFICATE_ID = cs.CERTIFICATE_ID AND td.DOCUMENT_ID = cs.DOCUMENT_ID
	  inner join [OC].doc_transaction_map tm on tm.Doc_TranD_ID = td.Doc_TranD_ID
	  inner join [OC].doc_vendor_item_map vi on vi.Vendor_item_id = tm.Vendor_item_id
	where td.deleted = 'N'
	and vi.Vendor_item_id = %s
	and td.DOCUMENT_ID = %s
	order by docExOrder
}

searchCheckDup{
	select count(1) as TOT 
	from [OC].m_certificate_std cs
	  inner join [OC].doc_transaction_d td on td.CERTIFICATE_ID = cs.CERTIFICATE_ID AND td.DOCUMENT_ID = cs.DOCUMENT_ID
	  inner join [OC].doc_transaction_map tm on tm.Doc_TranD_ID = td.Doc_TranD_ID
	  inner join [OC].doc_vendor_item_map vi on vi.Vendor_item_id = tm.Vendor_item_id
	where td.deleted = 'N'
	and vi.Vendor_item_id = %s
	and td.DOCUMENT_ID = %s
	and td.CERTIFICATE_ID = %s
	and td.Doc_TranD_ID <> %s
}

getSeqDocTransactionD{
	select max(Doc_TranD_ID)+1 as docTranPK from [OC].doc_transaction_d
}

insertDocTransactionMap{
	insert into [OC].doc_transaction_map
	(Vendor_item_id, Doc_TranD_ID) 
	values ( %s
	, %s
	)
}

insertDocTransaction{
	insert into [OC].doc_transaction_d
	(Doc_TranD_ID, DOCUMENT_ID, CERTIFICATE_ID, Doc_expire_date, Doc_mail_1, Doc_mail_2, Doc_mail_3, PDF_Path, Text_Path) 
	values (%s
	, %s
	, %s
	, STR_TO_DATE(%s, '%d/%m/%Y')
	, %s
	, null
	, null
	, %s
	, %s)
}

searchTransDByCertId{
	select td.Doc_TranD_ID, td.CERTIFICATE_ID, DATE_FORMAT(td.Doc_expire_date, '%d/%m/%Y') as Doc_expire_date,
		CONCAT_WS(', ', td.Doc_mail_1, td.Doc_mail_2, td.Doc_mail_3) as contract_email,
		SUBSTRING_INDEX(td.PDF_Path, '/', -1) as PDF_Path, SUBSTRING_INDEX(td.Text_Path, '/', -1) as Text_Path
	from [OC].doc_transaction_d td
	where td.CERTIFICATE_ID = %s
	and td.Doc_TranD_ID = %s
}

setDocTransaction{
	update [OC].doc_transaction_d 
	set Doc_expire_date = STR_TO_DATE(%s, '%d/%m/%Y')
	, Doc_mail_1 = %s
	, PDF_Path = %s
	, Text_Path = %s 
	where Doc_TranD_ID = %s
}

deleteDocVenderItemMap{
	update [OC].doc_vendor_item_map
	set deleted = 'Y'
	where Vendor_item_id IN (%s) 
}

deleteDocTransaction{
	update [OC].doc_transaction_d 
	set deleted = 'Y'
	where Doc_TranD_ID IN (%s)
}

setActiveStatus{
	update [OC].doc_vendor_item_map
	set active = %s
	where Vendor_item_id IN (%s)
}
