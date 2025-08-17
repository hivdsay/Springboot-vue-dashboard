package com.example.dashboard_api.mapper;

import com.example.dashboard_api.DTO.PortCreateRequest;
import com.example.dashboard_api.DTO.PortResponse;
import com.example.dashboard_api.DTO.PortUpdateRequest;
import com.example.dashboard_api.entity.City;
import com.example.dashboard_api.entity.Port;

/**
 * Port entity’si ile DTO’lar (Data Transfer Object) arasında dönüşüm işlemlerini yönetir.
 * Entity katmanını dış dünyaya açmak yerine DTO kullanımı bu sınıf ile kolaylaştırılır.
 */
public class PortMapper {

    /**
     * CREATE: PortCreateRequest → Port Entity
     *
     * Kullanıcıdan gelen port oluşturma isteğini, veritabanına kaydedilebilecek bir entity'ye dönüştürür.
     * Not: `city` burada henüz ayarlanmaz. Bu işlem service katmanında yapılmalıdır.
     *
     * @param dto Kullanıcıdan gelen port oluşturma verisi
     * @return Boş City atanmış yeni Port nesnesi
     */
    public static Port toEntity(PortCreateRequest dto) {
        Port port = new Port();
        port.setCode(dto.getCode());
        port.setName(dto.getName());
        // city burada set edilmez — service içinde veritabanından çekilerek set edilecek
        return port;
    }

    /**
     * UPDATE: Var olan Port entity’yi günceller
     *
     * Gelen DTO’daki bilgilerle var olan port nesnesinin güncellenmesini sağlar.
     * Not: code (primary key) güncellenmez.
     *
     * @param dto Güncelleme verilerini içeren DTO
     * @param port Güncellenecek mevcut Port entity
     */
    public static void updateEntity(PortUpdateRequest dto, Port port) {
        port.setName(dto.getName());
        // city serviste çekilip burada ayrı olarak set edilir
    }

    /**
     * Port Entity → PortResponse DTO
     *
     * Port verisini dış dünyaya (frontend vs.) taşımak için DTO'ya çevirir.
     * Şehre ait ID ve adı da kullanıcıya dönülür.
     *
     * @param port Veritabanından gelen Port nesnesi
     * @return PortResponse DTO
     */
    public static PortResponse toDto(Port port) {
        PortResponse dto = new PortResponse();
        dto.setCode(port.getCode());
        dto.setName(port.getName());

        City city = port.getCity();
        if (city != null) {
            dto.setCityId(city.getCity());     // city tablosundaki PK (örneğin "TRIST")
            dto.setCityName(city.getName());   // şehir adı (örneğin "İstanbul")
        }

        return dto;
    }
}
