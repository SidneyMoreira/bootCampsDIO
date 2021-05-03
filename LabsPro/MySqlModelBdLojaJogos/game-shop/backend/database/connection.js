const mysql = require('mysql2');

const Connection = mysql.createConnection({
    host: '127.0.0.1',
    user: 'root',
    password: 'Vl@.div-71k75',
    database: 'game-shop'
})

module.exports = Connection;
