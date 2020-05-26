<template>
  <div clasa="balance">
    <v-icon color="grey">mdi-swap-vertical</v-icon>
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
