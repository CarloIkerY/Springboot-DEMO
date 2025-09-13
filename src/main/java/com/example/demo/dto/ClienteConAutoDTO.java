package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteConAutoDTO {
    private String name;
    private String email;
    private String password;
    private String telefono;
    private List<AutoDTO> autos;
}