package com.example.dashboard_api.specification;

import com.example.dashboard_api.entity.City;
import org.springframework.data.jpa.domain.Specification;
// İçinde, City varlıkları (entity) için filtreleme koşulları yazıyoruz.
public class CitySpecifications {
     // Eğer name parametresi null değilse veritabanında name sütunu name parametresine eşit olanları getir
    public static Specification<City> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.equal(root.get("name"), name); //Eğer kullanıcı name parametresi göndermezse (null ise) bu filtre uygulanmaz (null döner).
    // Bu yazım sayesinde kullanıcı bu filtreyi kullanmadıysa, bu kural query'ye eklenmez.
        // Spring JPA null dönen Specification'ları yok sayar. Böylece gereksiz sorgular çalışmaz.
    } //root.get("name") → City tablosundaki name sütununu temsil eder. cb.equal(...) → SQL'deki WHERE name = ? gibi bir şey.

    public static Specification<City> hasCountry(String country) {
        return (root, query, cb) ->
                country == null ? null : cb.equal(root.get("country"), country);
    }

    public static Specification<City> hasArea(String area) {
        return (root, query, cb) ->
                area == null ? null : cb.equal(root.get("area"), area);
    }

    public static Specification<City> hasZone(String zone) {
        return (root, query, cb) ->
                zone == null ? null : cb.equal(root.get("zone"), zone);
    }

    public static Specification<City> hasState(String state) {
        return (root, query, cb) ->
                state == null ? null : cb.equal(root.get("state"), state);
    }
}
