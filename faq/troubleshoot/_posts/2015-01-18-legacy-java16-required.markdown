---
layout: post
title: My Mac says - To open “Geppetto” you need to install the legacy Java SE 6 runtime.
---
This is incorrect. Geppetto will not run using Java SE 6 and installing it will not help. The dialog is
probably caused by Oracle's missing definitions of the JVM capabilities in which case this [elaborate
answer on Stack Overflow](http://stackoverflow.com/questions/19563766/eclipse-kepler-for-os-x-mavericks-request-java-se-6/19594116#19594116) might be helpful.