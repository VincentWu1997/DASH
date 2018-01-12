const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

exports.createDatabase = function() {
    db = openConnection()

    db.serialize(function() {
        // creating tables
        db.run('CREATE TABLE IF NOT EXISTS Staff_Account (username VARCHAR PRIMARY KEY, password VARCHAR NOT NULL, canEditNews BOOLEAN DEFAULT FALSE)')
        db.run('CREATE TABLE IF NOT EXISTS User_Account (email VARCHAR PRIMARY KEY, password VARCHAR NOT NULL, username INTEGER NOT NULL, FOREIGN KEY (username) REFERENCES Staff_Account (username))')
        db.run('CREATE TABLE IF NOT EXISTS News (newsID INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, title VARCHAR NOT NULL, date DATETIME DEFAULT CURRENT_TIMESTAMP, blurb VARCHAR NOT NULL, FOREIGN KEY(username) REFERENCES Staff_Account(username))')
        db.run('CREATE TABLE IF NOT EXISTS Feedback (feedbackID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, feedback VARCHAR NOT NULL, date DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(email) REFERENCES User_Account(email))')    
        db.run('CREATE TABLE IF NOT EXISTS Referral_Form (referralID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, referralJSON VARCHAR NOT NULL, date DATETIME DEFAULT CURRENT_TIMESTAMP, status INTEGER DEFAULT 0, FOREIGN KEY(email) REFERENCES User_Account(email))')
        db.run('CREATE TABLE IF NOT EXISTS Messages (messageID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL, username VARCHAR NOT NULL, message VARCHAR NOT NULL, date DATETIME DEFAULT CURRENT_TIMESTAMP, sender BOOLEAN NOT NULL, FOREIGN KEY(email) REFERENCES User_Account(email), FOREIGN KEY(username) REFERENCES Staff_Account(username))')
    })

     // create admin account which will be stored as a staff account with the username admin
     var adminAccount = require('../models/staff_account')
     adminAccount.create('admin', 'password', 1, (err) => {if(err) console.log('Admin account already exists!')})
}