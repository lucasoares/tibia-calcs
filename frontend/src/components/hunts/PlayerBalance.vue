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
  <div clasa="balance">
    <v-icon class="stats-icon" color="grey">mdi-swap-vertical</v-icon>
    <label class="stats-key">Balance </label>
    <span
        :class="{
              'red-color' : balance < 0,
              'green-color' : balance > 0,
              'yellow-color' : balance === 0
        }">
          {{balance | round(0) }}</span><br>
  </div>
</template>

<script>
export default {
  name: 'Balance',
  props: {
    balance: {
      type: Number,
      default: () => ({ ...0 }),
    },
  },
};
</script>

<style>
</style>
