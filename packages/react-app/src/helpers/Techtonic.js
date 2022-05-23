import state from "./AppState";

export default class Techtonic{
  currPlayer =  0
  getCurrrentPlayer(){
    return state.players[0];
  }
  getOwnedCards(playerId){
    let result = []
    for (const [robotId, robot] of Object.entries(state.robots)){
      if (robot.owner == playerId){
        result.push(robot);
      }
    }
    return result;
  }
  getPlayers(){
    return state.players;
  }
  getCost(robot){
    const powerCost = Math.floor((robot.health + 3*robot.damage) / 10);
    const steelCost = Math.floor((3*robot.health + robot.damage) / 10);
    return {
      power:powerCost,
      steel: steelCost
    }
  }
  getImg(robot){
    switch (robot.type) {
      case 0:
        return "https://i.imgur.com/ktptAk5.png";
      case 1:
        return "https://i.imgur.com/0VyPNyf.png";
      case 2:
        return "https://i.imgur.com/lZjGvNR.png";
      case 3:
        return "https://i.imgur.com/wfTSQDz.png";
      case 4:
        return "https://i.imgur.com/yRssxcw.png";
      case 5:
        return "https://i.imgur.com/aTbWy2L.png";
      case 6:
        return "https://i.imgur.com/7hmDLcF.png";
      default:
        break;
    }
  }
  getRobotId(robot) {
    return Object.keys(state.robots).find(key => state.robots[key] === robot);
}
  getRobotsForSale(){
    let result = []
    for (const robotId of Object.keys(state.market)){
      console.log(robotId);
      console.log(state.robots[parseInt(robotId)])
      result.push(state.robots[parseInt(robotId)]);
    }
    console.log(result);
    return result;

  }
  getPrice(robot){
    return state.market[this.getRobotId(robot)];
  }
}
