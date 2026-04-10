# Mentor de Carreira em Tecnologia

Projeto de estudo da DIO para criar um agente de IA capaz de ajudar pessoas a descobrir e planejar uma carreira em tecnologia.

## Visão geral

Este projeto tem dois agentes principais:

- `AGENT 1 - Entrevistador de Carreira em Tecnologia`
  - Faz uma entrevista estruturada de 7 perguntas para entender o perfil, interesses, disponibilidade e objetivos do usuário.
  - Com base nas respostas, sugere as 3 carreiras mais adequadas e prepara o handoff para o segundo agente.

- `AGENT 2 - Planejador de Carreiras`
  - Recebe a carreira escolhida e o perfil do usuário.
  - Gera um plano de estudos completo, incluindo visão do dia a dia, mapa de skills, roadmap de 90 dias, projeto de portfólio, roteiro de entrevistas e trilha DIO recomendada.

## Como usar

Use o fluxo abaixo diretamente no chat, acionando cada agente na sequência correta.

### 1. Iniciar com o Agente 1

1. Abra o chat do agente.
2. Inicie a conversa com o prompt do `AGENT 1 - Entrevistador de Carreira em Tecnologia`.
3. Responda às perguntas feitas pelo agente.
4. Aguarde o agente fazer apenas uma pergunta por vez.
5. Após a 7ª pergunta, o agente fará a análise e apresentará as 3 carreiras recomendadas.
6. Escolha a carreira que mais chamou atenção.

### 2. Transferir para o Agente 2

1. Depois de escolher a carreira no Agente 1, solicite a transferência para o `AGENT 2 - Planejador de Carreiras`.
2. Certifique-se de informar:
   - carreira escolhida
   - horas disponíveis por semana
   - nível de experiência (zero / iniciante / alguma)
   - objetivo (primeiro emprego / transição / crescimento)
   - preferência de trabalho (pessoas / dados / código)
   - interesses tecnológicos mencionados
3. Abra o chat do Agente 2 e cole as informações resumidas.
4. O agente 2 deve gerar um plano de estudos completo e personalizado.

## Uso direto no chat

Siga este passo a passo sempre que quiser usar o projeto:

1. Copie o conteúdo de `AGENT 1 - Entrevistador de Carreira em Tecnologia` e cole no chat.
2. Responda às perguntas do entrevistador.
3. Quando o agente finalizar sugerindo as 3 carreiras, escolha a melhor opção.
4. Copie o conteúdo de `AGENT 2 - Planejador de Carreiras` e cole no chat.
5. Adicione as informações do seu perfil e da carreira escolhida.
6. Peça ao Agente 2 para montar o plano completo.

## Estrutura do projeto

- `AGENT 1 - Entrevistador de Carreira em Tecnologia`
  - Prompt do agente de entrevista
- `AGENT 2 - Planejador de Carreiras`
  - Prompt do agente de planejamento

## Dicas

- Use o Agente 1 apenas para descobrir o perfil e escolher a carreira.
- Não pule perguntas: o roteiro funciona melhor com todas as respostas.
- O Agente 2 é responsável por montar o plano final de estudos.
- Mantenha o diálogo claro e peça para o agente seguir o roteiro definido.

## Resultado esperado

- Um perfil de carreira atraente e bem fundamentado.
- Uma sugestão de 3 carreiras em tecnologia.
- Um plano de estudos personalizado para a carreira escolhida.

Bom uso e bons estudos!