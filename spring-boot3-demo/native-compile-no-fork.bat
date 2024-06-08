@echo off
rem 确保 Windows 安装了 Visual Studio Community
set PROFILES=dev
set THREAD=1C
rem 如果JAVA_HOME和mvn不一致，则手动修改jdk版本，即：set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3
set JAVA_HOME=C:/Users/X/.jdks/graalvm-jdk-21.0.3

echo clean and compile ......
rem 打包 native 必须使用 -P native否则打完包，启动后输出控制台会报错。
start /B /WAIT cmd /c "mvn clean compile -T %THREAD% -D maven.test.skip=true -P native,%PROFILES% -q -f pom.xml"
rem 如果上面这条 cmd 命令执行失败，则后续命令不会执行
if %ERRORLEVEL% neq 0 (
    echo Error: mvn clean compile failed.
    exit /b %ERRORLEVEL%
)

echo spring-boot:process-aot ......
start /B /WAIT cmd /c "mvn spring-boot:process-aot -T %THREAD% -D maven.test.skip=true -P native,%PROFILES% -q -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn spring-boot:process-aot failed.
    exit /b %ERRORLEVEL%
)

echo native:compile-no-fork ......
start /B /WAIT cmd /c "mvn native:compile-no-fork -T %THREAD% -D maven.test.skip=true -P native,%PROFILES% -q -f pom.xml"
if %ERRORLEVEL% neq 0 (
    echo Error: mvn native:compile-no-fork failed.
    exit /b %ERRORLEVEL%
)

exit /b 0
