package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class TradingDeal {
    // total length 36 chars of UUID
    @JsonAlias({"Id"})
    @Setter(AccessLevel.PRIVATE)
    String id;
    @JsonAlias({"CardToTrade"})
    @Setter(AccessLevel.PRIVATE)
    String cardToTrade;
    @JsonAlias({"Type"})
    @Setter(AccessLevel.PRIVATE)
    String type;
    @JsonAlias({"MinimumDamage"})
    @Setter(AccessLevel.PRIVATE)
    float mininumDamage;

    // Jackson needs the default constructor
    public TradingDeal() {}
}
