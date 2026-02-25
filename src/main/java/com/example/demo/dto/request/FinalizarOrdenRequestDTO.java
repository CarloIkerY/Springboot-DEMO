package com.example.demo.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FinalizarOrdenRequestDTO {
    private Long usuarioId;
    private LocalDateTime fechaEntrega;
}