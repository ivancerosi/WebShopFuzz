# How to test
1) build the app
2) download EvoMaster JAR - https://github.com/WebFuzzing/EvoMaster/releases
3) start the EvoMasterDriver class located in the test directory
4) start the EvoMaster JAR in the separate console (set the testing duration, e.g. --maxTime 1h30m) - https://github.com/WebFuzzing/EvoMaster/blob/master/docs/options.md
5) After the testing is complete, JUnit files will be generated in the local folder. Copy them to the test folder inside the main app (or configure the EvoMaster to write test files directly into the test folder)
6) To gather code coverage, run the `mvn test` command - target/site/jacoco folder will contain index.html page showing code coverage results (target folder will also contain jacoco.exec which can be used to upload coverage data remotely)
