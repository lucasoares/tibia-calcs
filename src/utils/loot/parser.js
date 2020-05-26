const moment = require('moment');

function getNumber(name, data) {
  const re = new RegExp(`.*${name}:`, 'g');

  return parseInt(data.replace(re, '').replace(',', ''), 10);
}

function getDates(sessionData) {
  const initDate = sessionData.replace(/.+From (.+) to.+/, '$1');
  const endDate = sessionData.replace(/.+to (.+)/, '$1');

  return {
    initDate,
    endDate,
  };
}

function parseSession(session) {
  return moment.duration(session.replace('h', '')).asMilliseconds();
}

function parse(partyHunt) {
  const lines = partyHunt.split('\n');

  let players = [];
  const hunt = {};
  let huntData = false;
  let newPlayer = false;
  let currentPlayer = {};

  lines.forEach((data) => {
    if (data.includes('Session data')) {
      const dates = getDates(data);

      hunt.initDate = dates.initDate;
      hunt.endDate = dates.endDate;

      return;
    }

    if (data.includes('Healing')) {
      players = players.concat(currentPlayer);
      newPlayer = true;

      return;
    }

    if (huntData || newPlayer) {
      currentPlayer = {};
      currentPlayer.imbuementCost = 0;
      currentPlayer.transferredTo = '';
      currentPlayer.name = data.replace(' (Leader)', '');
      currentPlayer.leader = data.includes('(Leader)');
      huntData = false;
      newPlayer = false;

      return;
    }

    if (!currentPlayer.name) {
      if (data.includes('Loot Type')) {
        hunt.lootType = data.replace('Loot Type: ', '');
        return;
      }

      if (data.includes('Session')) {
        hunt.session = parseSession(data.replace('Session: ', ''));

        return;
      }

      if (data.includes('Loot')) {
        hunt.loot = getNumber('Loot', data);
        return;
      }

      if (data.includes('Supplies')) {
        hunt.supplies = getNumber('Supplies', data);
        return;
      }

      if (data.includes('Balance')) {
        hunt.balance = getNumber('Balance', data);
        huntData = true;
        return;
      }
    }

    if (currentPlayer.name) {
      if (data.includes('Loot')) {
        currentPlayer.loot = getNumber('Loot', data);
        return;
      }

      if (data.includes('Supplies')) {
        currentPlayer.supplies = getNumber('Supplies', data);
        return;
      }

      if (data.includes('Balance')) {
        currentPlayer.balance = getNumber('Balance', data);
        return;
      }

      if (data.includes('Damage')) {
        currentPlayer.damage = getNumber('Damage', data);
        return;
      }

      if (data.includes('Healing')) {
        currentPlayer.healing = getNumber('Healing', data);
      }
    }
  });

  hunt.enabled = true;
  hunt.players = players;
  hunt.playersNumber = Object.keys(players).length;
  hunt.partyHunt = partyHunt;

  return hunt;
}

export default parse;
