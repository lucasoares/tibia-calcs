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

export function updateHunt({ commit }, hunt) {
  commit('updateHunt', hunt);

  return hunt;
}

export function toggleHunt({ commit }, hunt) {
  const huntToSend = hunt;
  huntToSend.paid = !huntToSend.paid;

  commit('updateHunt', huntToSend);
}

export function createHunt({ commit }, hunt) {
  commit('createHunt', hunt);

  return hunt;
}

export function removeHunt({ commit }, hunt) {
  commit('deleteHunt', hunt);

  return hunt;
}

export function updateStateVersion({ commit }) {
  commit('updateStateVersion');
}
