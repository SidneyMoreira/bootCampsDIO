# Instruções de uso

Nesse projeto foi ensinado como desenvolver um BD em MySQL com alguns relacionamentos entre tabelas para aprofundamento nos conceitos do SQL ANSI e fazer o acesso através de API e seu Respectivo backend. Projeto bem simples para fins didáticos.

- Instalar componentes do backend e frontend (npm i)
- Backend na porta 5000
- Frontend na porta 3000
- [ScriptInicial.sql](https://github.com/SidneyMoreira/bootCampsDIO/blob/main/LabsPro/MySqlModelBdLojaJogos/game-shop/ScriptInitial.sql) com as queres para criar o Schema e tabela do projeto.

Na minha versão utilizei o MySQL no docker, então tive que atualizar o driver no NodeJS para 'Mysql2' para poder funcionar. 

Comando para voltar ao pacote 'Mysql' caso o projeto não funcione:

```
npm un mysql2 && npm i mysql
```

