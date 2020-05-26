<template>
  <v-container fluid>
    <h1>Results</h1>
    <br>
    <v-container grid-list-xl>
      <v-layout row wrap>
        <v-flex xs4 sm4 md3 x20 v-for="player in computeResult()" :key="player.name">
          <h3>{{player.name}}</h3>
          <Balance :balance="player.balance"/>
        </v-flex>
      </v-layout>
    </v-container>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';
import compute from '@/utils/hunt/hunt_result';
import Balance from '@/components/Balance.vue';

export default {
  name: 'HuntsResult',
  components: {
    Balance,
  },
  computed: {
    ...mapState('hunts', [
      'hunts',
    ]),
  },
  methods: {
    computeResult() {
      return compute(this.getEnabledHunts());
    },
    getEnabledHunts() {
      return this.hunts.filter((hunt) => hunt.enabled);
    },
  },
};
</script>

<style>
</style>
