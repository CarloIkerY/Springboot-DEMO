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
public class AjusteAutoDTO {
    private Long ajusteAuto_id;
    private Long orden_id;
    private List<AjusteDTO> ajustes;
}
