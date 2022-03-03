package com.deepcre.radar;

import com.deepcre.radar.entity.SeriesEntity;
import com.deepcre.radar.service.SeriesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.junit.jupiter.api.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JinchanRadarApplicationTests {

    @Autowired
    SeriesService seriesService;

    @Test
    public void contextLoads() {

        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setSeriesName("test");
        seriesService.save(seriesEntity);
        System.out.println("保存成功。。。");
    }

    @Test
    public void testDate(){
        Date date = new Date();
        System.out.println(new SimpleDateFormat("yyyyMMdd").format(date));
        String str = new String("123456789");
        System.out.println(str.substring(str.length()-2,str.length()));
    }

}
