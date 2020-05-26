import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import round from './filters/round';
import abs from './filters/abs';

Vue.config.productionTip = false;

Vue.use(require('vue-moment'));

Vue.filter('round', round);
Vue.filter('abs', abs);

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
