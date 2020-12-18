/*
Desafio
Você terá o desafio de escrever um programa que leia um valor inteiro N (1 < N < 1000). Este N é a quantidade de linhas de saída que serão apresentadas na execução do programa.

Entrada
O arquivo de entrada contém um número inteiro positivo N.

Saída
Imprima a saída conforme o exemplo fornecido.

*/

let num = 5;
num = parseInt(num);
let i;

for (i = 1; i <= num; i++) {
  /* res = [i**1, i ** 2, i ** 3];
  console.log(res); */
  console.log(i+" "+ i**2+ " "+i**3)
}


