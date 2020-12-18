const pets = [
    {
        type: 'Dog',
        name: 'Nick',
        age: 10,
        weight: 30
    },
    {
        type: 'Dog',
        name: 'Fluck',
        age: 6,
        weight: 2
    },
    {
        type: 'Cat',
        name: 'Lua',
        age: 5,
        weight: 8
    },
    {
        type: 'Cat',
        name: 'Sol',
        age: 6,
        weight: 9
    },
    {
        type: 'Dog',
        name: 'Rex',
        age: 4,
        weight: 5
    },
];

const petNames = pets.map((pet) => {
    return pet.name
})



console.log(petNames);