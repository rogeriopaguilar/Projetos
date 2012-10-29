sset PATH=C:\Program Files\j2sdk_nb\j2sdk1.4.2\bin;
set PATH=c:\j2sdk1.4.0_03\bin;
set CLASSPATH=./;../bin;
del ..\bin\*.*
javac -d ..\bin ..\src\*.java 
copy executarServidor.bat ..\bin
copy config.xml ..\bin


