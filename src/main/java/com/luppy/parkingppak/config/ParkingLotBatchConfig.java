package com.luppy.parkingppak.config;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.init.batch.ParkingLotCustomWriter;
import com.luppy.parkingppak.init.batch.ParkingLotFieldSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@RequiredArgsConstructor
public class ParkingLotBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ParkingLotCustomWriter parkingLotCustomWriter;

    private final ParkingLotFieldSetMapper parkingLotFieldSetMapper;

    private String[] parkingLotFormat = new String[]{"id", "add_rates", "add_time_rates", "address", "holiday_begin",
            "holiday_end", "modification_date","parking_code", "parking_name", "payyn", "phone_number", "rates",
            "sync_time",
            "time_rates", "type", "weekday_begin", "weekday_end", "weekend_begin", "weekend_end", "x_coor",
            "y_coor"};

    @Bean
    public Job createJob() {
        return jobBuilderFactory.get("createJob").incrementer(new RunIdIncrementer()).flow(createStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("createStep").<ParkingLot, ParkingLot> chunk(10).reader(flatFileItemReader()).writer(parkingLotCustomWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<ParkingLot> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<ParkingLot>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("input/parking_lot.csv"))
                .delimited()
                .names(parkingLotFormat)
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(ParkingLot.class);
                }})
                .build();
    }

    @Bean
    public LineMapper<ParkingLot> lineMapper() {
        final DefaultLineMapper<ParkingLot> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(parkingLotFormat);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(parkingLotFieldSetMapper);

        return defaultLineMapper;
    }

}
