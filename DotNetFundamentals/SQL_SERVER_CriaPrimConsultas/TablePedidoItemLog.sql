USE [Ecomerce]
GO

ALTER TABLE [dbo].[PedidoItemLog] DROP CONSTRAINT [FK_PedidoItemLog_StatusPedidoItem]
GO

ALTER TABLE [dbo].[PedidoItemLog] DROP CONSTRAINT [FK_PedidoItemLog_PedidoItem]
GO

/****** Object:  Table [dbo].[PedidoItemLog]    Script Date: 05/04/2021 17:19:11 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PedidoItemLog]') AND type in (N'U'))
DROP TABLE [dbo].[PedidoItemLog]
GO

/****** Object:  Table [dbo].[PedidoItemLog]    Script Date: 05/04/2021 17:19:11 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PedidoItemLog](
	[CodigoPedido] [int] NOT NULL,
	[CodigoProduto] [int] NOT NULL,
	[CodigoStatusPedidoItem] [int] NOT NULL,
	[DataMovimento] [datetime] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidoItemLog]  WITH CHECK ADD  CONSTRAINT [FK_PedidoItemLog_PedidoItem] FOREIGN KEY([CodigoPedido], [CodigoProduto])
REFERENCES [dbo].[PedidoItem] ([CodigoPedido], [CodigoProduto])
GO

ALTER TABLE [dbo].[PedidoItemLog] CHECK CONSTRAINT [FK_PedidoItemLog_PedidoItem]
GO

ALTER TABLE [dbo].[PedidoItemLog]  WITH CHECK ADD  CONSTRAINT [FK_PedidoItemLog_StatusPedidoItem] FOREIGN KEY([CodigoStatusPedidoItem])
REFERENCES [dbo].[StatusPedidoItem] ([Codigo])
GO

ALTER TABLE [dbo].[PedidoItemLog] CHECK CONSTRAINT [FK_PedidoItemLog_StatusPedidoItem]
GO

