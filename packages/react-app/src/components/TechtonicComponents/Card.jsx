import Techtonic from "../../helpers/Techtonic";
const style = {
  display: "inline-block",
  padding: "4px"
}
const imgStyle = {
  width: "200px",
  height: "200px"
}
let app = new Techtonic();
export default function Card(props) {
  const robotCost = app.getCost(props.robot);
  const imgPath = app.getImg(props.robot);
  return (
    <div style={style}>
      <img src={imgPath} style={imgStyle} />
      <div>{props.robot.name}</div>
      <div>Power Use: {robotCost.power}</div>
      <div>Steel Cost: {robotCost.steel}</div>
    </div>
  );
}
