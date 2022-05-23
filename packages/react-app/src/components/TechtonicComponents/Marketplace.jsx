import Techtonic from "../../helpers/Techtonic";
import Card from "./Card";

const style = {
  display: "inline-block",
  padding: "4px"
}
const buyButtonStyle={
  color:"black",
  backgroundColor:"rgb(44, 170, 217)",
  borderRadius: "5px",
  fontSize: "20px"
}
let app = new Techtonic();
export default function Marketplace(props){
  let marketItems = [];
  for (const robot of app.getRobotsForSale()){
    if (robot.owner != app.currPlayer){
      marketItems.push(
        <div style={style}>
          <Card robot={robot} />
          <div>Price: {(app.getPrice(robot)+Math.random()).toFixed(5)}</div>
          <button style={buyButtonStyle}>Buy</button>
        </div>
      )
    }
  }
  return (
    <div>
      {marketItems}
    </div>
  );
}
