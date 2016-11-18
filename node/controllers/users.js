'use strict';

var UsersService = require('../services/users');

class UserController {
    constructor(router) {
        this.router = router;
        this.registerRoutes();
    }

    registerRoutes() {
        // this.router.get('/players', this.getPlayers.bind(this));
        // this.router.get('/players/:id', this.getSinglePlayer.bind(this));
        this.router.post('/user', this.postUser.bind(this));
        // this.router.put('/players/:id', this.putPlayer.bind(this));
    }

    postUser(req, res) {
        var userInfo = req.body;
        UsersService.addUser(userInfo, function (success) {
            if (success) {
                // TODO: o que colocar no header?
                // res.setHeader('Location', '/calcadas/' + playerInfo.id);
                res.setHeader('Location', '/users/testString');
                res.sendStatus(200);
            }
            else {
                res.sendStatus(500);
            }
        });
    }
}

module.exports = UserController;