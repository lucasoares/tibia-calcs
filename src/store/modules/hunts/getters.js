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

function mergePlayerData(oldValue, newValue) {
  return {
    balance: oldValue.balance + newValue.balance,
    name: oldValue.name,
  };
}

function getImbuementCost(hunt, perHour) {
  return perHour * (hunt.session / 1000 / 60 / 60);
}

export default {
  getHuntResult: (state) => {
    const playerData = {};
    state.hunts.filter((hunt) => !hunt.paid).forEach((hunt) => {
      const players = {};

      hunt.players.forEach((player) => {
        players[player.name] = {
          name: player.name,
          loot: player.loot,
          balance: player.balance,
          transferredTo: player.transferredTo,
          imbuementCost: player.imbuementCost,
        };
      });

      // Compute transferred to
      Object.keys(players).forEach((name) => {
        const player = players[name];

        if (player.transferredTo) {
          const unluckyPlayerWithTonsOfLootToSell = players[player.transferredTo];

          unluckyPlayerWithTonsOfLootToSell.balance += player.loot;
          player.balance -= player.loot;
        }
      });

      let huntBalance = hunt.balance;
      // Compute imbuements costs
      Object.keys(players).forEach((name) => {
        const player = players[name];

        if (player.imbuementCost) {
          const playerImbuementCost = getImbuementCost(hunt, player.imbuementCost);

          huntBalance -= playerImbuementCost;
          player.balance -= playerImbuementCost;
        }
      });

      Object.keys(players).forEach((name) => {
        const player = players[name];

        if (!(player.name in playerData)) {
          playerData[player.name] = {
            name: player.name,
            balance: 0,
          };
        }

        const result = {
          balance: huntBalance / Object.keys(players).length - player.balance,
          name: player.name,
        };

        playerData[player.name] = mergePlayerData(playerData[player.name], result);
      });
    });

    const result = [];

    Object.keys(playerData).forEach((name) => {
      result.push({
        name,
        balance: playerData[name].balance,
      });
    });

    return result;
  },
};
