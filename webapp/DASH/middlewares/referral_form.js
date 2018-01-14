var express = require('express')
var router = express.Router()

// post method for submitting a referral form
/*
    email
    referralJSON

*/
router.post('/form_submission', function(req, res) {
    console.log("/referral_form/form_submission")
})

// get method for the current status of a referral
/*
    email urlencoded
*/
router.get('/application_status', function(req, res) {
    console.log("/referral_form/application_status")
})

// post method for updating a specific users application status
/*
    referralID
    newStatus
*/
router.post('/application_status_update', function(req, res) {
    console.log("/referral_form/application_update")
})

module.exports = router