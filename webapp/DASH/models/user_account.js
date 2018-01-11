const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let database = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return database
}

exports.create = function(email, password, username, callback) {
    db = openConnection();
    db.run('INSERT INTO User_Account(email, password, username) VALUES(?, ?, ?)', [email, password, username], (err) => {
        if(err) callback(new Error("Email already exists!"))
        else callback(null)
    })
    db.close();
}

exports.delete = function(email) {
    db = openConnection();

    db.run('DELETE FROM User_Account WHERE email = (?)', [email], (err) => {
        if(err) console.log(err.message)
        else console.log('User Account successfully deleted.')
    })

    db.close();
}

exports.printAll = function() {
    db = openConnection()

    db.each('SELECT * FROM User_Account', (err, row) => {
        if(err) throw err
        console.log(row)
    })

    db.close()
}