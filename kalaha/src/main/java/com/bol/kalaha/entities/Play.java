package com.bol.kalaha.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Play {

    private int stones;
    private int pitIndex;
    private boolean isPlayerOne;
}
