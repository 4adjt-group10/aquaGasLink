package com.aquagaslink.product.batch;

import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineCallbackHandler;

public class LineValidator implements LineCallbackHandler {
    @Override
    public void handleLine(String line) throws FlatFileParseException {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new FlatFileParseException("Linha inválida. Deve conter exatamente 5 colunas.", line, 0);
        }
    }
}
