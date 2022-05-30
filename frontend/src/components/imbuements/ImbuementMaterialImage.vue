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
  <div class="material-image">
    <v-tooltip top>
      <template v-slot:activator="{ on }">
        <span class="quantity" v-if="value.quantity">{{value.quantity}}</span>
        <a :href="'https://tibia.fandom.com/wiki/' + normalizedMaterialName" target="_blank">
          <img :src="materialImage"
               width="32"
               height="32"
               v-on="on"/>
        </a>
      </template>
      <span>View on Tibia Fandom</span>
    </v-tooltip>
  </div>
</template>
<script>

export default {
  name: 'ImbuementMaterialImage',
  props: {
    value: {
      type: Object,
    },
  },
  computed: {
    normalizedMaterialName() {
      return this.value.name.replace(/ /g, '_');
    },
    materialImage() {
      return `/images/imbuements/materials/${this.normalizedMaterialName}.gif`;
    },
  },
  methods: {},
};
</script>

<style scoped>
  .quantity {
    margin-right: 5px;
  }

  .material-image {
    align-self: flex-end;
    vertical-align: middle;
    display: inline-flex;
    margin-right: 10px;
  }
</style>
