const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

exports.save = function(email, feedback) {
    db = openConnection();

    db.run('INSERT INTO Feedback(email, feedback) VALUES (?, ?)', [email, feedback], (err) => {
        if(err) console.log(err.message)
        else console.log('Feedback successfully inserted.')
    })

    db.close();
}

exports.get = function(callback) {
    db = openConnection();
    
    db.all('SELECT * FROM Feedback', (err, rows) => {
        callback(err, rows)
    })
    
    db.close();
}

exports.printAll = function() {
    db = openConnection()

    db.each('SELECT * FROM Feedback', (err, row) => {
        if(err) throw err
        console.log(row)
    })

    db.close()
}