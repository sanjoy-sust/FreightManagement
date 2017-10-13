package com.fm.assignment.core.params;

import com.fm.assignment.core.enums.TransportTypeEnum;
import lombok.Getter;
import lombok.experimental.Builder;

import java.util.List;

/**
 * Created by Lenovo on 13/10/2017.
 */
@Builder
@Getter
public class FindPathParam {
    String source;
    String destination;
    List<TransportTypeEnum> modeOfTransports;
    Long containerSize;
    Long durationFrom;
    Long durationTo;
    Double costFrom;
    Double costTo;
}
