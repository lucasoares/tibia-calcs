import Vue from 'vue';
import VueAnalytics from 'vue-analytics';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import './filters/global_filters';

Vue.config.productionTip = false;

Vue.use(require('vue-moment'));

Vue.use(VueAnalytics, {
  id: 'UA-167771791-1',
  router,
});

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
