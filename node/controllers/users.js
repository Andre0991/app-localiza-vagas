'use strict';

var UsersService = require('../services/users');
var jwt = require('jwt-simple');
var secrets = require('../config/secrets');

// var passport	= require('passport');
var passport	= require('passport');
require('../config/passport')(passport);

class UserController {
    constructor(router) {
        this.router = router;
        this.registerRoutes();
    }

    registerRoutes() {
        // this.router.get('/players', this.getPlayers.bind(this));
        // this.router.get('/players/:id', this.getSinglePlayer.bind(this));
        this.router.post('/user', this.postUser.bind(this));
        this.router.post('/auth', this.authUser.bind(this));
        this.router.post('/member', this.memberTest.bind(this));
        // this.router.put('/players/:id', this.putPlayer.bind(this));
    }

    // TODO: Confirmar se não ninguém com mesmo e-mail
    postUser(req, res) {
        var userInfo = req.body;
        UsersService.addUser(userInfo, function (error, success) {
            if (error) {
                // res.sendStatus(500);
                res.status(500).send(error.message);
            }
            else {
                // TODO: o que colocar no header?
                // res.setHeader('Location', '/calcadas/' + playerInfo.id);
                res.setHeader('Location', '/users/testString');
                res.sendStatus(200);
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
        console.log("oi")
        passport.authenticate('jwt', { session: false }),
            function (req, res) {
                var token = getToken(req.headers);
                if (token) {
                    var decoded = jwt.decode(token, secrets.jwt_secret);
                    var userInfo = req.body;
                    UsersService.userExists(userInfo.username, function (resp) {
                        if (resp.success == true) {
                            res.status(200).send(resp);
                        }
                        else {
                            res.status(500).send(resp)
                        }
                    });
                }
            }
    }

    // from https://devdactic.com/restful-api-user-authentication-1/
    getToken(headers) {
        if (headers && headers.authorization) {
            var parted = headers.authorization.split(' ');
            if (parted.length === 2) {
                return parted[1];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}

module.exports = UserController;