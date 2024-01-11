package entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonRootName("player")
public class Player {
    @JsonProperty
    private String name;
    @JsonProperty("class")
    private Classes playerClass;
}
