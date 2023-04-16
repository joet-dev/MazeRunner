package mazerunner.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import mazerunner.engine.GameEngine;
import javafx.fxml.FXML;

import java.io.*;
import java.util.regex.Pattern;

public class Controller {
    private GameEngine engine;
    private GridPane gridPane;

    @FXML
    private TextField difficulty;
    @FXML
    private Button save;
    @FXML
    private Button quit;
    @FXML
    private Button up;
    @FXML
    private Button left;
    @FXML
    private Button down;
    @FXML
    private Button right;
    @FXML
    private TextArea output;
    @FXML
    private BorderPane pane;

    @FXML
    public void initialize() {
        engine = new GameEngine(10);
        gridPane = new GridPane();
        pane.setCenter(gridPane);
        output.setDisable(true);
        output.setStyle("-fx-opacity: 1;");
        output.setWrapText(true);
        output.setText("Press START to begin the game!");

        setButton(false);

        difficulty.setText(String.valueOf(engine.getDifficulty()));
        difficulty.textProperty().addListener(e -> {
            String possibleVal = difficulty.getText();

            boolean isNumber = Pattern.matches("[0-9]+", possibleVal);
            if(isNumber) {
                int val = Integer.parseInt(possibleVal);
                if(engine.setDifficulty(val)) {
                    output.setText("Difficulty set!");
                } else {
                    output.setText("Difficulty value INVALID!");
                }
            } else {
                output.setText("Difficulty value INVALID!");
                engine.setDifficulty(5);
            }
        });
    }

    private void updateGame() {
        for (int y=0; y<10; y++) {
            for (int x=0; x<10; x++) {
                switch(engine.getCell(x, y).getType()) {
                    case ENTRY:
                    case EMPTY:
                        ImageView empty = new ImageView(new Image("empty.png"));
                        empty.setFitWidth(60);
                        empty.setFitHeight(60);
                        gridPane.add(empty, x, 9-y);
                        break;
                    case EXIT:
                        ImageView exit = new ImageView(new Image("exit.png"));
                        exit.setFitWidth(60);
                        exit.setFitHeight(60);
                        gridPane.add(exit, x, 9-y);
                        break;
                    case APPLE:
                        ImageView apple = new ImageView(new Image("apple.png"));
                        apple.setFitWidth(60);
                        apple.setFitHeight(60);
                        gridPane.add(apple, x, 9-y);
                        break;
                    case COIN:
                        ImageView coin = new ImageView(new Image("coin.png"));
                        coin.setFitWidth(60);
                        coin.setFitHeight(60);
                        gridPane.add(coin, x, 9-y);
                        break;
                    case TRAP:
                        ImageView trap = new ImageView(new Image("trap.png"));
                        trap.setFitWidth(60);
                        trap.setFitHeight(60);
                        gridPane.add(trap, x, 9-y);
                        break;
                }
                if (engine.getPlayerPos()[0] == x && engine.getPlayerPos()[1] == y) {
                    ImageView player = new ImageView(new Image("player.png"));
                    player.setFitWidth(60);
                    player.setFitHeight(60);
                    gridPane.add(player, x, 9-y);
                }
            }
        }

        if (!engine.checkGameState()) {
            gameResults();
            setButton(false);
        }
    }

    private void updateInfo(String moveResult) {
        output.appendText("Move: " + moveResult + "\nAction: " + engine.checkInteraction() + engine.getPlayerStats());
    }

    private void gameResults() {
        output.clear();
        output.setText(engine.getGameResults());
        output.appendText("\nPress START to play again.");
    }

    private void setButton(boolean bool) {
        up.setDisable(!bool);
        down.setDisable(!bool);
        left.setDisable(!bool);
        right.setDisable(!bool);
        save.setDisable(!bool);
        difficulty.setDisable(bool);
        quit.setDisable(!bool);
    }

    @FXML
    private void start(ActionEvent event) {
        event.consume();
        gridPane.getChildren().clear();

        engine.generateMap();
        engine.resetPlayer();
        difficulty.setText(String.valueOf(engine.getDifficulty()));
        output.clear();

        setButton(true);
        updateInfo("-");
        updateGame();
    }
    @FXML
    private void help(ActionEvent event) {
        event.consume();
        output.clear();
        output.setText("INSTRUCTIONS:\nGet to the exit while collecting as many coins as possible. " +
                "Eat apples to increase your stamina. Avoid traps!");
    }
    @FXML
    private void quit(ActionEvent event) {
        event.consume();
        gridPane.getChildren().clear();
        output.clear();
        output.setText("Press START to begin the game!");
        setButton(false);
    }
    @FXML
    private void save(ActionEvent event) {
        event.consume();

        try {
            File file = new File("src/saves/saveState.txt");
            if (file.createNewFile()) {
                System.out.println("New file created.");
            } else {
                System.out.println("File exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("src/saves/saveState.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(engine);
            output.setText("Game state saved.\n");
            updateInfo("-");
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void load(ActionEvent event) {
        event.consume();
        try {
            FileInputStream fileIn = new FileInputStream("src/saves/saveState.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            engine = (GameEngine) in.readObject();
            updateGame();
            updateInfo("-");
            difficulty.setText(String.valueOf(engine.getDifficulty()));
            output.setText("Game state loaded.\n");
            setButton(true);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            output.setText("No save state available.");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("GameEngine class not found.");
            c.printStackTrace();
        }
    }
    @FXML
    private void moveUp(ActionEvent event) {
        event.consume();
        move('w');
    }
    @FXML
    private void moveDown(ActionEvent event) {
        event.consume();
        move('s');
    }
    @FXML
    private void moveLeft(ActionEvent event) {
        event.consume();
        move('a');
    }
    @FXML
    private void moveRight(ActionEvent event) {
        event.consume();
        move('d');
    }

    private void move(char val) {
        output.clear();
        updateInfo(engine.movePlayer(val));
        updateGame();
    }
}
