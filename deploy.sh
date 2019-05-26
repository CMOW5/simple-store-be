git checkout build
git merge dev
gradle build 
git add build/libs/
git commit -m "0.0.1 simple store"
git subtree push --prefix build/libs production master