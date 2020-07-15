package com.summer.common.utils.concurrent;

import java.util.concurrent.Callable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallableEntry<T> {

    private Callable<T> callable;
    private String routerKey;
}
