--CWP303 addition and population of the SumTotal department table

USE CWP

create table dbo.sum_total_dept (
	id int IDENTITY not null,
	dept_abbrv varchar(5) not null,
	dept_desc varchar(50) not null,
	constraint sum_dept_pk primary key (id),
	constraint sum_dept_abbr_ak unique (dept_abbrv)
)
GO

-- insert the department data rows

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('MGR','Management')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('SGA','Softgoods Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('HGA','Hardgoods Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('CMS','CMS Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('NBG','NB/Golf Experts Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('CAS','Cash Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('SHI','Shipping Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('SER','Service Shop Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('VIS','Visual Associate')
GO

insert into sum_total_dept (dept_abbrv,dept_desc ) values ('OTH','Seasonal Associate')
GO