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

  License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
===========================================================================
*/

const moment = require('moment');

function getNumber(name, data) {
  const re = new RegExp(`.*${name}:`, 'g');

  return parseInt(data.replace(re, '').replace(/,/g, ''), 10);
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

    if (huntData || newPlayer) {
      currentPlayer = {};
      currentPlayer.imbuementCost = 0;
      currentPlayer.transferredTo = '';
      currentPlayer.paid = false;
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

    if (data.includes('Healing')) {
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
