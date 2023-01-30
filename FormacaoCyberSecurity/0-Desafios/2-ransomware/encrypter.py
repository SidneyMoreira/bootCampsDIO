import os, sys
import pyaes

param = sys.argv[1:]
print(param)

if len(param) == 3:
    ## abrir o arquivo a ser criptografado
    file_name = param[0]
    file = open(file_name, "rb")
    file_data = file.read()
    file.close()


    ## chave para descriptografia
    key = bytes(param[1], 'utf-8')
    aes = pyaes.AESModeOfOperationCTR(key)
    decrypt_data = aes.decrypt(file_data)

    ## remover o arquivo criptografado
    os.remove(file_name)

    ## criar o arquivo descriptografado
    new_file = param[2]
    new_file = open(new_file, "wb")
    new_file.write(decrypt_data)
    new_file.close()
else:
    print("Sintaxe 'python encrypter.py [arquivo_a_ser_criptografado] [chave] [novo_nome]'")
    sys.exit()


### help
if file_name == "-h" or file_name == '--help':
    print("Sintaxe 'python encrypter.py [arquivo_a_ser_criptografado] [chave] [novo_nome]'")
    sys.exit() 