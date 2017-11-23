create table cap
(
	cap_id int not null constraint pk_cap PRIMARY KEY,
	cap_name varchar(50) not null
)
go

insert into cap (cap_id, cap_name)
values (1, 'Temporary default CAP')
go

alter table store
add cap_id int not null constraint df_store_capid default 1 with values
go

alter table store
drop constraint df_store_capid 
go

create table style_cap_cost
(
	pmm_style_id int not null,
	cap_id int not null,
	cost money not null,
	constraint pk_style_cap_cost PRIMARY KEY (pmm_style_id,cap_id)
)
go
