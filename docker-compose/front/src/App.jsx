import { useState } from 'react'

function App() {
  const [list, setList] = useState([]);

  const handlePostBtn = () => {
    fetch('/api', {
      method: 'GET'
    })
    .then(res => res.text())
    .then(data => {
      console.log(data);
      setList(prevList => [...prevList, data]);
    })
    .catch(err => {
      console.log(err);
    })
  }

  return (
    <>
      <button onClick={handlePostBtn}>테스트!</button>
      {
        list.map((data, index) => {
          return <div key={index}>{data}</div>
        })
      }
    </>
  )
}

export default App
