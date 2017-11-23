-- SQL for JIRA CWP-329 --

/*
	Ensure that the correct document record is going to be updated.
	The returned title value should be 'Inventory Adjustment Form' for id 1618
*/
select id, title from dbo.document where id = 1618;

/* update the title for document record id 1618 */
update dbo.document set title = 'Inventory Adjustment Form - Standard' where id = 1618;

/*
	Ensure that the document record was updated.
	The returned title value should be 'Inventory Adjustment Form - Standard' for id 1618
*/
select id, title from dbo.document where id = 1618;