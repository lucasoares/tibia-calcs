<template>
  <v-app-bar class="navbar" app>
    <router-link to="/">
      <span class="site-name">Tibia Calc</span>
    </router-link>

    <v-spacer></v-spacer>

    <div class="links">
      <nav class="nav-links">
        <div class="nav-item">
          <router-link class="nav-link" to="/loot">
            Party Hunt Loot
          </router-link>
        </div>

        <!--<div class="nav-item">
          <a href="https://github.com/luizcsm/tibia-calc" target="_blank" rel="noopener noreferrer" class="repo-link external" >
            GitHub
            <v-icon size="14px">mdi-open-in-new</v-icon>
          </a>
        </div>-->
      </nav>
    </div>
  </v-app-bar>
</template>

<style scoped>
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
