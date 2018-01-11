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
    db.run('CREATE TABLE IF NOT EXISTS Staff_Account (staffID INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password VARCHAR not null, canEditNews BOOLEAN DEFAULT FALSE)')
    db.run('CREATE TABLE IF NOT EXISTS User_Account (userID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, password VARCHAR not null, staffID INTEGER NOT NULL, FOREIGN KEY (staffID) REFERENCES Staff_Account (staffID))')
    db.run('CREATE TABLE IF NOT EXISTS News (newsID INTEGER PRIMARY KEY AUTOINCREMENT, staffID INTEGER NOT NULL, title VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, blurb VARCHAR,  FOREIGN KEY(staffID) REFERENCES Staff_Account(staffID))')
    db.run('CREATE TABLE IF NOT EXISTS Feedback (feedbackID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER NOT NULL, feedback VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(userID) REFERENCES User_Account(userID))')    
    db.run('CREATE TABLE IF NOT EXISTS Referral_Form (referralID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER NOT NULL, referralJSON VARCHAR not null, date DATETIME DEFAULT CURRENT_TIMESTAMP, status INTEGER DEFAULT 0, FOREIGN KEY(userID) REFERENCES User_Account(userID))')
    
    // inserting test data
    db.run('INSERT INTO Staff_Account(staffID, username, password, canEditNews) VALUES(0, "staff", "password", 0)', (err) => {
        if(err) console.log(err)
        else console.log('Insert success')
    })
    
    db.all('SELECT * FROM Staff_Account', [], (err, rows) => {
        if (err) {
          throw err;
        }
        rows.forEach((row) => {
          console.log(row);
        });
      });

    db.close()
})

// testing routes....
router.use(express.static(path.join(__dirname, '..', 'views')))
router.get('/', function(req, res) {
    res.sendFile('index.html')
})

module.exports = router

