/*
===========================================================================
  Tibia Calcs GPL Source Code
  Copyright (C) 2020 Lucas Soares de Miranda
  Copyright (C) 2020 Luiz Claudio Soares de Miranda

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.

  License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
===========================================================================
*/

import Vue from 'vue';
import VueRouter from 'vue-router';
import Imbuements from '../views/Imbuements.vue';
import Loot from '../views/Loot.vue';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: {
      title: 'Tibia Calcs',
    },
  },
  {
    path: '/loot',
    name: 'Loot',
    component: Loot,
    meta: {
      title: 'Tibia Calcs - Party Hunt',
    },
  },
  {
    path: '/imbuements',
    name: 'Imbuements',
    component: Imbuements,
    meta: {
      title: 'Tibia Calcs - Imbuements',
    },
  },
  {
    path: '*',
    name: 'catchAll',
    component: Home,
    meta: {
      title: 'Tibia Calcs',
    },
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.afterEach((to) => {
  // Use next tick to handle router history correctly
  // see: https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
  Vue.nextTick(() => {
    document.title = to.meta.title || 'Tibia Calcs';
  });
});

export default router;
