package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Mock
    private Board board;

    @InjectMocks
    private Game game;

    @Test
    public void retrievesMarsPhotos_valid() {
        String dateType = "sol";
        String dateValue = "1000";
        String camera = "fhaz";
        String name = "curiosity";

       /* MarsRoverRequest request = MarsRoverRequest.builder()
                .dateType(dateType)
                .dateValue(dateValue)
                .camera(camera)
                .name(name)
                .build();

        List<MarsRoverPhoto> photos = asList(MarsRoverPhoto.builder()
                .sol(1000)
                .camera(Camera.builder()
                        .name(camera)
                        .build())
                .rover(Rover.builder()
                        .name(name)
                        .build())
                .build());

        MarsRoverResponseObject expectedResponse = MarsRoverResponseObject.builder()
                .photos(photos)
                .build();

        when(nasaApi.getPhotos(request)).thenReturn(expectedResponse);


        MarsRoverResponseObject response = marsRoverService.retrieveMarsPhotos(request);

        assertThat(response).isEqualTo(expectedResponse);*/

    }

}
