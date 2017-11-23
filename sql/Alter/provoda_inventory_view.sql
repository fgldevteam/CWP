/****** Object:  View [dbo].[provoda_inventory]    Script Date: 07/14/2011 09:39:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[provoda_inventory]
AS
SELECT     dbo.sku.number AS sku, dbo.sku.pmm_sku_id AS sku_id, dbo.style.description, dbo.style.number AS style, dbo.sku.pmm_style_id AS style_id, dbo.sku.colour_code, 
                      color.codi_description AS colour, dbo.sku.size_code, size.codi_description AS size, dbo.style.pmm_brand_id AS brand_code, dbo.brand.brand_name, 
                      dbo.hierarchy.dept_name AS dept, dbo.hierarchy.subdept_name AS subdept, dbo.hierarchy.class_code, dbo.hierarchy.class_name AS class, 
                      dbo.hierarchy.subclass_code, dbo.hierarchy.subclass_name AS subclass
FROM         dbo.colour_size_description AS size INNER JOIN
                      dbo.sku INNER JOIN
                      dbo.style ON dbo.sku.pmm_style_id = dbo.style.pmm_style_id INNER JOIN
                      dbo.brand ON dbo.style.pmm_brand_id = dbo.brand.pmm_brand_id INNER JOIN
                      dbo.hierarchy ON dbo.style.subclass_id = dbo.hierarchy.subclass_code ON size.codi_code = dbo.sku.size_code INNER JOIN
                      dbo.colour_size_description AS color ON dbo.sku.colour_code = color.codi_code
GO

