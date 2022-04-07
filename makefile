
all: build run

build:
	jar cvmf manifest.txt qui_est_ce.jar -C bin .

run:
	java -jar --module-path lib/javafx --add-modules javafx.controls,javafx.fxml qui_est_ce.jar

