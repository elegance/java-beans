package org.orh.javabeans.property.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;

public class IdPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            this.setValue(Long.parseLong(text));
        } else {
            this.setValue(0L);
        }
    }
}
