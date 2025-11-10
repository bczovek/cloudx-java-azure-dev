package com.chtrembl.petstore.function.orderitemreserver.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private String id;

    private String email;

    private List<Product> products = new ArrayList<>();

    private Status status;
    
    private boolean complete;

    public enum Status {
		PLACED("placed"),
		APPROVED("approved"),
		DELIVERED("delivered");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static Status fromValue(String text) {
			if (text == null) {
				return null;
			}

			for (Status status : Status.values()) {
				if (String.valueOf(status.value).equalsIgnoreCase(text.trim())) {
					return status;
				}
			}
			return null;
		}
	}
}
