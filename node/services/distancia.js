'use strict';

var secrets = require('../config/secrets');
var distance = require('google-distance');
var CalcadasService = require('./calcadas');
var mysql = require('mysql')

distance.apiKey = secrets.google_distance_key;

class DistanciaService {
    constructor() {
        this.connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'root',
            database: 'vagas_db'
        });
        this.connection.connect()
    }

    // TODO: colocar index para saber qual calçada do banco de dados é cada resposta
    calcadaMaisProxima(lat, lon, callback) {
        CalcadasService.getAll(function (resp) {
            if (resp.status == "success") {
                var all_calcadas = resp.calcadas;
                var all_calcadas_coordinates = all_calcadas.map(function(calcada) {
                    return calcada.latitude.toString() + "," + calcada.longitude.toString();
                });
                distance.get(
                    {
                        // origins: ['51.510652,-0.095444'],
                        origins: [lat + "," + lon],
                        // destinations: ['San Diego, CA', 'Seattle, WA', 'New York, NY']
                        destinations: all_calcadas_coordinates
                    },
                    function (err, data) {
                        if (err) {
                            callback({ status: 'error', message: err.message });
                            return;
                        }
                        var arr_indexes = Array.from(Array(data.length).keys()).reverse();
                        var data_with_indexes = data.map(function(dest) {
                            return {destino: dest, index: arr_indexes.pop()};
                        })
                        var sorted_data = data_with_indexes.sort(function (a, b) { return a.destino.distanceValue - b.destino.distanceValue; })
                        console.log(sorted_data);
                        var calcada_mais_proxima_index = sorted_data[0].index;
                        var calcada_mais_proxima = all_calcadas[calcada_mais_proxima_index];
                        callback({ status: 'success', calcadas: calcada_mais_proxima });
                    });
            }
            else {
                callback(resp);
            }
        });
    }

}

module.exports = new DistanciaService();