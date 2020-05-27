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

import {
  DELETE_HUNT,
  CREATE_HUNT,
  UPDATE_HUNT,
} from './mutation_types';

export default {
  [CREATE_HUNT]: (state, hunt) => {
    state.identifier += 1;
    const { identifier } = state;

    const newHunt = hunt;
    newHunt.id = identifier;

    state.hunts = [newHunt].concat(state.hunts);
  },
  [DELETE_HUNT]: (state, huntToDelete) => {
    state.hunts = state.hunts.filter((hunt) => hunt.id !== huntToDelete.id);
  },
  [UPDATE_HUNT]: (state, huntToUpdate) => {
    const { id } = huntToUpdate;
    state.hunts = state.hunts.map((hunt) => {
      if (hunt.id === id) {
        return huntToUpdate;
      }
      return hunt;
    });
  },
};
