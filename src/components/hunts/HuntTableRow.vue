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
  <tr
      class="hunts-table-row"
      @click="$emit('expand', props)"
  >
    <td class="text-left">
      {{ props.item.initDate }}
    </td>

    <td class="text-left">
      {{ props.item.endDate }}
    </td>

    <td class="text-left">
      {{ props.item.session | duration('humanize') }}
    </td>

    <td class="text-left">
      {{ props.item.playersNumber }}
    </td>

    <td class="text-left">
      {{ props.item.supplies }}
    </td>

    <td class="text-left">
      {{ props.item.loot }}
    </td>

    <td class="text-left">
      <v-chip :color="getBalanceColor(props.item.balance)" dark>{{ props.item.balance }}</v-chip>
    </td>

    <td class="text-left align-center justify-center">
      <v-switch
          :input-value="props.item.enabled"
          @click.stop="toggleEnabled(props.item)"
      />
    </td>

    <td class="pl-0 pr-0">
      <v-icon size="24px" @click.stop="clickOnRemoveHunt(props.item)">mdi-delete</v-icon>
    </td>
  </tr>
</template>
<script>
import { mapActions } from 'vuex';

export default {
  name: 'HuntTableRow',
  props: {
    props: {
      type: Object,
      default: () => {
      },
    },
  },
  methods: {
    ...mapActions('hunts', [
      'toggleHunt',
      'removeHunt',
    ]),
    getBalanceColor(balance) {
      if (balance > 0) return 'green';
      if (balance < 0) return 'red';
      return 'yellow';
    },
    toggleEnabled(hunt) {
      this.toggleHunt(hunt);
    },
    clickOnRemoveHunt(hunt) {
      this.removeHunt(hunt);
    },
  },
};
</script>

<style>
  td > span.v-chip {
    cursor: pointer !important;
  }
</style>
