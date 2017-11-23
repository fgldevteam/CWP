if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[item_price_change_staging]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
   DROP TABLE [item_price_change_staging]

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[item_price_change_staging](
	[pmm_style_id] [int] NOT NULL,
	[store_number] [int] NOT NULL,
	[date_created] [datetime] NOT NULL,
	[prc_effective_date] [datetime] NOT NULL,
	[prc_end_date] [datetime] NOT NULL,
	[price_priority_name] [varchar](30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[list_price] [money] NOT NULL,
	[event_price] [money] NOT NULL,
	[prc_direction] [smallint] NOT NULL,
	[prc_method] [varchar](10) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[prc_method_name] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[prc_method_number] [char](15) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[prc_method_2] [varchar](10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[prc_method_name_2] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[prc_method_number_2] [char](15) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[prc_mult] [smallint] NULL,
	[prc_qty_brk_qty] [smallint] NULL,
	[prc_buy_qty] [int] NULL,
	[prc_get_qty] [int] NULL,
	[prc_get_value] [money] NULL,
	[prc_get_type] [char](1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[previous_price] [money] NULL CONSTRAINT [DF_item_price_change_previous_price_staging]  DEFAULT (0)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF

