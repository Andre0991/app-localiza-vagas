
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

    addCalcada(info) {
        // TODO: prevenir que duas calcadas sejam adicionadas em lugares proximos!
        if (!info) {
            return false;
        }

        var calcada = {latitude: info.latitude, longitude: info.longitude}
        var query = this.connection.query('INSERT INTO calcadas SET ?', calcada, function (err, result) {
        });

        // TODO: deletar depois de testar
        console.log(query.sql);


        return true;
    }

    updatePlayer(playerId, info) {
        var player = this.getSinglePlayer(playerId);
        if (player) {
            player.firstName = info.firstName ? info.firstName : player.firstName;
            player.lastName = info.lastName ? info.lastName : player.lastName;
            player.displayName = info.displayName ? info.displayName : player.displayName;

            return true;
        }
        return false;
    }
}

module.exports = new CalcadasService();