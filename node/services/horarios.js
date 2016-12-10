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

    addHorario(username, info, callback) {
        // TODO: meldels
        var that = this
        if (!info) {
            return false;
        }
        UserService.getOne(username, function(resp) {
            if (resp.status == "success") {
                var id = resp.data.user_id;
                CalcadasService.getCalcadaByUserId(id, function(calcadaResp) {
                    if (calcadaResp.status = "success") {
                        var horario = {};
                        horario.event_day = info.day;
                        horario.start_time = that.pad_to_two_digits(info.inicio.hour) +
                            that.pad_to_two_digits(info.inicio.minute) +
                            "00";
                        horario.end_time = that.pad_to_two_digits(info.fim.hour) +
                            that.pad_to_two_digits(info.fim.minute) +
                            "00";
                        horario.calcada_id = calcadaResp.data.calcada_id;
                        // horario.start_time = info.
                        var query = that.connection.query('INSERT INTO horarios SET ?', horario, function (err, result) {
                            if (err) {
                                console.log("Erro ao inserir horário.");
                                callback({ status: "error", message: err.message });
                                throw err;
                            }
                            console.log(query.sql);
                        callback({ status: "success" });
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
    }

    // from http://stackoverflow.com/questions/2998784/how-to-output-integers-with-leading-zeros-in-javascript
    pad_to_two_digits(num) { return ('000000000' + num).substr(-2); }

}

module.exports = new HorarioService();
