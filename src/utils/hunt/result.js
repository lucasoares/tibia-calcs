function mergePlayerData(oldValue, newValue) {
  return {
    balance: oldValue.balance + newValue.balance,
    name: oldValue.name,
  };
}

function getImbuementCost(hunt, perHour) {
  return perHour * (hunt.session / 1000 / 60 / 60);
}

function compute(enabledHunts) {
  const playerData = {};

  enabledHunts.forEach((hunt) => {
    const players = {};

    hunt.players.forEach((player) => {
      players[player.name] = {
        name: player.name,
        loot: playerplayer.loot,
        balance: player.balance,
        transferredTo: player.transferredTo,
        imbuementCost: player.imbuementCost,
      };
    });

    // Compute transferred to
    Object.keys(players).forEach((name) => {
      const player = players[name];

      if (player.transferredTo) {
        const unlockPlayerWithTonsOfLootToSell = players[player.transferredTo];

        unlockPlayerWithTonsOfLootToSell.balance += player.loot;
        player.balance -= player.loot;

        const playerImbuementCost = getImbuementCost(hunt, player.imbuementCost);

        hunt.balace -= playerImbuementCost;
      }
    });

    Object.keys(players).forEach((name) => {
      const player = players[name];

      if (!(player.name in playerData)) {
        playerData[player.name] = {
          balance: 0,
        };
      }

      const result = {
        balance: hunt.balance / Object.keys(players).length - player.balance,
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
}


export default compute;
