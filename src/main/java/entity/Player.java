package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Player {
    @JsonProperty
    private String name;
    @JsonProperty("class")
    private Classes playerClass;
}
