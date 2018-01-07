var express = require('express')
var router = express.Router()

// post method for submitting a referral form
router.post('/form_submission', function(req, res) {
    console.log("/referral_form/form_submission")
})

// get method for the current status of a referral
router.get('/application_status', function(req, res) {
    console.log("/referral_form/application_status")
})

// post method for updating a specific users application status
router.post('/application_status_update', function(req, res) {
    console.log("/referral_form/application_update")
})

module.exports = router