'use strict';
var express = require('express');
var app = express();
var apiRouter = express.Router();
var bodyParser = require('body-parser');
// we choose a strategy for parsing
// it could have been raw, text or urlencoded too
app.use(bodyParser.json());

var passport	= require('passport');
require('./config/passport')(passport);

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

var UserController = require('./controllers/users');
var uc = new UserController(apiRouter, passport);

var CalcadaController = require('./controllers/calcadas');
var calc_c = new CalcadaController(apiRouter, passport);

var DistanciaController = require('./controllers/distancia');
var dist_c = new DistanciaController(apiRouter, passport);

var HorarioController = require('./controllers/horarios');
var horario_c = new HorarioController(apiRouter, passport);

// seed the db for testing
var UserService = require('./services/users');
var CalcadasService = require('./services/calcadas');
var DistanciaService = require('./services/distancia');

UserService.addUser({ username: "andre_default", password: "something_not_hashed", email: "andrept@gmail.com", first_name: "Andre", surname: "Peric Tavares"}, function () {
  UserService.authUser({ username: "andre_default", password: "something_not_hashed"}, function(res) {
    // mandar token a usuario
    UserService.getOne("andre_nao_existe", function(resp) {
      if (resp.status == "success") {
        console.log("sucesso ao getOne! " + resp.data);
      }
      else if (resp.status == "fail") {
        console.log("fail! motivo: " + resp.message);
      }
      else if (resp.status == "error") {
        console.log("error! motivo: " + resp.message);
      }
    });
  });
  // UserService.authUser({ username: "andre_not_exists", password: "something_not_hashed"})
})

UserService.addUser({
  username: "steffan_diorgy",
  password: "password123testetc",
  email: "steff@lgmai.com",
  first_name: "Steffan",
  surname: "Diorgy"
},
function () {});

UserService.addUser({
  username: "lais_marinho",
  password: "password123testetc",
  email: "lais_lalala@lgmai.com",
  first_name: "Lais",
  surname: "Marinho"
},
function () {});


apiRouter.post('/test', passport.authenticate('jwt', { session: false }), function(req, res) {
  res.status(200).send("oi!!!!");
  }); 