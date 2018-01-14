var express = require('express')
var router = express.Router()

var newsModel = require('../models/news')

// get method that returns an array of news stories from a certain date
/*
    date input in url
*/
router.get('/:date', function(req, res) {
    newsModel.get(req.params.date, (err, news) => {
        res.send({'News':news})
    })
})

// post method that adds a new news story 
/*
    title urlencoded in body
    blurb
    username
*/
router.post('/add', function(req, res) {
    console.log("/news/add")
})

// post method that removes a specific news story
/*
    newsID urlencoded in body
*/
router.post('/remove', function(req, res) {
    console.log("/news/remove")
})

// post method that alters an existing news story 
/*                      Look into staff updates 
    newsID
    blurb 
    title
*/
router.post('/update', function(req, res) {
    console.log("/news/update")
})

module.exports = router