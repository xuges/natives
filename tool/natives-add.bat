@ECHO OFF

IF "%1" == "-v" GOTO version
IF "%1" == ""   GOTO version
IF "%1" == "-h" GOTO usage

ECHO Args:
ECHO jar:     %1
ECHO path:    %2
ECHO os_arch: %3
ECHO library: %4
ECHO.

IF "%2" == "" (
  ECHO Please check command line arguments!
  GOTO usage
)

IF "%3" == "" (
  ECHO Please check command line arguments!
  GOTO usage
)

IF "%4" == "" (
  ECHO Please check command line arguments!
  GOTO usage
)

SET TEMP_DIR=%TEMP%\natives-add-%random%
SET FILE_PATH=%2\%3
SET SAVE_DIR=%TEMP_DIR%\%FILE_PATH:/=\%

ECHO Commands:
ECHO MKDIR %SAVE_DIR%
ECHO COPY %4 %SAVE_DIR%\
ECHO jar -uf %1 -C %TEMP_DIR% %2/%3/%4 
ECHO RMDIR /S /Q %TEMP_DIR%
ECHO.

SET /p ANSWER=Are you sure to exec them?(Y/n)
IF "%ANSWER%" == "n" GOTO end
IF "%ANSWER%" == "N" GOTO end

MKDIR %SAVE_DIR%
COPY %4 %SAVE_DIR%\
jar -uf %1 -C %TEMP_DIR% %2/%3/%4
RMDIR /S /Q %TEMP_DIR%

GOTO end

:version
  ECHO natives add library to jar util script.
  ECHO Version: 1.0.0
  ECHO.

:usage
  ECHO Usage:   %0 ^<jar^> ^<path^> ^<os_arch^> ^<library^>
  ECHO ^<jar^>      target jar file.
  ECHO ^<path^>     native library save ^path in jar.
  ECHO ^<os_arch^>  library's platform. etc. windows_x86 windows_x64 linux_x64 linux_arm64 ...
  ECHO ^<library^>  library file to add.
  ECHO Example:
  ECHO     CMD: %0 example.jar org/example/libs windows_x64 example.dll
  ECHO          REM add example.dll to example.jar!/org/example/libs/windows_x64/example.dll
  ECHO.
  ECHO     Java: LibraryLoader.loadFromJar("org/example/libs", "example");
  ECHO           //load the example.dll library on windows_x64 platform.

:end