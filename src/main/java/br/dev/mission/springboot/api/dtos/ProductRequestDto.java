package br.dev.mission.springboot.api.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ProductRequestDto {
    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 30)
    private String brand;
}
