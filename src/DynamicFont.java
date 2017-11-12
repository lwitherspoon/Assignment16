import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Program to demo dynamically updating text based on user selections
 * for font family, font size, font weight, and font style.
 * CSC 201 - Assignment 16.14
 * @author Laura Witherspoon
 */

public class DynamicFont extends Application {
    private Text text = new Text("Programming is fun!");
    private CheckBox cbBold = new CheckBox("Bold");
    private CheckBox cbItalic = new CheckBox("Italic");
    private Label lblFontFamily = new Label("Font Family");
    private ComboBox<String> cboFontFamily = new ComboBox<>();
    private Label lblFontSize = new Label("Font Size");
    private ComboBox<Integer> cboFontSize = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {

        // Create main layout to contain all components
        BorderPane pane = new BorderPane();

        // Create scrollable pane to contain text
        ScrollPane scrollPaneToHoldText = new ScrollPane();
        HBox textContainer = new HBox();
        textContainer.getChildren().add(text);
        textContainer.setAlignment(Pos.CENTER);

        // Bind width/height of text container to width of parent container
        textContainer.prefWidthProperty().bind(scrollPaneToHoldText.widthProperty().subtract(3));
        textContainer.prefHeightProperty().bind(scrollPaneToHoldText.heightProperty().subtract(3));

        // Add text container to scroll pane and show scroll bar only whene necessary
        scrollPaneToHoldText.setContent(textContainer);
        scrollPaneToHoldText.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneToHoldText.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Set initial font size/family and add to center of main layout
        text.setFont(new Font("Times New Roman", 20));
        text.setTextAlignment(TextAlignment.CENTER);
        pane.setCenter(scrollPaneToHoldText);

        // Create container to hold checkboxes and apply styles
        HBox fontStyles = new HBox();
        fontStyles.getChildren().addAll(cbBold, cbItalic);
        fontStyles.setAlignment(Pos.CENTER);
        fontStyles.setSpacing(3);
        BorderPane.setMargin(fontStyles, new Insets(10, 10, 10, 10));
        pane.setBottom(fontStyles);

        // Populate fonts in font family dropdown
        cboFontFamily.getItems().addAll(Font.getFamilies());
        cboFontFamily.setValue("Times New Roman");

        // Populate 1 to 100 in font sizes dropdown
        for (int i = 1; i < 101; i++) {
            cboFontSize.getItems().add(i);
        }
        cboFontSize.setValue(20);

        // Create another pane to hold dropdowns and their labels
        FlowPane fontBasics = new FlowPane();
        fontBasics.getChildren().addAll(lblFontFamily, cboFontFamily, lblFontSize, cboFontSize);
        fontBasics.setAlignment(Pos.CENTER);
        fontBasics.setHgap(5);
        pane.setTop(fontBasics);
        BorderPane.setMargin(fontBasics, new Insets(10, 10, 10, 10));

        // Create custom handler to update font styles based on selected values
        EventHandler<ActionEvent> handler = e -> {
            if (cbBold.isSelected() && cbItalic.isSelected()) {
                text.setFont(Font.font(cboFontFamily.getValue(), FontWeight.BOLD, FontPosture.ITALIC, cboFontSize.getValue()));
            } else if (cbBold.isSelected()) {
                text.setFont(Font.font(cboFontFamily.getValue(), FontWeight.BOLD, FontPosture.REGULAR, cboFontSize.getValue()));
            } else if (cbItalic.isSelected()) {
                text.setFont(Font.font(cboFontFamily.getValue(), FontWeight.NORMAL, FontPosture.ITALIC, cboFontSize.getValue()));
            } else {
                text.setFont(Font.font(cboFontFamily.getValue(), FontWeight.NORMAL, FontPosture.REGULAR, cboFontSize.getValue()));
            }

        };

        // Attach custom handler to each input
        cbBold.setOnAction(handler);
        cbItalic.setOnAction(handler);
        cboFontFamily.setOnAction(handler);
        cboFontSize.setOnAction(handler);

        // Set the scene and display
        Scene scene = new Scene(pane, 450, 200);
        primaryStage.setTitle("Dynamic Font Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
