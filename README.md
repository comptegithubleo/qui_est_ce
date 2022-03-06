## Qui_est_ce

## Installation
* Clone repository on your PC or download as ZIP and extract files
* **Makefile must be changed if dependencies names does not match. JavaFX 17.0.2 and Jackson 1.13.2 versions were used when publishing**
* Get dependencies :
  - [JavaFX](https://gluonhq.com/products/javafx/)
  - [Jackson-Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)
  - [Jackson-Core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core)
  - [Jackson-Annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations)
* Place JavaFX and Jackson libraries in **lib** folder
* Run `make all` (`make build` + `make run`) from qui_est_ce directory (where **makefile** is located)

## JSON Datasets
3 basic sets are already provided : **Animals**, **Human**, and **Countries**.
JSON Structure :

```
    .
    └── theme
        ├── human
        |   └── objects
        |       ├───.
        |       |   ├── id: name
        |       |   ├── ...
        |       |   └── attributes
        |       |       ├── attribute1
        |       |       └── ...
        |       |
        |       └── ...
        |
        ├── animals
        |   └── ...
        |   
        └── ...
```
