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

  License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
===========================================================================
*/

export default {
  goldToken: {
    name: 'Gold Token',
  },
  imbuements: [
    {
      id: 0,
      name: 'Strike',
      description: 'Critical Hit',
      goldToken: true,
      materials: [
        { name: 'Protective Charm', quantity: 20 },
        { name: 'Sabretooth', quantity: 25 },
        { name: 'Vexclaw Talon', quantity: 5 },
      ],
    },
    {
      id: 1,
      name: 'Vampirism',
      description: 'Life Leech',
      goldToken: true,
      materials: [
        { name: 'Vampire Teeth', quantity: 25 },
        { name: 'Bloody Pincers', quantity: 15 },
        { name: 'Piece of Dead Brain', quantity: 5 },
      ],
    },
    {
      id: 2,
      name: 'Void',
      description: 'Mana Leech',
      goldToken: true,
      materials: [
        { name: 'Rope Belt', quantity: 25 },
        { name: 'Silencer Claws', quantity: 25 },
        { name: 'Some Grimeleech Wings', quantity: 5 },
      ],
    },
    {
      id: 3,
      name: 'Bash',
      description: 'Club Skill',
      goldToken: false,
      materials: [
        { name: 'Cyclops Toe', quantity: 20 },
        { name: 'Ogre Nose Ring', quantity: 15 },
        { name: 'Warmaster\'s Wristguards', quantity: 10 },
      ],
    },
    {
      id: 4,
      name: 'Blockade',
      description: 'Shield Skill',
      materials: [
        { name: 'Piece of Scarab Shell', quantity: 20 },
        { name: 'Brimstone Shell', quantity: 25 },
        { name: 'Frazzle Skin', quantity: 25 },
      ],
    },
    {
      id: 5,
      name: 'Chop',
      description: 'Axe Skill',
      materials: [
        { name: 'Orc Tooth', quantity: 20 },
        { name: 'Battle Stone', quantity: 25 },
        { name: 'Moohtant Horn', quantity: 20 },
      ],
    },
    {
      id: 6,
      name: 'Epiphany',
      description: 'Magic Level',
      materials: [
        { name: 'Elvish Talisman', quantity: 25 },
        { name: 'Broken Shamanic Staff', quantity: 15 },
        { name: 'Strand of Medusa Hair', quantity: 15 },
      ],
    },
    {
      id: 7,
      name: 'Precision',
      description: 'Distance Skill',
      materials: [
        { name: 'Elven Scouting Glass', quantity: 25 },
        { name: 'Elven Hoof', quantity: 20 },
        { name: 'Metal Spike', quantity: 10 },
      ],
    },
    {
      id: 8,
      name: 'Slash',
      description: 'Sword Skill',
      materials: [
        { name: 'Lion\'s Mane', quantity: 25 },
        { name: 'Mooh\'tah Shell', quantity: 25 },
        { name: 'War Crystal', quantity: 5 },
      ],
    },
    {
      id: 9,
      name: 'Featherweight',
      description: 'Capacity',
      materials: [
        { name: 'Fairy Wings', quantity: 20 },
        { name: 'Little Bowl of Myrrh', quantity: 10 },
        { name: 'Goosebump Leather', quantity: 5 },
      ],
    },
    {
      id: 10,
      name: 'Swiftness',
      description: 'Velocity',
      materials: [
        { name: 'Damselfly Wing', quantity: 15 },
        { name: 'Compass', quantity: 25 },
        { name: 'Waspoid Wing', quantity: 20 },
      ],
    },
    {
      id: 11,
      name: 'Vibrancy',
      description: 'Paralyze',
      materials: [
        { name: 'Wereboar Hooves', quantity: 20 },
        { name: 'Crystallized Anger', quantity: 15 },
        { name: 'Quill', quantity: 5 },
      ],
    },
    {
      id: 12,
      name: 'Electrify',
      description: 'Energy Damage',
      materials: [
        { name: 'Rorc Feather', quantity: 25 },
        { name: 'Peacock Feather Fan', quantity: 5 },
        { name: 'Energy Vein', quantity: 1 },
      ],
    },
    {
      id: 13,
      name: 'Frost',
      description: 'Ice Damage',
      materials: [
        { name: 'Frosty Heart', quantity: 25 },
        { name: 'Seacrest Hair', quantity: 10 },
        { name: 'Polar Bear Paw', quantity: 5 },
      ],
    },
    {
      id: 14,
      name: 'Reap',
      description: 'Death Damage',
      materials: [
        { name: 'Pile of Grave Earth', quantity: 25 },
        { name: 'Demonic Skeletal Hand', quantity: 20 },
        { name: 'Petrified Scream', quantity: 5 },
      ],
    },
    {
      id: 15,
      name: 'Scorch',
      description: 'Fire Damage',
      materials: [
        { name: 'Fiery Heart', quantity: 25 },
        { name: 'Green Dragon Scale', quantity: 5 },
        { name: 'Demon Horn', quantity: 5 },
      ],
    },
    {
      id: 16,
      name: 'Venom',
      description: 'Earth Damage',
      materials: [
        { name: 'Swamp Grass', quantity: 25 },
        { name: 'Poisonous Slime', quantity: 20 },
        { name: 'Slime Heart', quantity: 2 },
      ],
    },
    {
      id: 17,
      name: 'Fabric',
      description: 'Energy Protection',
      materials: [
        { name: 'Wyvern Talisman', quantity: 20 },
        { name: 'Crawler Head Plating', quantity: 15 },
        { name: 'Wyrm Scale', quantity: 10 },
      ],
    },
    {
      id: 18,
      name: 'Demon Presence',
      description: 'Holy Protection',
      materials: [
        { name: 'Cultish Robe', quantity: 25 },
        { name: 'Cultish Mask', quantity: 25 },
        { name: 'Hellspawn Tail', quantity: 20 },
      ],
    },
    {
      id: 19,
      name: 'Dragon Hide',
      description: 'Fire Protection',
      materials: [
        { name: 'Green Dragon Leather', quantity: 20 },
        { name: 'Blazing Bone', quantity: 10 },
        { name: 'Draken Sulphur', quantity: 5 },
      ],
    },
    {
      id: 20,
      name: 'Lich Shroud',
      description: 'Death Protection',
      materials: [
        { name: 'Flask of Embalming Fluid', quantity: 25 },
        { name: 'Gloom Wolf Fur', quantity: 20 },
        { name: 'Mystical Hourglass', quantity: 5 },
      ],
    },
    {
      id: 21,
      name: 'Quara Scale',
      description: 'Ice Protection',
      materials: [
        { name: 'Winter Wolf Fur', quantity: 25 },
        { name: 'Thick Fur', quantity: 15 },
        { name: 'Deepling Warts', quantity: 5 },
      ],
    },
    {
      id: 22,
      name: 'Snake Skin',
      description: 'Earth Protection',
      materials: [
        { name: 'Piece of Swampling Wood', quantity: 25 },
        { name: 'Snake Skin', quantity: 20 },
        { name: 'Brimstone Fangs', quantity: 10 },
      ],
    },
  ],
};
