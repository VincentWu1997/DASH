const sqlite3 = require('sqlite3').verbose()
var path = require('path');

function openConnection() {
    let db = new sqlite3.Database(path.join(__dirname, '.', 'data/database.db'), (err) => {
        if(err) console.log(err.message)
    })
    return db
}

exports.add = function(username, title, blurb) {
    db = openConnection()
    
    db.run('INSERT INTO News(username, title, blurb) VALUES(?, ?, ?)', [username, title, blurb], (err) => {
        if(err) console.log(err.message)
        else console.log('News with title: ' + title + ' successfully inserted by staff user: ' + username + '!')
    })

    db.close()
}

// returns all the news after a certain date
exports.get = function(date, callback) {
    db = openConnection()
    
    db.all('SELECT newsID, title, date, blurb FROM News WHERE DATETIME((?)) <= DATETIME(date) ', [date], (err, rows) => {
       callback(err, rows)
    })

    db.close()
}

exports.remove = function (newsID) {
    db = openConnection()

    db.run('DELETE FROM News WHERE newsID = (?)', [newsID], (err) => {
        if (err)
            console.log(err.message)
        else
            console.log('News with Id:' + newsID + 'deleted from feed')
    })

    db.close()
}

exports.printAll = function() {
    db = openConnection()
    
    db.each('SELECT * FROM News', (err, row) => {
        if(err) console.log(err.message)
        console.log(row);
    })

    db.close()
}