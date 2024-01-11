package entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum Classes implements Serializable {
    @JsonValue
    ROGUE,
    @JsonValue
    MAGE,
    @JsonValue
    WARRIOR
}
