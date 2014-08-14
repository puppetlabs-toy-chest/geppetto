---
layout: post
title: How can I install DLTK for Ruby?
---
If you want to add support for editing ruby files in Geppetto, you can install
the Eclipse DLTK/Ruby into Geppetto. There is however no support for erb files

To install:

- Select Help > Install new Software
- Add the URL of the latest Eclipse release train (currently Luna) http://download.eclipse.org/releases/luna/
- Expand the "Programming Languages" category
- Select feature "Dynamic Languages Toolkit - Ruby Development Tools"
- Click throught the wizard and accept the license
- You are warned about unsigned content, accept that
- When prompted to restart, do so
- Create a Ruby project (helps you configure a ruby runtime)
- Ruby files can now be edited with syntax coloring etc.

###Note:
The installation of DLTK for Ruby into the Geppetto standalone product will likely cause a
"theme" conflict since the install implies the install of the Eclipse Platform feature which
in turn brings in a theme that conflicts with the one provided with Geppetto.

The conflict will only be visible as trace output in the application log and can be safely ignored.
