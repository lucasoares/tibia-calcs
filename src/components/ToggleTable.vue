<template>
  <div class="toggle-table-container">
    <v-data-table
        ref="table"
        :headers="headers"
        :header-props="headerProps"
        :items="items"
        :items-per-page="itemsPerPage"
        class="mt-3 mb-5"
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

    .v-input--switch {
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
