from models import Pessoas, Usuarios

def insere_pessoas():
    pessoa = Pessoas(nome='Moreira', idade=40)
    print(pessoa)
    pessoa.save()

def consulta_pessoas():
    pessoas = Pessoas.query.all()   
    print(pessoas) 
    pessoa = Pessoas.query.filter_by(nome='Sidney').first()
    print(pessoa.idade)

def altera_pessoas():
    pessoa = Pessoas.query.filter_by(nome='Moreira').first()
    pessoa.idade = 21
    pessoa.save()

def exclui_pessoas():
    pessoa = Pessoas.query.filter_by(nome='Moreira').first()
    pessoa.delete()

def insere_usuario(login, senha):
    user = Usuarios(login=login, senha=senha)
    user.save() 

def consulta_todos_usuarios():
    user = Usuarios.query.all()
    print(user)

if __name__ == "__main__":
    """ insere_pessoas()
    altera_pessoas()
    exclui_pessoas()
    consulta_pessoas() """
    #insere_usuario('sidney', '1234')
    #insere_usuario('moreira', '5678')
    consulta_todos_usuarios()
    