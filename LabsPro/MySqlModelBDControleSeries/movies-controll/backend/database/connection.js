const mysql = require('mysql2');

const Connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '<COLOCAR SENHA CASO TENHA>',
    database: 'movies-controll'
})

module.exports = Connection;
