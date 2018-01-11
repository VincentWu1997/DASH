var express = require('express')
var router = express.Router()

// inputs email, password, staffIdentifier(optional)  outputs all messages associated with said account
router.get('/get_messages', function(req, res) {
    console.log("/messages/get_messages")
})

// inputs email, password, message, staffID function saves the messages in the database with 
router.post('/send_message', function(req, res) {
    console.log("/messages/send_message")
})

module.exports = router