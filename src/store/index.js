import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersist from 'vuex-persist';

import hunts from './modules/hunts';

const vuexPersist = new VuexPersist({
  key: 'store',
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    hunts,
  },
  plugins: [vuexPersist.plugin],
});
