<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <router-link to="/">
          <h1>Tibia Calc</h1>
        </router-link>
      </div>
      <v-spacer></v-spacer>
      <router-link to="/loot">
        <v-btn text>
          <span class="mr-2">Loot Calculator</span>
        </v-btn>
      </router-link>
      <v-btn href="https://github.com/luizcsm/tibia-calc" target="_blank" text>
        <span class="mr-2">GitHub</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: 'App',

  components: {},

  data: () => ({
    //
  }),
};
</script>
