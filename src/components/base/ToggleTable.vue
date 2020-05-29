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
  <div class="toggle-table-container">
    <v-data-table
        ref="table"
        :headers="headers"
        :header-props="headerProps"
        :items="items"
        :items-per-page="itemsPerPage"
        class="mt-3 mb-5 elevation-3"
        hide-default-footer
        must-sort
        single-expand
    >
      <template v-slot:headerCell="props">
        <span class="pl-0">{{ props.header.text }}</span>
      </template>

      <template v-slot:item="props">
        <slot
            name="table-row"
            :props="props"
        />
      </template>

      <template v-slot:expanded-item="props">
        <td
            :key="props.index + Math.floor(1000 * Math.random())"
            :colspan="props.headers.length"
        >
          <v-row
              align="center"
              justify="start"
              class="expanded-form"
              no-gutters
          >
            <v-col class="pa-4">
              <slot
                  name="creation-form"
                  :props="props"
                  :close="() => close(props.item)"
              />
            </v-col>
          </v-row>
        </td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  name: 'ToggleTable',
  data() {
    return {
      headerProps: {
        sortIcon: 'mdi-chevron-down',
      },
    };
  },
  props: {
    headers: {
      type: Array,
      required: true,
    },
    items: {
      type: Array,
      required: true,
    },
    itemsPerPage: {
      type: Number,
      default: Number.MAX_VALUE,
    },
  },
  methods: {
    close(item) {
      this.$refs.table.expand(item, false);
    },
  },
};
</script>

<style lang="scss">
  .toggle-table-container {
    border-bottom: grey;
  }

  .toggle-table-container {
    .mdi-delete {
      &:hover {
        color: red;
      }
    }

    tbody tr:not(.v-datatable__expand-row):not(.v-data-table__empty-wrapper) {
      cursor: pointer;

      td {
        font-size: 16px;
      }
    }

    .v-input--checkbox {
      height: 50%;

      .v-input__slot {
        margin: 0;
      }
    }

    .v-input--selection-controls {
      padding-top: 0;
      margin-top: 0;
    }
  }
</style>

<style lang="scss" scoped>
  .expanded-form {
    border-width: 0 !important;
  }
</style>
