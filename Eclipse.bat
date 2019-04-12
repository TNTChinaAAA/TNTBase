@echo off
title TNTBase SetupWorkSpace
gradlew SetupDecompWorkSpace eclipse -Dorg.gradle.jvmargs=-Xms1440m --no-daemon