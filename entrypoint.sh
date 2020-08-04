#!/bin/sh

if [[ -n ${BASE_PATH} ]]; then
  sed "s|\(\"basePath\"\s*:\s*\"\)\(.*\)\(\"\)|\1${BASE_PATH}\2\3|g" \
    "${HOME}/apidocs.json.orig" > "${HOME}/apidocs.json"
  JAVA_OPTS="${JAVA_OPTS} -DdataIndexResource.apidocs=file:${HOME}/apidocs.json"
  JAVA_OPTS="${JAVA_OPTS} -Dserver.servlet.context-path=${BASE_PATH}"
  echo "Notice: BASE_PATH set to '${BASE_PATH}'"
fi

echo "The application will start in ${SIMLIFE_SLEEP}s..." && sleep ${SIMLIFE_SLEEP}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.war" "$@"