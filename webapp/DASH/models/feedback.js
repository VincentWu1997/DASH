const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

exports.save = function(userID, feedback) {
    db = openConnection();

    db.run('INSERT INTO Feedback(userID, feedback) VALUES (?, ?)', (userID, feedback), (err) => {
        if(err) console.log(err.message)
        else console.log('Feedback successfully inserted.')
    })

    db.close();
}