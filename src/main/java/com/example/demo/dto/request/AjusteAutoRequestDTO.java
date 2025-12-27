package com.example.demo.dto.request;

import com.example.demo.dto.AjusteDTO;
import lombok.Data;

import java.util.List;

@Data
public class AjusteAutoRequestDTO {
    private Long orden_id;
    private List<AjusteDTO> ajustes;
}
