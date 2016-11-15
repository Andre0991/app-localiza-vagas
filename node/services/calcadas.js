
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
            if (err) throw err
            var latitude = rows[0].latitude;
            var longitude = rows[0].longitude;
            console.log('A calçada tem latitude: ', latitude)
            console.log('A calçada tem longitude: ', longitude)

        return { "latitude": latitude,
                 "longitude": longitude
               }
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