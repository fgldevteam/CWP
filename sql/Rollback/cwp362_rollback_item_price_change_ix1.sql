-- SQL for JIRA CWP-362 --



/*
    Drop the existing index
 */
DROP INDEX [item_price_change].[item_price_change_ix1];
-- this syntax supported in 2005 DROP INDEX [item_price_change_ix1] ON [dbo].[item_price_change];
/*
	Rollback script for the original script
*/

CREATE CLUSTERED INDEX [item_price_change_ix1] ON [dbo].[item_price_change] 
( 
[pmm_style_id] ASC,
[store_number] ASC
) ON [PRIMARY]; 
