import { useContractReader } from "eth-hooks";
import { ethers } from "ethers";
import React from "react";
import { Link } from "react-router-dom";
import Techtonic from "../helpers/Techtonic";
import PlayerInfo from "../components/TechtonicComponents/PlayerInfo";
import Deck from "../components/TechtonicComponents/Deck";
/**
 * web3 props can be passed from '../App.jsx' into your local view component for use
 * @param {*} yourLocalBalance balance on current network
 * @param {*} readContracts contracts from current chain already pre-loaded using ethers contract module. More here https://docs.ethers.io/v5/api/contract/contract/
 * @returns react component
 **/
function Home({ yourLocalBalance, readContracts }) {
  const app = new Techtonic()
  const currPlayer = app.getCurrrentPlayer();
  const ownedCards = app.getOwnedCards(app.currPlayer);
  return (
    <div>
      <PlayerInfo player={currPlayer} />
      <Deck cards={ownedCards}/>
    </div>
  );
}

export default Home;
