/*
===========================================================================
  Tibia Calcs GPL Source Code
  Copyright (C) 2020 Lucas Soares de Miranda
  Copyright (C) 2020 Luiz Claudio Soares de Miranda

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.

  License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
===========================================================================
*/

const moment = require('moment');

function getValueFromLine(label, line) {
  return line.replace(new RegExp(`.*${label}:? ?`, 'g'), '');
}

function getNumber(label, line) {
  return parseInt(getValueFromLine(label, line).replace(/,/g, ''), 10);
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

  lines.forEach((line) => {
    if (line.includes('Session data')) {
      const dates = getDates(line);

      hunt.initDate = dates.initDate;
      hunt.endDate = dates.endDate;

      return;
    }

    if (huntData || newPlayer) {
      currentPlayer = {};
      currentPlayer.imbuementCost = 0;
      currentPlayer.transferredTo = '';
      currentPlayer.paid = false;
      currentPlayer.name = line.replace(' (Leader)', '');
      currentPlayer.leader = line.includes('(Leader)');
      huntData = false;
      newPlayer = false;

      return;
    }

    if (!currentPlayer.name) {
      if (line.includes('Loot Type')) {
        hunt.lootType = getValueFromLine('Loot Type', line);
        return;
      }

      if (line.includes('Session')) {
        hunt.session = parseSession(getValueFromLine('Session', line));

        return;
      }

      if (line.includes('Loot')) {
        hunt.loot = getNumber('Loot', line);
        return;
      }

      if (line.includes('Supplies')) {
        hunt.supplies = getNumber('Supplies', line);
        return;
      }

      if (line.includes('Balance')) {
        hunt.balance = getNumber('Balance', line);
        huntData = true;
        return;
      }
    }

    if (currentPlayer.name) {
      if (line.includes('Loot')) {
        currentPlayer.loot = getNumber('Loot', line);
        return;
      }

      if (line.includes('Supplies')) {
        currentPlayer.supplies = getNumber('Supplies', line);
        return;
      }

      if (line.includes('Balance')) {
        currentPlayer.balance = getNumber('Balance', line);
        return;
      }

      if (line.includes('Damage')) {
        currentPlayer.damage = getNumber('Damage', line);
        return;
      }

      if (line.includes('Healing')) {
        currentPlayer.healing = getNumber('Healing', line);
      }
    }

    if (line.includes('Healing')) {
      players = players.concat(currentPlayer);
      newPlayer = true;
    }
  });

  hunt.paid = false;
  hunt.players = players;
  hunt.playersNumber = Object.keys(players).length;
  hunt.partyHunt = partyHunt;
  hunt.profit = Math.round(hunt.balance / hunt.playersNumber);

  return hunt;
}

export default parse;
