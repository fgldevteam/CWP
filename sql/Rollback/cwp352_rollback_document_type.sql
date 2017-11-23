-- SQL for JIRA CWP-352 --

/*
	Ensure that the correct document record is going to be updated.
	The returned title value should be 'Repairs and Maintenance Form' for id 993
*/
select id, title from dbo.document where id = 993;

/* update the type for document record id 993 */
update dbo.document set type = 'F' where id = 993;

/*
	Ensure that the document record was updated.
	The returned type value should be 'F' for id 993
*/
select id, type from dbo.document where id = 993;