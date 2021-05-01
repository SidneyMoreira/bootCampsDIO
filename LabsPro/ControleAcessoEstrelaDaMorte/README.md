# Criando um gerenciador de espaçonaves do star wars com SQL Server + .NET



Nesse Lab foi ensinado como modelar o BD SQL Server com suas respectivas tabelas para o gerenciamento de espaçonaves Star Wars, explicando os conceitos básicos de SQL (CREATE DATABASE, CREATE TABLE, CONSTRAINTS, etc) e para ficar de forma visual foi desenvolvimento um APP em WinForm .Net Core 5 bem básico para realizar a sincronização dos dados através de uma API (Star Wars -  Swapi.dev).

###### Antes de executar necessário ter o SQL SERVER instalado na maquina ou em um DOCKER e criar as tabelas abaixo:

1º DATABASE:

```sql
CREATE DATABASE EstrelaDaMorte;
```

2º Tabela Planetas:

```sql
CREATE TABLE Planetas(
    IdPlaneta INT NOT NULL,
    Nome VARCHAR(50) NOT NULL,
    Rotacao FLOAT NOT NULL,
    Orbita FLOAT NOT NULL,
    Diametro FLOAT NOT NULL,
    Clima VARCHAR(50) NOT NULL,
    Populacao INT NOT NULL
)
GO
ALTER TABLE Planetas ADD CONSTRAINT PK_Planetas PRIMARY KEY (IdPlaneta);
```

3º Tabela Naves:

```sql
CREATE TABLE Naves(
    IdNave INT NOT NULL,
    Nome VARCHAR(100) NOT NULL,
    Modelo VARCHAR(150) NOT NULL,
    Passageiros int NOT NULL,
    Carga FLOAT NOT NULL,
    Classe VARCHAR(100) NOT NULL,
    Populacao INT NOT NULL
)
GO
ALTER TABLE Naves ADD CONSTRAINT PK_Naves PRIMARY KEY (IdNave);
```

4º Tabela Pilotos:

```sql
CREATE TABLE Pilotos(
    IdPiloto INT NOT NULL,
    Nome VARCHAR(200) NOT NULL,
    AnoNascimento VARCHAR(10) NOT NULL,
    IdPlaneta INT NOT NULL
)
GO
ALTER TABLE Pilotos ADD CONSTRAINT PK_Pilotos PRIMARY KEY (IdPiloto);
GO
ALTER TABLE Pilotos ADD CONSTRAINT FK_Pilotos_Planetas FOREIGN KEY (IdPlaneta) REFERENCES Planetas (IdPlaneta);
```

5º Tabela PilotosNaves:

```sql
CREATE TABLE PilotosNaves(
    IdPiloto INT NOT NULL,
    IdNave INT NOT NULL,
    FlagAutorizado BIT NOT NULL
)
GO
ALTER TABLE PilotosNaves ADD CONSTRAINT PK_PilotosNaves PRIMARY KEY (IdPiloto, IdNave);
GO
ALTER TABLE PilotosNaves ADD CONSTRAINT FK_PilotosNaves_Pilotos FOREIGN KEY (IdPiloto) REFERENCES Pilotos (IdPiloto);
GO
ALTER TABLE PilotosNaves ADD CONSTRAINT FK_PilotosNaves_Naves FOREIGN KEY (IdNave) REFERENCES Naves (IdNave);
GO
ALTER TABLE PilotosNaves ADD CONSTRAINT DF_PilotosNaves_FlagAutorizado DEFAULT (1) FOR FlagAutorizado;
```

6º Tabela HistoricoViagens:

```sql
CREATE TABLE HistoricoViagens(
    IdNave INT NOT NULL,
    IdPiloto INT NOT NULL,
    DtSaida DATETIME NOT NULL,
    DtChegada DATETIME NULL
)
GO
ALTER TABLE HistoricoViagens ADD CONSTRAINT FK_HistoricoViagens_PilotosNaves FOREIGN KEY (IdPiloto, IdNave) REFERENCES PilotosNaves (IdPiloto, IdNave);
GO
ALTER TABLE HistoricoViagens CHECK CONSTRAINT FK_HistoricoViagens_PilotosNaves;
```

