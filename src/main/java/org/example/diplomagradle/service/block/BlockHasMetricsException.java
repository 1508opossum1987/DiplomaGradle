package org.example.diplomagradle.service.block;

public class BlockHasMetricsException extends RuntimeException {
    public BlockHasMetricsException(Long id) {
        super("This block id: "+id+ " has metrics");
    }
    public BlockHasMetricsException() {
        super("This block has some metrics");
    }
}
