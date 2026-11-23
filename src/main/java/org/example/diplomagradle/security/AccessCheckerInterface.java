package org.example.diplomagradle.security;

import org.springframework.security.core.Authentication;

public interface AccessCheckerInterface {
    public boolean canEditByMetricId(Authentication auth, Long metricId);
    public boolean canEditByMetricValueId(Authentication auth, Long metricValueId);
    public boolean canViewBlock (Authentication auth, Long blockId);
    public boolean canViewMetric(Authentication auth, Long metricId);
}
