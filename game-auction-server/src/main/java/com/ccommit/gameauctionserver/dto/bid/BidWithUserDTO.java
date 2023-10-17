package com.ccommit.gameauctionserver.dto.bid;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidWithUserDTO {

    Bid bid;
    RequestUserInfo userInfo;

    int pirceGold;
}
