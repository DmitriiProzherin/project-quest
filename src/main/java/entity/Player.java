package entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @JsonProperty
    private String name;
    @JsonProperty("class")
    private Classes playerClass;
}
