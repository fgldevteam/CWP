-- SQL for JIRA CWP-362 --



/*
    Drop the existing index
 */
DROP INDEX [item_price_change].[item_price_change_ix1];
-- this syntax supported in 2005 DROP INDEX [item_price_change_ix1] ON [dbo].[item_price_change];
/*
	This index improves the performance of the item_price_change report
	Create's a new index called item_price_change_ix1 on item_price_change
*/

CREATE CLUSTERED INDEX [item_price_change_ix1] ON [dbo].[item_price_change] 
( 
[store_number] ASC, 
[pmm_style_id] ASC 
) ON [PRIMARY]; 
