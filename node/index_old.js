'use strict';
var express = require('express');
var app = express();
var apiRouter = express.Router();

var server = app.listen(3000, function () {
  var host = server.address().address;
  host = (host === '::' ? 'localhost' : host);
  var port = server.address().port;
  console.log('listening at http://%s:%s', host, port);
});

app.use('/api/v1', apiRouter);

var PlayersController = require('./controllers/players');
var pc = new PlayersController(apiRouter);

// seed the db for testing
var PlayersService = require('./services/players');
var p1 = PlayersService.addPlayer({firstName: 'Ben', lastName: 'Sparks', displayName: 'Warspawn'});
var p2 = PlayersService.addPlayer({firstName: 'Joe', lastName: 'Blow', displayName: 'Joey558'});
var p3 = PlayersService.addPlayer({firstName: 'Danny', lastName: 'Danger', displayName: 'DD83'});


