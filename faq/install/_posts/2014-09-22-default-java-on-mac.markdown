---
layout: post
title: How do I make Java >= 1.7 the default on MacOS/X
---
Geppetto versions >= 4.3 requires Java >= 1.7 to run. It is possible that you have this
installed on a MacOS/X based machine but Geppetto still reports that the Java version
in use is Java 1.6. This is because the installation doesn't change the default setting
that appoints older installations of the Apple JVM. The default java version is reported
with the command:

    $ java -version

If it reports 1.6.x then Geppetto will not run unless you take action. You can either
tell Geppetto to explicitly use a Java other than the default
(see _<geppetto install dir>/Geppetto.app/Contents/Info.plist_ for more info), or you can
change the default.

Start a command shell and navigate to:

    $ cd /System/Library/Frameworks/JavaVM.framework/Versions

Here you'll find a symbolic link named CurrentJDK. It probably points to
_/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents_ Use the following command:

    $ /usr/libexec/java_home

to find the home of the new JDK. It will be something like:
_/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home_.
Now change the link (notice that the last segment /Home should NOT be included):

    $ sudo rm CurrentJDK
    $ sudo ln -s /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/ CurrentJDK

That's all. Verify that the new version is reported by:

    $ java -version
