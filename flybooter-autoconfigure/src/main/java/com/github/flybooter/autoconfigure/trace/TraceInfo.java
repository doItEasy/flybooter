package com.github.flybooter.autoconfigure.trace;

import lombok.Data;

import java.io.Serializable;

@Data
public class TraceInfo implements Serializable {
    private String desc;
    boolean traceParam;
    boolean traceRet;
}
