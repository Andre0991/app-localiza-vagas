
'use strict';

var uuid = require('node-uuid');
var mysql = require('mysql')


class CalcadasService {
    constructor() {
        this.connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'root',
            database: 'vagas_db'
        });
        this.connection.connect()
    }

    rowToCalcada(row) {
        return {
            calcada_id: row.calcada_id,
            numero : row.numero,
            cep : row.cep,
            rua : row.rua,
            latitude : row.latitude,
            longitude : row.longitude,
            user: row.user
        }
    }

    getOne(numero, rua, cep, callback) {
        var that = this;
        var query = this.connection.query(
            'SELECT * FROM calcadas WHERE numero = ' + mysql.escape(numero) + ' and ' +
            'rua = ' + mysql.escape(rua) + ' and ' +
            'cep = ' + mysql.escape(cep),
            function (err, rows, fields) {
                // TODO: tratar erro?
                if (err) {
                    console.log({status: 'error', message: err.message});
                    throw err;
                }
                if (rows.length != 0) {
                    var calcada = that.rowToCalcada(rows[0]);
                    callback({status: 'success', calcada: calcada});
                }
                else {
                    callback({ status: 'fail', message: 'Esta calçada não existe.' });
                }
            });
            console.log(query.sql);
    }

    // TODO
    getAll(callback) {
        var that = this;
        var query = this.connection.query(
            'SELECT * FROM calcadas',
            function (err, rows, fields) {
                // TODO: tratar erro?
                if (err) {
                    callback({status: 'error', message: err.message});
                    throw err;
                }
                if (rows.length != 0) {
                    var all_calcadas = rows.map(function(row) {
                        return that.rowToCalcada(row);
                    });
                    callback({status: 'success', calcadas: all_calcadas});
                }
                else {
                    callback({ status: 'fail', message: 'Não há nenhuma calçada registrada.' });
                }
            });
            console.log(query.sql);
    }

    getCalcada(calcadaId) {
        this.connection.query('SELECT * FROM calcadas WHERE id = ' + this.connection.escape(calcadaId), function (err, rows, fields) {
            // TODO: ver se nào existe calçada
            if (err) throw err
            if (rows.length == 0){
                return false;
            }
            return makeCalcadaJsonFromRow(rows[0])
        });
    }

    makeCalcadaJsonFromRow(row) {
        var latitude = row.latitude;
        var longitude = row.longitude;
        console.log('A calçada tem latitude: ', latitude)
        console.log('A calçada tem longitude: ', longitude)
        return {
            "latitude": latitude,
            "longitude": longitude
        }
    }

    getCalcadaByUserId(userId){
        this.connection.query('SELECT * FROM calcadas WHERE user_id = ' + this.connection.escape(user_id), function (err, rows, fields) {
            // TODO: ver se nào existe calçada
            if (err) throw err
            if (rows.length == 0){
                return false;
            }
            return makeCalcadaJsonFromRow(rows[0])
        });
    }

    addCalcada(info, callback) {
        // TODO: lol
        var that = this;
        // TODO: prevenir que duas calcadas sejam adicionadas em lugares proximos!
        if (!info) {
            return false;
        }
        this.getOne(info.numero, info.rua, info.cep, function (resp) {
            // calcada ja existe
            if (resp.status == "success") {
                callback({"status": "fail", message: "Já existe calçada cadastrada nesse endereço."});
            // TODO: usar status mais específico para inficar que calçada não existe?
            }
            else if (resp.status == "fail") {
                var calcada = {latitude: info.latitude, longitude: info.longitude, cep: info.cep, numero: info.numero, rua: info.rua};
                var query = that.connection.query('INSERT INTO calcadas SET ?', calcada, function (err, result) {
                    if (err){
                        callback({status: "error", "message": err.message});
                    }
                    else {
                        callback({"status": "success"});
                    }
                });
                console.log(query.sql);
            }
        });
    }
}

module.exports = new CalcadasService();