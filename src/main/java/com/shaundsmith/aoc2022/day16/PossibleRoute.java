package com.shaundsmith.aoc2022.day16;

import java.util.Map;

record PossibleRoute(TunnelPath route, Map<Valve, Integer> valvesClosed, int remainingTime) {

}
