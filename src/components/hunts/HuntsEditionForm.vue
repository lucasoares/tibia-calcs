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
        <v-flex xs4 sm4 md3 x20 v-for="player in currentHunt.players" :key="player">
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
