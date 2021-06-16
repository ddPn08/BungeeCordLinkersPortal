const net       = require('net');

const client = net.connect('25566', 'localhost', () => {
    console.log('connected to server');
    client.write('test');
});

client.on('data', data => {
    console.log('client-> ' + data);
    client.destroy();
});

client.on('close', () => {
    console.log('client-> connection is closed');
});