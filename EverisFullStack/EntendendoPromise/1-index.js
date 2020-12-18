/* const fs = require("fs");
const { resolve } = require("path");
const path = require("path");
const basePath = "./assets/";
const { promisify } = require('util');
const readFileAsync = promisify(fs.readFile);  */// essa linha é igual a function abaixo. O professor comentou q não é tudo q funciona o promisify.

// tem um forma melhor de fazer o que está na function readFileAsysnc
/* function readFileAsync(path, options) {
  return new Promise((resolve, reject) => {
    fs.readFile(path, options, (err, data) => {
      if (err) return reject(err);
      return resolve(data);
    });
  });
} */



/* console.log("Begin");
readFileAsync(path.resolve(basePath, "invictus.txt"), { encoding: "utf-8" })
  .then(console.log)
  .catch(console.error);
console.log("End"); */


// Continuando a explicação após block no 2-index.js linha 58

const fs = require('fs');
const path = require('path');
const { promisify } = require('util');
const basePath = './assets/';

const readFileAsync = promisify(fs.readFile)

//console.log('Begin')

/* readFileAsync(path.resolve(basePath, 'invictus.txt'), { encoding: 'utf-8'})
  .then(console.log)
  .catch(console.error)
  .finally(() => console.log('End'))
 */

// com Promise.all

function read(index){
  return readFileAsync(path.resolve(basePath, `s${index}.txt`), { encoding: 'utf-8'})
}

/* function start(index, max){
  if (index > max) return
  read(index).then((data) => {
    console.log(data, '\n')
    start(index + 1, max)
  })
}

start(1,4) */

// Melhorando a function Start
console.log('Begin')
const promiseArray = []
for (let i = 1; i <= 4; i++) {
  promiseArray[i-1] = read(i);
}

Promise.all(promiseArray)
  .then((arr) => console.log(arr.join('\n')))

// voltar para o 2-index.js

//INICIO DA EXPLICACAO

/* const promise = new Promise((resolve, reject) => {
    setTimeout(() => reject("End"), 2000);
  });
  
  console.log("Begin");
 */

//promise.then((data) => console.log(data));
//promise.then((data) => console.log(data)).catch((err) => console.log('ooppss', err));
//promise.then(console.log)

//promise.then((resolve) => {}, (reject) => {});

// as linhas abaixo é a mesma coisa da linha acima...
/* promise.then((resolve) => {});
promise.catch((reject) => {}); */
