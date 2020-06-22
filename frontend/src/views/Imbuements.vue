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
  <div class="imbuement">
    <h1 id="main-title">
      Imbuements Calculator
    </h1>

    <v-container class="calculator">
      <v-row no-gutters>
        <v-col cols="4">
          <h3>Configuration</h3>

          <v-row>
            <v-col sm="4" lg="3" xl="2">
              <img :src="getBaseImage"/>
            </v-col>
            <v-col>
              <v-autocomplete
                  class="imbuement-selector"
                  :items="imbuements"
                  label="Imbuement"
                  v-model="selected"
                  item-value="id"
                  :item-text="getLabel"
              ></v-autocomplete>
            </v-col>
          </v-row>

          <v-radio-group
              class="tier-select"
              dense
              v-model="tier"
              mandatory
          >
            <v-radio :value="1" label="Basic"></v-radio>
            <v-radio :value="2" label="Intricate"></v-radio>
            <v-radio :value="3" label="Powerful"></v-radio>
          </v-radio-group>
          <v-checkbox v-model="chance" label="100% Chance"></v-checkbox>
        </v-col>
        <v-col cols="4">
          <h3>Materials Prices</h3>

          <v-row dense>
            <v-col
                v-for="n in 4"
                :key="n"
                cols="12"
            >
              <b v-if="imbuements[selected].goldToken && n===4">Or</b>
              <v-row no-gutters
                     v-if="imbuements[selected].goldToken && n === 4 || (n < 4 && tier >= n)"
              >
                <ImbuementMaterialImage
                    v-if="n === 4"
                    v-model="goldToken"/>
                <v-text-field
                    v-if="n === 4"
                    v-on:change="updateStore"
                    type="number"
                    class="material-price"
                    v-model="goldToken.price"
                    append-icon="mdi-currency-usd-circle-outline"
                    :label="getMaterialLabel(n - 1)"/>

                <ImbuementMaterialImage
                    v-if="n < 4"
                    v-model="selectedImbuement.materials[n-1]"/>
                <v-text-field
                    v-if="n < 4"
                    v-on:change="updateStore"
                    type="number"
                    class="material-price"
                    v-model="imbuements[selected].materials[n - 1].price"
                    append-icon="mdi-currency-usd-circle-outline"
                    :label="getMaterialLabel(n - 1)"/>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="4">
          <h3>Results</h3>

          <v-container class="imbuements-results">
            <v-row>
              <v-col cols="12" v-if="imbuements[selected].goldToken">
                <b>Best choice:</b>

                <span v-if="totalMaterialsPrice < goldTokenTotalPrice">
                  <ImbuementMaterialImage
                      v-for="n in 3"
                      :key="n"
                      v-model="selectedImbuement.materials[n-1]"/>
                </span>

                <span v-if="goldTokenTotalPrice <= totalMaterialsPrice">
                  <ImbuementMaterialImage
                      v-model="goldToken"/>
                </span>
              </v-col>
              <v-col cols="12">
                <h4 v-if="imbuements[selected].goldToken">Materials</h4>
                <v-divider class="materials-diviser" v-if="imbuements[selected].goldToken"/>
                <p>Total Price: {{totalMaterialsPrice.toLocaleString()}}</p>
                <p>Price per Hour: {{materialsPricePerHour.toLocaleString()}}</p>
              </v-col>
              <v-col cols="12" v-if="imbuements[selected].goldToken">
                <h4>Gold Tokens</h4>
                <v-divider class="materials-diviser"></v-divider>
                <p>Total Price: {{goldTokenTotalPrice.toLocaleString()}}</p>
                <p>Price per Hour: {{goldTokenPricePerHour.toLocaleString()}}</p>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import ImbuementMaterialImage from '@/components/imbuements/ImbuementMaterialImage.vue';

export default {
  name: 'Imbuements',
  components: { ImbuementMaterialImage },
  data: () => ({
    selected: 0,
    tier: 3,
    chance: true,
  }),
  computed: {
    ...mapGetters('imbuements', { imbuements: 'getImbuements', goldToken: 'getGoldToken' }),
    tierPrice() {
      switch (this.tier) {
        case 1:
          return 5000;
        case 2:
          return 25000;
        default:
          return 100000;
      }
    },
    chancePrice() {
      if (!this.chance) {
        return 0;
      }
      switch (this.tier) {
        case 1:
          return 10000;
        case 2:
          return 30000;
        default:
          return 50000;
      }
    },
    materialsPrice() {
      let price = 0;

      for (let n = 0; n < this.tier; n += 1) {
        const material = this.selectedImbuement.materials[n];

        if (material.price) {
          price += material.quantity * Number(material.price);
        }
      }

      return price;
    },
    totalMaterialsPrice() {
      return this.tierPrice + this.chancePrice + this.materialsPrice;
    },
    materialsPricePerHour() {
      return Math.round(this.totalMaterialsPrice / 20);
    },
    getBaseImage() {
      return `/images/imbuements/base/${this.normalizeString(this.selectedImbuement.name)}_${this.tier}.png`;
    },
    goldTokenTotalPrice() {
      if (!this.goldToken.price) {
        return this.tierPrice + this.chancePrice;
      }

      return 2 * this.tier * this.goldToken.price
          + this.tierPrice + this.chancePrice;
    },
    goldTokenPricePerHour() {
      return Math.round(this.goldTokenTotalPrice / 20);
    },
    selectedImbuement() {
      return this.imbuements[this.selected];
    },
  },
  methods: {
    ...mapActions('imbuements', ['updateImbuements']),
    updateStore() {
      this.updateImbuements(this.imbuements);
    },
    normalizeString(str) {
      return str.replace(/ /g, '_');
    },
    getMaterialLabel(n) {
      if (n === 3) {
        return `${this.tier * 2}x ${this.goldToken.name}`;
      }

      return `${this.selectedImbuement.materials[n].quantity}x ${this.selectedImbuement.materials[n].name}`;
    },
    getLabel(imbuement) {
      return `${imbuement.name} (${imbuement.description})`;
    },
  },
};
</script>

<style scoped>
  .materials-diviser {
    margin-bottom: 10px;
  }

  .material-price {
    margin-left: 5px;
  }

  .imbuements-results {
    padding: 0px 10% 0px 10%;
  }

  h1, h3, h4 {
    text-align: center;
  }

  .imbuement-selector {
    max-width: 80%;
  }

  .tier-select {
    max-width: 100px;
  }

  ::v-deep input::-webkit-outer-spin-button,
  ::v-deep input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
</style>
