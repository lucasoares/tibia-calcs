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

  License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
===========================================================================
-->

<template>
  <v-form
      ref="form"
      v-model="valid"
      @submit.prevent
  >
    <v-container fluid>
      <v-textarea
          v-model="currentHunt.partyHunt"
          filled
          readonly
          no-resize
          class="party-hunt-text"
          row-height="15"
          label='Party Hunt Clipboard'
      ></v-textarea>
    </v-container>

    <v-container grid-list-xl>
      <v-layout row wrap>
        <v-flex md3 v-for="player in currentHunt.players" :key="player.name">
          <Player :player="player" :hunt="currentHunt"/>
        </v-flex>
      </v-layout>
    </v-container>

    <v-row
        align="end"
        justify="end"
        no-gutters
    >
      <v-btn
          rounded
          class="grey--text mr-4"
          @click="$emit('cancel', initialHunt)"
      >Cancel
      </v-btn>

      <v-btn
          id="btn-action"
          rounded
          color="primary"
          @click.prevent="submitForm"
      >Save
      </v-btn>
    </v-row>
  </v-form>
</template>

<script>
import { mapActions } from 'vuex';

import Player from '@/components/hunts/Player.vue';

const defaultHunt = () => ({
  partyHunt: '',
});

export default {
  name: 'HuntsEditionForm',
  components: {
    Player,
  },
  props: {
    initialHunt: {
      type: Object,
      default: () => ({ ...defaultHunt() }),
    },
  },
  data() {
    return {
      valid: false,
      currentHunt: {},
    };
  },
  watch: {
    initialHunt: {
      immediate: true,
      handler() {
        this.currentHunt = { ...defaultHunt(), ...this.initialHunt };
      },
    },
  },
  methods: {
    ...mapActions('hunts', [
      'updateHunt',
    ]),
    submitForm() {
      if (!this.valid) {
        this.$refs.form.validate();
        return 'Fill all fields';
      }

      this.updateHunt(this.currentHunt);

      return '';
    },
  },
};
</script>

<style>
  #btn-action {
    min-width: 86px;
  }

  .party-hunt-text textarea {
    margin-top: 30px !important;
  }

  .v-text-field__details {
    display: none;
  }
</style>
