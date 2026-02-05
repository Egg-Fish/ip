package egg;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.application.Platform;


public class GraphicUi extends Ui {
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image eggImage = new Image(this.getClass().getResourceAsStream("/images/DaEgg.png"));
    
    private Stage stage;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private AnchorPane mainLayout;
    private Scene scene;
    private Egg egg;
    
    public GraphicUi(Stage stage, Egg egg) {
        this.stage = stage;
        this.egg = egg;
        
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);

        formatWindow();

        addHandlers();

        printMessage("Hello! I'm Egg \nWhat can I do for you?");
        
        stage.show();
    }

    private void formatWindow() {
        stage.setTitle("Egg - Your Personal Task Manager");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

    }

    private void addHandlers() {
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        
        userInput.setOnAction((event) -> {
            handleUserInput();
        });
    }

    private void handleUserInput() {
        String commandString = userInput.getText();
        
        dialogContainer.getChildren().addAll(DialogBox.createUserDialog(commandString, userImage));
        userInput.clear();

        egg.runCommand(commandString);

        if (commandString.equals("bye")) {
            Platform.exit();
        }
    }

    public static class DialogBox extends HBox {
        private Label text;
        private ImageView displayPicture;

        private DialogBox(String s, Image i) {
            text = new Label(s);
            displayPicture = new ImageView(i);
            this.getChildren().addAll(text, displayPicture);

            text.setWrapText(true);
            displayPicture.setFitWidth(100.0);
            displayPicture.setFitHeight(100.0);
            this.setAlignment(Pos.TOP_RIGHT);
        }

        private void flip() {
            this.setAlignment(Pos.TOP_LEFT);
            ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
            FXCollections.reverse(tmp);
            this.getChildren().setAll(tmp);
        }

        public static DialogBox createUserDialog(String s, Image i) {
            return new DialogBox(s, i);
        }

        public static DialogBox createEggDialog(String s, Image i) {
            var db = new DialogBox(s, i);
            db.flip();
            return db;
        }
    }
    
    @Override
    public void printMessage(String message) {
        dialogContainer.getChildren().addAll(DialogBox.createEggDialog(message, eggImage));
        userInput.clear();
    }
    
    @Override
    public void printTaskAddedMessage(TaskList tasks, Task addedTask) {
        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + addedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    @Override
    public  void printTaskDeletedMessage(TaskList tasks, Task deletedTask) {
        String l1 = "Noted. I've removed this task:\n";
        String l2 = "  " + deletedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    @Override
    public void printTaskList(TaskList tasks, String message) {
        String s = message + "\n";
        
        for (int i = 1; i <= tasks.getSize(); i++) {
            s += i + "." + tasks.getTaskAtIndex(i) + "\n";
        }

        printMessage(s);
    }
}
