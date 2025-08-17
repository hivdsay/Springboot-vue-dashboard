package com.example.dashboard_api.mapper;

import com.example.dashboard_api.DTO.CityCreateRequest;
import com.example.dashboard_api.DTO.CityResponse;
import com.example.dashboard_api.DTO.CityUpdateRequest;
import com.example.dashboard_api.entity.City;

public class CityMapper {
    //DTO-Entity dönüşümleri bu sınıfta yapıyoruz.

    public static City toEntity(CityCreateRequest dto){
    //kullanıcıdan gelen CityCreateRequest objesini al, yeni bir City objesi üret.
        City city = new City(); // Yeni bir boş bir City nesnesi oluşturduk.

      //DTO dan gelen verileri, City nesnesine set ediyoruz. Bu veri POST body ile gelir.
        city.setCity(dto.getCity());
        city.setName(dto.getName());
        city.setCountry(dto.getCountry());
        city.setArea(dto.getArea());
        city.setZone(dto.getZone());
        city.setState(dto.getState());
         return city; // Hazırladığımız City objesini geri döndürüyoruz. City DB'ye kaydedilmek üzere save içine gider.

    }
    // DB'den gelen mevcut City nesnesi üzerinden gelen verilerle güncelleme yapmak.
    // Bu method void döner çünkü dışarı bir şey göndermez. Var olan city objesini yerinde günceller.
    public static void updateEntity(CityUpdateRequest dto, City city){
    //City(id) sabit kaldığı için onu değiştiremiyoruz.
        city.setName(dto.getName());
        city.setCountry(dto.getCountry());
        city.setArea(dto.getArea());
        city.setZone(dto.getZone());
        city.setState(dto.getState());

    }

    //Veritabanındaki City nesnesini kullanıcıya göstermek için CityResponse nesnesine çevirmek.
    public static CityResponse toDto(City city){
        CityResponse dto = new CityResponse(); // Yeni bir DTO nesnesi oluşturuyoruz.

        dto.setCity(city.getCity());
        dto.setName(city.getName());
        dto.setCountry(city.getCountry());
        dto.setArea(city.getArea());
        dto.setZone(city.getZone());
        dto.setState(city.getState());

        return dto; //Bu nesne genellikle contoller katmanında return Responseentity.ok(dto) gibi döndürülür.
    }
}
