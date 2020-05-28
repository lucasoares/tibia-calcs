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

  License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
===========================================================================
-->

<template>
  <v-app-bar class="navbar" app>
    <router-link to="/">
      <span class="site-name">Tibia Calcs</span>
    </router-link>

    <v-spacer></v-spacer>

    <div class="links">
      <nav class="nav-links">
        <div class="nav-item">
          <router-link class="nav-link" to="/loot">
            Party Hunt Loot
          </router-link>
        </div>

        <div class="nav-item">
          <a href="https://github.com/luizcsm/tibia-calc" target="_blank" rel="noopener noreferrer"
             class="repo-link external">
            GitHub
            <v-icon size="14px">mdi-open-in-new</v-icon>
          </a>
        </div>
      </nav>
    </div>

    <v-switch
        class="theme-switch"
        v-model="darkTheme"
        @change="refreshDarkTheme"
        hide-details
        dense
        label="Theme"
    ></v-switch>
  </v-app-bar>
</template>

<script>
import { mapMutations, mapGetters } from 'vuex';

export default {
  name: 'NavBar',
  methods: {
    ...mapMutations('settings', ['setDarkTheme']),
    refreshDarkTheme() {
      this.$vuetify.theme.dark = this.getDarkTheme;
    },
  },
  computed: {
    ...mapGetters('settings', ['getDarkTheme']),
    darkTheme: {
      get() {
        return this.getDarkTheme;
      },
      set(darkTheme) {
        this.setDarkTheme(darkTheme);
      },
    },
  },
};
</script>

<style scoped>
  .theme-switch {
    margin-left: 15px;
  }

  .navbar span.site-name {
    font-size: 1.3rem;
    font-weight: 500;
  }

  .navbar .links {
    font-size: .9rem;
  }

  a {
    color: var(--v-primary-text-base) !important;
    font-weight: 500;
    text-decoration: none;
  }

  .nav-links .nav-item:first-child {
    margin-left: 0;
  }

  .nav-links .nav-item {
    position: relative;
    display: inline-block;
    margin-left: 1.5rem;
    line-height: 2rem;
  }

  .nav-links a {
    line-height: 1.4rem;
  }

  .nav-links .nav-item {
    display: inline-block;
    margin-left: 1.5rem;
  }

  .nav-item > a:not(.external).router-link-active, .nav-item > a:not(.external):hover {
    border-bottom: 2px solid var(--v-primary-base);
  }
</style>
