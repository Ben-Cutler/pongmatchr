import React, {useEffect, useState} from "react";
import {getMatches} from "./apiHelper";
import {WinnerSelectionMenu} from './WinnerSelectionMenu'

export function Matches(){
  const [matches, setMatches] = useState([])

  const fetchMatches = () => {
    getMatches().then( (result) => {
      setMatches(result)
    })
  };
  useEffect(fetchMatches, []);

  return <div>
    <h2>Matches</h2>
    {
      matches.map((match) => <div key={match.id} data-aid="App__match" className= "Matches__match">
        {match.player1.name} + {match.player2.name} <WinnerSelectionMenu players = {[match.player1, match.player2 ]} />
      </div>)
    }
  </div>

}
