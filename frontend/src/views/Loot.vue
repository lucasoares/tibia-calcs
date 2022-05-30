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
    <p>Create and manage your party hunts.</p>
    <p>Click at the create button and paste the Session Data from the Party Hunt Analyzer.</p>

    <v-btn v-show="!creating" rounded color="primary" @click="startCreating">Create </v-btn>

    <div v-if="creating">
      <HuntCreationForm @cancel="cancel" @close="cancel" />
    </div>

    <HuntsTable />

    <div class="ad-container">
      <Adsense
        data-ad-client="ca-pub-4254262349718636"
        data-ad-slot="7936683191"
        :data-full-width-responsive="true"
      >
      </Adsense>
    </div>

    <HuntResult />
  </div>
</template>

<script>
import HuntCreationForm from '@/components/hunts/HuntCreationForm.vue';
import HuntsTable from '@/components/hunts/HuntsTable.vue';
import HuntResult from '@/components/hunts/HuntResult.vue';

export default {
  name: 'Loot',
  components: {
    HuntsTable,
    HuntCreationForm,
    HuntResult,
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
