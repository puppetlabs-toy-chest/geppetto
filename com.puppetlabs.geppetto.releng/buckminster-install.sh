# Install Buckminster with plug-ins needed to run ECore2Java and
# MWE2 workflows
#
DIRECTOR_HOME=$HOME/buildtools/director
INSTALLATION_DIR=`pwd`

$DIRECTOR_HOME/director\
 -p2.os linux -p2.ws gtk -p2.arch x86_64\
 -r file:/home/thhal/build.output/org.eclipse.buckminster.site.eclipse.headless_1.6.0-eclipse.feature/site.p2\
 -r http://download.eclipse.org/releases/luna\
 -i org.eclipse.buckminster.cmdline.product\
 -i org.eclipse.buckminster.core.headless.feature.feature.group\
 -i org.eclipse.buckminster.pde.headless.feature.feature.group\
 -i org.eclipse.buckminster.maven.headless.feature.feature.group\
 -i org.eclipse.buckminster.git.headless.feature.feature.group\
 -i org.eclipse.equinox.p2.repository.tools\
 -i org.eclipse.emf.ant\
 -i org.eclipse.emf.mwe2.launch\
 -i org.eclipse.emf.importer.ecore\
 -d $INSTALLATION_DIR/buckminster\
 -p Buckminster

