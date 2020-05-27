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
      <HuntsEditionForm
          :initial-hunt="props.item"
          @cancel="close"
          @close="close"
      />
    </template>
  </ToggleTable>
</template>

<script>
import HuntTableRow from '@/components/hunts/HuntTableRow.vue';
import ToggleTable from '@/components/ToggleTable.vue';
import HuntsEditionForm from '@/components/hunts/HuntsEditionForm.vue';
import { mapState } from 'vuex';

export default {
  name: 'HuntsTable',
  components: {
    HuntTableRow,
    HuntsEditionForm,
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
          text: 'Enabled',
          value: 'enabled',
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

  methods: {
    expandTable(props) {
      this.$emit('expando', props);
      props.expand(!props.isExpanded);
    },
  },
};
</script>
