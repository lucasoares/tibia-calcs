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

    <HuntsResult/>
  </div>
</template>

<script>
import HuntsCreationForm from '@/components/hunts/HuntsCreationForm.vue';
import HuntsTable from '@/components/hunts/HuntsTable.vue';
import HuntsResult from '@/components/hunts/HuntsResult.vue';

export default {
  name: 'Loot',
  components: {
    HuntsTable,
    HuntsCreationForm,
    HuntsResult,
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

<style>
  .green-color {
    color: green;
  }

  .red-color {
    color: red;
  }

  .yellow-color {
    color: yellow;
  }
</style>

<style scoped>
  #main-title {
    text-align: center;
  }
</style>
