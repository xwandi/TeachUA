package com.softserve.teachua.repository;

import com.softserve.teachua.model.BannerItem;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = "/testdata/banner_items.sql")
class BannerItemRepositoryIT {

    @Autowired
    private BannerItemRepository bannerItemRepository;

    @Test
    void findAllByOrderBySequenceNumberAscShouldReturnSortedListBySequenceNumber() {
        assertThat(bannerItemRepository.findAllByOrderBySequenceNumberAsc()).isEqualTo(List.of(BannerItem.builder().id(1L).title("1").picture("1").sequenceNumber(1).build()));
    }
}
