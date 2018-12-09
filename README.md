Extra Utilities Superflat Slime Fix
===================================

Fixes slimes not spawning in superflat worlds when you have Extra Utilities installed because it adds an event that kills slime spawning and doesn't add a config option for it. This makes it possible to use the Narslimmus in garden of glass again, etc.

Also suppresses a TON of Extra Utilities log spam (also configurable)

* remove errors caused by Extra Utilities registering tile entities with the wrong mod ID
* remove model loading errors caused by Extra Utilities using special snowflake model loading system
* remove a big missing texture error caused by a missing potion texture (this one is not configurable, because the fix is just me adding a texture with the right filename lol)