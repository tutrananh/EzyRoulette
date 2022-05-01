package org.youngmonkeys.app.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

@Data
@EzyObjectBinding
public class Bettor implements Comparable<Bettor>{
    private String username;
    private float betAmount;

    public Bettor(String username, float betAmount) {
        this.username = username;
        this.betAmount = betAmount;
    }

    @Override
    public String toString() {
        return username +"\t"+ betAmount;
    }

    @Override
    public int compareTo(Bettor b) {
        if(getBetAmount()<=b.getBetAmount()) return 1;
        return -1;
    }
}
