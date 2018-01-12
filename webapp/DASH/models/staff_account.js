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

    db.run('INSERT INTO Staff_Account(username, password, canEditNews) VALUES(?, ?, ?)', [username, password, canEditNews], (err) => {
        if(err) callback(new Error("Username already exists!"))
        else callback(null)
    })
    
    db.close();
}

exports.delete = function(username) {
    db = openConnection();

    db.run('DELETE FROM Staff_Account WHERE username = (?)', [username], (err) => {
        if(err) console.log(err.message)
        else console.log('Staff Account successfully deleted.')
    })

    db.close();
}

exports.changePassword = function(username, old_password, new_password) {
    db = openConnection()
    
    db.run('UPDATE Staff_Account SET password = (?) WHERE username = (?) AND password = (?)', [new_password, username, old_password], (err) => {
        if(err) console.log(err.message)
        else console.log('Password updated!')
    })
    
    db.close()
}

exports.updatePriviledge = function(username, canEditNews) {
    db = openConnection()

    db.run('UPDATE Staff_Account SET canEditNews = (?) WHERE username = (?)', [canEditNews, username], (err) => {
        if(err) console.log(err.message)
        else console.log('Priviledge level updated to ' + canEditNews + ' for user: ' + username)
    })

    db.close()
}

exports.printAll = function() {
    db = openConnection()

    db.each('SELECT * FROM Staff_Account', (err, row) => {
        if(err) throw err
        console.log(row)
    })

    db.close()
}

