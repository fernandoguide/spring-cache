package br.com.fernandoguide.cache.controller.exceptions;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}