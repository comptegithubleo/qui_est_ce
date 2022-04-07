
all: build run

build:
	jar cvmf manifest.txt qui_est_ce.jar -C bin .

run:
<<<<<<< HEAD
	java -jar --enable-preview --module-path lib/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml qui_est_ce.jar
=======
	java -jar --module-path lib/javafx --add-modules javafx.controls,javafx.fxml qui_est_ce.jar
>>>>>>> refactoring navigability issues

