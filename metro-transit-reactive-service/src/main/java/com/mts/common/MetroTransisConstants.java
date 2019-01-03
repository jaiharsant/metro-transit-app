

package com.mts.common;


public interface MetroTransisConstants {

    String API_VERSION = "v1";
    String API_PREFIX = "/api/" + API_VERSION;

    String API_TRIPS = API_PREFIX + "/trips";

    String API_NEXTBUS = "/nextbus";

    String URI_ROUTE = "route";
    String URI_DIRECTION = "direction";
    String URI_STOP = "stop";

    String VALIDATION_ERROR = "INVALID-INPUT";

    String ERROR_ROUTE_CODE = "100";
    String ERROR_ROUTE_DESC = "Not a valid route";

    String ERROR_DIRECT_CODE = "101";
    String ERROR_DIRECT_DESC = "Not a valid direction for this route";

    String ERROR_STOP_CODE = "102";
    String ERROR_STOP_DESC = "Not a valid stop for this route";

    String ERROR_NOBUS_CODE = "103";
    String ERROR_NOBUS_DESC = "There is no bus schedule avilable now !";


}
