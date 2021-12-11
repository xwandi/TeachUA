package com.softserve.teachua.tools.transfer_controller;

import com.softserve.teachua.constants.RoleData;
import com.softserve.teachua.controller.marker.Api;
import com.softserve.teachua.dto.banner_item.SuccessCreatedBannerItem;
import com.softserve.teachua.tools.transfer_service.BannerItemTransferService;
import com.softserve.teachua.tools.transfer_service.transfer_impl.BannerItemTransferServiceImpl;
import com.softserve.teachua.utils.annotation.AllowedRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BannerTransferController implements Api {

   private final BannerItemTransferService bannerItemTransferService;

    @Autowired
    public BannerTransferController(BannerItemTransferServiceImpl bannerItemTransferService) {
        this.bannerItemTransferService = bannerItemTransferService;
    }

    @AllowedRoles(RoleData.ADMIN)
    @PostMapping("/transferBannersToDB")
    public List<SuccessCreatedBannerItem> moveBannerToDB(){
            return bannerItemTransferService.moveBannerToDB();
    }

}
