#!/bin/bash
#
# Upload all artifacts (pom's and jar's) to the eclipse.xtext repository
#
# Prerequisite:
# Should be exectued from the repository root
# Credentials must have been configured in $HOME/.m2/settings.xml
#
REPOSITORY_ID=eclipse.xtext
SERVER_ID=tada.se
REPOSITORY_URL=http://nexus.tada.se/content/repositories/$REPOSITORY_ID
echo "Deploying files from '$PWD'" > "$PWD/deploy.log"
for jarFile in `find "$PWD/com" "$PWD/org" -type f -name '*.jar'`; do
	pomFile=${jarFile%.jar}.pom
	mvn deploy:deploy-file "-Dfile=$jarFile" "-DpomFile=$pomFile" -DrepositoryId=$SERVER_ID -Durl=$REPOSITORY_URL >> "$PWD/deploy.log" 2>&1
	exitcode=$?
	if [ $exitcode -eq 0 ]; then
		echo 'Deployed ' $jarFile
	fi
done
