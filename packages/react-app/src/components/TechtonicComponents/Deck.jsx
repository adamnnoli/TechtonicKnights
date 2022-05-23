import Card from "./Card";

const style = {
  display: "inline-block",
}
export default function Deck(props) {
  let cards = [];
  props.cards.forEach(card=>{
    cards.push(<Card robot={card} />)
  })
  return (
    <div style={style}>
      {cards}
    </div>
  );
}