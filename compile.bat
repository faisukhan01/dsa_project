@echo off
echo Compiling Chess Game...
javac *.java
if %errorlevel% == 0 (
    echo.
    echo Compilation successful!
    echo To run the game, type: java ChessGame
) else (
    echo.
    echo Compilation failed. Please check errors above.
)
pause
