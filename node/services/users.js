'use strict';

var bcrypt = require('bcryptjs');
var mysql = require('mysql')


class UserService {
    constructor() {
        this.connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'root',
            database: 'vagas_db'
        });
        this.connection.connect()
    }

    addUser(info, callback) {
        // TODO: meldels
        var that = this
        if (!info) {
            return false;
        }
        this.userExists(info.username, function (rows) {
            // não existe
            if (rows.length == 0) {
                bcrypt.genSalt(10, function (err, salt) {
                    bcrypt.hash(info.password, salt, function (err, hash) {
                        var usuario = { username: info.username, user_hash: hash }
                        var query = that.connection.query('INSERT INTO usuarios SET ?', usuario, function (err, result) {
                            if (err) {
                                console.log("erro ao inserir usuário!");
                                throw err;
                            }
                            console.log(query.sql);
                            console.log("usuário adicionado");
                            // TODO: true correto?
                            callback(true)
                        });
                    });
                });
            }
            else {
                callback(false)
            }
        })
    }

    authUser(info) {
        if (!info) {
            return false;
        }
        this.userExists(info.username, function (rows) {
            if (rows.length != 0) {
                console.log("autenticacao: usuario existe")
                bcrypt.compare(info.password, rows[0].user_hash, function (err, res) {
                    console.log("hash confere?");
                    console.log(res);
                    return res;
                });
            }
            else {
                console.log("autenticacao: usuario nao existe")
            }
        });
    }

    userExists(username, callback){
        var query = this.connection.query('SELECT * FROM usuarios WHERE username = ' + mysql.escape(username), function (err, rows, fields) {
            // TODO: tratar erro?
            if (err) {
                console.log("erro!");
                throw err;
            }
            callback(rows);
        });
    }
}


module.exports = new UserService();
