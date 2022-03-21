
const express = require('express')
const app = express()
var count=0
app.get('/', (req, res) => {
    res.send('Hello World!')
    count +=1
    console.log(count)
})

app.listen(3000, () => console.log('Example app listening on port 3000!'))
