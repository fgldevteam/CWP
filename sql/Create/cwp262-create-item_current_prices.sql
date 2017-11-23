-- History
--    CWP-267 - Created

USE CWP

-- Drop tables
-- check if table exists before dropping, dropping the table will drop the index too
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[item_current_prices]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE dbo.item_current_prices
GO


-- Create tables
CREATE TABLE dbo.item_current_prices
(
   pmm_style_id int not null,
   store_number int,
   current_price money not null
)
GO

ALTER TABLE dbo.item_current_prices 
ADD CONSTRAINT pmm_style_id_idx UNIQUE CLUSTERED (pmm_style_id,	store_number)
GO
