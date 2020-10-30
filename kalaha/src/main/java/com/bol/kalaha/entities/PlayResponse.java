package com.bol.kalaha.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kalfie
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayResponse {

    private Board board;
    private String turn;
    private String message;

}
