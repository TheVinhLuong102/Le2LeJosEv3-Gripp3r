# Lego2LeJosEv3-Gripp3r
This is the LEGO Mindstorms EV3 **Gripp3r Robot** example program in the Java programming language that uses a Java Implementation of _LEGO Mindstorms EV3 Programming Blocks (icons)_ on LeJOS EV3. 

You can find the building instructions and the LEGO icon-based program of the _Gripp3r Robot_ in LEGO's icon based (LabView-based) Programming Environment (Home Edition). You can download the LEGO Programming Environment at https://www.lego.com/en-us/mindstorms/downloads/download-software (or one of the other language pages).

You can also find the building instructions of the Gripp3r robot at https://www.lego.com/en-us/mindstorms/build-a-robot/gripp3r (or one of the other language pages).

**Note:** The original Gripp3r mission 4 uses the touch sensor to close or open the gripper part of the robot. 
Instead of the Touch Sensor, I use the top (beacon) button of the remote control to switch from the default move-around mode to the gripper movements: press the beacon button and then one of the upper buttons to open or close the gripper part.

## Dependencies
This project depends on the **LEGO 2 LeJOS EV3** Library (see https://github.com/robl0377/Lego2LeJosEv3) that sits on top of the current version of the LeJOS EV3 framework. 
Please copy the classes in the LEGO 2 LeJOS EV3 Library packages _lego2lejosev3.pblocks_ and _lego2lejosev3.logging_ into this project's source directory before running it.

In this project I am using the **LeJOS EV3 v0.9.1beta** framework (see https://sourceforge.net/projects/ev3.lejos.p/) and a standard LEGO Mindstorms EV3 Brick.

## Resources
The programs play sound sample files during movements of the gripper part of the robot. 
You can download them from:
http://tastyspleen.net/~quake2/baseq2/sound/world/airhiss1.wav and http://tastyspleen.net/~quake2/baseq2/sound/world/airhiss2.wav
Please copy these sound files (via SCP) to the directory _/home/lejos/lib_ on EV3 Brick before running the program.

