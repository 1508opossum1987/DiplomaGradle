package org.example.diplomagradle;

import org.example.diplomagradle.datamodel.repository.*;
import org.example.diplomagradle.security.AccessChecker;
import org.example.diplomagradle.security.AccessCheckerInterface;
import org.example.diplomagradle.service.block.BlockService;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.example.diplomagradle.service.metric.MetricRestrictionValidator;
import org.example.diplomagradle.service.metric.MetricRestrictionValidatorInterface;
import org.example.diplomagradle.service.metric.MetricService;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.example.diplomagradle.service.metricValue.MetricValueService;
import org.example.diplomagradle.service.metricValue.MetricValueServiceInterface;

import org.example.diplomagradle.service.metricValue.metricDataService.MetricDataService;
import org.example.diplomagradle.service.metricValue.metricDataService.MetricTestDataServiceInterface;
import org.example.diplomagradle.service.metricValue.metricDataService.TestDataService;
import org.example.diplomagradle.service.metricValue.metricDataService.TestDataServiceInterface;
import org.example.diplomagradle.service.page.PageService;
import org.example.diplomagradle.service.page.PageServiceInterface;
import org.example.diplomagradle.service.role.RoleService;
import org.example.diplomagradle.service.role.RoleServiceInterface;
import org.example.diplomagradle.service.user.CurrentUserProvider;
import org.example.diplomagradle.service.user.UserService;
import org.example.diplomagradle.service.user.UserServiceInterface;
import org.example.diplomagradle.service.wagon.WagonService;
import org.example.diplomagradle.service.wagon.WagonServiceInterface;
import org.example.diplomagradle.service.wagon.WagonTestDataService;
import org.example.diplomagradle.service.wagon.WagonTestDataServiceInterface;
import org.example.diplomagradle.service.wagon.wagonMetric.WagonMetricService;
import org.example.diplomagradle.service.wagon.wagonMetric.WagonMetricServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.diplomagradle.service.user.detail.DbUserDetailService;


@Configuration
public class ConfigurationClass {
    @Bean
    public BlockServiceInterface blockService(BlockRepository blockRepository,
                                              MetricRepository metricRepository) {
        return new BlockService(blockRepository, metricRepository);
    }

    @Bean
    public MetricServiceInterface metricService(MetricRepository metricRepository,
                                                BlockServiceInterface blockServiceInterface,
                                                MetricValueRepository metricValueRepository) {
        return new MetricService(metricRepository, blockServiceInterface, metricValueRepository);
    }

    @Bean
    public MetricValueServiceInterface metricValueService(MetricValueRepository metricValueRepository,
                                                          MetricServiceInterface metricServiceInterface,
                                                          MetricRestrictionValidatorInterface metricRestrictionValidatorInterface,
                                                          CurrentUserProvider currentUserProvider,
                                                          AccessCheckerInterface accessCheckerInterface) {
        return new MetricValueService(metricValueRepository, metricServiceInterface,
                metricRestrictionValidatorInterface, currentUserProvider, accessCheckerInterface);
    }

    @Bean
    public WagonServiceInterface wagonService(WagonRepository wagonRepository,
                                              JdbcTemplate jdbcTemplate) {
        return new WagonService(wagonRepository, jdbcTemplate);
    }

    @Bean
    public WagonTestDataServiceInterface wagonTestDataService(WagonRepository wagonRepository,
                                                              JdbcTemplate jdbcTemplate) {
        return new WagonTestDataService(wagonRepository, jdbcTemplate);
    }

    @Bean
    public MetricRestrictionValidatorInterface metricRestrictionValidator() {
        return new MetricRestrictionValidator();
    }

    @Bean
    public WagonMetricServiceInterface wagonMetricService(WagonRepository wagonRepository,
                                                          MetricServiceInterface metricServiceInterface,
                                                          MetricValueRepository metricValueRepository,
                                                          MetricRestrictionValidatorInterface metricRestrictionValidator) {
        return new WagonMetricService(wagonRepository, metricServiceInterface,
                metricValueRepository, metricRestrictionValidator);
    }

    @Bean
    public MetricTestDataServiceInterface metricTestDataService(MetricValueRepository metricValueRepository,
                                                                MetricRepository metricRepository,
                                                                MetricRestrictionValidatorInterface metricRestrictionValidatorInterface) {
        return new MetricDataService(metricValueRepository, metricRepository, metricRestrictionValidatorInterface);
    }

    @Bean
    public TestDataServiceInterface testDataService(WagonMetricServiceInterface wagonMetricServiceInterface,
                                                    MetricTestDataServiceInterface metricTestDataServiceInterface) {
        return new TestDataService(wagonMetricServiceInterface, metricTestDataServiceInterface);
    }

    //бин для PageService
    @Bean
    public PageServiceInterface pageService(BlockRepository blockRepository,
                                            MetricRepository metricRepository,
                                            MetricValueRepository metricValueRepository) {
        return new PageService(blockRepository, metricRepository, metricValueRepository);
    }

    //бин для RoleService
    @Bean
    public RoleServiceInterface roleServiceInterface (RoleRepository roleRepository){
        return new RoleService(roleRepository);
    }

    //бин для UserService
    @Bean
    public UserServiceInterface userServiceInterface(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                                     RoleServiceInterface roleServiceInterface){
        return new UserService(userRepository, passwordEncoder, roleServiceInterface);
    }

    //Бин для UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new DbUserDetailService(userRepository);
    }

    //добавление в зависимость прослойки для получения данных о пользователе
    //из security
    @Bean
    public CurrentUserProvider currentUserProvider() {
        return new CurrentUserProvider();
    }

    //добавление в зависимость бина для проверки доступа к функциям
    @Bean
    public AccessCheckerInterface accessChecker(MetricAccessRepository metricAccessRepository,
                                                BlockViewRepository blockViewRepository,
                                                MetricValueRepository metricValueRepository,
                                                MetricRepository metricRepository) {
        return new AccessChecker(metricAccessRepository, blockViewRepository,
                metricValueRepository, metricRepository);
    }
}
