/* const itens = ['a','b','c','d']

;(async function() {
  const promiseFunction = async (element) => {
    return new Promise((resolve, reject) => {
      return resolve(`${element} - promise`)
    })
  }


/* const itemMapped = promiseFunction('x')

console.log(await itemMapped) 

const itemMappedPromises = itens.map(promiseFunction)

const itemMapped = await Promise.all(itemMappedPromises)
console.log(itemMapped)

})() */

let entrada = '7 3';
entrada = entrada.split(' ');

let a = parseInt(entrada[0]);
let b = parseInt(entrada[1]);
let q = parseInt(a / b);
let r = a-b*q;

if (r < 0){
  r += parseInt(Math.abs(b));
  q = (a-r) / b;
}

console.log(r + " " + q);