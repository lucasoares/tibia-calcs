/*
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
*/

import Vue from 'vue';

Vue.filter('round', (value, accuracy, keep) => {
  if (typeof value !== 'number') {
    return value;
  }

  const fixed = value.toFixed(accuracy);

  return keep ? fixed : +fixed;
});

Vue.filter('abs', (value) => {
  if (typeof value !== 'number') {
    return value;
  }

  return Math.abs(value);
});
