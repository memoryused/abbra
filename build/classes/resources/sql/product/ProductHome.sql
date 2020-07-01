searchCount{
	select count(1) as TOT 	
	from  [OC].m_vendor_h vh
	      inner join [OC].doc_vendor_item_map vi on vh.vendor_id = vi.vendor_id
	      inner join [OC].m_item_h ih on vi.item_id = ih.item_id
	      inner join [OC].doc_transaction_map tm on tm.Vendor_item_id = vi.Vendor_item_id
	      inner join [OC].doc_transaction_d td on td.Doc_TranD_ID = tm.Doc_TranD_ID
	      inner join [OC].m_certificate_std cs on cs.CERTIFICATE_ID = td.CERTIFICATE_ID and cs.DOCUMENT_ID = td.DOCUMENT_ID
	      inner join [OC].m_document_std ds on ds.DOCUMENT_ID = cs.DOCUMENT_ID
	where td.deleted = 'N'
	and ih.item_id = %s
	and vh.vendor_id = %s
	and ds.DOCUMENT_ID = %s
}

search{
	select td.Doc_TranD_ID, ih.item_id, ih.item_short_name, vh.vendor_id, vh.vendor_name, 
	td.DOCUMENT_ID, ds.DOCUMENT_NAME, td.CERTIFICATE_ID, cs.CERTIFICATE_NAME, DATE_FORMAT(td.Doc_expire_date, '%d/%m/%Y') as Doc_expire_date, 
	CASE WHEN td.Doc_expire_date >= SYSDATE() 
	  THEN 'Y' 
	  ELSE 'N' 
	END as PDF_Path 	
	from  [OC].m_vendor_h vh
	      inner join [OC].doc_vendor_item_map vi on vh.vendor_id = vi.vendor_id
	      inner join [OC].m_item_h ih on vi.item_id = ih.item_id
	      inner join [OC].doc_transaction_map tm on tm.Vendor_item_id = vi.Vendor_item_id
	      inner join [OC].doc_transaction_d td on td.Doc_TranD_ID = tm.Doc_TranD_ID
	      inner join [OC].m_certificate_std cs on cs.CERTIFICATE_ID = td.CERTIFICATE_ID and cs.DOCUMENT_ID = td.DOCUMENT_ID
	      inner join [OC].m_document_std ds on ds.DOCUMENT_ID = cs.DOCUMENT_ID
	where td.deleted = 'N'
	and ih.item_id = %s
	and vh.vendor_id = %s
	and ds.DOCUMENT_ID = %s
	order by
	%s
	limit %s,
	%s
}

searchFile{
	select td.PDF_Path as FILE_PATH
	from [OC].doc_transaction_d td 
	where td.Doc_expire_date >= SYSDATE()
	and td.Doc_TranD_ID = %s
}