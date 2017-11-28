package org.orh.javabeans.property.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.setValue(format.parse(text));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
