function soma(n1, n2){
    return n1 + n2;
}

function validaIdade(idade){
    var validar;

    if (idade >= 18){
        validar = true;
    }
    else {
        validar = false;
    }

    return validar;
}

var idade = prompt("qual sua idade?");
alert(validaIdade(idade));

alert(soma(2,2));

/* var d = new Date();
alert(d);
alert(d.getDate());
alert(d.getDay());
alert(d.getHours()); */


/* var count;
for (count= 0; count <= 5; count++){
    alert(count);
} */

/* var count = 0;

while (count < 5){
    console.log(count);
    count = count+1;
} */



/* var idade = prompt("Qual sua idade?");
if(idade >= 18){
    alert("maior de idade");
} else{
    alert("menor de idade");
} */


/* var frutas = [{nome: "maça", cor:"vermelha"},{nome: "pera", cor:"amarela"}];
console.log(frutas);
console.log(frutas[1].nome); */

/* var fruta = {nome: "maça", cor:"vermelha"};
console.log(fruta);
console.log(fruta.nome); */

/*var nome = "Sidney Moreira";
var idade = 44;
var idade2 = 12;
var frase = "Capoeira é a melhor arte marcial do mundo";
//alert("Bem vindo " + nome + " Sua idade é " + idade + " Anos.");
//alert(idade + idade2);
console.log(nome);
console.log(idade + idade2);
console.log(frase.replace("Capoeira", "Taekwondo"));
alert(frase.replace("Capoeira", "Taekwondo"));*/