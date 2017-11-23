
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[v_item_price_change]
-- 2006-11-20 Created
-- 2010-03-29 CWP-344 Removed LEFT(CONVERT(VARCHAR, upc), 1) = 4 so to pull all UPCs
-- 2010-12-15 CWP-371 Removed WHERE      len(CONVERT(VARCHAR, upc)) = 12
AS
SELECT     ipc.*, upc.upc
FROM         dbo.item_price_change ipc LEFT OUTER JOIN
                          (SELECT     pmm_style_id, MIN(upc) AS upc
                            FROM          sku INNER JOIN
                                                   upc ON upc.pmm_sku_id = sku.pmm_sku_id
                            GROUP BY pmm_style_id) upc ON upc.pmm_style_id = ipc.pmm_style_id
GO

SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO


