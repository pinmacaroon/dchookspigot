# Discord Webhook Wizard (paper/spigot) dev

[For user downloads please visit the Modrinth page!](https://modrinth.com/project/qJ9ZfKma)

## Branch information

Since the Spigot api isn't 100% backwards compatible, the repo had to be branched into three plus one different
distributions:

* [Compatible with 1.8.8-1.11.2](https://github.com/pinmacaroon/dchookspigot/tree/1.8.8)
    * Fork of the master branch
    * Compiled with Java 17 at language level 17
    * Isn't picky with Java versions
    * Uses Spigot API 1.8.8
* [Compatible with 1.12-1.16.5](https://github.com/pinmacaroon/dchookspigot/tree/1.12)
    * Fork of the 1.8.8 branch
    * Compiled with Java 11 at language level 11
    * Might be picky with Java versions because of Minecraft
    * Uses Spigot API 1.12
* [Compatible with 1.17-1.21.8](https://github.com/pinmacaroon/dchookspigot/tree/1.17)
    * Fork of the 1.12 branch
    * Compiled with Java 21 at language level 17
    * Java 17 and forwards work fine
    * Uses Spigot API 1.17
* Development branch (this branch)
    * Fork of 1.8.8 branch
    * This is where development happens, changes are merged into 1.8.8 and forwards implemented into the other branches

The master branch will be kept in case something goes wrong and I need to revert all changes. All distributions should
work the same way. If there are functional differences/bugs in any of the distributions, please open an issue!

## TODO

You can request features by submitting an issue!

* idk