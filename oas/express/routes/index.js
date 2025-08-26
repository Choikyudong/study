var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.status(200).send("Get Ok!");
});

router.post('/', function(req, res, next) {
  const {num} = req.body;
  res.status(201).location('/').send(`Post ${num} Ok!`);
});

module.exports = router;
