<template>
  <v-form
      ref="form"
      v-model="valid"
      @submit.prevent
  >
    <v-btn
        rounded
        class="grey--text mr-4"
        @click="$emit('cancel')"
    >
      Cancel
    </v-btn>

    <v-btn
        id="btn-action"
        rounded
        color="primary"
        @click.prevent="submitForm"
    >Apply
    </v-btn>

    <v-container fluid>
      <v-textarea
          height="20rem"
          filled
          :rules="textRules"
          v-model="partyHunt"
          no-resize
          class="creation-text"
          label='Paste your Party Hunt here'
      ></v-textarea>
    </v-container>
  </v-form>
</template>

<script>
import { mapActions } from 'vuex';
import { partyHuntValidation } from '@/utils/form_rules';
import parse from '@/utils/loot/parser';

export default {
  name: 'HuntsCreationForm',
  props: {
    isEditing: {
      type: Boolean,
      default: false,
    },
    isDialog: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      textRules: [partyHuntValidation()],
      valid: false,
      partyHunt: '',
    };
  },
  methods: {
    ...mapActions('hunts', [
      'createHunt',
    ]),
    submitForm() {
      if (!this.valid) {
        this.$refs.form.validate();

        return;
      }

      this.createHunt(parse(this.partyHunt));

      this.$nextTick(() => {
        this.$emit('close', this.partyHunt);
      });
    },
  },
};
</script>

<style>
  #btn-action {
    min-width: 86px;
  }

  .creation-text textarea {
    margin-top: 30px !important;
  }
</style>
