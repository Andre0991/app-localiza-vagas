'use strict';

var bcrypt = require('bcryptjs');
var mysql = require('mysql')
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');
var CalcadasService = require('./calcadas');
var UserService = require('./users');


class HorarioService {
    constructor() {
        this.connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'root',
            database: 'vagas_db'
        });
        this.connection.connect()
    }

    addHorario(info, callback) {
        // TODO: meldels
        var that = this
        if (!info) {
            return false;
        }
        user = UserService.getOne(info.username, function(resp) {
            if (resp.status == "success") {
                id = resp.data.user_id;
                CalcadasService.getCalcadaByUserId(id, function(calcadaResp) {
                    if (calcadaResp.status = "success") {
                        calcada = calcadaResp.data;
                        horario = {};
                        var query = that.connection.query('INSERT INTO horarios SET ?', info.data, function (err, result) {
                            if (err) {
                                console.log("Erro ao inserir horário.");
                                throw err;
                            }
                            console.log(query.sql);
                        });
                    }
                    else {
                        callback({ status: "fail", message: 'Não foi possível encontrar calçada cadastrada para este usuário.' });
                    }
                });
            }
            else {
                callback({ status: "fail", message: 'Não foi possível encontrar este usuário.' });
            }
        });
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

                                    var token = jwt.encode(info.username, secrets.jwt_secret);
                                    // return the information including token as JSON
                                    callback({ status: "success", token: 'JWT ' + token });
                                    // TODO: true correto?
                                    // callback(null, true)
                                });
                            });
                        });

                    } else {
                        // callback(new Error("Já existe usuário registrado com este e-mail."))
                        callback({ status: "fail", message: 'Já existe usuário registrado com este e-mail.' });
                    }
                })
            }
            else {
                // callback(new Error("Usuário já existe."))
                callback({ status: "fail", message: 'Usuário já existe.' });
            }
        })
    }

}

module.exports = new HorarioService();
