'use strict';

var UsersService = require('../services/users');
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');

class UserController {
    constructor(router, passport) {
        this.router = router;
        this.passport = passport;
        this.registerRoutes();
    }

    registerRoutes() {
        // this.router.get('/players', this.getPlayers.bind(this));
        // this.router.get('/players/:id', this.getSinglePlayer.bind(this));
        this.router.post('/user', this.postUser.bind(this));
        this.router.post('/auth', this.authUser.bind(this));
        this.router.post('/member', this.passport.authenticate('jwt', { session: false }), this.memberTest.bind(this));
        // this.router.put('/players/:id', this.putPlayer.bind(this));
    }

    // TODO: colocar 404 quando usuário existe e adequar outros codigos http
    postUser(req, res) {
        var userInfo = req.body;
        UsersService.addUser(userInfo, function (resp) {
            if (resp.status != "success") {
                // res.sendStatus(500);
                res.status(500).send(resp);
            }
            else {
                res.status(200).send(resp)
            }
        });
    }

    authUser(req, res) {
        var userInfo = req.body;
        UsersService.authUser(userInfo, function (resp) {
            if (resp.success == true) {
                res.status(200).send(resp);
            }
            else {
                res.status(500).send(resp)
            }
        });
    }

    memberTest(req, res) {
        res.status(200).send("Agora vai!");
    }

}

module.exports = UserController;