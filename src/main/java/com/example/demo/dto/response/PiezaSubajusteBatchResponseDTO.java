package com.example.demo.dto.response;

import com.example.demo.dto.PiezaSubajusteResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PiezaSubajusteBatchResponseDTO {
    private Long ordenId;
    private Long subajusteId;
    private List<PiezaSubajusteResponseDTO> piezas;
}
