@echo off
set PROFILES=dev
set THREAD=1C

@REM 如果JAVA_HOME和mvn不一致，则手动修改jdk版本，即：set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3
set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3

@REM 用于启动运行 Maven 的 JVM 的参数
set MAVEN_OPTS=-Xmx2048m -Xms1024m -XX:MaxMetaspaceSize=512m -XX:ReservedCodeCacheSize=512m

@REM 从 Maven 3.9.0 开始，此变量包含在 CLI 参数之前传递给 Maven 的参数。“^”符号表示命令将在下一行继续
set MAVEN_ARGS=-Dmaven.test.skip=true ^
-Dmaven.compile.fork=true ^
-T %THREAD% ^
-Dmaven.artifact.threads=%NUMBER_OF_PROCESSORS% ^
-Dmaven.site.skip=true ^
-Dmaven.javadoc.skip=true ^
-q ^
-o

echo mvn clean package ......
start /B /WAIT cmd /c "mvn clean package -f pom.xml -P %PROFILES%"
@REM 如果cmd执行失败，则后续的命令不会执行。
if %ERRORLEVEL% neq 0 (
    echo Error: mvn clean package failed.
    exit /b %ERRORLEVEL%
)

echo mvn dockerfile:build ......
start /B /WAIT cmd /c "mvn dockerfile:build -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn dockerfile:build failed.
    exit /b %ERRORLEVEL%
)

echo mvn dockerfile:push ......
start /B /WAIT cmd /c "mvn dockerfile:push -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn dockerfile:push failed.
    exit /b %ERRORLEVEL%

)

exit /b 0
