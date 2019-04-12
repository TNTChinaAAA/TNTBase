@echo off
title TNTBase Build
gradlew clean build -Dorg.gradle.jvmargs=-Xms1440m --no-daemon