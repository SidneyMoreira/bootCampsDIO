lista = [1, 10]

try:
    arquivo = open('teste.txt', 'r')
    texto = arquivo.read()
    divisao = 10 / 0
    #numero = lista[3]
    #x = a
except ZeroDivisionError:
    print("naõ é possivel realizar divisão por 0")
except ArithmeticError:
    print("Houve um erro ao realizar uma operação aritmética")
except IndexError:
    print("erro ao acessao um index invalidao da sintas")
except BaseException as ex:
    print('erro desconhecido. Erro: {}'.format(ex))
else:
    print('Executa quando não ocorre exceção')
finally:
    print('sempre executa')
    print('fechando arquivo')
    arquivo.close()