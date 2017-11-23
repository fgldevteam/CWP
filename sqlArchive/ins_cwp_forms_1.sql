INSERT INTO dbo.document(title,parentdoc,haschild,type,mod_date,category) 
VALUES('Pricing Issue Reporting',0,'Y','H',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Pricing Issues Form','/secure/forms/newPricingIssues.do',(select id from dbo.document where title = 'Pricing Issue Reporting'),'N','F','supportlink@forzani.com; klessard@forzani.com',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Inventory Adjustment Form','/secure/forms/newInventoryAdj.do',(select id from dbo.document where title = 'Inv Management'),'N','F','inventory@forzani.com',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Non Warranty Adjustment Form','/secure/forms/newNonWarrantyAdj.do',(select id from dbo.document where title = 'Inv Management'),'N','F','inventory@forzani.com',GetDate(),'documents');
