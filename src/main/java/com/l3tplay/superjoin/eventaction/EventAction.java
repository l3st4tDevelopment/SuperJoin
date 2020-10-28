package com.l3tplay.superjoin.eventaction;

import lombok.Data;

import java.util.List;

@Data
public class EventAction {

    private final List<String> joinActions;
    private final List<String> quitActions;
    private final String permission;
}
