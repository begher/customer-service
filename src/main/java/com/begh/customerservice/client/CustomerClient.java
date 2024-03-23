package com.begh.customerservice.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/report")
public interface CustomerClient {
    @Operation(
            summary = "Fetches orders value by week for the given year",
            description = "Fetches weekly values from orders by week by given year",
            parameters =
            @Parameter(
                    name = "year",
                    description = "Given year to fetch weekly values of that year",
                    example = "2023",
                    in = ParameterIn.QUERY)
    )
    @GetMapping("/orders")
    ResponseEntity<Boolean> getOrdersValuePerWeek(
            @RequestParam Integer year
    );
}
