USE [Ecomerce]
GO

ALTER TABLE [dbo].[PedidoItem] DROP CONSTRAINT [FK_PedidoItem_Produtos]
GO

ALTER TABLE [dbo].[PedidoItem] DROP CONSTRAINT [FK_PedidoItem_Pedido]
GO

/****** Object:  Table [dbo].[PedidoItem]    Script Date: 05/04/2021 17:18:50 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PedidoItem]') AND type in (N'U'))
DROP TABLE [dbo].[PedidoItem]
GO

/****** Object:  Table [dbo].[PedidoItem]    Script Date: 05/04/2021 17:18:50 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PedidoItem](
	[CodigoPedido] [int] NOT NULL,
	[CodigoProduto] [int] NOT NULL,
	[Preco] [float] NOT NULL,
	[Quantidade] [int] NOT NULL,
 CONSTRAINT [PK_PedidoItem] PRIMARY KEY CLUSTERED 
(
	[CodigoPedido] ASC,
	[CodigoProduto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidoItem]  WITH CHECK ADD  CONSTRAINT [FK_PedidoItem_Pedido] FOREIGN KEY([CodigoPedido], [CodigoProduto])
REFERENCES [dbo].[PedidoItem] ([CodigoPedido], [CodigoProduto])
GO

ALTER TABLE [dbo].[PedidoItem] CHECK CONSTRAINT [FK_PedidoItem_Pedido]
GO

ALTER TABLE [dbo].[PedidoItem]  WITH CHECK ADD  CONSTRAINT [FK_PedidoItem_Produtos] FOREIGN KEY([CodigoProduto])
REFERENCES [dbo].[Produtos] ([Codigo])
GO

ALTER TABLE [dbo].[PedidoItem] CHECK CONSTRAINT [FK_PedidoItem_Produtos]
GO

