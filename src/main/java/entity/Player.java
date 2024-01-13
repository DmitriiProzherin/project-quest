package entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true, value = "class")
    private Classes playerClass;
    @JsonProperty
    private boolean blessing;
}
