package org.example.diplomagradle.security;

import jakarta.persistence.EntityNotFoundException;
import org.example.diplomagradle.datamodel.repository.*;
import org.example.diplomagradle.service.block.InvalidIdBlockException;
import org.example.diplomagradle.service.metric.InvalidIdMetricException;
import org.example.diplomagradle.service.metricValue.InvalidIdMetricValueException;
import org.example.diplomagradle.service.user.detail.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

//компонент для проверки доступа
public class AccessChecker implements AccessCheckerInterface{
    private final MetricAccessRepository metricAccessRepository;

    private final BlockViewRepository blockViewRepository;

    private final MetricValueRepository metricValueRepository;

    private final MetricRepository metricRepository;

    private final BlockRepository blockRepository;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public AccessChecker(MetricAccessRepository metricAccessRepository,
                         BlockViewRepository blockViewRepository,
                         MetricValueRepository metricValueRepository,
                         MetricRepository metricRepository,
                         BlockRepository blockRepository) {
        this.metricAccessRepository = metricAccessRepository;
        this.blockViewRepository = blockViewRepository;
        this.metricValueRepository = metricValueRepository;
        this.metricRepository = metricRepository;
        this.blockRepository = blockRepository;
    }

    //проверка прав доступа к показателям
    public boolean canEditByMetricId(Authentication auth, Long metricId) {
        if (auth == null || metricId == null) {
            return false;
        }

        if (!metricRepository.existsById(metricId)) {
            throw new InvalidIdMetricException(metricId);
        }

        if (isAdmin(auth)) {
            return true;
        }

        if (auth.getPrincipal() instanceof AppUserDetails details) {
            Long roleId = details.getUser().getRole().getId();
            if (roleId == null) {
                return false;
            }

            return metricAccessRepository.existsByRole_IdAndMetric_Id(roleId, metricId);
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
                    .orElseThrow(() -> new InvalidIdMetricValueException(metricValueId));
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

            if (!blockRepository.existsById(blockId)) {
                throw new InvalidIdBlockException(blockId);
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
                    .orElseThrow(() -> new InvalidIdMetricException(metricId));
        }
        return false;
    }


}
