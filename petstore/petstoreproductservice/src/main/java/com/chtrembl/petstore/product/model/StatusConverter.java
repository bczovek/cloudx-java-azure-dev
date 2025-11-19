package com.chtrembl.petstore.product.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Product.Status, String> {

    @Override
    public String convertToDatabaseColumn(Product.Status status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public Product.Status convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Product.Status.fromValue(value);
    }
}
