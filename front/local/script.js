let cardsRaised = [];

function raise(cardId) {
  let card = document.getElementById("button" + cardId);
  if (!cardsRaised.includes(cardId)) {
    card.style.transform = "translateY(-20px)";
    cardsRaised.push(cardId);
  } else {
    card.style.transform = "translateY(0px)";
    cardsRaised = cardsRaised.filter((id) => id !== cardId);
  }
  confirmButton();
}

function confirmButton() {
  if (!cardsRaised.length == 0 && !document.getElementById("confirmButton")) {
    button = document.createElement("button");
    button.innerHTML = "Confirm";
    button.id = "confirmButton";
    button.onclick = () => console.log("test");
    document.body.appendChild(button);
  } else if (
    cardsRaised.length == 0 &&
    document.getElementById("confirmButton")
  ) {
    document.getElementById("confirmButton").remove();
  }
}
