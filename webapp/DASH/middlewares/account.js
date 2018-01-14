var express = require('express')
var router = express.Router()

// post method to send account details for a potential user account
/*
    email
    password
*/
router.post('/user_create', function(req, res) {
    console.log("/account/user_create")
})

// post method to delete a specific user account
/*
    email
*/
router.post('/user_delete', function(req, res) {
    console.log("/account/user_delete")
})

// post method to send account details for a potential staff account
/*
    username
    password
*/
router.post('/staff_create', function(req, res) {
    console.log("/account/staff_create")
})

// post method to delete a specific staff account
/*
    username
*/
router.post('/staff_delete', function(req, res) {
    console.log("/account/staff_delete")
})

// post method to update the privileges of a specific staff account
// e.g. assigning a new user to them
// or   allowing them to add/alter news stories 
/*
    username
    email
    canEditNews
*/
router.post('/staff_update_privileges', function(req, res) {
    console.log("/account/staff_update_privileges")
})

module.exports = router