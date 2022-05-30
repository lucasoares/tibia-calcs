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
  <li class="accordion__item">
    <div
        class="accordion__trigger"
        :class="{'accordion__trigger_active': visible}"
        @click="open">
      <div class="arrow_box" :class="{'arrow_box--open' : visible}"></div>
      <b>{{name}}</b>
    </div>

    <transition
        name="accordion"
        @enter="start"
        @after-enter="end"
        @before-leave="start"
        @after-leave="end">
      <div class="accordion__content"
           v-show="visible">
        <ul>
          <slot name="accordion-content"></slot>
        </ul>
      </div>
    </transition>
  </li>
</template>

<script>
export default {
  props: { name: {} },
  inject: ['Accordion'],
  data() {
    return {
      index: null,
    };
  },
  computed: {
    visible() {
      return this.index === this.Accordion.active;
    },
  },
  methods: {
    open() {
      if (this.visible) {
        this.Accordion.active = null;
      } else {
        this.Accordion.active = this.index;
      }
    },
    start(el) {
      // eslint-disable-next-line no-param-reassign
      el.style.height = `${el.scrollHeight}px`;
    },
    end(el) {
      // eslint-disable-next-line no-param-reassign
      el.style.height = '';
    },
  },
  created() {
    this.Accordion.count += 1;
    this.index = this.Accordion.count;
  },
};
</script>

<style lang="scss" scoped>
  .accordion__content {
    margin-bottom: 10px;
  }

  .accordion__trigger {
    cursor: pointer;
    margin: 20px 0px 5px 10px;
  }

  .accordion-enter-active,
  .accordion-leave-active {
    will-change: height, opacity;
    transition: height 0.1s ease-out, opacity 0.1s ease-out;
    overflow: hidden;
  }

  .accordion-enter,
  .accordion-leave-to {
    height: 0 !important;
    opacity: 0;
  }

  .arrow_box {
    width:10px;
    height:10px;
    transition: all .3s;
    position:absolute;
    margin:7px 0px 0px -15px;
  }

  .arrow_box:after, .arrow_box:before {
    border: solid transparent;
    content: " ";
    position: absolute;
  }

  .arrow_box:after {
    border-width: 5px;
  }

  .arrow_box:before {
    border-left-color: var(--v-primary-text-base);
    border-width: 5px;
    margin-left: 2.5px;
  }

  .arrow_box--open{
    transform: rotateZ(90deg);
    transform-origin: center center;
  }
</style>
