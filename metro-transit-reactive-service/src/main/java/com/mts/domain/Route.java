

package com.mts.domain;


import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;


@Data
public class Route {

    @JsonAlias("Description")
    String description;

    @JsonAlias("ProviderID")
    String providerID;

    @JsonAlias("Route")
    String route;

}
