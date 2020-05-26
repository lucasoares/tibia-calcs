import {
  DELETE_HUNT,
  CREATE_HUNT,
  UPDATE_HUNT,
} from './mutation_types';

export function updateHunt({ commit }, hunt) {
  commit(UPDATE_HUNT, hunt);
  return hunt;
}

export function toggleHunt({ commit }, hunt) {
  const huntToSend = hunt;
  huntToSend.enabled = !huntToSend.enabled;

  commit(UPDATE_HUNT, huntToSend);
}

export function createHunt({ commit }, hunt) {
  commit(CREATE_HUNT, hunt);
  return hunt;
}

export function removeHunt({ commit }, hunt) {
  commit(DELETE_HUNT, hunt);

  return hunt;
}
