
'use strict';

var CalcadasService = require('../services/calcadas');

class CalcadasController {
    constructor(router) {
        this.router = router;
        this.registerRoutes();
    }

    registerRoutes() {
        // this.router.get('/players', this.getPlayers.bind(this));
        // this.router.get('/players/:id', this.getSinglePlayer.bind(this));
        this.router.post('/calcada', this.postCalcada.bind(this));
        // this.router.put('/players/:id', this.putPlayer.bind(this));
    }

    postCalcada(req, res) {
        var calcadaInfo = req.body;
        if (CalcadasService.addCalcada(calcadaInfo)) {
            // TODO: o que colocar no header? latitude e longitude?
            // res.setHeader('Location', '/calcadas/' + playerInfo.id);
            res.setHeader('Location', '/calcadas/testString');
            res.sendStatus(200);
        } else {
            res.sendStatus(500);
        }
    }

    getPlayers(req, res) {
        var players = PlayersService.getPlayers();
        res.send(players);
    }

    getSinglePlayer(req, res) {
        var id = req.params.id;
        var player = PlayersService.getSinglePlayer(id);

        if (!player) {
            res.sendStatus(404);
        } else {
            res.send(player);
        }
    }

    putPlayer(req, res) {
        var id = parseInt(req.params.id, 10);
        var existingPlayer = PlayersService.getSinglePlayer(id);

        if (!existingPlayer) {
            let playerInfo = req.body;
            playerInfo.id = id;
            if (PlayersService.addPlayer(playerInfo)) {
                res.setHeader('Location', '/players/' + id);
                res.sendStatus(201);
            } else {
                res.sendStatus(500);
            }
        } else {
            if (PlayersService.updatePlayer(id, req.body)) {
                res.sendStatus(204);
            } else {
                res.sendStatus(404);
            }
        }
    }


}

module.exports = CalcadasController;