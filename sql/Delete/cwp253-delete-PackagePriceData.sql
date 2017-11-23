-- Below Query will fetch all package price related rows from v_item_price_change Table. This query is usefull 
-- to get the number of records related to package price report data. 

SELECT COUNT(pmm_style_id)  AS packagereport_count  
FROM v_item_price_change  
WHERE (prc_method <> 'SP' ) AND (prc_direction = 0 ); 

-- Below query is used If the count is greater than Zero then execute below query to remove the package price related data.

Delete from item_price_change where (prc_method <> 'SP' ) AND (prc_direction = 0 ); 

-- Below query is used Query to validate whether the deletion has successfully done.

SELECT COUNT(pmm_style_id)  AS packagereport_count  
FROM v_item_price_change 
WHERE (prc_method <> 'SP' ) AND (prc_direction = 0 ); 
