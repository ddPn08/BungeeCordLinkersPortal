const net       = require('net');

const client = net.connect('25566', 'localhost', () => {
    console.log('connected to server');
    

    let data = {
        username: "ddPn08",
        Target: "lobby"
    }

    let connectionData = {
        method: "getServers",
        data: JSON.stringify(data)
    }

    client.write(JSON.stringify(connectionData));
});

client.on('data', data => {
    console.log('client-> ' + data);
    client.destroy();
});

client.on('close', () => {
    console.log('client-> connection is closed');
});