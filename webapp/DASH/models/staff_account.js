const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let database = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return database
}

exports.create = function(username, password, canEditNews, callback) {
    db = openConnection();
    db.run('INSERT INTO Staff_Account(username, password, canEditNews) VALUES(?, ?, ?)', [username, password, 0], (err) => {
        if(err) callback(new Error("Username already exists!"))
        else callback(null)
    })
    db.close();
}

exports.delete = function(staffID) {
    db = openConnection();

    db.run('DELETE FROM Staff_Account WHERE staffID = (?)', [staffID], (err) => {
        if(err) console.log(err.message)
        else console.log('Staff Account successfully deleted.')
    })

    db.close();
}

exports.printAll = function() {
    db = openConnection()

    db.each('SELECT * FROM Staff_Account', (err, row) => {
        if(err) throw err
        console.log(row)
    })

    db.close()
}

