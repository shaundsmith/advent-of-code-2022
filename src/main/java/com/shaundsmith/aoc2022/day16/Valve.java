package com.shaundsmith.aoc2022.day16;

import java.util.Set;

record Valve (ValveLabel label, int flowRate, Set<ValveLabel> leadsTo) {

}
