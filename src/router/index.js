import Vue from 'vue';
import VueRouter from 'vue-router';
import LootCalculator from '../views/LootCalculator.vue';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

const routes = [{
  path: '/',
  name: 'Home',
  component: Home,
},
{
  path: '/loot',
  name: 'Loot Calculator',
  component: LootCalculator,
},
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
