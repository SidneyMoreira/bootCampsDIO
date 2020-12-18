// encadeamento de Promise

//const coinFlip = new Promise((resolve, reject ) => Math.random > 0.5 ? resolve('Yay') : reject('Opppssss'));

// Primeira explicacao
/* coinFlip
    .then((data) => console.log(data, '1'))
    .catch((err) => console.log('Erro 1'))
    .then(() => { throw new Error('Erro 2') })
    .catch((err) => console.log(err.message));
 */

 //Segunda Explicacao

/* coinFlip.then((data) => console.log('Yay 2')).then(() => {throw new Error('xpto')}).catch(console.log)
coinFlip.catch((err) => console.error('Next the will not execute'))
coinFlip.then(() => console.log('End 2')) */

// terceira explicacao
/* 
console.log('Begin')

coinFlip.then((data) => console.log(data))
  .catch((err) => { throw err})
  .then(() => console.log('End1'))

new Promise((resolve) => setTimeout(() => resolve(), 2000)).then(() => console.log('Yay')) */

//multiplos catch

/* coinFlip.then((data) => console.log('Yay 1'))
  .catch(() => console.log('First catch'))
  .then(() => console.log('Result'))
  .catch((err) => console.error('Error in First case, next then will not execute'))
  .then(() => console.log('End1')) */

//segundo caso de multipos catch (qdo todos vão se executados, pq os thens foram separados dos catchs)
// bom para tratamento de erros padrões
/* coinFlip
    .then((data) => console.log('Yay 1'))
    .then(() => console.log('Result')) 
    .then(() => console.log('End1'))

coinFlip
    .catch(() => {throw new Error('First catch')})
    .catch((err) => console.error('Error in First case, next then will not execute')) */

// Promises em Lock

/* const coinFlip = new Promise(
    (resolve, reject ) => setTimeout(() => Math.random > 0.5 ? resolve('Yay') : reject('Opppssss'), 2000)
);

const p = Promise.resolve('resolve').then(coinFlip)

p.then(console.log).catch(() => console.log('Error'))
 */
// voltar para o 1-index.js a partir da linha 27

//continuando com promise.all aqui
const coinFlip = (n) => new Promise((resolve, reject ) => n > 0 ? resolve('Yay') : reject('Opppssss'));

console.log('Begin')
const promiseArray = []
for (let i = 1; i <= 4; i++) {
  promiseArray[i] = coinFlip(Math.random());
}

//console.log(promiseArray)
 Promise.all(promiseArray)
  .then(console.log)