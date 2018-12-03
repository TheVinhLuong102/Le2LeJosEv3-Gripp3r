# Lego2LeJosEv3-Gripp3r
A LeJOS EV3 Implementation of _LEGO Mindstorms EV3 Programming Blocks_. This is the _Gripp3r Robot_ example.

The **Gripp3r Robot** is a standard example to learn how to program using the LEGO Programming Blocks based on LEGO's LabView-based Programming environment.
You can find a description how to build the Gripp3r robot at https://www.lego.com/en-us/mindstorms/build-a-robot/gripp3r

This project comprises of the _first version_ of the **LEGO 2 LeJOS EV3** library that sits on top of the current version of the LeJOS EV3 framework. 
The _Gripp3r Robot_ program shows how to use it.

In this project I am using LeJOS EV3 v0.9.1beta (see https://sourceforge.net/projects/lejos/) and a standard LEGO Mindstorms Education EV3 brick.

The LEGO 2 LeJOS EV3 library files are located in the **lego2lejosev3.pblocks** package. 
It is not complete by far, as it covers only the Programming Blocks used in the Gripp3r Robot program.
However, it still makes it easy to port a LEGO LabView-based Program to Java on the LeJOS emvironment. 
I am planning to build further robots and port their LEGO LabView programs to LeJOS EV3 and thus extending the LEGO 2 LeJOS EV3 library more and more.

The package **lego2lejosev3.logging** holds some helper classes to use the standard _java.util.logging_ package to write log messages to a file.

The package **lego2lejosev3.tests** hold some programs to test some features of the LEGO 2 LeJOS EV3 library.

Last, but not least, the package **lego2lejosev3.robots.gripper** contains the main programs for the four 'missions' of the LEGO Education Gripp3r Project.
Additionally there is a helper class to move the gripper part of the robot.

**Note:** During movements of the gripper part of the robot sound files are played. You can download them from:
http://tastyspleen.net/~quake2/baseq2/sound/world/airhiss1.wav and http://tastyspleen.net/~quake2/baseq2/sound/world/airhiss2.wav
Copy the sound files via SCP to the directory /home/lejos/lib on EV3 brick before running the program.

**How To Use:**
If you want to use the _LEGO 2 LeJOS EV3_ library in your own LeJOS EV3 project, just copy the classes in the packages
_lego2lejosev3.pblocks_ and _lego2lejosev3.logging_ into your project. The Gripp3r Robot programs show how to use them.
