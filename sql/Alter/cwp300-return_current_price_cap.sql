create table style_receiptless_return_price
(
	pmm_style_id int not null,
	cap_id int not null,
	return_price money not null,
	constraint pk_rec_ret_price PRIMARY KEY (pmm_style_id,cap_id)
)
go

alter table style
drop column receiptless_return_price
go

alter table item_current_prices
drop constraint pmm_style_id_idx
go

alter table item_current_prices
add cap_id int not null constraint df_item_price_capid default 0 with values,
constraint unique_item_price UNIQUE (pmm_style_id,store_number,cap_id)
go

alter table item_current_prices
drop constraint df_item_price_capid
go
