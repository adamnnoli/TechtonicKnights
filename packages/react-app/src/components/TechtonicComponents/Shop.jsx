const style = {
  display: "inline-block",
  margin: "50px"
}
const imgStyle = {
  height: "400px",
  width: "400px",
  margin: "10px"
}
const buyButtonStyle={
  color:"rgb(33, 33, 33)",
  display: "block",
  margin: "auto",
  fontSize: "20px",
  backgroundColor:"rgb(44, 170, 217)",
  borderRadius: "5px"
}
export default function Shop(props){
  return (
    <div>
      {/** Buy a booster pack */}
      <div style={style}>
        <img style={imgStyle} src={"https://i.imgur.com/FV9YWU7.png"} />
        <button style={buyButtonStyle}>Buy a Booster pack for {(30+Math.random()).toFixed(5)} NRC(s)</button>
      </div>
      <div style={style}>
        <img style={imgStyle} src={"https://i.imgur.com/FDTHtoE.png"} />
        <button style={buyButtonStyle}>Buy 5 NRCs ${(8+Math.random()).toFixed(5)}</button>
      </div>
    </div>
  );
}
