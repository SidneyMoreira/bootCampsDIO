const mysql = require('mysql2');

const Connection = mysql.createConnection({
    host: '127.0.0.1',
    user: 'root',
    password: '<COLOCA SUA SENHA AQUI SE EXISTIR>',
    database: 'game-shop'
})

module.exports = Connection;
