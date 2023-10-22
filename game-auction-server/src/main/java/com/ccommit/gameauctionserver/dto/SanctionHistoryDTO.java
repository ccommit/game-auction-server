package com.ccommit.gameauctionserver.dto;

import com.ccommit.gameauctionserver.dto.admin.SanctionReasonType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SanctionHistoryDTO {

    private int id;

    private String adminId;
    private String userId;
    private Integer bidId;
    private String reasonType;

}
