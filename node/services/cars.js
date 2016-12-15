'use strict';
var async = require("async");
var uuid = require('node-uuid');
var mysql = require('mysql')
var UserService = require('./users');

class CarsService {
    constructor() {
        this.connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'root',
            database: 'vagas_db'
        });
        this.connection.connect();
    }

    rowToCar(row) {
        return {
            placa: row.placa,
            cor: row.cor,
            modelo: row.modelo
        }
    }

    getOne(placa, callback) {
        var that = this;
        var query = this.connection.query(
            'SELECT * FROM carros WHERE placa = ' + mysql.escape(placa),
            function (err, rows, fields) {
                // TODO: tratar erro?
                if (err) {
                    console.log({status: 'error', message: err.message});
                    throw err;
                }
                if (rows.length != 0) {
                    var calcada = that.rowToCar(rows[0]);
                    callback({status: 'success', calcada: calcada});
                }
                else {
                    callback({ status: 'fail', message: 'Este carro não existe.' });
                }
            });
            console.log(query.sql);
    }


    getCarByUserId(user_id, callback){
        var that = this;
        this.connection.query('SELECT * FROM carros WHERE user_id = ' + this.connection.escape(user_id), function (err, rows, fields) {
            // TODO: ver se nào existe carro
            if (err) throw err
            if (rows.length == 0){
                callback({ status: "fail", "message": "Não foi encontrada carro para este usuário" });
            }
            else {
                callback({ status: "success", "data": that.rowToCar(rows[0]) });
            }
            });
    }

    addCar(info, callback) {
        // TODO: lol
        var that = this;
        if (!info) {
            return false;
        }
        this.getOne(info.plate, function (resp) {
            if (resp.status == "success") {
                callback({"status": "fail", message: "Já existe carro cadastrado com essa placa"});
            }
            else if (resp.status == "fail") {
                var user_id;
                UserService.getOne(info.user.username, function (user_result) {
                    if (user_result.status == "success") {
                        user_id = user_result.data.user_id;
                        var car = {
                            placa: info.plate,
                            cor: info.color,
                            user_id: user_id,
                            modelo: info.model
                        };
                        var query = that.connection.query('INSERT INTO carros SET ?', car, function (err, result) {
                            if (err) {
                                callback({ status: "error", "message": err.message });
                            }
                            else {
                                callback({ "status": "success" });
                            }
                        });
                        console.log(query.sql);
                    }
                    else {
                        callback({ status: "error", "message": "Não há usuário com o id informado" });
                    }
                });
            }
        });
    }
}

module.exports = new CarsService();