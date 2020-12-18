const pets = [
  {
    type: "Dog",
    name: "Nick",
    age: 10,
    weight: 30,
  },
  {
    type: "Dog",
    name: "Fluck",
    age: 6,
    weight: 2,
  },
  {
    type: "Cat",
    name: "Lua",
    age: 5,
    weight: 8,
  },
  {
    type: "Cat",
    name: "Sol",
    age: 6,
    weight: 9,
  },
  {
    type: "Dog",
    name: "Rex",
    age: 4,
    weight: 5,
  },
];

/* const totalWeight = pets.reduce((total, pet) =>{
  return total + pet.weight
}, 0)

console.log(`Total Peso: ${totalWeight}`); */

/* const totalWeight = pets.reduce((total, pet) =>{
  return {
    totalAge: total.totalAge + pet.age,
    totalWeight: total.totalWeight + pet.weight
  }
}, {totalAge: 0,  totalWeight: 0}) 

console.log(`Total Age: ${totalWeight.totalAge} | Total Weight: ${totalWeight.totalWeight}`);

*/
/* 
const totalWeight = pets.reduce((total, pet) =>{
  if (pet.type !== 'dog') return total

  return total + pet.weight
}, 0)

console.log(totalWeight); */

/* const dogs = pets.filter((pet) =>{
  return pet.type === 'Dog'
})


const totalWeightDogs = dogs.reduce((total, pet) =>{

  return total + pet.weight
}, 0) 

console.log(totalWeightDogs);*/

/* Encadeando tudo */

const totalWeightDogs = pets.filter((pet) =>{
  return pet.type === 'Dog'
}).reduce((total, pet) =>{

  return total + pet.weight
}, 0)

console.log(totalWeightDogs);