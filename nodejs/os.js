import * as os from "os";
import * as http from "http";
const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify({
    platform: os.platform(),
    cpus: os.cpus().length,
    memory: os.freemem() / 1024 / 1024 + ' MB'
  }));
});
server.listen(8000);