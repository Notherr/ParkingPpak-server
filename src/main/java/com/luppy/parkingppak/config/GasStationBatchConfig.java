package com.luppy.parkingppak.config;

import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.init.batch.GasStationCustomWriter;
import com.luppy.parkingppak.init.batch.GasStationFieldSetMapper;
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
public class GasStationBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final GasStationCustomWriter customWriter;

    private final GasStationFieldSetMapper gasStationFieldSetMapper;

    private String[] gasStationFormat = new String[]{"id", "comp_name", "diesel_price", "gasoline_price","lat", "lon",
            "name",
            "unique_id"};

    @Bean
    public Job createGasStationJob() {
        return jobBuilderFactory.get("createGasStationJob").incrementer(new RunIdIncrementer()).flow(createGasStationStep()).end().build();
    }

    @Bean
    public Step createGasStationStep() {
        return stepBuilderFactory.get("createGasStationStep").<GasStation, GasStation> chunk(10).reader(gasStationFlatFileItemReader()).writer(customWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<GasStation> gasStationFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<GasStation>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("input/gas_station.csv"))
                .delimited()
                .names(gasStationFormat)
                .linesToSkip(1)
                .lineMapper(gasStationLineMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(GasStation.class);
                }})
                .build();
    }

    @Bean
    public LineMapper<GasStation> gasStationLineMapper() {
        final DefaultLineMapper<GasStation> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(gasStationFormat);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(gasStationFieldSetMapper);

        return defaultLineMapper;
    }

}
