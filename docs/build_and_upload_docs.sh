#!/bin/bash

# This script clones the current repo to a temp dir
# and then builds and uploads the documentation.
# The cloned repo is left in the build/ dir as I am allergic to
# unguarded "rm -rf" commands in scripts.   The documentation
# then lives in the github repo in the "gh-pages" branch.
# 
# The documentation on Github uses the awesome mkdocs + Material facility
# kindly provided by the awesome Martin Donath (squidfunk)
# FMI:
# https://www.mkdocs.org/
# https://squidfunk.github.io/mkdocs-material/
#
#  script assumes you run this with the current dir in the "docs" folder
#  Note for windoze users : in gvim do a:
#    set ff=unix
#  to get the right line endings for unix.


REPO="https://github.com/bammellab/GamepadTest"
DIR=build/temp-$$

if [ ! -f build_and_upload_docs.sh ]; then
   echo "this script must be run from the docs dir"
   exit
fi

cd ..
if [ ! -d build ]; then
   mkdir build
fi

if [ -d $DIR ]; then
   echo "build dir already exists!! EXITING."
   exit
fi

# Clone the current repo into temp folder
git clone $REPO $DIR

# Move working directory into temp folder
cd $DIR

# Build the site and push the new files up to GitHub
mkdocs gh-deploy


