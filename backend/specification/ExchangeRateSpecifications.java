package com.example.dashboard_api.specification;

import com.example.dashboard_api.entity.ExchangeRate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * ExchangeRate filtreleme kriterlerini barındıran JPA Specification sınıfı.
 */
public class ExchangeRateSpecifications {

    public static Specification<ExchangeRate> hasDate(LocalDate date) {
        return (root, query, cb) -> date == null
                ? cb.conjunction()
                : cb.equal(root.get("exchangeDate"), date);
    }

    public static Specification<ExchangeRate> hasFromCurr(String fromCurr) {
        return (root, query, cb) -> fromCurr == null
                ? cb.conjunction()
                : cb.equal(cb.lower(root.get("fromCurr")), fromCurr.toLowerCase());
    }

    public static Specification<ExchangeRate> hasToCurr(String toCurr) {
        return (root, query, cb) -> toCurr == null
                ? cb.conjunction()
                : cb.equal(cb.lower(root.get("toCurr")), toCurr.toLowerCase());
    }

    public static Specification<ExchangeRate> hasRateType(String rateType) {
        return (root, query, cb) -> rateType == null
                ? cb.conjunction()
                : cb.equal(cb.lower(root.get("rateType")), rateType.toLowerCase());
    }
}
