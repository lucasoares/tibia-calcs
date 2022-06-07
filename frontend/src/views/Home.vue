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
  <div class="home">
    <h1 id="main-title">Tibia Calcs</h1>
    <p class="description">🛠️ Useful news and tools for Tibia MMORPG</p>
    <br/>
    <h2>Tibia News</h2>
    <br/>
    <div class="news">
      <v-flex xs8 offset-md2>
        <div v-if="news.length == 0">
          <v-skeleton-loader
            v-bind="attrs"
            type="article@4"
          ></v-skeleton-loader>
        </div>
        <div v-if="news.length > 0">
          <div v-for="article in news" :key="article.id" class="article">
            <v-card v-bind:src="article.link">
              <v-container>
                <span class="headline">{{ article.title }}</span>
                <br>
                <v-chip small color="secondary" class="white--text">
                  {{article.date}}
                </v-chip>
              </v-container>
              <v-card-text v-html="article.content_html">
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <ShareNetwork
                    network="twitter"
                    :style="{color: '#1da1f2'}"
                    :url="'https://tibiacalcs.com ' + article.link"
                    :title="article.title"
                    :quote="article.link"
                    :media="article.link"
                    hashtags="tibia,tibiacalcs"
                  >
                  <i class="fab fah fa-lg fa-twitter"></i>
                  <span>Share on Twitter</span>
                </ShareNetwork>
                <v-spacer></v-spacer>

                <v-btn
                  small
                  replace
                  color="primary"
                  v-bind:href="article.link"
                  target="_blank"
                  >Read More</v-btn
                >
              </v-card-actions>
            </v-card>
            <Adsense
              data-ad-client="ca-pub-4254262349718636"
              data-ad-slot="7936683191"
              data-ad-format="auto"
            >
            </Adsense>
          </div>
        </div>
      </v-flex>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'News',
  data() {
    return {
      news: [],
    };
  },
  mounted() {
    this.fetchNews();
  },
  methods: {
    async fetchNews() {
      const { data } = await axios.get('https://api.tibiadata.com/v3/news/latest');

      const promises = [];

      const responseNews = data.news;
      for (let i = 0; i < responseNews.length && i < 10; i += 1) {
        const news = responseNews[i];
        promises.push(this.getNewsData(news.id));
      }

      const result = await Promise.all(promises);

      this.news = result;

      return result;
    },
    getNewsData: async (id) => {
      const { data } = await axios.get(`https://api.tibiadata.com/v3/news/id/${id}`);

      return {
        title: data.news.title,
        content_html: data.news.content_html,
        id: data.news.id,
        link: data.news.url,
        date: data.news.date,
      };
    },
  },
};
</script>

<style>
  .home {
    text-align: center;
  }

  div.news, div.news p {
    text-align: left !important;
  }

  div.article {
    margin-bottom: 1.5rem;
  }

  .headline {
    text-align: center;
  }

  img {
    margin: 10px;
  }
</style>
