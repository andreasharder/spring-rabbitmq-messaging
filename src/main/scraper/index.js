'use strict';

var amqp = require('amqp');
var metascraper = require('metascraper');

var connection = amqp.createConnection({url: "amqp://guest:guest@127.0.0.1:5672"});

// add this for better debuging
connection.on('error', function(e) {
    console.log("Error from amqp: ", e);
});

function sendMessage(exchange, payload) {
    var encoded_payload = JSON.stringify(payload);

    exchange.publish('', encoded_payload, {
        contentType: "application/json"
    }, function (hasErrorOccured, err) {

        if (hasErrorOccured) {
          return console.log(err.message);
        }
        console.log('Message send to exchange!');
    });
}

function scrapeWeb(message, exchange) {
    metascraper.scrapeUrl(message.url).then((metadata) => {
        sendMessage(exchange, {
            summary: metadata.description,
            url: metadata.url
        });
        console.log(metadata);
    });
}

// Wait for connection to become established.
connection.once('ready', function () {

    var exc = connection.exchange('scraper.exchange', {
        type: 'fanout',
        durable: true,
        autoDelete: false
    }, function (exchange) {
        console.log('Exchange ' + exchange.name + ' is open');
    });

    connection.queue('tasks.queue', {
        durable: true,
        autoDelete: false
    }, function (queue) {

        console.log('Queue ' + queue.name + ' is open');
        queue.bind('tasks.exchange', '');

        queue.once('queueBindOk', function () {
            queue.subscribe({
                ack: true
            }, function (message) {

                console.log(message);
                queue.shift();

                scrapeWeb(message, exc);
            });
        });
    });
});
