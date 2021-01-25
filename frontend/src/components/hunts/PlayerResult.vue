<!--
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
-->

<template>
  <div class="player-result">
    <b>{{player.name}}</b>
    <br>
    <label v-if="player.balance > 0" class="stats-key">needs to receive </label>
    <label v-if="player.balance < 0" class="stats-key">owes </label>
    <label v-if="player.balance === 0" class="stats-key">everything settled </label>
    <span v-if="player.balance != 0"
          :class="{
              'green-color' : player.balance < 0,
              'red-color' : player.balance > 0,
        }">
          {{player.balance | abs }}</span><br>
  </div>
</template>

<script>
export default {
  name: 'PlayerResult',
  props: {
    player: {
      default: () => {
      },
    },
  },
};
</script>

<style>
  .red-color {
    color: red;
  }

  .green-color {
    color: green;
  }
</style>
