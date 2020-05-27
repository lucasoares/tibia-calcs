<template>
  <v-container fluid>
    <h1>Results</h1>
    <br>
    <v-container grid-list-xl>
      <v-layout row wrap>
        <v-flex xs4 sm4 md3 x20 v-for="player in getHuntResult" :key="player.name">
          <h3>{{player.name}}</h3>
          <PlayerResult :result="player.balance"/>
        </v-flex>
      </v-layout>
    </v-container>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex';
import PlayerResult from '@/components/PlayerResult.vue';

export default {
  name: 'HuntsResult',
  components: {
    PlayerResult,
  },
  computed: {
    ...mapGetters('hunts', [
      'getHuntResult',
    ]),
  },
};
</script>

<style>
</style>
