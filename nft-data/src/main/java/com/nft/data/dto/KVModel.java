package com.nft.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "key-value模型")
public class KVModel<K,V> {

    @ApiModelProperty(value = "key")
    private K key;

    @ApiModelProperty(value = "value值")
    private V value;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KVModel)) {
            return false;
        }

        KVModel<?, ?> kvModel = (KVModel<?, ?>) o;

        return key != null ? key.equals(kvModel.key) : kvModel.key == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}

