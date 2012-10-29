set PATH=c:\j2sdk1.4.0_03\bin;
sset PATH=c:\Program Files\j2sdk_nb\j2sdk1.4.2\bin;
set CLASSPATH=%CLASSPATH%;c:\wtk20\lib\midpapi.zip;.;
javadoc -author -version  ..\..\src\*.* c:\wtk20\apps\maumau\src\*.*
