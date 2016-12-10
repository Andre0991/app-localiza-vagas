'use strict';

var DistanciaService = require('../services/distancia');
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');


class DistanciaController {
    constructor(router, passport) {
        this.router = router;
        this.passport = passport;
        this.registerRoutes();
    }

    registerRoutes() {
        // TODO: nome decente
        this.router.post('/distancia', this.passport.authenticate('jwt', { session: false }), this.getCalcadaMaisProxima.bind(this));
    }

    getCalcadaMaisProxima(req, res) {
        var locationInfo = req.body.data;
        DistanciaService.calcadaMaisProxima(locationInfo.latitude, locationInfo.longitude, function (resp) {
            if (resp.status == "success") {
                res.status(200).send(resp);
            }
            else {
                res.status(500).send(resp)
            }
        });
    }
}

module.exports = DistanciaController;