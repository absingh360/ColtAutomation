set projectLocation=D:\swapnil\colt_project cd %projectLocation% 
set classpath=%projectLocation%\bin;%projectLocation%\lib\* 
java org.testng.TestNG %projectLocation%\RegressionSuite.xml
pause