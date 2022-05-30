## Contribute

You can help this project by reporting problems, suggestions, localizing it or contributing to the code.

### Report a problem or suggestion

Go to our [issue tracker](https://github.com/lucasoares/tibia-calcs/issues) and check if your problem/suggestion is already reported. If not, create a new issue with a descriptive title and detail your suggestion or steps to reproduce the problem.

### Contribute to the code

If you know how to code or have better ideas for the page layout itself, we welcome you to send fixes and new features, but in order to be efficient we ask you to follow the following procedure:

* Fork this repo using the button at the top.
* Clone your forked repo locally.

``$ git clone git@github.com:yourname/tibia-calcs.git``

* Don't modify or work on the main branch, we'll use it to always be in sync with tibia-calcs upstream.

```
$ git remote add upstream git@github.com:lucasoares/tibia-calcs.git
$ git fetch upstream
```

* Always create a new issue when you plan to work on a bug or new feature and wait for other devs input before start coding.
* Once the new feature is approved or the problem confirmed, go to your local copy and create a new branch to work on it. Use a descriptive name for it, include the issue number for reference.

``$ git checkout -b changes-layout-3``

* Do your coding and push it to your fork. Include as few commits as possible (one should be enough) and a good description. Always include a reference to the issue with "Fix #number".

```
$ git add .
$ git commit -m "Changes the page layout. Fixes #3"
$ git push origin changes-layout-3
```

* Do a new pull request from your "changes-layout-3" branch to tibia-calcs "main".

#### How to implement changes suggested on a pull request

Sometimes when you submit a PR, you will be asked to correct some code. You can make the changes on your work branch and commit normally, and the PR will be automatically updated.

``$ git commit -am "Fixes some typo found on PR"``

Once everything is OK the Pull Request will be accepted and all changes will be squashed into a single commit to keep our main history as clean as possible.

#### How to keep your local branches updated

To keep your local main branch updated with upstream main, regularly do:

```
$ git fetch upstream
$ git checkout main
$ git pull --rebase upstream main
```

To update the branch you are coding in:

```
$ git checkout changes-layout-3
$ git rebase main
```

### Licesing

Every file of our codebase needs to start with the license comment:

For `.java` files:
```java
/*
 * Tibia Calcs GPL Source Code
 * Copyright (C) 2020 Lucas Soares de Miranda
 * Copyright (C) 2020 Luiz Claudio Soares de Miranda
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
 */
```

For `.js` files:
```js
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
```

For `.yaml` files:

```yaml
#===========================================================================
#  Tibia Calcs GPL Source Code
#  Copyright (C) 2020 Lucas Soares de Miranda
#  Copyright (C) 2020 Luiz Claudio Soares de Miranda
#
#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#  License available on https://github.com/lucasoares/tibia-calcs/blob/main/LICENSE.md
#===========================================================================
```

For `.vue` files:

```html
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
```