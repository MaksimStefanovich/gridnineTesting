package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        listFlightWithoutBeforeTimeNow(flights);
        System.out.println("--------------------------------------------");
        listFlightWithoutDateArriveBeforeDateDeparture(flights);
        System.out.println("--------------------------------------------");
        listFlightWithoutLongTransplant(flights);
    }

    public static void listFlightWithoutBeforeTimeNow(List<Flight> flights) {
        List<Flight> flightList = new ArrayList<>();

        for (Flight value : flights) {

            List<Segment> segmentsList = new ArrayList<>();

            for (int j = 0; j < value.getSegments().size(); j++) {
                if (!value.getSegments().get(j).getDepartureDate().isBefore(LocalDateTime.now())) {
                    segmentsList.add(value.getSegments().get(j));
                }
            }

            if (!segmentsList.isEmpty()) {
                Flight flight = new Flight(segmentsList);
                flightList.add(flight);
            }
        }

        flightList.forEach(System.out::println);
    }

    public static void listFlightWithoutDateArriveBeforeDateDeparture(List<Flight> flights) {
        List<Flight> flightList = new ArrayList<>();

        for (Flight value : flights) {

            List<Segment> segmentsList = new ArrayList<>();

            for (int j = 0; j < value.getSegments().size(); j++) {
                if (!value.getSegments().get(j).getArrivalDate()
                        .isBefore(value.getSegments().get(j).getDepartureDate())) {
                    segmentsList.add(value.getSegments().get(j));
                }
            }
            if (!segmentsList.isEmpty()) {
                Flight flight = new Flight(segmentsList);
                flightList.add(flight);
            }
        }

        flightList.forEach(System.out::println);
    }

    public static void listFlightWithoutLongTransplant(List<Flight> flights) {
        List<Flight> flightList = new ArrayList<>();

        for (Flight flightCurrently : flights) {
            long hours = 0;

            for (int j = 0; j < flightCurrently.getSegments().size() - 1; j++) {
                LocalDateTime start = flightCurrently.getSegments().get(j + 1).getDepartureDate();
                LocalDateTime end = flightCurrently.getSegments().get(j).getArrivalDate();
                long hoursBetweenSegment = Duration.between(end, start).toHours();
                hours = hours + hoursBetweenSegment;
            }
            if (!(hours > 2)) flightList.add(flightCurrently);

        }

        flightList.forEach(System.out::println);
    }

}
