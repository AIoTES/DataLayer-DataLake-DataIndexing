/** 
* Copyright 2020 National University of Ireland Galway 
* 
* See the NOTICE file distributed with this work for additional information
* regarding copyright ownership.
*
* Licensed under the EUPL, Version 1.2 or – as soon the European Commission 
* approves - subsequent versions of the EUPL (the "Licence"); 
* You may not use this work except in compliance with the Licence. 
* You may obtain a copy of the Licence at: 
*
*     https://joinup.ec.europa.eu/software/page/eupl 
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the Licence is distributed on an "AS IS" basis, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the Licence for the specific language governing permissions and 
* limitations under the Licence. 
*/

package eu.activageproject.datalake.config;

import io.github.simlife.config.SimlifeProperties;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.JvmAttributeGaugeSet;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.*;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration extends MetricsConfigurerAdapter {

    private static final String PROP_METRIC_REG_JVM_MEMORY = "jvm.memory";
    private static final String PROP_METRIC_REG_JVM_GARBAGE = "jvm.garbage";
    private static final String PROP_METRIC_REG_JVM_THREADS = "jvm.threads";
    private static final String PROP_METRIC_REG_JVM_FILES = "jvm.files";
    private static final String PROP_METRIC_REG_JVM_BUFFERS = "jvm.buffers";
    private static final String PROP_METRIC_REG_JVM_ATTRIBUTE_SET = "jvm.attributes";

    private final Logger log = LoggerFactory.getLogger(MetricsConfiguration.class);

    private MetricRegistry metricRegistry = new MetricRegistry();

    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    private final SimlifeProperties simLifeProperties;

    public MetricsConfiguration(SimlifeProperties simLifeProperties) {
        this.simLifeProperties = simLifeProperties;
    }

    @Override
    @Bean
    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    @Override
    @Bean
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @PostConstruct
    public void init() {
        log.debug("Registering JVM gauges");
        metricRegistry.register(PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet());
        metricRegistry.register(PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge());
        metricRegistry.register(PROP_METRIC_REG_JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
        metricRegistry.register(PROP_METRIC_REG_JVM_ATTRIBUTE_SET, new JvmAttributeGaugeSet());
        if (simLifeProperties.getMetrics().getJmx().isEnabled()) {
            log.debug("Initializing Metrics JMX reporting");
            JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
            jmxReporter.start();
        }
        if (simLifeProperties.getMetrics().getLogs().isEnabled()) {
            log.info("Initializing Metrics Log reporting");
            Marker metricsMarker = MarkerFactory.getMarker("metrics");
            final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger("metrics"))
                .markWith(metricsMarker)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
            reporter.start(simLifeProperties.getMetrics().getLogs().getReportFrequency(), TimeUnit.SECONDS);
        }
    }
}
