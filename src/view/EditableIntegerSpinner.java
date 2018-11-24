package view;

import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;

import static javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

/**
 * Integer spinner that can be edited (adds events to a regular JavaFX Spinner)
 */
public class EditableIntegerSpinner extends Spinner<Integer> {

    public EditableIntegerSpinner()
    {
        setEditable(true);
        getEditor().setOnKeyTyped(e ->
        {
            if (!Character.isDigit(e.getCharacter().toCharArray()[0]))
                e.consume();
        });
        getEditor().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE && getEditor().getText().length() <= 1)
            {
                e.consume();
            }
        });

        getEditor().textProperty().addListener((observable, oldValue, newValue) ->
        {
            IntegerSpinnerValueFactory valueFactory = ((IntegerSpinnerValueFactory)getValueFactory());
            int newIntValue = Integer.parseInt(newValue);

            if (newIntValue < valueFactory.getMin())
            {
                getEditor().setText(Integer.toString(valueFactory.getMin()));
                valueFactory.setValue(valueFactory.getMin());
            }
            else if (newIntValue > valueFactory.getMax())
            {
                getEditor().setText(Integer.toString(valueFactory.getMax()));
                valueFactory.setValue(valueFactory.getMax());
            }
            else
            {
                valueFactory.setValue(newIntValue);
            }
        });
    }
}
