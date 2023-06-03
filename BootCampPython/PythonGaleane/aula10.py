from datetime import date, time, datetime, timedelta

def trabalhando_com_datetime():
    data_atual = datetime.now()
    print(data_atual)
    print(data_atual.strftime('%d/%m/%Y'))
    print(data_atual.strftime('%H:%M:%S'))
    print(data_atual.strftime('%c'))
    print(data_atual.time())
    print(data_atual.weekday())
    tupla = ('Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabado', 'Domingo')
    print(tupla[data_atual.weekday()])
    data_criada = datetime(1975, 12, 11, 23, 45, 30)
    print(data_criada)
    print(data_criada.strftime('%c'))
    data_string = '01/01/2019 12:20:22'
    data_convertida = datetime.strptime(data_string, '%d/%m/%Y %H:%M:%S')
    print(data_convertida)
    nova_date = data_convertida - timedelta(days=365, hours=2, minutes=15)
    print(nova_date)
    datetime.now
    time(hour=0, minute=0)


def trabalhando_com_date():
    data_atual = date.today()
    data_atual_str = data_atual.strftime('%A  %B %Y')
    print(type(data_atual))
    print(data_atual_str)
    print(type(data_atual_str))

def trabalhando_com_time():
    horario = time(hour=15, minute=18, second=30)
    print(horario)
    print(type(horario))
    horario_str = horario.strftime('%H:%M:%S')
    print(horario_str)
    print(type(horario_str))

def datas_lua():
    print(datetime(1969, 7, 21, 2, 56, 15)) 
    print(datetime(1969, 7, 16, 13, 32, 00)) 
    print(datetime(1969, 7, 24, 16, 50, 35)) 
    #print(datetime(1969, July, 24, 16, 50, 35)) 
    #print(datetime(1969, 7, 16, 13, 31, 60))
    data_string = '15 do 04 de 1502 às 18 e 30'
    data_convertida = datetime.strptime(data_string, '%d do %m de %Y às %H e %M')
    #print(data_atual.strftime{'%d-%m-%Y'} )
    data_viagem = datetime.now() - timedelta(days=1) 
    print(datetime.now().weekday()) 
    #retornou 0 print(data_viagem)
    #------
    data_leonardo = datetime(1502, 4, 15) 
    data_einstein = datetime(1929, 3, 14) 
    data_diferenca = data_einstein - data_leonardo 
    print(data_diferenca)
    #retornaria uma variável tipo 'datetime.timedelta' com valor de 155.927 dias. 
    data_diferenca = data_einstein - timedelta(days=155927) 
    print(data_diferenca)
    print(type(data_diferenca))
    #retornaria uma variável tipo 'datetime.datetime'

if __name__ == "__main__":
    #trabalhando_com_date()
    #trabalhando_com_time()
    #trabalhando_com_datetime()
    datas_lua()
