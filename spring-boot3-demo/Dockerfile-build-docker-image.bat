@echo off
set PROFILES=dev
set THREAD=1C
@REM 如果JAVA_HOME和mvn不一致，则手动修改jdk版本，即：set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3
set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3

echo mvn clean package ......
start /B /WAIT cmd /c "mvn -T %THREAD% -D maven.test.skip=true -P %PROFILES% clean package -q -f pom.xml"
@REM 如果cmd执行失败，则后续的命令不会执行。
if %ERRORLEVEL% neq 0 (
    echo Error: mvn clean package failed.
    exit /b %ERRORLEVEL%
)

echo mvn dockerfile:build ......
start /B /WAIT cmd /c "mvn -T %THREAD% dockerfile:build -q -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn dockerfile:build failed.
    exit /b %ERRORLEVEL%
)

echo mvn dockerfile:push ......
start /B /WAIT cmd /c "mvn dockerfile:push -q -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn dockerfile:push failed.
    exit /b %ERRORLEVEL%

)

exit /b 0
