'use strict';
// some code is taken from https://html5hive.org/how-to-create-rest-api-with-node-js-and-express/
var app = require('express')();

app.get('/', function (req, res) {
    res.send('Hello World!');
});

var server = app.listen(3000, function () {
    var host = server.address().address;
    host = (host === '::' ? 'localhost' : host);
    var port = server.address().port;

    console.log('listening at http://%s:%s', host, port);
});

var resources = [
    {
        id: 1,
        name: 'Foo'
    }
];

app.get('/resources', function(req, res) {
    res.send(resources);
});

app.get('/resources/:id', function(req, res) {
    var id = parseInt(req.params.id, 10);
    var result = resources.filter(r => r.id === id)[0];

    if (!result) {
        res.sendStatus(404);
    } else {
        res.send(result);
    }
});

var bodyParser = require('body-parser');

// we choose a strategy for parsing
// it could have been raw, text or urlencoded too
app.use(bodyParser.json());

app.post('/resources', function(req, res) {
    // req.body is exposed by bodyParser
    var item = req.body;
 
    if (!item.id) {
        return res.sendStatus(500);
    }
 
    resources.push(item);
 
    res.send('/resources/' + item.id);
});

// update resource
app.put('/resources/:id', function(req, res) {
    var id = parseInt(req.params.id, 10);
    var existingItem = resources.filter(r => r.id === id)[0];

    if (!existingItem) {
        let item = req.body;
        item.id = id;
        resources.push(item);
        res.setHeader('Location', '/resources/' + id);
        res.sendStatus(201);
    } else {
        existingItem.name = req.body.name;
        res.sendStatus(204);
    }
});

app['delete']('/resources/:id', function(req, res) {
    var id = parseInt(req.params.id, 10);
    var existingItem = resources.filter(r => r.id === id)[0];
 
    if (!existingItem) {
        return res.sendStatus(404);
    }
 
    resources = resources.filter(r => r.id !== id);
    res.sendStatus(204);
});