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
    calcadasMaisProximas(lat, lon, callback) {
        CalcadasService.getAllAvailable(new Date(), function (resp) {
            if (resp.status == "success") {
                var all_calcadas = resp.data;
                var all_calcadas_coordinates = all_calcadas.map(function(calcada) {
                    return calcada.latitude.toString() + "," + calcada.longitude.toString();
                });
                distance.get(
                    {
                        // formato: ['51.510652,-0.095444'],
                        origins: [lat + "," + lon],
                        // formato: ['San Diego, CA', 'Seattle, WA', 'New York, NY']
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
                        var sorted_data = data_with_indexes.sort(function (a, b) {
                            return a.destino.distanceValue - b.destino.distanceValue;
                        })
                        console.log(sorted_data);
                        // var calcadas_mais_proximas = 
                        // sorted_data.map(function(cidade) {
                        //     return {index: cidade.index,
                        //         distance: cidade.destino.distance,
                        //         duration: cidade.destino.duration};
                        // }).slice(0,3).map(function(idx) {
                        //     return all_calcadas[idx];
                        // });
                        var calcadas_mais_proximas =
                            sorted_data.slice(0, 3).map(function (cid) {
                                return {
                                    calcada: all_calcadas[cid.index],
                                    distance: cid.destino.distance,
                                    duration: cid.destino.duration
                                };
                            });
                        // var calcada_mais_proxima_index = sorted_data[0].index;
                        // var calcada_mais_proxima = all_calcadas[calcada_mais_proxima_index];
                        callback({ status: 'success', data: calcadas_mais_proximas });
                    });
            }
            else {
                callback(resp);
            }
        });
    }

}

module.exports = new DistanciaService();