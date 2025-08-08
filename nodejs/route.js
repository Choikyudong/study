const fs = require('fs');
const http = require('http');
const server = http.createServer((req, res) => {
  let page;
  if (req.url === '/home') {
    page = 'home.html';
  } else if (req.url === '/about') {
    page = 'about.html';
  } else {
    page = 'index.html';
  }
  fs.readFile(page, (err, data) => {
    if (err) {
      res.statusCode = 404;
      res.setHeader('Content-Type', 'text/plain');
      res.end('Page Not Found');
      return;
    }
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/html');
    res.end(data)
  });
});
server.listen(8000, () => console.log('Server running'));