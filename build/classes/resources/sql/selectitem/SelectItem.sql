searchProductSelectItem {
	select i.Item_id, i.item_short_name  
	from [OC].m_item_h i
	where i.active = 'Y'
	order by i.item_short_name
}

searchVenderSelectItem {
	select v.vendor_id, v.vendor_name 
	from [OC].m_vendor_h v
	where v.active = 'Y'
	order by v.vendor_name
}

searchDocumentTypeSelectItem {
	select d.DOCUMENT_ID, d.DOCUMENT_NAME 
	from [OC].m_document_std d
	where d.ACTIVE = 'Y'
	order by d.DOCUMENT_ID
}

searchStandardSelectItem{
	select cs.CERTIFICATE_ID, cs.CERTIFICATE_NAME 
	from [OC].m_certificate_std cs
	where cs.ACTIVE = 'Y'
	and cs.DOCUMENT_ID = %s
	order by cs.DOCUMENT_ID ASC, cs.CERTIFICATE_NAME
}

searchMailControl{
	SELECT Email_id, Sender_1, CC_1, CC_2, Send_notif, M_Email_Controlcol 
	FROM [OC].m_email_control
}