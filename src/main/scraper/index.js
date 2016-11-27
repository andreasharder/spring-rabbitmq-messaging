'use strict';

var amqp = require('amqp');
var getPage = require('summarizer').getPage;

var connection = amqp.createConnection({url: "amqp://guest:guest@127.0.0.1:5672"});

// add this for better debuging
connection.on('error', function(e) {
    console.log("Error from amqp: ", e);
});

function sendMessage(payload) {
    var encoded_payload = JSON.stringify(payload);
    connection.publish('scraper.queue', encoded_payload);
}

connection.once('ready', function () {
    connection.queue('scraper.queue', {
        durable: true,
        autoDelete: false
    }, function (queue) {
        console.log('Queue ' + queue.name + ' is open');
    });
});

// Wait for connection to become established.
connection.on('ready', function () {
    connection.queue('tasks.queue', {
        durable: true,
        autoDelete: false
    }, function (queue) {

        console.log('Queue ' + queue.name + ' is open');
        queue.bind('#');

        queue.once('queueBindOk', function () {
            queue.subscribe({
                ack: true
            }, function (message) {

                console.log(message);
                queue.shift();

                getPage(message.url).then(function (data) {
                    sendMessage({summary: data.summary});
                }, console.error);
            });
        });
    });
});
