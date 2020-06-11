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

function getSingleHuntResult(hunt) {
  const result = [];
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

    const playerBalance = {
      balance: Math.round(huntBalance / Object.keys(players).length - player.balance),
      name: player.name,
    };

    result.push(playerBalance);
  });

  return result;
}

export default {
  getHuntResult: (state) => {
    const playersFinalBalance = {};
    state.hunts.filter((hunt) => !hunt.paid).forEach((hunt) => {
      const huntResult = getSingleHuntResult(hunt);

      huntResult.forEach((result) => {
        if (!(result.name in playersFinalBalance)) {
          playersFinalBalance[result.name] = {
            name: result.name,
            balance: 0,
          };
        }

        playersFinalBalance[result.name] = mergePlayerData(
          playersFinalBalance[result.name],
          result,
        );
      });
    });

    const result = [];

    Object.keys(playersFinalBalance).forEach((name) => {
      result.push({
        name,
        balance: playersFinalBalance[name].balance,
      });
    });

    return result;
  },
  getTransferSuggestion: (state) => {
    const huntTransfers = {};

    state.hunts.filter((hunt) => !hunt.paid).forEach((hunt) => {
      const huntResult = getSingleHuntResult(hunt);

      const positivePlayers = [];
      const negativePlayers = [];

      huntResult.forEach((player) => {
        if (player.balance < 0) {
          positivePlayers.push(player);
        }

        if (player.balance > 0) {
          negativePlayers.push(player);
        }
      });

      for (let p = 0; p < positivePlayers.length; p += 1) {
        const positivePlayer = positivePlayers[p];
        const positivePlayerName = positivePlayer.name;

        for (let n = 0; n < negativePlayers.length; n += 1) {
          const negativePlayer = negativePlayers[n];
          const negativePlayerName = negativePlayer.name;

          const positivePlayerBalance = Math.abs(positivePlayer.balance);

          if (positivePlayerBalance !== 0 && negativePlayer.balance !== 0) {
            let amount = 0;
            if (positivePlayerBalance >= negativePlayer.balance) {
              amount = negativePlayer.balance;
            } else {
              amount = positivePlayerBalance;
            }

            if (!(positivePlayerName in huntTransfers)) {
              huntTransfers[positivePlayerName] = {};
            }

            if (!(negativePlayerName in huntTransfers[positivePlayerName])) {
              huntTransfers[positivePlayerName][negativePlayerName] = 0;
            }

            huntTransfers[positivePlayerName][negativePlayerName] += amount;
            positivePlayer.balance += amount;
            negativePlayer.balance -= amount;
          }
        }
      }
    });

    const result = [];
    let id = 0;
    Object.keys(huntTransfers).forEach((from) => {
      Object.keys(huntTransfers[from]).forEach((to) => {
        result.push({
          id: id += 1,
          from,
          to,
          amount: huntTransfers[from][to],
        });
      });
    });
    return result;
  },
};
