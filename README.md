# Lego2LeJosEv3-Gripp3r
A LeJOS EV3 Implementation of _LEGO Mindstorms EV3 Programming Blocks (icons)_. This is the _Gripp3r Robot_ example.
Please see the **Lego2LeJosEv3_Handbook** for more details. It is a LibreOffice Writer 6.0 document.

The **Gripp3r Robot** is a standard example to learn how to program using the LEGO Programming Blocks based on LEGO's icon based (LabView-based) programming environment (Home Edition). You can download it at https://www.lego.com/en-us/mindstorms/downloads/download-software (or one of the other language pages).
You can also find the building instructions of the Gripp3r robot at https://www.lego.com/en-us/mindstorms/build-a-robot/gripp3r (or one of the other language pages).

## Library
This project comprises of the _first version_ of the **LEGO 2 LeJOS EV3** library that sits on top of the current version of the LeJOS EV3 framework. It intends to provide Java programmers with methods that are similar to use as the Lego Programming Blocks (icons). The _Gripp3r Robot_ program shows how to use it. This library leaves the control structures, calculations, and variable or constant handling to the Java programming language, because they are much easier to read in Java.

In this project I am using the LeJOS EV3 v0.9.1beta (see https://sourceforge.net/projects/ev3.lejos.p/) framework and a standard LEGO Mindstorms EV3 Brick.

This version of the LEGO 2 LeJOS EV3 library is _not_ complete by far, as it covers only the Programming Blocks used in the Gripp3r Robot program.
However, it still makes it easy to port a LEGO icon-based Program to the Java language on the LeJOS EV3 framework. 
I plan to build further robots and port their LEGO icon-based programs to LeJOS EV3 and thus extending the LEGO 2 LeJOS EV3 library more and more.

## How To Use
If you want to use the _LEGO 2 LeJOS EV3_ library in your own LeJOS EV3 project, just copy the classes in the packages
_lego2lejosev3.pblocks_ and _lego2lejosev3.logging_ into your project. The Gripp3r Robot programs show how to use them.
