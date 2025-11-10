package com.chtrembl.petstore.function.orderitemreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    private Long id;

    private Integer quantity = 0;

    private String name;

    private String photoUrl;
}
