export function round(value, accuracy, keep) {
  if (typeof value !== 'number') return value;

  const fixed = value.toFixed(accuracy);

  return keep ? fixed : +fixed;
}

export default round;
