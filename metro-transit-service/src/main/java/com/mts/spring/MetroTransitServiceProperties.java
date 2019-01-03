

package com.mts.spring;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@ConfigurationProperties(prefix = "metro")
@Data
public class MetroTransitServiceProperties {
    String baseUrl;
    String routesUrl;
    String directionsUrl;
    String stopsUrl;
    String nextTripUrl;
}
