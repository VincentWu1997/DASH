var express = require('express')
var router = express.Router()

var referralModel = require('../models/referral_form')

// post method for submitting a referral form
/*
    email
    referralJSON

*/
router.post('/form_submission', function(req, res) {
    console.log("/referral_form/form_submission")
    referralModel.save(req.body.email, req.body.referralJSON)
})

// get method for the current status of a referral
/*
    email urlencoded
*/
router.get('/application_status', function(req, res) {
    console.log("/referral_form/application_status")
    referralModel.getStatus(req.body.email, (err, status) => {
        res.send({ 'Status': status })
    })
})

// post method for updating a specific users application status
/*
    referralID
    newStatus

    **** NEEDS REVISION ****

*/
router.post('/application_status_update', function(req, res) {
    console.log("/referral_form/application_update")
    referralModel.updateStatus(req.body.email , req.body.status)
})

module.exports = router