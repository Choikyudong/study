import {useState} from 'react'

function App() {
  const [result, setResult] = useState("");
  
  async function corsTest() {
    try {
      const res = await fetch('http://localhost:8081');
      const data = await res.text();
      setResult(data);
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <>
      {result !== "" ? result : "버튼으로 요청을 해보세요"}
      <br/>
      <button onClick={corsTest}>cors 테스트</button>
    </>
  )
}

export default App
