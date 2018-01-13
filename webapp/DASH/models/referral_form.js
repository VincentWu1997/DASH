const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

// saves a referral for a given email
exports.save = function(email, referralJSON) {
    db = openConnection()

    db.run('INSERT INTO Referral_Form(email, referralJSON) VALUES(?, ?)', [email, referralJSON], (err) => {
        if(err) console.log(err.message)
        else console.log('referral: \n----------\n' + referralJSON + '\n----------\n successfully inserted!')
    })

    db.close()
}

// gets status for a given email
exports.getStatus = function(email, callback) {
    db = openConnection()
    
    // get the status of the most recent referral attempt
    db.all('SELECT status FROM Referral_Form WHERE email = (?) ORDER BY datetime(date) DESC Limit 1', [email], (err, rows) => {
        callback(err, rows[0].status)
    })

    db.close()
}

// updates status for a given email
exports.updateStatus = function(email, status) {
    db = openConnection()
    
    // updates the status of the most recent referral attempt
    db.run('UPDATE Referral_Form SET status = (?) WHERE referralID = (SELECT referralID FROM Referral_Form WHERE date = (SELECT max(date) FROM Referral_Form WHERE email = (?))) AND email = (?)', [status, email, email], (err) => {
        if(err) console.log(err.message)
        else console.log('Status of ' + email + '`s referral updated to ' + status)
    })

    db.close()
}

// returns most recent referral for an email
exports.getMostRecent = function(email, callback) {
    db = openConnection()
    
    // get the status of the most recent referral attempt
    db.all('SELECT referralJSON FROM Referral_Form WHERE email = (?) ORDER BY datetime(date) DESC Limit 1', [email], (err, rows) => {
        callback(err, JSON.stringify(rows[0]))
    })

    db.close()
}

// returns all referrals for an email
exports.getAll = function(email, callback) {
    db = openConnection()
    
    // get the status of the most recent referral attempt
    db.all('SELECT referralJSON FROM Referral_Form WHERE email = (?)', [email], (err, rows) => {
        callback(err, JSON.stringify(rows))
    })

    db.close()
}

exports.printAll = function() {
    db = openConnection()
    
    db.each('SELECT * FROM Referral_Form', (err, row) => {
        if(err) console.log(err.message)
        console.log(row);
    })

    db.close()
}