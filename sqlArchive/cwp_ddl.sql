use cwp
GO

/* create users and roles*/
if not exists (select * from master.dbo.syslogins where name = N'cwpuser')
    EXEC sp_addlogin N'cwpuser', N'cwpuser'
GO

if not exists (select * from sysusers where name = N'cwpuser' and uid < 16382)
    EXEC sp_grantdbaccess N'cwpuser', N'cwpuser'
GO

if not exists (select * from sysusers where name = N'fwp_update' and uid > 16399)
    EXEC sp_addrole N'cwp_update'
GO


/* add users to roles*/
exec sp_addrolemember N'cwp_update', N'cwpuser'
GO

exec sp_addrolemember N'db_datareader', N'cwpuser'
GO

exec sp_addrolemember N'db_datawriter', N'cwpuser'
GO

create table application_user (
    user_id int identity(1000,1) not null,
    username varchar(30) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    password varchar(30) not null,
    creation_date datetime not null default (getDate()),
    email_address varchar(50),
    phone varchar(50),
    fax varchar(50),
    role int not null default(0),
    constraint application_user_pk primary key clustered (user_id),
    constraint username_ak unique nonclustered (username)
)
GO


create table colour_size_description (
    codi_code int not null,
    codi_description char(30),
    constraint colour_size_description_pk primary key clustered (codi_code)
)
GO


create table document (
    id int IDENTITY (1, 1) not null,
    title varchar(50) not null,
    filename varchar(100),
    parentdoc int,
    haschild char(1) not null default('N'),
    type char(1),
    emailto varchar(100),
    mod_date smalldatetime,
    expiry_date smalldatetime,
    category varchar(20),
    constraint document_pk primary key clustered (id)
)
GO


create table hierarchy (
    subclass_code int not null,
    subclass_name varchar(50) not null,
    class_code int not null,
    class_name varchar(50) not null,
    subdept_code int not null,
    subdept_name varchar(50) not null,
    dept_code int not null,
    dept_name varchar(50) not null,
    division_code int not null,
    division_name varchar(50) not null,
    constraint hierarchy_pk primary key clustered (subclass_code)
)
GO


create table inventory_store_sku (
    pmm_sku_id int not null,
    store_number int not null,
    quantity int not null,
    qty_in_transit int not null,
    constraint inventory_store_sku_pk primary key clustered ( pmm_sku_id, store_number )
)
GO


create table inventory_store_style (
    pmm_style_id int not null,
    store_number int not null,
    quantity int not null,
    qty_in_transit int not null,
    constraint inventory_store_style_pk primary key clustered (pmm_style_id, store_number )
)
GO


create table item_price_change (
    pmm_style_id int not null,
    store_number int not null,
    date_created datetime not null,
    prc_effective_date datetime not null,
    prc_end_date datetime not null,
    price_priority_name varchar(30) not null,
    list_price money not null,
    previous_price money not null,
    event_price money not null,
    prc_direction smallint not null,
    prc_method varchar(10) not null,
    prc_method_name varchar(50),
    prc_method_number char(15),
    prc_method_2 varchar(10),
    prc_method_name_2 varchar(50),
    prc_method_number_2 char(15),
    prc_mult smallint,
    prc_qty_brk_qty smallint,
    prc_buy_qty int,
    prc_get_qty int,
    prc_get_value money,
    prc_get_type char(1)
)
GO

create clustered index item_price_change_ix1 on item_price_change(pmm_style_id, store_number)
GO


create table notice (
    id int IDENTITY (1, 1) not null,
    name varchar(50),
    description varchar(200),
    document varchar(100),
    creation_date datetime not null default (getDate()),
    constraint notice_pk primary key clustered (id)
)
GO

create table dbo.pricing_issues (
	id int identity (1, 1) not null,
	style_number char (10)  null,
	upc bigint null,
	current_price varchar (15)  null,
	correct_price varchar (15)  null,
	comment varchar (255)  null,
	opened_timestamp datetime not null default(getdate()),
	closed_timestamp datetime null,
	submitted_by varchar (40)  null,
	store_number int null,
	division_name varchar (10)  null,
	constraint pk_pricingissues primary key  clustered (id)
) 
GO

create table sku (
    pmm_sku_id int not null,
    pmm_style_id int not null,
    description varchar(50) not null,
    number char(10) not null,
    colour_code int not null,
    size_code int not null,
    dimension_code int not null,
    notes varchar(200) null,
    constraint sku_pk primary key clustered (pmm_sku_id)
)
GO

create index sku_ix1 on sku(pmm_style_id)
GO
create index sku_ix2 on sku(pmm_sku_id, pmm_style_id)
GO


create table store (
    number int not null,
    full_name varchar(50) not null,
    short_name varchar(50) not null,
    description varchar(50) null,
    address varchar(50) null,
    postal_code varchar(10) null,
    city varchar(20) null,
    province varchar(50) null,
    country varchar(15) null,
    area_code varchar(5),
    phone_number char(10),
    fax_area_code varchar(5),
    fax_number char(10),
    open_date datetime not null,
    close_date datetime,
    chain varchar(10) not null,
    region varchar(10) not null,
    rgm varchar(50) not null,
    rgm_init char(5) not null,
    related_store_number int null,
    constraint store_pk primary key clustered (number)
)
GO


create table style (
    pmm_style_id int not null,
    description varchar(50) not null,
    number char(10) not null,
    vpn char(15) not null,
    vendor_id int not null,
    subclass_id int not null,
    r12_number char(9) null,
    pmm_brand_id int null,
    constraint style_pk primary key clustered (pmm_style_id)
)
GO


create table brand (
    pmm_brand_id int not null,
    brand_code char(10) not null,
    brand_name varchar(50) not null,
    constraint brand_pk primary key clustered (pmm_brand_id)
)
GO

create table upc (
    upc bigint not null,
    pmm_sku_id int not null,
    constraint upc_pk primary key clustered (upc),
    constraint upc_ak unique nonclustered (upc, pmm_sku_id)
)
GO

create index upc_ix1 on upc(pmm_sku_id)
GO


create table user_store (
    user_id int not null,
    store_number int not null,
    constraint user_store_pk primary key clustered (user_id, store_number)
    /* ,
    constraint user_store_user_fk foreign key (user_id) references application_user (user_id),
    constraint user_store_store_fk foreign key (store_number) references store (number) */
)
GO


create table vendor (
    id int not null,
    name varchar(50) not null,
    code varchar(50) not null,
    constraint vendor_pk primary key clustered (id)
)
GO

create table reporter (
	report_id int identity (1, 1) not null,
	server varchar(50) not null,
	user_id int not null,
	report_name varchar(50) not null,
	report_parameters varchar(50) not null,
	url varchar(400) not null,
	pdf_file varchar(128),
	moddate datetime not null default(getdate()),
	status char(1) not null default('Q'),
	email varchar(50),
    constraint reporter_pk primary key clustered (report_id),
)
GO

create  index reporter_ix1 on reporter(server, user_id)
GO

grant insert, update, select on reporter to cwpuser;
GO

/* insert into reporter (server, user_id, report_name, report_parameters, url , pdf_file, moddate, status, email) values(
    'fwpdev.fgl.net',
    '100',
    'Test report',
    'nothing',
    'http://www.apple.com/',
    null,
    getdate(),
    'Q',
    'dschultz@forzani.com'); */

create table functionality (
    id int identity (1, 1) not null,
    description varchar (50) not null,
    constraint functionality_pk primary key clustered (id)
)
GO

grant select on functionality to cwpuser;
GO

create table user_role (
    id int identity (1, 1) not null,
    description varchar (50) not null,
    constraint user_role_pk primary key clustered (id)
)
GO

grant select on user_role to cwpuser;
GO

create table role_functionality (
    user_role_id int not null,
    functionality_id int not null,
    constraint role_functionality_pk primary key clustered (user_role_id, functionality_id),
    constraint role_functionality_functionality_fk foreign key(functionality_id) references functionality (id),
    constraint role_functionality_user_role_fk foreign key(user_role_id) references user_role (id)
)
GO

grant select on role_functionality to cwpuser;
GO

create table user_roles (
    user_id int not null,
    user_role_id int not null,
    constraint user_roles_pk primary key clustered (user_id, user_role_id),
    constraint user_roles_user_fk foreign key (user_id) references application_user(user_id),
    constraint user_roles_user_role_fk foreign key (user_role_id) references user_role(id)
)
GO

grant select, insert, update, delete on user_roles to cwpuser;
GO

create table dbo.pricing_issues (
	id int identity (1, 1) not null,
	style_number char (10)  null,
	upc bigint null,
	current_price varchar (15)  null,
	correct_price varchar (15)  null,
	comment varchar (255)  null,
	opened_timestamp datetime not null default(getdate()),
	closed_timestamp datetime null,
	submitted_by varchar (40)  null,
	store_number int null,
	division_name varchar (10)  null,
	constraint pk_pricingissues primary key  clustered (id)
) 
GO

create view dbo.v_item_price_change
as
select	ipc.*, upc
from	item_price_change ipc
left join
(
	select pmm_style_id, min(upc) as upc
	from sku
	inner join upc on upc.pmm_sku_id = sku.pmm_sku_id
	where len(convert(varchar, upc)) = 12
	and left(convert(varchar, upc), 1) = 4
	group by pmm_style_id
) upc on upc.pmm_style_id = ipc.pmm_style_id
GO

grant select on dbo.v_item_price_change to cwpuser
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE dbo.create_deal_group_report
(
    @start_date datetime,
    @end_date datetime,
    @store int,
    @prc_direction smallint
)
AS

SELECT -- DISTINCT
    i.prc_end_date,
    h.dept_code AS department_code,
    h.class_code,
    h.subclass_code,
    h.subdept_code,
    v.code AS vendor_code,
    v.name AS vendor_style,
    s.description AS style_description,
    i.list_price,
    i.event_price,
    i.price_priority_name,
    iss.quantity AS oh_quantity,
    i.prc_effective_date,
    s.number AS style_number,
    i.store_number,
    i.prc_method,
    i.prc_method_name,
    i.prc_method_number,
    i.prc_method_2,
    i.prc_mult,
    i.prc_method_name_2,
    i.prc_method_number_2,
    i.prc_qty_brk_qty,
    i.prc_buy_qty,
    i.prc_get_qty,
    s.vpn AS vendor_product_number,
    i.date_created,
    i.prc_get_value,
    i.prc_get_type
FROM
    dbo.item_price_change i
    INNER JOIN dbo.inventory_store_style iss ON iss.pmm_style_id = i.pmm_style_id AND iss.store_number = i.store_number
    INNER JOIN dbo.style s ON s.pmm_style_id = i.pmm_style_id
    INNER JOIN dbo.hierarchy h ON h.subclass_code = s.subclass_id
    INNER JOIN dbo.vendor v ON v.id = s.vendor_id
WHERE
    i.prc_method <> 'SP'
    AND i.store_number = @store
    AND i.prc_direction = @prc_direction
    AND i.prc_effective_date BETWEEN @start_date AND @end_date
ORDER BY
    i.prc_effective_date,
    i.prc_end_date,
    i.prc_method,
    i.prc_method_number,
    i.prc_method_2,
    i.prc_method_number_2,
    i.style_number,
    i.PRC_QTY_BRK_QTY
GO
SET QUOTED_IDENTIFIER OFF
GO
SET ANSI_NULLS ON
GO

GRANT  EXECUTE  ON dbo.create_deal_group_report  TO cwpuser
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS OFF
GO

CREATE PROCEDURE dbo.create_item_price_change
(
    @start_date datetime,
    @end_date datetime,
    @store int,
    @prc_direction smallint
)
AS

SELECT
    i.store_number,
    i.prc_effective_date,
    i.prc_end_date,
    i.price_priority_name,
    h.dept_code AS department_code,
    h.subdept_code AS subdepartment_code,
    v.code AS vendor_code,
    v.name AS vendor_style,
    s.number AS style_number,
    s.vpn AS vendor_product_number,
    s.description AS style_description,
--	LEFT(i.list_price,LEN(i.list_price)-1) AS list_price,
--	LEFT(i.event_price,LEN(i.event_price)-1) AS event_price,
    i.list_price,
    i.event_price,
    iss.quantity AS oh_quantity,
    h.dept_name AS department_name,
    h.subdept_name AS class_name,
    @start_date AS start_date,
    @end_date AS end_date,
    h.division_name AS category
FROM
    dbo.item_price_change i
    INNER JOIN dbo.inventory_store_style iss
        ON iss.pmm_style_id = i.pmm_style_id
        AND iss.store_number = i.store_number
    INNER JOIN dbo.style s
        ON s.pmm_style_id = i.pmm_style_id
    INNER JOIN dbo.hierarchy h
        ON h.subclass_code = s.subclass_id
    INNER JOIN dbo.vendor v
        ON v.id = s.vendor_id
WHERE
    i.store_number = @store
    AND i.prc_direction = @prc_direction
    AND i.prc_effective_date  between @start_date and @end_date
    AND i.prc_method = 'SP'
    AND iss.quantity > 0
ORDER BY
    h.division_name,
    i.prc_effective_date,
    i.prc_end_date,
    i.price_priority_name,
    h.dept_code,
    h.subdept_code,
    h.class_code,
    v.code,
    i.style_number
GO
SET QUOTED_IDENTIFIER OFF
GO
SET ANSI_NULLS ON
GO

GRANT  EXECUTE  ON dbo.create_item_price_change  TO cwpuser
GO

set identity_insert application_user on;
insert into application_user(user_id, username, first_name, last_name, password, email_address, phone, role)
values(1, 'admin','System','Administrator','HZsnJeCdpAY=','ebeste@forzani.com','717-1400 x.1',1);
set identity_insert application_user off;

-- TEST DATA
/*
insert into document (title, filename, parentdoc, haschild, type, emailto, mod_date, category)
values ('Claims', null, 3, 'Y', 'H', null, getdate(), 'documents')
-- 3 is the parent document id of header 'Inv Managment

insert into document (title, filename, parentdoc, haschild, type, emailto, mod_date, category)
values ('Claims on Carrier Distributions', '/secure/forms/newCarrierDist.do', 411, 'N', 'F', 'bting@forzani.com', getdate(),'documents');
-- 411 is the parent document id of header 'Claims'
                                                 
insert into document (title, filename, parentdoc, haschild, type, emailto, mod_date, category)
values ('Claims on Damaged Cartons', '/secure/forms/newDamagedCarton.do', 411, 'N', 'F', 'bting@forzani.com', getdate(),'documents');

insert into document (title, filename, parentdoc, haschild, type, emailto, mod_date, category)
values ('Claims on Inter-Store Transfer', '/secure/forms/newInterStoreTransfer.do', 411, 'N', 'F', 'bting@forzani.com', getdate(),'documents');

insert into document (title, filename, parentdoc, haschild, type, emailto, mod_date, category)
values ('Repairs and Maintenance Form', '/secure/forms/newRepairAndMaintenance.do', 46, 'N', 'F', 'bting@forzani.com' getdate(), 'documents');

INSERT INTO dbo.document(title,parentdoc,haschild,type,mod_date,category) 
VALUES('Pricing Issue Reporting',0,'Y','H',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Pricing Issues Form','/secure/forms/newPricingIssues.do',(select id from dbo.document where title = 'Pricing Issue Reporting'),'N','F','supportlink@forzani.com; klessard@forzani.com',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Inventory Adjustment Form','/secure/forms/newInventoryAdj.do',(select id from dbo.document where title = 'Inv Management'),'N','F','inventory@forzani.com',GetDate(),'documents');

INSERT INTO dbo.document(title,filename,parentdoc,haschild,type,emailto,mod_date,category) 
VALUES('Non Warranty Adjustment Form','/secure/forms/newNonWarrantyAdj.do',(select id from dbo.document where title = 'Inv Management'),'N','F','inventory@forzani.com',GetDate(),'documents');

'
*/
