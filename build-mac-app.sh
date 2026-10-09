#!/bin/bash
# Builds a standalone "GLI Solitaire.app" (Java runtime included) into target/app,
# plus target/GLI-Solitaire-macOS.zip (the app, to attach to a GitHub release) and
# dist/GLI-Solitaire.jar (any OS with Java 21).
# Needs JDK 21+ (for jpackage) and Maven: brew install openjdk@21 maven
set -euo pipefail

cd "$(dirname "$0")"
export JAVA_HOME="${JAVA_HOME:-/opt/homebrew/opt/openjdk@21}"

mvn -q -B clean package

# Turn the ace of spades into a square macOS .icns
iconset=target/Solitaire.iconset
mkdir -p "$iconset"
sips --padToHeightWidth 96 96 --padColor FFFFFF src/main/resources/1S.png --out target/icon.png >/dev/null
for size in 16 32 128 256 512; do
    sips -z "$size" "$size" target/icon.png --out "$iconset/icon_${size}x${size}.png" >/dev/null
    double=$((size * 2))
    sips -z "$double" "$double" target/icon.png --out "$iconset/icon_${size}x${size}@2x.png" >/dev/null
done
iconutil -c icns "$iconset" -o target/Solitaire.icns

mkdir -p target/jpackage-input
cp target/gli-solitaire-0.0.1-SNAPSHOT.jar target/jpackage-input/

"$JAVA_HOME/bin/jpackage" \
    --type app-image \
    --name "GLI Solitaire" \
    --app-version 1.0.0 \
    --input target/jpackage-input \
    --main-jar gli-solitaire-0.0.1-SNAPSHOT.jar \
    --main-class solitaire.main.SolitaireGLI \
    --icon target/Solitaire.icns \
    --add-modules java.desktop \
    --jlink-options "--strip-debug --no-man-pages --no-header-files" \
    --dest target/app

mkdir -p dist
cp target/gli-solitaire-0.0.1-SNAPSHOT.jar dist/GLI-Solitaire.jar
rm -f "target/GLI-Solitaire-macOS.zip"
# ditto keeps the app bundle's symlinks and permissions intact
ditto -c -k --keepParent "target/app/GLI Solitaire.app" "target/GLI-Solitaire-macOS.zip"

echo "Built target/app/GLI Solitaire.app, target/GLI-Solitaire-macOS.zip and dist/GLI-Solitaire.jar"
