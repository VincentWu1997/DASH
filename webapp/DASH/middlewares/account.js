var express = require('express')
var router = express.Router()

// post method to send account details for a potential user account
router.post('/user_create', function(req, res) {
    console.log("/account/user_create")
})

// post method to delete a specific user account
router.post('/user_delete', function(req, res) {
    console.log("/account/user_delete")
})

// post method so that a users password can be altered (can only be done by the user that owns the account)
router.post('/user_password_change', function(req, res) {
    console.log("/account/user_password_change")
})

// post method to send account details for a potential staff account
router.post('/staff_create', function(req, res) {
    console.log("/account/staff_create")
})

// post method to delete a specific staff account
router.post('/staff_delete', function(req, res) {
    console.log("/account/staff_delete")
})

// post method so that a staffs password can be altered (can only be done by the staff member that owns the account)
router.post('/staff_password_change', function(req, res) {
    console.log("/account/staff_password_change")
})

// post method to update the privileges of a specific staff account
// e.g. assigning a new user to them
// or   allowing them to add/alter news stories 
router.post('/staff_update_privileges', function(req, res) {
    console.log("/account/staff_update_privileges")
})

module.exports = router