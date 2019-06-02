git checkout build
git merge dev
gradle build 
git add build/libs/
git commit -m "0.0.1 simple store"
git subtree push --prefix build/libs production master

#!/usr/bin/env sh

# abort on errors
set -e

# build
git checkout build
git merge master
gradle build 
git add build/libs/
git commit -m "0.0.1 simple store"
git subtree push --prefix build/libs production master
git checkout master

nohup java -jar -Dspring.profiles.active=production simple-store-be-0.0.1-SNAPSHOT.jar &
ps -fea|grep -i java
kill -9 pId