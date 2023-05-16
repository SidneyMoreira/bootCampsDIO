# Criptografando Arquivos com Ransomware

### Ferramentas

- Kali Linux
- Python3 com as library (os, sys e pyaes)

### Executanto o Ransomware no Kali Linux

Foi feita uma pequena mudança no código do professor para permitir passagem de parâmetros no shell, a sintaxe padrão é:

**Para criptografar:** 

   python encrypter.py [arquivo_a_ser_criptografado] [chave] [novo_nome]

**Para Descriptografar:**

   python decrypter.py [arquivo_criptografado] [chave] [novo_nome]

Também é possivel consultar a sintaxe pelo comando:

python decrypter.py -h 

python encrypter.py -h 

ou

python decrypter.py -h

python encrypter.py --help 

### Aviso

Os testes foram realizados em ambiente virtual seguro, cuidado ao utilizar para fins que não sejam para estudos. Invadir ou acessar documentos sem permissão é crime.

**[LEI Nº 12.737, DE 30 DE NOVEMBRO DE 2012.](http://www.planalto.gov.br/ccivil_03/_Ato2011-2014/2012/Lei/L12737.htm)**

Dispõe sobre a tipificação criminal de delitos informáticos; altera o Decreto-Lei nº 2.848, de 7 de dezembro de 1940 - Código Penal; e dá outras providências.

