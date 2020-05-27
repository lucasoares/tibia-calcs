<template>
  <div class="player-result">
    <label v-if="result > 0" class="stats-key">needs to receive </label>
    <label v-if="result < 0" class="stats-key">owes </label>
    <label v-if="result === 0" class="stats-key">everything settled </label>
    <span v-if="result != 0"
          :class="{
              'red-color' : result < 0,
              'green-color' : result > 0,
        }">
          {{result | round(0) | abs }}</span><br>
  </div>
</template>

<script>
export default {
  name: 'PlayerResult',
  props: {
    result: {
      type: Number,
      default: () => ({ ...0 }),
    },
  },
};
</script>
