import Vue from 'vue';

Vue.filter('round', (value, accuracy, keep) => {
  if (typeof value !== 'number') {
    return value;
  }

  const fixed = value.toFixed(accuracy);

  return keep ? fixed : +fixed;
});

Vue.filter('abs', (value) => {
  if (typeof value !== 'number') {
    return value;
  }

  return Math.abs(value);
});
