const pets = [
    {
        name: 'Nick',
        type: 'Dog',
        age: 10
    },
    {
        name: 'Fluck',
        type: 'Dog',
        age: 15
    },
    {
        name: 'Lua',
        type: 'Cat',
        age: 5
    },
    {
        name: 'Sol',
        type: 'Cat',
        age: 6
    }
];

const idadeMenor = (numero) => {
    return numero < 10
}

const newPets = pets.filter(({age}) => idadeMenor(age))

console.log(newPets);