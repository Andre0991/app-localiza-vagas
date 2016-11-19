'use strict';
var express = require('express');
var app = express();
var apiRouter = express.Router();
var bodyParser = require('body-parser');
// we choose a strategy for parsing
// it could have been raw, text or urlencoded too
app.use(bodyParser.json());

var API_VERSION = '/api/v1';

// locally
// var server = app.listen(3000, function () {
//   var host = server.address().address;
//   host = (host === '::' ? 'localhost' : host);
//   var port = server.address().port;
//   console.log('listening at http://%s:%s' + API_VERSION, host, port);
// });

// externally
var server = app.listen(3000);

app.use(API_VERSION, apiRouter);

var CalcadaController = require('./controllers/calcadas');
var pc = new CalcadaController(apiRouter);

var UserController = require('./controllers/users');
var uc = new UserController(apiRouter);

// seed the db for testing
var UserService = require('./services/users');
var CalcadasService = require('./services/calcadas');
var p1 = CalcadasService.addCalcada({ latitude: '10', longitude: '50' });
var p2 = CalcadasService.addCalcada({ latitude: '20', longitude: '60' });
var p3 = CalcadasService.addCalcada({ latitude: '30', longitude: '70' });
UserService.addUser({ username: "andre_default", password: "something_not_hashed" }, function () {
  UserService.authUser({ username: "andre_default", password: "something_not_hashed" })
  UserService.authUser({ username: "andre2", password: "something_not_hashed_wrong" })
  UserService.authUser({ username: "user_that_doesnt_exist", password: "something_not_hashed_wrong" })
})