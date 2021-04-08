USE [Ecomerce]
GO

ALTER TABLE [dbo].[Pedido] DROP CONSTRAINT [FK_Status_Pedido]
GO

ALTER TABLE [dbo].[Pedido] DROP CONSTRAINT [FK_Clientes_Pedido]
GO

/****** Object:  Table [dbo].[Pedido]    Script Date: 05/04/2021 17:18:35 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pedido]') AND type in (N'U'))
DROP TABLE [dbo].[Pedido]
GO

/****** Object:  Table [dbo].[Pedido]    Script Date: 05/04/2021 17:18:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Pedido](
	[Codigo] [int] IDENTITY(1,1) NOT NULL,
	[DataSolicitacao] [datetime] NOT NULL,
	[FlagPago] [bit] NOT NULL,
	[TotalPedido] [float] NOT NULL,
	[CodigoCliente] [int] NOT NULL,
	[CodigoStatus] [int] NOT NULL,
 CONSTRAINT [PK_Pedido] PRIMARY KEY CLUSTERED 
(
	[Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Pedido]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Pedido] FOREIGN KEY([CodigoCliente])
REFERENCES [dbo].[Clientes] ([Codigo])
GO

ALTER TABLE [dbo].[Pedido] CHECK CONSTRAINT [FK_Clientes_Pedido]
GO

ALTER TABLE [dbo].[Pedido]  WITH CHECK ADD  CONSTRAINT [FK_Status_Pedido] FOREIGN KEY([CodigoStatus])
REFERENCES [dbo].[Status] ([Codigo])
GO

ALTER TABLE [dbo].[Pedido] CHECK CONSTRAINT [FK_Status_Pedido]
GO

