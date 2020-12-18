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

const mapPetNames = pets.map((pet) => {
  return pet.name;
});

console.log(mapPetNames);

/*
  Foreach
  Não retorna um novo array com a mesma quantidade de elementos
*/

const forEachPetNames = [];

pets.forEach((pet) => {
    forEachPetNames.push(pet.name)
});

console.log(forEachPetNames);
