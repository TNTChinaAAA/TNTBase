@echo off
title TNTBase SetupWorkSpace
gradlew SetupDecompWorkSpace idea genIntellijRuns -Dorg.gradle.jvmargs=-Xms1440m --no-daemon --i