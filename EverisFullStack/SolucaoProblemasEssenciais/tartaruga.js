/* 
Desafio
A corrida de tartarugas é um esporte que cresceu muito nos últimos anos, fazendo com que vários competidores se dediquem a capturar tartarugas rápidas, e treina-las para faturar milhões em corridas pelo mundo. Porém a tarefa de capturar tartarugas não é uma tarefa muito fácil, pois quase todos esses répteis são bem lentos. Cada tartaruga é classificada em um nível dependendo de sua velocidade:


Nível 1: Se a velocidade é menor que 10 cm/h .
Nível 2: Se a velocidade é maior ou igual a 10 cm/h e menor que 20 cm/h .
Nível 3: Se a velocidade é maior ou igual a 20 cm/h .

Sua tarefa é identificar qual o nível de velocidade da tartaruga mais veloz de um grupo.

Entrada
A entrada consiste de múltiplos casos de teste, e cada um consiste em duas linhas: A primeira linha contém um inteiro L (1 ≤ L ≤ 500) representando o número de tartarugas do grupo, e a segunda linha contém L inteiros Vi (1 ≤ Vi ≤ 50) representando as velocidades de cada tartaruga do grupo.

Saída
Para cada caso de teste, imprima uma única linha indicando o nível de velocidade da tartaruga mais veloz do grupo.

 
Exemplo de Entrada	Exemplo de Saída
10
10 10 10 10 15 18 20 15 11 10
10
1 5 2 9 5 5 8 4 4 3
10
19 9 1 4 5 8 6 11 9 7

3
1
2


*/
let l = 10;
let V1 = "10 10 10 10 15 18 20 15 11 10";
let V2 = "1 5 2 9 5 5 8 4 4 3";
let V3 = "19 9 1 4 5 8 6 11 9 7";
//let L = parseInt(gets());
//let Vi = parseInt(gets()).split(" ");
//l = parseInt(l)
V1 = V1.split(" ");
V2 = V2.split(" ");
V3 = V3.split(" ");
let Vel = [];
Vel[0] = V1;
Vel[1] = V2;
Vel[2] = V3;
let veloz = [];
let i, y;
let nivel;

for (y = 0; y < 3; y++) {
    var cont = y;
  for (i = 0; i < cont; i++) {
    if (Vel[cont][i] > veloz) {
        console.log(Vel[cont][i])
      veloz = Vel[cont][i];
    }
  }
}
if (veloz < 10) {
    nivel = 1;
  } else if (veloz >= 10 && veloz< 20) {
    nivel = 2;
  } else if (veloz >= 20) {
    nivel = 3;
  } 
console.log(nivel);
//console.log(Vel[0])

// Entregue

/* 
for (let entrada= 0; entrada <3; entrada++){
let L = parseInt(gets()) 
let V = gets().split(" ")
let maisVeloz = 0;
let Nivel;

for (let i = 0; i < L; i++) {
  if (parseInt(V[i]) > maisVeloz) {
    maisVeloz = parseInt(V[i]);
  };
};
    
if (maisVeloz < 10) {
    Nivel = 1;
} else if (maisVeloz >= 10 && maisVeloz < 20) {
    Nivel = 2;
} else {
    Nivel = 3;
}; 
console.log(Nivel);
}


*/
