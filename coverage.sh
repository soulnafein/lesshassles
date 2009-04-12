mvn clean
mvn compile
java -cp /home/david/.m2/repository/emma/emma/2.0.5312/emma-2.0.5312.jar emma instr -verbose -m overwrite -ip tar        get/classes -outfile target/coverage.em
mvn test
cd target
java -cp /home/david/.m2/repository/emma/emma/2.0.5312/emma-2.0.5312.jar emma report -sp ../src/main/java/ -r html -in coverage.em,coverage.ec
cd ..
