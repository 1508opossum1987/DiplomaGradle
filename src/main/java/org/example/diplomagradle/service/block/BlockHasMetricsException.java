package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//исключение для попытки удаления блоков с метриками
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlockHasMetricsException extends RuntimeException {
    public BlockHasMetricsException(Long id) {
        super("This block id: "+id+ " has metrics");
    }
    public BlockHasMetricsException() {
        super("This block has some metrics");
    }
}
