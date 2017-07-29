package com.bkdev.translation.api.body;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * ExampleBody.
 *
 * @author BiNC
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(suppressConstructorProperties = true)
public class ExampleBody {
    @SerializedName("data")
    int abc;
}
