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
  <v-form
      ref="form"
      v-model="valid"
      @submit.prevent
  >
    <v-btn
        rounded
        class="grey--text mr-4"
        @click="$emit('cancel')"
    >
      Cancel
    </v-btn>

    <v-btn
        id="btn-action"
        rounded
        color="primary"
        @click.prevent="submitForm"
    >Apply
    </v-btn>

    <v-container fluid>
      <v-textarea
          height="20rem"
          filled
          :rules="textRules"
          v-model="partyHunt"
          no-resize
          class="creation-text"
          label='Paste your Party Hunt here'
      ></v-textarea>
    </v-container>
  </v-form>
</template>

<script>
import { mapActions } from 'vuex';
import { partyHuntValidation } from '@/utils/form_rules';
import parse from '@/utils/loot/parser';

export default {
  name: 'HuntsCreationForm',
  props: {
    isEditing: {
      type: Boolean,
      default: false,
    },
    isDialog: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      textRules: [partyHuntValidation()],
      valid: false,
      partyHunt: '',
    };
  },
  methods: {
    ...mapActions('hunts', [
      'createHunt',
    ]),
    submitForm() {
      if (!this.valid) {
        this.$refs.form.validate();

        return;
      }

      this.createHunt(parse(this.partyHunt));

      this.$nextTick(() => {
        this.$emit('close', this.partyHunt);
      });
    },
  },
};
</script>

<style>
  #btn-action {
    min-width: 86px;
  }

  .creation-text textarea {
    margin-top: 30px !important;
  }
</style>
