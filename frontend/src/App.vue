<!--
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

  License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
===========================================================================
-->

<template>
  <v-app>
    <NavBar/>
    <main class="main">
      <router-view/>
      <notifications position="bottom right"/>
    </main>
  </v-app>
</template>

<script>
import { mapGetters } from 'vuex';
import NavBar from './components/NavBar.vue';

export default {
  name: 'App',
  methods: {
    ...mapGetters('settings', [
      'getDarkTheme',
    ]),
  },
  created() {
    this.$vuetify.theme.dark = this.getDarkTheme();
  },
  components: {
    NavBar,
  },
};
</script>

<style>
  #app {
    font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
    Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
    height: 100%;
  }

  #main-title {
    font-size: 2rem;
    margin-bottom: 1rem;
  }

  main {
    padding: 2rem;
    margin: 6rem 3% 3rem;
    border-radius: 25px;
  }
</style>
