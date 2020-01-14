import React, {useState} from 'react';
import './App.css';
import {addPlayer} from "./apiHelper";

function App() {
  const [name, setName] = useState("");

  console.log("name", name);

  return (
    <div className="App">
      <h1>PONG MATCHR™</h1>
        <input value={name} onChange={(e)=> {setName(e.target.value)}}/>
        <button onClick={()=> {addPlayer(name)}}>Find Match </button>
    </div>
  );
}

export default App;
