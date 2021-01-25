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
  <v-container fluid>
    <h1>Results
      <v-btn v-if="containsPlayers(getHuntResult)" icon v-on:click="copyResultToClipboard">
        <v-icon>mdi-content-copy</v-icon>
      </v-btn>
    </h1>

    <p v-if="!containsPlayers(getHuntResult)">
      <br>
      There is no session that has not yet been paid.
    </p>

    <accordion v-if="containsPlayers(getHuntResult)">
      <accordion-item :name="`Transfer Suggestion`">
        <template slot="accordion-content">
          <v-container grid-list-xl>
            <v-layout row wrap>
              <v-flex md6 v-for="transfer in getTransferSuggestion" :key="transfer.id">
                <TransferSuggestion
                    :fromPlayer="transfer.from"
                    :toPlayer="transfer.to"
                    :amount="transfer.amount"/>
              </v-flex>
            </v-layout>
          </v-container>
        </template>
      </accordion-item>

      <accordion-item :name="`Player Balance`">
        <template slot="accordion-content">
          <v-container>
            <v-row>
              <v-col v-for="n in 2" :key="n">
                <v-row>
                  <v-col cols="4" :class="'mt-3'"
                         v-for="player in filterPlayers(getHuntResult, n)"
                         :key="player.name">
                    <PlayerResult :player="player"/>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-container>
        </template>
      </accordion-item>
    </accordion>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex';
import PlayerResult from '@/components/hunts/PlayerResult.vue';
import Accordion from '@/components/base/Accordion.vue';
import AccordionItem from '@/components/base/AccordionItem.vue';
import TransferSuggestion from '@/components/hunts/TransferSuggestion.vue';

const moment = require('moment');

export default {
  name: 'HuntsResult',
  components: {
    PlayerResult,
    Accordion,
    AccordionItem,
    TransferSuggestion,
  },
  computed: {
    ...mapGetters('hunts', [
      'getHuntResult',
      'getNonPaidHunts',
      'getTransferSuggestion',
    ]),
    huntStringResult() {
      let result = 'Party hunt analyzer provided by https://tibiacalcs.com/\n\n';

      result += 'Sessions:\n```\n';
      for (let i = 0; i < this.getNonPaidHunts.length; i += 1) {
        const hunt = this.getNonPaidHunts[i];
        result += `${hunt.initDate} to ${hunt.endDate} (${moment.duration(hunt.session).humanize()}) with ${hunt.playersNumber} players: `;

        const players = [];
        hunt.players.forEach((player) => {
          players.push(player.name);
        });

        result += `${players.join(', ')}.\n`;
      }

      result += '```\nTransfer:\n```\n';

      const suggestion = this.getTransferSuggestion;

      for (let i = 0; i < suggestion.length; i += 1) {
        const transfer = suggestion[i];

        result += `${transfer.from} should transfer ${transfer.amount} to ${transfer.to}\n`;
        result += `\t> transfer ${transfer.amount} to ${transfer.to}\n`;
        result += `\t> withdraw ${transfer.amount}\n`;
      }

      result += '```\nPlayer Balance:\n```\n';

      for (let i = 0; i < this.getHuntResult.length; i += 1) {
        const playerBalance = this.getHuntResult[i];

        if (playerBalance.balance < 0) {
          result += `${playerBalance.name} owes ${-playerBalance.balance}\n`;
        } else if (playerBalance.balance > 0) {
          result += `${playerBalance.name} needs to receive ${playerBalance.balance}\n`;
        }
      }

      return `${result}\`\`\``;
    },
  },
  methods: {
    containsPlayers(players) {
      return this.filterPlayers(players, 1).length > 0 || this.filterPlayers(players, 2).length > 0;
    },
    filterPlayers(players, n) {
      return players.filter((player) => {
        if (n === 1 && player.balance > 0) {
          return true;
        }

        if (n === 2 && player.balance < 0) {
          return true;
        }

        return false;
      });
    },
    copyResultToClipboard() {
      const el = document.createElement('textarea');
      el.value = this.huntStringResult;
      el.setAttribute('readonly', '');
      el.style.position = 'absolute';
      el.style.left = '-9999px';
      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);

      this.$notify({
        title: 'Copied to Clipboard',
        type: 'success',
        text: 'Party hunt results were copied to the clipboard.',
      });
    },
  },
};
</script>

<style scoped>
  .players-balance {
    text-align: center;
  }
</style>
