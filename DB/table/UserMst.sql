USE [ysjDB]
GO

/****** Object:  Table [dbo].[USER_MST]    Script Date: 2022-03-05 오후 6:57:00 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[USER_MST](
	[USER_ID] [varchar](50) NULL,
	[USER_NM] [varchar](10) NULL,
	[USER_PW] [varchar](10) NULL,
	[INS_DT] [date] NULL,
	[ADDRESS] [varchar](10) NULL,
	[PH_NUMBER] [varchar](10) NULL
) ON [PRIMARY]
GO

