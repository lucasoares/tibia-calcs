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
  <div class="transfer-suggestion">
    <p>
      <b>{{fromPlayer}}</b> should transfer
      <span class="green-color">{{amount | abs}}</span>
      to <b>{{toPlayer}}</b>
      <ClipboardTextField
          class="transfer"
          readonly
          v-model="transferText"
      />
      <ClipboardTextField
          class="transfer"
          readonly
          v-model="withdrawText"
      />
    </p>
  </div>
</template>

<script>
import ClipboardTextField from '@/components/base/ClipboardTextField.vue';

export default {
  name: 'TransferSuggestion',
  components: { ClipboardTextField },
  props: {
    fromPlayer: {},
    toPlayer: {},
    amount: {},
  },
  computed: {
    transferText() {
      return `transfer ${Math.abs(this.amount)} to ${this.toPlayer}`;
    },
    withdrawText() {
      return `withdraw ${Math.abs(this.amount)}`;
    },
  },
};
</script>

<style>
  .transfer.v-text-field > .v-input__control > .v-input__slot:before,
  .transfer.v-text-field > .v-input__control > .v-input__slot:after {
    width: 0px;
  }

  .transfer.v-text-field {
    padding-top: 0px;
  }

  .transfer.v-text-field .v-input__slot {
    margin-bottom: 0px;
  }
</style>
