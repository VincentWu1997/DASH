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

// setting up database
const sqlite3 = require('sqlite3').verbose()
let db = new sqlite3.Database(path.join(__dirname, '..', 'models/data/database.db'), (err) => {
    if(err) {
        console.log(err.message)
    } else {
        console.log('Connected to the database.')
    }
})
db.serialize(function() {
    // creating tables
    db.run('CREATE TABLE IF NOT EXISTS Staff_Account (username VARCHAR PRIMARY KEY, password VARCHAR NOT NULL, canEditNews BOOLEAN DEFAULT FALSE)')
    db.run('CREATE TABLE IF NOT EXISTS User_Account (email VARCHAR PRIMARY KEY, password VARCHAR NOT NULL, username INTEGER NOT NULL, FOREIGN KEY (username) REFERENCES Staff_Account (username))')
    db.run('CREATE TABLE IF NOT EXISTS News (newsID INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, title VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, blurb VARCHAR,  FOREIGN KEY(username) REFERENCES Staff_Account(username))')
    db.run('CREATE TABLE IF NOT EXISTS Feedback (feedbackID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, feedback VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(email) REFERENCES User_Account(email))')    
    db.run('CREATE TABLE IF NOT EXISTS Referral_Form (referralID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, referralJSON VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, status INTEGER DEFAULT 0, FOREIGN KEY(email) REFERENCES User_Account(email))')
    
    // create admin account which will be stored as a staff account with the username admin
    var adminAccount = require('../models/staff_account')
    adminAccount.create('admin', 'password', 1, (err) => {
        if(err) console.log(err.message)
    })

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
    FeedbackTest.save('email@email.com', 'Your app is shit fam!')
    FeedbackTest.get()
    FeedbackTest.printAll()
    */

    db.close()
})






// testing routes....
router.use(express.static(path.join(__dirname, '..', 'views')))
router.get('/', function(req, res) {
    res.sendFile('index.html')
})

module.exports = router

