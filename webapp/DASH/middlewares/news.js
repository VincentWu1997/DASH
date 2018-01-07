var express = require('express')
var router = express.Router()

// get method that returns an array of news stories from a certain date
router.get('/', function(req, res) {
    console.log("/news")
})

// post method that adds a new news story 
router.post('/add', function(req, res) {
    console.log("/news/add")
})

// post method that removes a specific news story
router.post('/remove', function(req, res) {
    console.log("/news/remove")
})

// post method that alters an existing news story
router.post('/update', function(req, res) {
    console.log("/news/update")
})

module.exports = router