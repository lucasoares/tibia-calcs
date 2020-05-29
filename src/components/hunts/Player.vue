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
  <v-card raised class="mx-auto">
    <v-card-text>
      <p class="display-1 text--primary">
        <v-tooltip top>
          <template v-slot:activator="{ on }">
            <span>{{player.name}}</span>
            <v-icon v-if="player.leader" v-on="on">mdi-shield-star</v-icon>
          </template>
          <span>Leader</span>
        </v-tooltip>
      </p>

      <div class="text--primary">
        <PlayerBalance :balance="player.balance"/>
        <v-icon color="grey">mdi-diamond-stone</v-icon>
        <label class="stats-key">Loot </label>{{player.loot}}<br>
        <v-icon color="grey">mdi-bottle-tonic-outline</v-icon>
        <label class="stats-key">Supplies </label>{{player.supplies}}<br>
        <v-icon color="grey">mdi-sword-cross</v-icon>
        <label class="stats-key">Damange </label>{{player.damage}}<br>
        <v-icon color="grey">mdi-hospital-box-outline</v-icon>
        <label class="stats-key">Healing </label>{{player.healing}}
      </div>
    </v-card-text>
    <v-card-text>
      <v-text-field
          v-model.number="player.imbuementCost"
          :rules="[rules.isInteger]"
          label="Imbuement cost per hour"
      />
      <v-select
          v-model="player.transferredTo"
          clearable
          :items="hunt.players.map(p => p.name).filter(name => name != player.name)"
          :menu-props="{ offsetY: true }"
          label="Has transferred loot to..."
      />
    </v-card-text>
  </v-card>
</template>

<script>
import PlayerBalance from '@/components/hunts/PlayerBalance.vue';

export default {
  name: 'Player',
  components: {
    PlayerBalance,
  },
  data() {
    return {
      rules: {
        isInteger: (value) => !value || /^\d+$/.test(value),
      },
    };
  },
  props: {
    player: {},
    hunt: {},
  },
  methods: {
    getBalanceColor(balance) {
      if (balance > 0) return 'green';
      if (balance < 0) return 'red';
      return 'yellow';
    },
  },
};
</script>

<style>
  i.mdi-shield-star {
    margin-bottom: 5px;
    margin-left: 5px;
  }

  label.stats-key {
    color: grey;
    padding-left: 4px;
  }
</style>
