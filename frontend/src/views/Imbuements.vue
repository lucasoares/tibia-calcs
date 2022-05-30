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
  <div class="imbuement">
    <h1 id="main-title">
      Imbuements Calculator
    </h1>

    <v-container class="calculator">
      <v-row no-gutters>
        <v-col cols="4">
          <h3>Imbuement Selector</h3>

          <v-row>
            <v-col sm="4" lg="3" xl="2">
              <img :src="getBaseImage" />
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

          <v-radio-group class="tier-select" dense v-model="tier" mandatory>
            <v-radio :value="1" label="Basic"></v-radio>
            <v-radio :value="2" label="Intricate"></v-radio>
            <v-radio :value="3" label="Powerful"></v-radio>
          </v-radio-group>
          <v-checkbox v-model="chance" label="100% Chance"></v-checkbox>
        </v-col>
        <v-col cols="4">
          <h3>Price</h3>

          <v-row dense>
            <v-col v-for="n in 4" :key="n" cols="12">
              <b v-if="imbuements[selected].goldToken && n === 4">Or</b>
              <v-row
                no-gutters
                v-if="(imbuements[selected].goldToken && n === 4) || (n < 4 && tier >= n)"
              >
                <ImbuementMaterialImage v-if="n === 4" v-model="goldToken" />
                <v-text-field
                  v-if="n === 4"
                  v-on:change="updateStore"
                  type="number"
                  class="material-price"
                  v-model="goldToken.price"
                  append-icon="mdi-currency-usd-circle-outline"
                  :label="getMaterialLabel(n - 1)"
                />

                <ImbuementMaterialImage v-if="n < 4" v-model="selectedImbuement.materials[n - 1]" />
                <v-text-field
                  v-if="n < 4"
                  v-on:change="updateStore"
                  type="number"
                  class="material-price"
                  v-model="imbuements[selected].materials[n - 1].price"
                  append-icon="mdi-currency-usd-circle-outline"
                  :label="getMaterialLabel(n - 1)"
                />
              </v-row>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="4">
          <h3>Result</h3>

          <v-container class="imbuements-results">
            <v-row>
              <v-col v-for="(option, n) in orderedOptions" :key="n" cols="12">
                <div class="material-icon">
                  <ImbuementMaterialImage
                    v-for="(_, i) in option.materials"
                    :key="i"
                    v-model="option.materials[i]"
                  />
                </div>
                <p>
                  Total Price:
                  <span
                    v-bind:class="{
                      'green--text': orderedOptions[n].best,
                      'red--text': !orderedOptions[n].best
                    }"
                    >{{ orderedOptions[n].price | formatNumber }}</span
                  >
                </p>
                <p>
                  Price per Hour:
                  <span
                    v-bind:class="{
                      'green--text': orderedOptions[n].best,
                      'red--text': !orderedOptions[n].best
                    }"
                    >{{ getPricePerHour(orderedOptions[n].price) | formatNumber }}</span
                  >
                </p>
                <v-divider class="materials-divider" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-container>

    <div class="ad-container">
      <Adsense
        data-ad-client="ca-pub-4254262349718636"
        data-ad-slot="7936683191"
        data-ad-format="auto"
        :data-full-width-responsive="true"
      >
      </Adsense>
    </div>
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
    getBaseImage() {
      return `/images/imbuements/base/${this.normalizeString(this.selectedImbuement.name)}_${
        this.tier
      }.png`;
    },
    selectedImbuement() {
      return this.imbuements[this.selected];
    },
    orderedOptions() {
      const options = this.paymentOptions;

      options.sort((a, b) => a.price - b.price);
      options[0].best = true;

      return options;
    },
    paymentOptions() {
      const options = [];

      const normalMaterials = [];
      for (let i = 0; i < this.tier; i += 1) {
        normalMaterials.push(this.selectedImbuement.materials[i]);
      }
      this.addToOptions(options, normalMaterials);

      if (!this.selectedImbuement.goldToken) {
        return options;
      }

      this.addToOptions(options, [
        {
          name: this.goldToken.name,
          quantity: this.tier * 2,
        },
      ]);

      if (this.tier === 1) {
        return options;
      }

      this.addToOptions(options, [
        this.selectedImbuement.materials[this.tier - 1],
        {
          name: this.goldToken.name,
          quantity: 2 * (this.tier - 1),
        },
      ]);

      if (this.tier === 2) {
        return options;
      }

      this.addToOptions(options, [
        this.selectedImbuement.materials[1],
        this.selectedImbuement.materials[2],
        {
          name: this.goldToken.name,
          quantity: 2,
        },
      ]);

      return options;
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
        return `${this.goldToken.name}`;
      }

      return `${this.selectedImbuement.materials[n].name}`;
    },
    getLabel(imbuement) {
      return `${imbuement.name} (${imbuement.description})`;
    },
    getMaterialsPrice(option) {
      let price = this.tierPrice + this.chancePrice;

      for (let n = 0; n < option.length; n += 1) {
        if (option[n].quantity) {
          if (option[n].name === this.goldToken.name) {
            if (this.goldToken.price) {
              price += option[n].quantity * this.goldToken.price;
            }
          } else {
            price += option[n].quantity * option[n].price;
          }
        }
      }

      return price;
    },
    getPricePerHour(totalPrice) {
      return Math.round(totalPrice / 20);
    },
    addToOptions(options, option) {
      options.push({
        materials: option,
        price: this.getMaterialsPrice(option),
      });
    },
  },
};
</script>

<style scoped>
.materials-divider {
  margin-bottom: 10px;
}

.material-price {
  margin-left: 5px;
}

.imbuements-results {
  padding: 0px 10% 0px 10%;
}

h1,
h3,
h4 {
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
