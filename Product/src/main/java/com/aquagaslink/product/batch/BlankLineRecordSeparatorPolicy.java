package com.aquagaslink.product.batch;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

public class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {
    @Override
    public boolean isEndOfRecord(String line) {
        return !line.trim().isEmpty();
    }

    @Override
    public String postProcess(String record) {
        return record.trim().isEmpty() ? null : super.postProcess(record);
    }
}
