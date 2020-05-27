export function partyHuntValidation() {
  return (text) => {
    if (!text) {
      return false;
    }

    if (text.length <= 0) {
      return false;
    }

    return new RegExp('^Session data:.+?Healing: [\\d,]+[\\s]*$', 's').test(text);
  };
}

export function notEmpty() {
  return (text) => {
    if (!text) {
      return false;
    }

    return text.length > 0;
  };
}
