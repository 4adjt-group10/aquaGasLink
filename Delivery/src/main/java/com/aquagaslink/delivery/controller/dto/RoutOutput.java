package com.aquagaslink.delivery.controller.dto;

import java.util.List;

public record RoutOutput(List<Route> routes) {
    public static record Route(List<Leg> legs) {
    }

    public static record Leg(Distance distance, Duration duration, List<Step> steps) {
    }

    public static record Distance(String text, int value) {
    }

    public static record Duration(String text, int value) {
    }

    public static record Step(Distance distance, Duration duration, String htmlInstructions) {
    }
}