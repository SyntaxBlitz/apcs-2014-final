Saviors of Gundthor
===============
Our Java final. Glued into place by Timothy, Chris, Dan, and Nick.

Synopsis
--------
Back in the ages when dragons roamed, there was a kingdom named Gundthor. This kingdom held many secrets and its leader was considered to be the most powerful man in the world. King Orchard's reign started in 1179 AD. He took the lands and maintained its security for nearly five decades. Then, a powerful sorcerer by the name of Arcanus emerged and conjured an army of possessed minions. Using this army, he took King Orchard hostage and the realm which he possessed. It is your job as one of Gundthor’s special order to forge your way to the keep, rescue the king, and slay the mighty Arcanus.

Usage
-----
Download the source files for both the server and the client. On running the server, it attempts to bind to port 21102. You can change this value by passing the new port as a commandline argument into the server application, but the client will not accomodate port changes: you'll need to change the port in the client's source code.  
The game is written in Java, so you'll need the JRE 1.6+ to run it. If you're running a remote server, make sure there is not a firewall or router blocking traffic to the port.

Be careful running this game on a publicly-exposed server. It doesn't do much verification on packets, so it's possible to crash the server with a malformed packet. It's also possible to spam the server with entity creation packets until it runs out of memory, so just... be careful. The server trusts the client very much. Keep that in mind.


Features
--------
Saviors of Gundthor features intense multiplayer action over UDP with enemies that track users and five different character classes. Each character has either a melee or ranged attack which can be used while the character is not moving, and characters also have different special abilities (activated with right-click) that relate to their class types. The game features three levels, the third of which contains the boss Arcanus who shoots powerful magic projectiles and, after lowered past a certain HP level, will begin to spawn minions of his own. Player death results in a respawn at the beginning of the section of the map, and it can also be triggered by pressing 'r'. The game is very playable and very fun. I recommend it.

---

What I learned writing this project (by Timothy):

1\. Things get done a lot quicker if you throw out all your design principles and just throw references to everything around everywhere.  
3\. It's really fun to play with anyway.  
2\. Multithreading is hard.
