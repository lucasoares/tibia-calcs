import {
  DELETE_HUNT,
  CREATE_HUNT,
  UPDATE_HUNT,
} from './mutation_types';

export default {
  [CREATE_HUNT]: (state, hunt) => {
    state.identifier += 1;
    const { identifier } = state;

    const newHunt = hunt;
    newHunt.id = identifier;

    state.hunts = [newHunt].concat(state.hunts);
  },
  [DELETE_HUNT]: (state, huntToDelete) => {
    state.hunts = state.hunts.filter((hunt) => hunt.id !== huntToDelete.id);
  },
  [UPDATE_HUNT]: (state, huntToUpdate) => {
    const { id } = huntToUpdate;
    state.hunts = state.hunts.map((hunt) => {
      if (hunt.id === id) {
        return huntToUpdate;
      }
      return hunt;
    });
  },
};
