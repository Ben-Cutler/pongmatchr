#!/bin/bash

set -eu

#cf login -a api.run.pivotal.io -o osugi -s development

pushd frontend
  yarn test:ci
  yarn build
  cp -r build/* ../src/main/resources/static
popd

./gradlew clean build

cf push -f manifest.yml