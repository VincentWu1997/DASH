const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

exports.save = function(email, username, message, sender) {
    db = openConnection()

    db.run('INSERT INTO Messages(email, username, message, sender) VALUES(?, ?, ?, ?)', [email, username, message, sender], (err) => {
        if(err) console.log(err.message)
        else console.log('message: ' + message + ' successfully inserted!')
    })

    db.close()
}

// gets all messages for a particular user
exports.get = function(email, callback) {
    db = openConnection()
    
    db.all('SELECT email, username, message, date, sender FROM Messages WHERE email = (?) ORDER BY datetime(date)', [email], (err, rows) => {
        callback(err, rows)
    })

    db.close()
}

exports.printAll = function(email) {
    db = openConnection()
    
        db.each('SELECT * FROM Messages', (err, row) => {
            if(err) console.log(err.message)
            console.log(row);
        })
    
        db.close()
}