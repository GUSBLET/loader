package com.source.loader.technical.model.attribute;

import lombok.*;

import java.util.Objects;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelPageAttributes<T> {

    String title;
    String content;
    T entity;
}
