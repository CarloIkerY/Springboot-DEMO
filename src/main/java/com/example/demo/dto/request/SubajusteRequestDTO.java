package com.example.demo.dto.request;

import com.example.demo.dto.response.SubajusteDTO;
import lombok.Data;

import java.util.List;

@Data
public class SubajusteRequestDTO {
    private Long orden_id;
    private Long ajusteAuto_id;
    private List<SubajusteDTO> subajustes;
}
