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

set identity_insert user_role on;
insert into user_role (id, description) values (8, 'Pricing Issues Manager');
set identity_insert user_role off;

set identity_insert functionality on;
insert into functionality (id, description) values (16, 'View Pricing Issues');
insert into functionality (id, description) values (17, 'Edit Pricing Issues');
set identify_insert functionality off;


insert into role_functionality (user_role_id, functionality_id) values (1, 16);
insert into role_functionality (user_role_id, functionality_id) values (1, 17);

insert into role_functionality (user_role_id, functionality_id) values (8, 16);
insert into role_functionality (user_role_id, functionality_id) values (8, 17);




