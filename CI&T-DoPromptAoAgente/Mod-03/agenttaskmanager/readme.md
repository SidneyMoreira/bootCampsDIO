# 🤖 Agente Inteligente de Tarefas com Trello

Sistema automatizado de gerenciamento de tarefas que integra a Google ADK com a API do Trello, permitindo organização eficiente através de conversação natural com IA.

## 📋 Índice

- [Características](#-características)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração do Trello](#-configuração-do-trello)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Uso](#-uso)
- [Funcionalidades](#-funcionalidades)
- [Arquitetura](#-arquitetura)
- [Solução de Problemas](#-solução-de-problemas)
- [Contribuindo](#-contribuindo)

---

## ✨ Características

- ✅ **Gerenciamento conversacional** - Interaja com suas tarefas usando linguagem natural
- 📅 **Controle de prazos** - Sistema automático de alertas para tarefas atrasadas
- 🔄 **Fluxo Kanban** - Movimentação de tarefas entre diferentes status
- 🎯 **Organização inteligente** - Sugestões proativas baseadas em contexto
- ⏰ **Contexto temporal** - Reconhecimento automático de data e hora
- 🔐 **Segurança** - Credenciais gerenciadas via variáveis de ambiente

---

## 🔧 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Python 3.7+** ([Download](https://www.python.org/downloads/))
- **pip** (gerenciador de pacotes Python)
- **Conta ativa no Trello** ([Criar conta](https://trello.com/signup))
- **Navegador web** atualizado

---

## 📦 Instalação

### 1. Clone ou baixe o projeto

```bash
git clone <seu-repositorio>
cd <nome-do-projeto>
```

### 2. Crie um ambiente virtual (recomendado)

```bash
# Windows
python -m venv venv
venv\Scripts\activate

# Linux/Mac
python3 -m venv venv
source venv/bin/activate
```

### 3. Instale as dependências

```bash
pip install -r requirements.txt
```

**Conteúdo do `requirements.txt`:**
```txt
google-adk
py-trello
python-dotenv
```

> ⚠️ **Nota:** O pacote `datetime` é built-in do Python e não precisa ser instalado separadamente.

---

## 🔑 Configuração do Trello

### Passo 1: Criar um Power-Up (Aplicativo)

#### 1.1 Acessar o Portal de Desenvolvedores

1. Acesse: [https://trello.com/power-ups/admin/](https://trello.com/power-ups/admin/)
2. Faça login com sua conta Trello
3. Clique em **"New"** ou **"Criar novo Power-Up"**

#### 1.2 Preencher Informações do Aplicativo

| Campo | Valor de Exemplo | Descrição |
|-------|------------------|-----------|
| **Nome** | `Agente de Tarefas AI` | Identificação do seu aplicativo |
| **Workspace** | Seu workspace principal | Onde o app será gerenciado |
| **Email de suporte** | `seu-email@example.com` | Contato para suporte |
| **Autor** | `Seu Nome` | Desenvolvedor/Empresa |
| **URL do iframe** | `https://placeholder.com` | Pode ser placeholder para API |

#### 1.3 Criar e Obter Credenciais

1. Clique em **"Criar"**
2. Na página do Power-Up, localize a seção **"API Key"**
3. **Copie e guarde:**
   - ✅ **API Key** (string alfanumérica longa)
   - ✅ **API Secret** (string secreta)

**Formato esperado:**
```
API Key: abc123def456ghi789jkl012mno345pqr678
Secret:  xyz987wvu654tsr321qpo098nml765kji432
```

---

### Passo 2: Gerar Token de Autorização

#### 2.1 Construir URL de Autorização

Substitua `SUA_API_KEY_AQUI` pela sua API Key real:

```
https://trello.com/1/authorize?expiration=never&name=AgenteTarefasAI&scope=read,write&response_type=token&key=SUA_API_KEY_AQUI
```

#### 2.2 Parâmetros Explicados

| Parâmetro | Valor | Opções Disponíveis |
|-----------|-------|-------------------|
| `expiration` | `never` | `1hour`, `1day`, `30days`, `never` |
| `name` | `AgenteTarefasAI` | Nome do seu app |
| `scope` | `read,write` | `read`, `write`, `account` |
| `response_type` | `token` | Sempre `token` |
| `key` | `<sua-api-key>` | API Key do Passo 1 |

#### 2.3 Autorizar e Obter Token

1. **Cole a URL completa** no navegador
2. Revise as permissões solicitadas
3. Clique em **"Permitir"** ou **"Allow"**
4. **Copie o Token** exibido na tela (string alfanumérica longa)

**Formato do Token:**
```
a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2
```

> ⚠️ **IMPORTANTE:** Guarde este token em local seguro! Se perdido, será necessário gerar um novo.

---

### Passo 3: Configurar o Board no Trello

#### 3.1 Criar o Board

1. Acesse [Trello](https://trello.com)
2. Crie um novo board chamado **"DIO"** (ou altere `BOARD_NAME` no código)

#### 3.2 Criar as Listas Necessárias

Crie as seguintes listas no board (ordem sugerida):

1. **A FAZER** (ou TO DO)
2. **EM ANDAMENTO** (ou DOING)
3. **CONCLUÍDO** (ou DONE)

> 💡 **Dica:** O agente reconhece automaticamente variações dos nomes (maiúsculas/minúsculas).

---

## ⚙️ Configuração do Ambiente

### 1. Criar arquivo `.env`

Copie o arquivo de exemplo:

```bash
cp _env.exemplo .env
```

### 2. Preencher as credenciais

Edite o arquivo `.env` com suas credenciais:

```env
# Google Gemini API
GOOGLE_GENAI_USE_VERTEXAI=0
GOOGLE_API_KEY=sua-chave-google-api-aqui

# Trello API
TRELLO_API_KEY=sua-api-key-do-trello
TRELLO_API_SECRET=seu-secret-do-trello
TRELLO_TOKEN=seu-token-do-trello
```

### 3. Obter Google API Key

1. Acesse [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Clique em **"Create API Key"**
3. Copie a chave e cole em `GOOGLE_API_KEY`

---

## 📁 Estrutura do Projeto

```
projeto/
│
├── agent.py              # Código principal do agente
├── requirements.txt      # Dependências Python
├── .env                  # Credenciais (NÃO versionar!)
├── _env.exemplo          # Template de credenciais
├── README.md             # Esta documentação
│
└── venv/                 # Ambiente virtual (criado por você)
```

---

## 🚀 Uso

### Executar o Agente

```bash
python agent.py
```

### Exemplo de Interação

```
🤖 Agente: Olá! Hoje é 2026/04/24 14:30:00. Quais são suas tarefas para hoje?

👤 Você: Preciso estudar Python e fazer compras

🤖 Agente: Ótimo! Vamos adicionar essas tarefas. 
         Primeiro, me conte mais sobre "estudar Python":
         - Descrição detalhada?
         - Qual o prazo? (formato: YYYY-MM-DD HH:MM)

👤 Você: Estudar conceitos avançados de POO, prazo: 2026-04-25 18:00

🤖 Agente: ✅ Tarefa 'Estudar Python' adicionada com sucesso!
         Agora sobre "fazer compras"...
```

---

## 🛠️ Funcionalidades

### 1️⃣ Adicionar Tarefa

```python
adicionar_tarefa(
    nome_da_task="Estudar Python",
    descricao_da_task="Revisar POO e design patterns",
    due_date="2026-04-25 18:00"
)
```

**Resultado:** ✅ Tarefa adicionada na lista "A FAZER"

---

### 2️⃣ Listar Tarefas

```python
# Listar todas
tarefas = listar_tarefas("todas")

# Filtrar por status
tarefas_pendentes = listar_tarefas("a fazer")
em_andamento = listar_tarefas("em andamento")
concluidas = listar_tarefas("concluido")
```

**Retorno:**
```python
[
    {
        "nome": "Estudar Python",
        "descricao": "Revisar POO",
        "vencimento": "2026-04-25T18:00:00Z",
        "status": "A FAZER",
        "id": "abc123"
    }
]
```

---

### 3️⃣ Mudar Status

```python
mudar_status_tarefa(
    nome_da_task="Estudar Python",
    novo_status="em andamento"
)
```

**Resultado:** ✅ 'Estudar Python': A FAZER → EM ANDAMENTO

---

### 4️⃣ Verificar Tarefas Atrasadas ⚠️

```python
verificar_tarefas_atrasadas()
```

**Saída:**
```
⚠️ ATENÇÃO! Você tem 2 tarefa(s) atrasada(s):

1. 📌 Estudar Python
   Vencimento: 24/04/2026 18:00
   Atraso: 2 dia(s)
   Status atual: EM ANDAMENTO

2. 📌 Fazer compras
   Vencimento: 23/04/2026 10:00
   Atraso: 3 dia(s)
   Status atual: A FAZER
```

---

### 5️⃣ Remover Tarefa

```python
remover_tarefa("Estudar Python")
```

**Resultado:** ✅ Tarefa 'Estudar Python' removida com sucesso!

---

### 6️⃣ Contexto Temporal

```python
get_temporal_context()
```

**Retorno:** `"2026/04/24 14:30:00"`

---

## 🏗️ Arquitetura

### Classe `TrelloManager`

Gerenciador centralizado que implementa:

- ✅ **Lazy Loading** - Recursos carregados apenas quando necessários
- ✅ **Cache de objetos** - Evita requisições desnecessárias à API
- ✅ **Métodos reutilizáveis** - Eliminação de código duplicado
- ✅ **Tratamento de erros** - Exceções claras e informativas

**Benefícios:**
- 🚀 Melhor performance
- 🔧 Código mais limpo e manutenível
- 🐛 Menos bugs por reutilização
- 📊 Facilita testes unitários

### Padrões Aplicados

1. **Singleton Pattern** - Uma única instância do `TrelloManager`
2. **Factory Pattern** - Métodos para criar/buscar recursos
3. **DRY (Don't Repeat Yourself)** - Funções reutilizáveis
4. **Error Handling** - Try-catch em todas as operações críticas

---

## 🔍 Solução de Problemas

### Erro: "Board 'DIO' não encontrado"

**Causa:** Nome do board não corresponde ao configurado

**Solução:**
```python
# Em agent.py, altere:
BOARD_NAME = 'DIO'  # Para o nome do seu board
```

---

### Erro: "Lista 'A Fazer' não encontrada"

**Causa:** Nomes das listas não correspondem aos esperados

**Solução:** Verifique se as listas usam um dos nomes aceitos:
- A FAZER / TO DO / TODO
- EM ANDAMENTO / DOING
- CONCLUÍDO / CONCLUIDO / DONE

---

### Erro: "Unauthorized" ou "Invalid Token"

**Causa:** Credenciais inválidas ou expiradas

**Solução:**
1. Verifique o arquivo `.env`
2. Gere um novo token seguindo o Passo 2
3. Certifique-se de que a API Key e Secret estão corretos

---

### Erro: "Module not found"

**Causa:** Dependências não instaladas

**Solução:**
```bash
pip install -r requirements.txt
```

---

### Tarefas atrasadas não detectadas

**Causa:** Formato de data incorreto

**Solução:** Use o formato ISO 8601:
```
YYYY-MM-DD          # Apenas data
YYYY-MM-DD HH:MM    # Data e hora
```

**Exemplos válidos:**
```
2026-04-25
2026-04-25 14:30
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

---

## 📚 Recursos Adicionais

### Documentação Oficial

- [Google ADK](https://developers.google.com/adk)
- [Trello API](https://developer.atlassian.com/cloud/trello/)
- [py-trello GitHub](https://github.com/sarumont/py-trello)
- [Gemini API](https://ai.google.dev/)

### Bibliotecas Utilizadas

| Biblioteca | Versão | Descrição |
|------------|--------|-----------|
| `google-adk` | Latest | Framework de agentes AI do Google |
| `py-trello` | Latest | Cliente Python para API do Trello |
| `python-dotenv` | Latest | Gerenciamento de variáveis de ambiente |

---

## 📝 Changelog

### v2.0.0 (2026-04-24)

#### 🎉 Novidades
- ✨ Nova função `verificar_tarefas_atrasadas()` com alertas automáticos
- ✨ Função `remover_tarefa()` para deletar tarefas
- 🏗️ Classe `TrelloManager` para centralizar operações
- 📊 Melhor formatação de mensagens com emojis

#### 🔧 Melhorias
- ♻️ Refatoração completa eliminando duplicação de código
- 🚀 Implementação de lazy loading para melhor performance
- 📝 Documentação expandida com exemplos práticos
- 🐛 Tratamento robusto de erros em todas as funções
- 🎯 Instrução do agente mais clara e proativa

#### 🔨 Correções
- 🐛 Correção no requirements.txt (removido `datetime` built-in)
- 🔐 Melhoria na segurança com validações de entrada

---

## 📄 Licença

Este projeto é fornecido como está, para fins educacionais e de demonstração.

---

## 👥 Autores

- **Desenvolvedor Principal** - [Seu Nome]
- **Documentação** - [Contribuidores]

---

## 🙏 Agradecimentos

- Google ADK Team pelo framework robusto
- Atlassian pela API do Trello
- Comunidade open-source do py-trello

---

**Última atualização:** Abril 2026  
**Versão da API Trello:** v1  
**Python:** 3.7+  
**Status:** ✅ Produção

---

<div align="center">

### ⭐ Se este projeto foi útil, considere dar uma estrela!

**[⬆ Voltar ao topo](#-agente-inteligente-de-tarefas-com-trello)**

</div>
