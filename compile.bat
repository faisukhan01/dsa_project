@echo off
echo Compiling Chess Game...
javac -d out src\*.java
if %errorlevel% == 0 (
    echo.
    echo Compilation successful!
    echo To run the game, type: java -cp out ChessGame
) else (
    echo.
    echo Compilation failed. Please check errors above.
)
pause
