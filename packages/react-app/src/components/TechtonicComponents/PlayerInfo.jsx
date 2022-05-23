const style = {
  display: "inline-block",
  height: "400px",
  width: "400px",
  margin: "20px"
}
const imgStyle = {
  height:"400px", width:"400px"
}
const nameStyle = {
  fontSize: "24px"
}
const balanceStyle = {
  display: "inline",
  fontSize:"24px"
}
const balanceNumStyle = {
  display:"inline",
  fontSize:"20px"
}
export default function PlayerInfo(props) {
  return (
    <div style={style}>
      <img src={props.player.profilePic} style={imgStyle} />
      <div style={nameStyle}>{props.player.name}</div>
      <div style={balanceStyle}>Balance: </div>
      <div style={balanceNumStyle}>{props.player.balance}</div>
    </div>
  );
}
