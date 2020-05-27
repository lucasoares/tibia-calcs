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
    state.hunts.filter((hunt) => hunt.enabled).forEach((hunt) => {
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
