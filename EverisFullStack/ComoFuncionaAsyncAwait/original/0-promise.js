const promise = new Promise((resolve, reject) => {
    setTimeout(() => resolve('End', 5000))
})

console.log('Begin');
promise.then((text) => console.log(text))

//or

promise.then(console.log)

//or

promise.then(
    (res) => setTimeout(() => console.log(res), 5000),
    (rej) =>  console.error(rej)
)
