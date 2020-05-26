<template>
  <v-card raised class="mx-auto">
    <v-card-text>
      <p class="display-1 text--primary">
        {{player.name}}
        <v-icon v-if="player.leader">mdi-shield-star</v-icon>
      </p>

      <div class="text--primary">
        <v-icon color="grey">mdi-swap-vertical</v-icon>
        <label class="stats-key">Balance </label>
        <span
            :class="{
              'red-color' : player.balance < 0,
              'green-color' : player.balance > 0,
              'yellow-color' : player.balance === 0
        }">
          {{player.balance}}</span><br>

        <v-icon color="grey">mdi-diamond-stone</v-icon>
        <label class="stats-key">Loot </label>{{player.loot}}<br>
        <v-icon color="grey">mdi-bottle-tonic-outline</v-icon>
        <label class="stats-key">Supplies </label>{{player.supplies}}<br>
        <v-icon color="grey">mdi-sword-cross</v-icon>
        <label class="stats-key">Damange </label>{{player.damage}}<br>
        <v-icon color="grey">mdi-hospital-box-outline</v-icon>
        <label class="stats-key">Healing </label>{{player.damage}}
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
          :items="hunt.players.map(p => p.name)"
          :menu-props="{ offsetY: true }"
          label="Has transferred loot to..."
      />
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: 'Player',
  methods: {
    getBalanceColor(balance) {
      if (balance > 0) return 'green';
      if (balance < 0) return 'red';
      return 'yellow';
    },
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
};
</script>

<style>
  label.stats-key {
    color: grey;
    padding-left: 4px;
  }

  .green-color {
    color: green;
  }

  .red-color {
    color: red;
  }

  .yellow-color {
    color: yellow;
  }
</style>
