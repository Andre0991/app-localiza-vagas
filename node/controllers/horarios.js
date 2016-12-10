'use strict';

var HorariosServices = require('../services/horarios');
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');


class HorariosController {
    constructor(router, passport) {
        this.router = router;
        this.passport = passport;
        this.registerRoutes();
    }

    registerRoutes() {
        this.router.post('/horario', this.passport.authenticate('jwt', { session: false }), this.postHorario.bind(this));
    }

    postHorario(req, res) {
        var horarioInfo = req.body.data;
        var username = req.body.username;
        HorariosServices.addHorario(username, horarioInfo, function (resp) {
            if (resp.status == "success") {
                res.status(200).send(resp);
            }
            else {
                res.status(500).send(resp)
            }
        });
    }
}

module.exports = HorariosController;