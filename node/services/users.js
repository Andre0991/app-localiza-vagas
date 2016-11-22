'use strict';

var bcrypt = require('bcryptjs');
var mysql = require('mysql')
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');


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
            // usuário não existe
            if (rows.length == 0) {
                that.emailExists(info.email, function (emailRows) {
                    // não existe usuário com esse email
                    if (emailRows.length == 0) {
                        bcrypt.genSalt(10, function (err, salt) {
                            bcrypt.hash(info.password, salt, function (err, hash) {
                                var usuario = {
                                    username: info.username,
                                    user_hash: hash,
                                    email: info.email,
                                    first_name: info.first_name,
                                    surname: info.surname
                                }
                                var query = that.connection.query('INSERT INTO usuarios SET ?', usuario, function (err, result) {
                                    if (err) {
                                        console.log("Error when inserting user.");
                                        throw err;
                                    }
                                    console.log(query.sql);
                                    console.log("User added.");
                                    // TODO: true correto?
                                    callback(null, true)
                                });
                            });
                        });

                    } else {
                        callback(new Error("Já existe usuário registrado com este e-mail."))
                    }
                })
            }
            else {
                callback(new Error("Usuário já existe."))
            }
        })
    }

    authUser(info, callback) {
        if (!info) {
            return false;
        }
        this.userExists(info.username, function (rows) {
            if (rows.length != 0) {
                console.log("autenticacao: usuario existe")
                bcrypt.compare(info.password, rows[0].user_hash, function (err, res) {
                    console.log("hash confere?");
                    console.log(res);
                    if (res == true) {
                        // trocar info.usarname para objeto completo de usuario
                        var token = jwt.encode(info.username, secrets.jwt_secret);
                        // return the information including token as JSON
                        callback({ success: true, token: 'JWT ' + token });
                    }
                    else {
                        callback({ success: false, message: 'Wrong password'});
                    }
                    // TODO: precisa retornar?
                    return res;
                });
            }
            else {
                console.log("autenticacao: usuario " + info.username + " nao existe")
                callback({ success: false, message: 'User does not exist' });
            }
        });
    }

    // TODO: retornar objeto user em vez de row?
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

    emailExists(email, callback){
        var query = this.connection.query('SELECT * FROM usuarios WHERE email = ' + mysql.escape(email), function (err, rows, fields) {
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
