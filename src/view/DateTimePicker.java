package view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import utils.DI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Date picker with extended time field in the textbox
 */
public class DateTimePicker extends DatePicker {

    /**
     * Last good value that was typed in/selected
     */
    private LocalDateTime lastKnownGood = null;

    /**
     * Date time property
     */
    private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());

    public DateTimePicker()
    {
        // Used to convert string to both to the calendar picker and the local dateTimeValue property
        setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object)
            {
                return (dateTimeValue.get() != null) ? dateTimeValue.get().format(DI.settings.getDateTimeFormatter()) : "";
            }

            @Override
            public LocalDate fromString(String string)
            {
                if (string == null || string.isEmpty())
                {
                    resetLastKnownGood();
                }
                else
                {
                    try
                    {
                        setDateTimeValue(LocalDateTime.parse(string, DI.settings.getDateTimeFormatter()));
                    }
                    catch (DateTimeParseException e)
                    {
                        resetLastKnownGood();
                    }
                }

                return dateTimeValue.get().toLocalDate();
            }
        });

        // Calendar picker to DateTime
        valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null)
            {
                resetLastKnownGood();
            }
            else
            {
                if (dateTimeValue.get() == null)
                {
                    setDateTimeValue(LocalDateTime.of(newValue, LocalTime.now()));
                }
                else
                {
                    setDateTimeValue(LocalDateTime.of(newValue, dateTimeValue.get().toLocalTime()));
                }
            }
        });

        // Text change so that we can show invalid coloring
        getEditor().textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null || newValue.isEmpty())
            {
                getStyleClass().add("invalid-border");
                getEditor().getStyleClass().add("invalid");
                return;
            }

            try
            {
                LocalDateTime.parse(newValue, DI.settings.getDateTimeFormatter());
            }
            catch (DateTimeParseException e)
            {
                getStyleClass().add("invalid-border");
                getEditor().getStyleClass().add("invalid");
                return;
            }

            getStyleClass().removeIf(s -> s.equals("invalid-border"));
            getEditor().getStyleClass().removeIf(s -> s.equals("invalid"));
        });

        // Focus lost commit the value
        getEditor().focusedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!newValue) getEditor().commitValue();
        });

        // DateTime property to Calendar picker
        dateTimeValue.addListener((observable, oldValue, newValue) -> setValue(newValue == null ? null : newValue.toLocalDate()));
    }

    /**
     * Reset to last known good value
     */
    private void resetLastKnownGood()
    {
        if (lastKnownGood != null)
        {
            setDateTimeValue(lastKnownGood);
        }
        else
        {
            setDateTimeValue(LocalDateTime.now());
        }
    }

    /**
     * @return Date time property value
     */
    public LocalDateTime getDateTimeValue()
    {
        return dateTimeValue.get();
    }

    /**
     * @param dateTimeValue Date time property value
     */
    public void setDateTimeValue(LocalDateTime dateTimeValue)
    {
        lastKnownGood = dateTimeValue;
        this.dateTimeValue.set(dateTimeValue);
    }

    /**
     * @return Date time property
     */
    public ObjectProperty<LocalDateTime> dateTimeValueProperty()
    {
        return dateTimeValue;
    }
}
