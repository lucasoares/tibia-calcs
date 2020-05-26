function mergePlayerData(oldValue, newValue) {
  return {
    balance: oldValue.balance + newValue.balance,
    name: oldValue.name,
  };
}

function compute(enabledHunts) {
  const playerData = {};

  enabledHunts.forEach((hunt) => {
    const { players } = hunt;

    const playersAfterCalc = [];

    // Compute transferred to
    players.forEach((player) => {
      const playerComputed = player;

      if (playerComputed.transferredTo) {
        const playerName = playerComputed.transferredTo;

        const notALuckGuyWithTonsOfLootToSell = players.filter((p) => p.name === playerName);

        notALuckGuyWithTonsOfLootToSell.loot += player.loot;
        playerComputed.loot = 0;
      }

      playersAfterCalc.push(playerComputed);
    });

    playersAfterCalc.forEach((player) => {
      if (!(player.name in playerData)) {
        playerData[player.name] = {
          balance: 0,
        };
      }

      const result = {
        balance: hunt.balance / playersAfterCalc.length - player.balance,
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
