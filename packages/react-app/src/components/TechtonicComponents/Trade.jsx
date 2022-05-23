import Techtonic from "../../helpers/Techtonic";
import Deck from "./Deck";
import Card from "./Card";
import React from "react";

const style = {
  display: "inline-block",
  padding: "4px"
}
const buttonStyle={
  color:"black",
  backgroundColor:"rgb(44, 170, 217)",
  borderRadius: "5px"
}
const playerSelectStyle={
  width: "200px",
  display:"block",
  margin:"auto",
  color:"black",
  backgroundColor:"rgb(44, 170, 217)",
  borderRadius: "5px"
}
const tradeSectionStyle ={
  display:"inline-block",
  margin: "20px"
}
const tradeButtonStyle = {
  fontSize:"40px",
  color: "black",
  display: "block",
  margin: "auto",
  color:"rgb(33, 33, 33)",
  backgroundColor:"rgb(44, 170, 217)",
  borderRadius: "5px"
}
export default function Trade(props){
  const app = new Techtonic();
  const [otherPlayer, setOtherPlayer] = React.useState("");
  const ownedCards = app.getOwnedCards(app.currPlayer);
  let yourTradeItems = []
  for (const currPlayerCard of ownedCards){
    yourTradeItems.push(
      <div style={style}>
        <Card robot={currPlayerCard} />
        <button style={buttonStyle}>Select</button>
      </div>
    )
  }
  let otherPlayerNum = app.currPlayer;
  const players = app.getPlayers()
  for (const playerNum in players) {
    if (players[playerNum].name == otherPlayer){
      otherPlayerNum = playerNum;
    }
  }
  const otherPlayerCards = app.getOwnedCards(otherPlayerNum);
  let theirTradeItems = []
  for (const otherPlayerCard of otherPlayerCards){
    theirTradeItems.push(
      <div style={style}>
        <Card robot={otherPlayerCard} />
        <button style={buttonStyle}>Select</button>
      </div>
    )
  }
  return (
    <div>
      <div style={tradeSectionStyle}>
      <div>Pick a Card To Trade</div>
        {yourTradeItems}
      </div>
      <div style={tradeSectionStyle}>
        <input
          type="text"
          onChange={((e) => setOtherPlayer(e.target.value))}
          value = {otherPlayer}
          placeholder="Select a Player To Trade With"
          style={playerSelectStyle}
        />
        {theirTradeItems}
      </div>
      <button style={tradeButtonStyle}>Send Request</button>
    </div>
  )
}
