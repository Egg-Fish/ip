package egg;

import egg.task.Task;
import egg.task.TaskList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.application.Platform;

/**
 * Implements a Graphical User Interface for the Egg application using JavaFX.
 * This class manages the main window, input handling, and the visual
 * representation of the chat dialog.
 */
public class GraphicUi extends Ui {
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.jpg"));
    private final Image eggImage = new Image(this.getClass().getResourceAsStream("/images/DaEgg.jpg"));

    private Stage stage;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private AnchorPane mainLayout;
    private Scene scene;
    private Egg egg;

    /**
     * Initializes the GraphicUi with the primary stage and the main logic controller.
     * Sets up the layout, event handlers, and displays the initial greeting.
     * @param stage The primary stage provided by the JavaFX Application.
     * @param egg   The Egg instance that processes user commands.
     */
    public GraphicUi(Stage stage, Egg egg) {
        this.stage = stage;
        this.egg = egg;

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        userInput.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-background-radius: 5;");
        sendButton.setStyle("-fx-background-color: #FFB347; -fx-text-fill: white; -fx-font-weight: bold;");

        mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout, 400.0, 600.0);
        stage.setScene(scene);

        formatWindow();
        addHandlers();

        printMessage("Hello! I'm Egg \nWhat can I do for you?");
        stage.show();
    }

    /**
     * Configures the window properties and dynamic anchoring for UI elements.
     * Ensures that components resize correctly when the window is adjusted.
     */
    private void formatWindow() {
        stage.setTitle("Egg - Your Personal Task Manager");
        stage.setResizable(true);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        dialogContainer.setPadding(new Insets(15));

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 45.0);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);

        userInput.prefWidthProperty().bind(mainLayout.widthProperty().subtract(sendButton.widthProperty().add(20)));
        sendButton.setPrefWidth(55.0);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, #FFDAB9);");
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        dialogContainer.setStyle("-fx-background-color: transparent;");
        dialogContainer.setSpacing(10);
    }

    /**
     * Sets up event listeners for user interactions (button clicks and Enter key).
     */
    private void addHandlers() {
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());
    }

    /**
     * Captures user input, creates a user dialog box, and triggers command execution.
     * If the command is "bye", the application will terminate.
     */
    private void handleUserInput() {
        String commandString = userInput.getText();
        if (commandString.isEmpty()) return;

        dialogContainer.getChildren().addAll(DialogBox.createUserDialog(commandString, userImage));
        userInput.clear();

        egg.runCommand(commandString);

        if (commandString.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }

    /**
     * Represents a custom dialog component consisting of an image and a text bubble.
     */
    public static class DialogBox extends HBox {
        private Label text;
        private ImageView displayPicture;

        /**
         * Private constructor to initialize a dialog box with a message and an avatar.
         * @param s The message text.
         * @param i The image for the avatar.
         */
        private DialogBox(String s, Image i) {
            text = new Label(s);
            displayPicture = new ImageView(i);
            this.getChildren().addAll(text, displayPicture);

            this.setSpacing(15);

            double imageSize = 70.0;
            displayPicture.setFitWidth(imageSize);
            displayPicture.setFitHeight(imageSize);

            double radius = imageSize / 2.0;
            Circle clip = new Circle(radius, radius, radius);
            displayPicture.setClip(clip);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(2.0);
            dropShadow.setOffsetY(2.0);
            dropShadow.setColor(Color.color(0, 0, 0, 0.1));

            text.setStyle("-fx-text-fill: black; " +
                          "-fx-background-color: white; " +
                          "-fx-background-radius: 15; " +
                          "-fx-padding: 12; " +
                          "-fx-font-family: 'Segoe UI', Arial;");
            text.setEffect(dropShadow);

            text.setWrapText(true);
            this.setAlignment(Pos.TOP_RIGHT);
        }

        /**
         * Changes the text font to monospace. Used primarily for bot responses.
         */
        private void changeToMonospace() {
            text.setStyle(text.getStyle() + "-fx-font-family: 'Courier New', monospace;");
        }

        /**
         * Flips the dialog box horizontally so the image appears on the left.
         */
        private void flip() {
            this.setAlignment(Pos.TOP_LEFT);
            ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
            FXCollections.reverse(tmp);
            this.getChildren().setAll(tmp);
        }

        /**
         * Factory method to create a dialog box for the user.
         * @param s User message.
         * @param i User avatar.
         * @return A DialogBox aligned to the right.
         */
        public static DialogBox createUserDialog(String s, Image i) {
            return new DialogBox(s, i);
        }

        /**
         * Factory method to create a dialog box for the Egg bot.
         * @param s Bot message.
         * @param i Bot avatar.
         * @return A flipped DialogBox aligned to the left with monospace font.
         */
        public static DialogBox createEggDialog(String s, Image i) {
            var db = new DialogBox(s, i);
            db.flip();
            db.changeToMonospace();
            return db;
        }
    }

    /**
     * Appends a bot response to the dialog container.
     * @param message The message to display.
     */
    @Override
    public void printMessage(String message) {
        dialogContainer.getChildren().addAll(DialogBox.createEggDialog(message, eggImage));
        userInput.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTaskAddedMessage(TaskList tasks, Task addedTask) {
        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + addedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";
        printMessage(l1 + l2 + l3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTaskDeletedMessage(TaskList tasks, Task deletedTask) {
        String l1 = "Noted. I've removed this task:\n";
        String l2 = "  " + deletedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";
        printMessage(l1 + l2 + l3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTaskList(TaskList tasks, String message) {
        StringBuilder sb = new StringBuilder(message).append("\n");
        for (int i = 1; i <= tasks.getSize(); i++) {
            sb.append(i).append(".").append(tasks.getTaskAtIndex(i)).append("\n");
        }
        printMessage(sb.toString());
    }
}
