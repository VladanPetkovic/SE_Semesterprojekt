package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class TradingDeal {
    // total length 36 chars of UUID
    @JsonAlias({"Id"})
    String id;
    @JsonAlias({"CardToTrade"})
    String cardToTrade;
    @JsonAlias({"Type"})
    String type;
    @JsonAlias({"MinimumDamage"})
    float mininumDamage;

    // Jackson needs the default constructor
    public TradingDeal() {}
}
