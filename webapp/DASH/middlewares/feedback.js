var express = require('express')
var router = express.Router()

// post method to send feedback from mobile app
/*
    email
    feedback
*/
router.post('/send_feedback', function(req, res) {
    console.log("/feedback/send_feedback")
})

// get method to receive feedback that users have sent
router.get('/get_feedback', function(req, res) {
    console.log("/feedback/get_feedback")
})

module.exports = router