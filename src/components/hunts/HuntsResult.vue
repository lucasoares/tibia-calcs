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
