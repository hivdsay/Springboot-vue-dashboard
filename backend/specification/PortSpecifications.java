package com.example.dashboard_api.specification;

import com.example.dashboard_api.entity.Port;
import org.springframework.data.jpa.domain.Specification;

/**
 * Port entity'si için dinamik filtreleme kriterleri oluşturur.
 * Her metot, belirli bir alan için null kontrolü yaparak uygun filtreyi döner.
 */
public class PortSpecifications {

    /**
     * name alanı için filtre.
     *
     * @param name filtrelenecek değer (opsiyonel)
     * @return Specification nesnesi veya null
     */
    public static Specification<Port> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.equal(root.get("name"), name);
    }

    /**
     * city alanı için filtre.
     *
     * @param city filtrelenecek değer (opsiyonel)
     * @return Specification nesnesi veya null
     */
    public static Specification<Port> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.equal(root.get("city"), city);
    }
}
