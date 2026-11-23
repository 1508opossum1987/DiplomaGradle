package org.example.diplomagradle.security;

import org.example.diplomagradle.datamodel.repository.BlockViewRepository;
import org.example.diplomagradle.datamodel.repository.MetricAccessRepository;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.service.user.detail.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

//компонент для проверки доступа
public class AccessChecker implements AccessCheckerInterface{
    private final MetricAccessRepository metricAccessRepository;

    private final BlockViewRepository blockViewRepository;

    private final MetricValueRepository metricValueRepository;

    private final MetricRepository metricRepository;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public AccessChecker(MetricAccessRepository metricAccessRepository,
                         BlockViewRepository blockViewRepository,
                         MetricValueRepository metricValueRepository,
                         MetricRepository metricRepository) {
        this.metricAccessRepository = metricAccessRepository;
        this.blockViewRepository = blockViewRepository;
        this.metricValueRepository = metricValueRepository;
        this.metricRepository = metricRepository;
    }

    //проверка прав доступа к показателям
    public boolean canEditByMetricId(Authentication auth, Long metricId) {
        if (auth == null || metricId == null) {
            return false;
        }

        if (isAdmin(auth)) {
            return true;
        }

        if (auth.getPrincipal() instanceof AppUserDetails details) {
            Long roleId = details.getUser().getRole().getId();
            return roleId != null
                    && metricAccessRepository.existsByRole_IdAndMetric_Id(roleId, metricId);
        }

        return false;
    }

    //вспомогательная проверка на админа
    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

    //проверка возможности редактирования показателей
    public boolean canEditByMetricValueId(Authentication auth, Long metricValueId) {
        if (auth == null || metricValueId == null) {
            return false;
        }

        if (isAdmin(auth)) {
            return true;
        }

        if (auth.getPrincipal() instanceof AppUserDetails details) {
            Long roleId = details.getUser().getRole().getId();
            if (roleId == null) {
                return false;
            }
            return metricValueRepository.findById(metricValueId)
                    .map(mv -> metricAccessRepository
                    .existsByRole_IdAndMetric_Id(roleId, mv.getMetric().getId()))
                    .orElse(false);
        }
        return false;
    }

    //проверка прав просмотра блоков
    public boolean canViewBlock (Authentication auth, Long blockId){
        if (auth == null || blockId == null) {
            return false;
        }

        if (isAdmin(auth)) {
            return true;
        }

        if (auth.getPrincipal() instanceof AppUserDetails details) {
            Long roleId = details.getUser().getRole().getId();

            if (roleId == null) {
                return false;
            }

            return blockViewRepository.existsByRole_IdAndBlock_Id(roleId, blockId);
        }
        return false;
    }

    //проверка прав просмотра показателей
    public boolean canViewMetric(Authentication auth, Long metricId) {
        if (auth == null || metricId == null) {
            return false;
        }
        if (isAdmin(auth)) {
            return true;
        }
        if (auth.getPrincipal() instanceof AppUserDetails details) {
            Long roleId = details.getUser().getRole().getId();
            if (roleId == null) {
                return false;
            }
            return metricRepository.findById(metricId)
                    .map(metric -> blockViewRepository
                            .existsByRole_IdAndBlock_Id(roleId, metric.getBlock().getId()))
                    .orElse(false);
        }
        return false;
    }


}
