<template>
  <div class="loot">
    <h1 id="main-title">
      Party Hunt Loot
    </h1>
    <p>Here you can create and manage party hunts.</p>
    <p>Click at the create button and paste the party hunt analyzer.</p>

    <v-btn
        v-show="!creating"
        rounded
        color="primary"
        @click="startCreating"
    >Create
    </v-btn>

    <div
        v-if="creating"
    >
      <HuntsCreationForm
          @cancel="cancel"
          @close="cancel"
      />
    </div>

    <HuntsTable/>

    <h2>
      Result
    </h2>
  </div>
</template>

<script>
import HuntsCreationForm from '@/components/hunts/HuntsCreationForm.vue';
import HuntsTable from '../components/hunts/HuntsTable.vue';


export default {
  name: 'Loot',
  components: {
    HuntsTable,
    HuntsCreationForm,
  },
  data: () => ({
    creating: false,
  }),
  methods: {
    startCreating() {
      this.creating = true;
    },
    cancel() {
      this.creating = false;
    },
  },

};
</script>

<style scoped>
  #main-title {
    text-align: center;
  }
</style>
