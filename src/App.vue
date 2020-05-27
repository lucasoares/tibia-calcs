<template>
  <v-app>
    <NavBar/>

    <main class="main">
      <v-content>
        <router-view/>
      </v-content>
    </main>
  </v-app>
</template>

<script>
import NavBar from './components/NavBar.vue';

export default {
  name: 'App',

  components: {
    NavBar,
  },
};
</script>

<style>
  #app {
    font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
    Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
  }

  #main-title {
    font-size: 2rem;
    margin-bottom: 1rem;
  }

  .main {
    padding: 0.6rem 10% 0;
  }
</style>
