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
