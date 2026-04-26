from google.adk.agents.llm_agent import Agent
from trello import TrelloClient
from dotenv import load_dotenv
from datetime import datetime
from typing import List, Dict, Optional
import os

load_dotenv()

# Configurações
API_KEY = os.getenv('TRELLO_API_KEY')
API_SECRET = os.getenv('TRELLO_API_SECRET')
TOKEN = os.getenv('TRELLO_TOKEN')
BOARD_NAME = 'DIO'

# Mapeamento de status
STATUS_MAP = {
    "a fazer": ["A FAZER", "TO DO", "TODO"],
    "em andamento": ["EM ANDAMENTO", "DOING"],
    "concluído": ["CONCLUÍDO", "CONCLUIDO", "DONE"]
}


class TrelloManager:
    """Gerenciador centralizado de operações do Trello."""
    
    def __init__(self):
        """Inicializa o cliente Trello."""
        self.client = TrelloClient(
            api_key=API_KEY,
            api_secret=API_SECRET,
            token=TOKEN
        )
        self._board = None
        self._lists = None
    
    @property
    def board(self):
        """Obtém o board de forma lazy (apenas quando necessário)."""
        if self._board is None:
            boards = self.client.list_boards()
            self._board = next((b for b in boards if b.name == BOARD_NAME), None)
            if not self._board:
                raise ValueError(f"Board '{BOARD_NAME}' não encontrado")
        return self._board
    
    @property
    def lists(self):
        """Obtém as listas do board de forma lazy."""
        if self._lists is None:
            self._lists = self.board.list_lists()
        return self._lists
    
    def get_list_by_status(self, status: str):
        """
        Obtém uma lista do Trello baseada no status.
        
        Args:
            status: Status desejado (a fazer, em andamento, concluído)
            
        Returns:
            Lista do Trello correspondente ou None
        """
        status_names = STATUS_MAP.get(status.lower(), [])
        return next(
            (lst for lst in self.lists if lst.name.upper() in status_names),
            None
        )
    
    def find_card_by_name(self, card_name: str):
        """
        Busca um card pelo nome em todas as listas.
        
        Args:
            card_name: Nome do card a ser buscado
            
        Returns:
            Tupla (card, lista_origem) ou (None, None) se não encontrado
        """
        for lista in self.lists:
            cards = lista.list_cards()
            card = next(
                (c for c in cards if c.name.lower() == card_name.lower()),
                None
            )
            if card:
                return card, lista
        return None, None


# Instância global do gerenciador
trello_manager = TrelloManager()


def get_temporal_context() -> str:
    """
    Retorna o contexto temporal atual (data e hora).
    
    Returns:
        String formatada com data e hora atual
    """
    now = datetime.now()
    return now.strftime('%Y/%m/%d %H:%M:%S')


def adicionar_tarefa(nome_da_task: str, descricao_da_task: str, due_date: str) -> str:
    """
    Adiciona uma nova tarefa na lista 'A Fazer' do Trello.
    
    Args:
        nome_da_task: Nome/título da tarefa
        descricao_da_task: Descrição detalhada da tarefa
        due_date: Data de vencimento (formato: YYYY-MM-DD ou YYYY-MM-DD HH:MM)
        
    Returns:
        Mensagem de confirmação ou erro
    """
    try:
        lista_todo = trello_manager.get_list_by_status("a fazer")
        
        if not lista_todo:
            return "❌ Lista 'A Fazer' não encontrada no board"
        
        # Criar o card
        lista_todo.add_card(
            name=nome_da_task,
            desc=descricao_da_task,
            due=due_date
        )
        
        return f"✅ Tarefa '{nome_da_task}' adicionada com sucesso!"
    
    except Exception as e:
        return f"❌ Erro ao adicionar tarefa: {str(e)}"


def listar_tarefas(status: str = "todas") -> List[Dict]:
    """
    Lista tarefas do Trello, podendo filtrar por status.
    
    Args:
        status: Filtro de status (todas, a fazer, em andamento, concluído)
        
    Returns:
        Lista de dicionários com informações das tarefas
    """
    try:
        # Filtrar listas baseado no status
        if status.lower() == "todas":
            listas_filtradas = trello_manager.lists
        else:
            lista = trello_manager.get_list_by_status(status)
            listas_filtradas = [lista] if lista else []
        
        tarefas = []
        for lista in listas_filtradas:
            cards = lista.list_cards()
            for card in cards:
                tarefas.append({
                    "nome": card.name,
                    "descricao": card.desc,
                    "vencimento": card.due,
                    "status": lista.name,
                    "id": card.id
                })
        
        return tarefas
    
    except Exception as e:
        return [{"erro": f"Erro ao listar tarefas: {str(e)}"}]


def mudar_status_tarefa(nome_da_task: str, novo_status: str) -> str:
    """
    Move uma tarefa para uma lista diferente (muda o status).
    
    Args:
        nome_da_task: Nome da tarefa a ser movida
        novo_status: Novo status (a fazer, em andamento, concluído)
        
    Returns:
        Mensagem de confirmação ou erro
    """
    try:
        # Validar status
        if novo_status.lower() not in STATUS_MAP:
            return f"❌ Status inválido. Use: 'a fazer', 'em andamento' ou 'concluído'"
        
        # Encontrar lista de destino
        lista_destino = trello_manager.get_list_by_status(novo_status)
        if not lista_destino:
            return f"❌ Lista para status '{novo_status}' não encontrada no board"
        
        # Buscar card
        card_encontrado, lista_origem = trello_manager.find_card_by_name(nome_da_task)
        if not card_encontrado:
            return f"❌ Tarefa '{nome_da_task}' não encontrada"
        
        # Mover card
        card_encontrado.change_list(lista_destino.id)
        return f"✅ '{nome_da_task}': {lista_origem.name} → {lista_destino.name}"
    
    except Exception as e:
        return f"❌ Erro ao mudar status: {str(e)}"


def verificar_tarefas_atrasadas() -> str:
    """
    Verifica e lista todas as tarefas que estão atrasadas.
    
    Returns:
        Mensagem formatada com tarefas atrasadas ou confirmação de que não há atrasos
    """
    try:
        todas_tarefas = listar_tarefas("todas")
        agora = datetime.now()
        tarefas_atrasadas = []
        
        for tarefa in todas_tarefas:
            # Verificar se tem data de vencimento
            if tarefa.get("vencimento"):
                try:
                    # Converter string de data para datetime
                    due_date = datetime.fromisoformat(tarefa["vencimento"].replace('Z', '+00:00'))
                    
                    # Verificar se está atrasada e não está concluída
                    if due_date < agora and tarefa["status"].upper() not in STATUS_MAP["concluído"]:
                        dias_atraso = (agora - due_date).days
                        tarefas_atrasadas.append({
                            "nome": tarefa["nome"],
                            "vencimento": due_date.strftime("%d/%m/%Y %H:%M"),
                            "dias_atraso": dias_atraso,
                            "status": tarefa["status"]
                        })
                except (ValueError, AttributeError):
                    continue
        
        # Formatar resposta
        if not tarefas_atrasadas:
            return "✅ Parabéns! Não há tarefas atrasadas."
        
        mensagem = f"⚠️ ATENÇÃO! Você tem {len(tarefas_atrasadas)} tarefa(s) atrasada(s):\n\n"
        
        for i, tarefa in enumerate(tarefas_atrasadas, 1):
            dias = tarefa['dias_atraso']
            texto_dias = f"{dias} dia(s)" if dias > 0 else "hoje"
            mensagem += f"{i}. 📌 {tarefa['nome']}\n"
            mensagem += f"   Vencimento: {tarefa['vencimento']}\n"
            mensagem += f"   Atraso: {texto_dias}\n"
            mensagem += f"   Status atual: {tarefa['status']}\n\n"
        
        return mensagem
    
    except Exception as e:
        return f"❌ Erro ao verificar tarefas atrasadas: {str(e)}"


def remover_tarefa(nome_da_task: str) -> str:
    """
    Remove uma tarefa do Trello.
    
    Args:
        nome_da_task: Nome da tarefa a ser removida
        
    Returns:
        Mensagem de confirmação ou erro
    """
    try:
        card_encontrado, lista_origem = trello_manager.find_card_by_name(nome_da_task)
        
        if not card_encontrado:
            return f"❌ Tarefa '{nome_da_task}' não encontrada"
        
        card_encontrado.delete()
        return f"✅ Tarefa '{nome_da_task}' removida com sucesso!"
    
    except Exception as e:
        return f"❌ Erro ao remover tarefa: {str(e)}"


# Configuração do agente
root_agent = Agent(
    model='gemini-2.5-flash',
    name='root_agent',
    description='Agente Inteligente de Organização de Tarefas',
    instruction="""
Você é um agente inteligente de organização de tarefas integrado com o Trello.
Sua missão é ajudar o usuário a gerenciar suas atividades de forma eficiente e proativa.

COMPORTAMENTO INICIAL:
1. Ao ser ativado, cumprimente o usuário e informe a data/hora atual usando get_temporal_context
2. Pergunte quais são as tarefas do dia
3. IMPORTANTE: Sempre verifique tarefas atrasadas usando verificar_tarefas_atrasadas antes de perguntar sobre novas tarefas
4. Se houver tarefas atrasadas, alerte o usuário e sugira priorizá-las

FUNCIONALIDADES DISPONÍVEIS:
✅ Adicionar novas tarefas com nome, descrição e prazo
✅ Listar todas as tarefas ou filtrar por status (a fazer, em andamento, concluído)
✅ Mudar status de tarefas (mover entre listas)
✅ Verificar tarefas atrasadas automaticamente
✅ Remover tarefas da lista
✅ Fornecer contexto temporal para organização

FLUXO DE TRABALHO:
1. Pergunte sobre novas tarefas do dia
2. Para cada tarefa mencionada, solicite:
   - Nome da tarefa (obrigatório)
   - Descrição detalhada (obrigatório)
   - Data/hora de vencimento (obrigatório - formato: YYYY-MM-DD ou YYYY-MM-DD HH:MM)
3. Continue perguntando se há mais tarefas até o usuário confirmar que não há
4. Ofereça verificar o status das tarefas existentes
5. Seja proativo em alertar sobre prazos e sugerir priorização

DIRETRIZES:
- Seja cordial, eficiente e proativo
- Sempre confirme ações realizadas com mensagens claras
- Alerte sobre tarefas atrasadas de forma educada mas clara
- Sugira boas práticas de organização quando apropriado
- Use emojis para tornar a interação mais amigável (✅ ⚠️ 📌 🎯)
""",
    tools=[
        get_temporal_context,
        adicionar_tarefa,
        listar_tarefas,
        mudar_status_tarefa,
        verificar_tarefas_atrasadas,
        remover_tarefa
    ],
)
