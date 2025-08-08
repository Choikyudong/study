const https = require("https");

const server = https.createServer((req, res) => {
  console.log(`Request URL: ${req.url}, Method: ${req.method}`);
  res.statusCode = 200;
  res.setHeader("Content-Type", "text/plain");
  res.write("Hello World\n");
  res.end();
});

server.listen(8000, () => console.log("Hello Node.js"));