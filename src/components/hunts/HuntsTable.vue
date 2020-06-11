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
  <ToggleTable
      ref="ToggleTable"
      :headers="headers"
      :items="hunts"
  >
    <template v-slot:table-row="{ props }">
      <HuntTableRow
          :key="props.index"
          :props="props"
          @expand="expandTable"
      />
    </template>

    <template v-slot:creation-form="{ props, close }">
      <Hunt
          :initial-hunt="props.item"
          @cancel="close"
          @close="close"
      />
    </template>
  </ToggleTable>
</template>

<script>
import HuntTableRow from '@/components/hunts/HuntTableRow.vue';
import ToggleTable from '@/components/base/ToggleTable.vue';
import Hunt from '@/components/hunts/Hunt.vue';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'HuntsTable',
  components: {
    HuntTableRow,
    Hunt,
    ToggleTable,
  },
  data() {
    return {
      headers: [
        {
          text: 'Start Date',
          value: 'initDate',

        }, {
          text: 'End Date',
          value: 'endDate',
        },
        {
          text: 'Session',
          value: 'session',
        },
        {
          text: 'Players',
          value: 'playersNumber',
        },
        {
          text: 'Supplies',
          value: 'supplies',
        },
        {
          text: 'Loot',
          value: 'loot',
        },
        {
          text: 'Balance',
          value: 'balance',
        }, {
          text: 'Profit/Player',
          value: 'profit',
        }, {
          text: 'Paid',
          value: 'paid',
          width: '100px',
          sortable: false,
        },
        {
          sortable: false,
          width: '20px',
        },
      ],
    };
  },
  computed: {
    ...mapState('hunts', [
      'hunts',
    ]),
  },
  created() {
    this.updateStateVersion();
  },
  methods: {
    ...mapActions('hunts', [
      'updateStateVersion',
    ]),
    expandTable(props) {
      this.$emit('expando', props);
      props.expand(!props.isExpanded);
    },
  },
};
</script>
