var express = require('express')
var app = express();

var index = require('./controllers/index')

BASE = ''

app.use(BASE + '/', index);

app.listen(8090)
console.log("App running at port 8090")