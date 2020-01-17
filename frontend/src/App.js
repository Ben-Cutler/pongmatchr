import React, {useEffect, useState} from 'react';
import './App.css';
import {addPlayer, getPlayers} from "./apiHelper";
import Button from "@material-ui/core/Button";
import {Avatar, TextField} from "@material-ui/core";
import {Matches} from "./Matches";

function App() {
  const [name, setName] = useState("");
  const [players, setPlayers] = useState([]);

  const fetchPlayers = () => {
    getPlayers().then((result) => {
      setPlayers(result)
    })
  };


  useEffect(fetchPlayers, []);

  useEffect(() => {
    setInterval(fetchPlayers, 5000)
  }, []);

  return (
    <div className="App">
      <h1>Pong Matchr 🏓</h1>
      <div className="App__nameSubmission">
        <TextField placeholder="Enter Your Name" variant="outlined" value={name} onChange={(e) => {
          setName(e.target.value)
        }}/>
        <Button variant="outlined" color="primary" onClick={() => {
          addPlayer(name).then((response) => {
            setPlayers(response);
          })
        }}>
          Find Match
        </Button>
      </div>
      <div className="App__playerWaitingSpace">
        <h2>Waiting room</h2>
        {
          players.map((player) => <div className="App__waitingPlayer" data-aid="player" key={player.player.name}>
            <Avatar>{player.player.name[0]}</Avatar><span>{player.player.name}</span></div>)
        }
      </div>
      <Matches/>
    </div>
  );
}

export default App;
