var express = require('express')
var router = express.Router()
var path = require('path');

// backend api 
var account = require('../middlewares/account')
var referral_form = require('../middlewares/referral_form')
var messages = require('../middlewares/messages')
var feedback = require('../middlewares/feedback')
var news = require('../middlewares/news')
var logon = require('../middlewares//logon')


// setting up routes for backend api
router.use('/account', account)
router.use('/referral_form', referral_form)
router.use('/messages', messages)
router.use('/feedback', feedback)
router.use('/news', news)
router.use('/logon', logon)

// testing routes....
router.use(express.static(path.join(__dirname, '..', 'views')))
router.get('/', function(req, res) {
    res.sendFile('index.html')
})

module.exports = router

