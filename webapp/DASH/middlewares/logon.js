var express = require('express')
var router = express.Router()

// may have to create a cookie authentication service type deal

// post method which checks if a user logon is legitimate
/*
    email
    password
*/
router.post('/user', function(req, res) {
    console.log("/logon/user")
})

// post method which allows a user to reset their password
/*
    email
    old_password
    new_password
*/
router.post('/user/reset_password', function(req, res) {
    console.log("/logon/user/reset_password")
})

// post method which checks if a staff (includes admin) logon is legitimate
/*
    username
    password
*/
router.post('/staff', function(req, res) {
    console.log("/logon/staff")
})

// post method which allows staff to reset their password
/*
    username
    old_password
    new_password
*/
router.post('/staff/reset_password', function(req, res) {
    console.log("/logon/staff/reset_password")
})

module.exports = router