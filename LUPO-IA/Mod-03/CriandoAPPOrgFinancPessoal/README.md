# 💸 App de Finanças Pessoais Finny com Vibe Coding

Este projeto foi desenvolvido como Desafio de Projetos de Vibe Coding na DIO em parceria com a LUPO utilizando Lovable e o Copilot WEB. A proposta é criar um Aplicativo de organização financeira pessoal baseado em interações em linguagem natural.

```markdown
## PRD Refinado no Copilot

```txt
PRD – Aplicativo de Organização de Finanças Pessoais

Contexto
Criar um aplicativo que permita ao usuário organizar suas finanças pessoais por meio de conversas em linguagem natural.
A proposta é simplificar o controle financeiro, evitando formulários complexos e planilhas manuais.

Problema
Os aplicativos atuais exigem muita entrada manual e oferecem pouca personalização, o que desmotiva os usuários.
O objetivo é oferecer uma experiência conversacional com recomendações automáticas de economia.

Público-Alvo
- Pessoas que desejam iniciar o controle financeiro de forma prática.
- Usuários iniciantes que buscam simplicidade e acessibilidade.
- Todos os perfis de usuários, incluindo pessoas com diferentes níveis de letramento digital, necessidades especiais ou preferências de interação.

Funcionalidades-Chave
1. Registro de gastos via chat em linguagem natural.
2. Classificação automática das transações.
3. Definição e acompanhamento de metas financeiras.
4. Agente Financeiro que fornece dicas de economia personalizadas.
5. Relatórios simples e personalizados para visualização clara dos resultados.
6. Design Universal: interface inclusiva, acessível e adaptável para diferentes perfis de usuários.

Entregável da IA
- Plano de MVP (Produto Mínimo Viável) com:
  - Principais telas (Chat, Metas, Relatórios).
  - Recursos necessários (IA de NLP, categorização automática, motor de recomendações).
  - Esboço de validação inicial (testes com usuários iniciantes e diversos perfis, feedback sobre clareza, acessibilidade e utilidade).
- Linguagem acessível e educativa para guiar o aprendizado do usuário.
```

## Iterações com o Lovable

> Crie um APP de finanças pessoais com base no seguinte PRD (Product Requirements Document) {PRD}

> Tentei criar uma meta "Novo Mackbok", mas ela de início não apareceu na página (/metas) e no componente, adicionei manualmente e mesmo assim não foi adicionado os valores solicitados, aparecendo apenas no total de despesas. A impressão que eu tive foi que apenas o Assistente Financeiro a reconheceu, poderia verificar. Além disso falta a opção de perfil e logout e em relatórios não tem um extrato, somente os gráficos, adicionar proteção quanto acesso indevido a outras contas.
> Outra questão, as categorias por exemplo, Cartões (crédito, debito, VA, VR, et), PIX serão criadas automáticas, como direcionar especificamente valores para elas para controle.

**Resultado final no Lovable:**  
[chat-your-way-finance.lovable.app](https://chat-your-way-finance.lovable.app)

<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/70a5af54-7235-43a0-aa04-8b3ea8134ecd" />
<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/91762f35-86f1-41a7-a980-eba90a2c2ed8" />
<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/cdefbc36-0795-451d-9ca1-c48945c00bdf" />
<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/5a5b35dc-bcd7-4ca7-ac75-4690baa6c84f" />
<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/43614980-4c8e-4357-b9e2-aca802b1a9ed" /> 

---

# ✨ Funcionalidades Finny – Assistente Financeiro Pessoal

### Chat Financeiro (Home)
- Registro de gastos e receitas por linguagem natural (ex: *"Gastei R$50 no mercado via PIX"*)
- Extração automática de valor, categoria, data e método de pagamento pela IA
- Respostas educativas com dicas de economia personalizadas
- Consulta de resumos financeiros por conversa (ex: *"Quanto gastei em alimentação?"*)
- Histórico completo de mensagens

### Metas Financeiras
- Criação de metas via chat ou interface visual (ex: *"Crie uma meta de Viagem R$5000"*)
- Barras de progresso com valor atual vs. objetivo
- Adição de aportes via chat (ex: *"Adicione R$200 na meta Viagem"*)
- Prazo e acompanhamento visual

### Relatórios
- Resumo mensal de receitas e despesas
- Gráfico de pizza por categoria (alimentação, transporte, lazer, saúde, moradia, educação, salário, outros)
- Gráfico de barras comparando meses
- Extrato detalhado com método de pagamento
- Filtros por período (semana, mês, ano)

## Categorias e Métodos de Pagamento
**Categorias automáticas:** Alimentação, Transporte, Saúde, Lazer, Moradia, Educação, Salário, Outros  
**Métodos de pagamento:** PIX, Cartão de Crédito, Cartão de Débito, Vale Alimentação (VA), Vale Refeição (VR), Dinheiro, Transferência  

## Segurança
- Autenticação por email
- Row Level Security (RLS) — cada usuário acessa apenas seus próprios dados
- Dados isolados por conta

## Stack Técnica
- **Frontend:** React 18 + TypeScript + Tailwind CSS + shadcn/ui
- **Backend:** Lovable Cloud (Supabase) — banco de dados, autenticação e edge functions
- **IA:** Lovable AI Gateway (Google Gemini) para processamento de linguagem natural
- **Gráficos:** Recharts

## Design
- Mobile-first e responsivo
- Fonte Nunito
- Tema claro com cores semânticas (verde para receitas, vermelho para despesas)
- Navegação inferior com 3 abas: Chat, Metas, Relatórios
- Acessibilidade: contraste alto, labels em inputs, ícones com texto

---

## Reflexão

### O que funcionou bem?
- Refinar o PRD pelo Copilot foi o ponto chave para ser usado no Lovable.
- Mesmo com créditos limitados na conta free, consegui validar bem o MVP.

### O que não funcionou como o esperado?
- A interação inicial com a função de Metas não funcionou como esperado via Chat.
- Ajustes posteriores resolveram, mas o limite de créditos diários (apenas 3) foi restritivo.

### O que aprendeu sobre conversar com IAs?
- Quanto mais refinamos o prompt, melhores são os resultados.
- Prompts bem estruturados trazem respostas mais satisfatórias logo no início.
