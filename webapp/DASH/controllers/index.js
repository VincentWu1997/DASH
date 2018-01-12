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

// setting up basic database if it doesnt exist
var database_setup = require('../models/setup')
database_setup.createDatabase()

/* testing /models/staff_account.js
    * all use cases for staff_account model
var StaffAccountTest = require('../models/staff_account')
StaffAccountTest.create('staff01', 'password', 0, (err) => {
    if(err) console.log(err.message)
})
StaffAccountTest.updatePriviledge('staff01', 0)
StaffAccountTest.changePassword('staff01', 'password', 'password123')
StaffAccountTest.printAll()
*/

/*/ testing /models/user_account.js
var UserAccountTest = require('../models/user_account')
UserAccountTest.create('email@email.com', 'password', 1, (err) => {
    if(err) console.log(err.message)
})
UserAccountTest.printAll()
*/

/* testing /models/feedback.js
var FeedbackTest = require('../models/feedback')
//FeedbackTest.save('email@email.com', 'Your app is shit fam!')
FeedbackTest.get((err, feedback) => {
    if(err) console.log('Error getting feedback!')
    else console.log (feedback)
})
//FeedbackTest.printAll()
*/

/* testing /models/messages.js
var MessagesTest = require('../models/messages')
//MessagesTest.save('email@email.com', 'staff01', 'Hi help me i live in a box!', 0)
//MessagesTest.save('email@email.com', 'staff01', 'I`ll ask the admin to help you upgrade to a toaster.', 1)
//MessagesTest.save('email@email.com', 'admin', 'Can`t help you sorry.', 1)
//MessagesTest.save('email@email.com', 'staff01', 'Fuck you.', 0)
MessagesTest.get('email@email.com', (err, messages) => {
    if(err) console.log('Error getting messages!')
    else console.log(messages)
})
*/


/* testing /models/referral_form.js
var ReferralTest = require('../models/referral_form')
//ReferralTest.save('email@email.com', '{JSON would be here} sent first')
//ReferralTest.save('email@email.com', '{JSON would be here} sent second')
//ReferralTest.updateStatus('email@email.com', 1)
ReferralTest.getStatus('email@email.com', (err, status) => {
    if(err) console.log('Error getting status!')
    else console.log('Status is ' + status)
})
ReferralTest.getMostRecent('email@email.com', (err, referral) => {
    if(err) console.log('Error getting referral!')
    else console.log('Most recent referral is ' + referral)
})
ReferralTest.getAll('email@email.com', (err, referrals) => {
    if(err) console.log('Error getting all referrals for user!')
    else console.log('All of the submitted referrals are below \n' + referrals)
})
//ReferralTest.printAll()
*/

// testing routes....
router.use(express.static(path.join(__dirname, '..', 'views')))
router.get('/', function(req, res) {
    res.sendFile('index.html')
})

module.exports = router

