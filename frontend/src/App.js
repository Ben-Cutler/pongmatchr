import React, {useEffect, useState} from 'react';
import './App.css';
import {addPlayer, getPlayers} from "./apiHelper";

function App() {
  const [name, setName] = useState("");
  const [players, setPlayers] = useState([]);

  useEffect( () => {
    getPlayers().then((result) => {
      setPlayers(result)
    })
  }, []);

  return (
    <div className="App">
      <h1>PONG MATCHR™</h1>
      <input value={name} onChange={(e) => {
        setName(e.target.value)
      }}/>
      <button onClick={() => {
        addPlayer(name)
      }}>Find Match
      </button>
      {
        players.map((player) => <div key={player.name} data-aid="player">{player.name}</div>)
      }
    </div>
  );
}

export default App;
