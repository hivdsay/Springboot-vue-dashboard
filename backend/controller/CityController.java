package com.example.dashboard_api.controller;


import com.example.dashboard_api.DTO.CityCreateRequest;
import com.example.dashboard_api.DTO.CityResponse;
import com.example.dashboard_api.DTO.CityUpdateRequest;
import com.example.dashboard_api.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Spring gelen http isteklerini buraya yönlendirir.
@RequestMapping("api/cities") // Bu sınıftaki tüm endpointler bu adres ile başlar.
public class CityController {

    private final CityService cityService; //Servis katmanını çağırmak için kullanılır
    //Spring CityService nesnesini contructor üzerinden bağımlılık olarak enjekte eder.
    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestBody CityCreateRequest request){
        //Gelen JSON verisini CityCreateRequest nesnesine dönüştürür. Geriye JSON veri döner içinde HTTP statü kodu da vardır.
        CityResponse response = cityService.createCity(request);
        //request nesnesi servise gönderilir, orada DTO-Entity dönüşümü ve DB kaydı yapılır.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);//Gövde kısmına şehir bilgisi eklenir
    }
    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities(){ //şehir listesi döner
        List<CityResponse> cities = cityService.getAllCities(); //Servis katmanı çağırılır, şehir listesi alınır.
        return ResponseEntity.ok(cities); //200 OK ile şehir listesi döndürülür

    }
    @GetMapping("/{cityId}") //Şehir bu id ile veritabanından getirilir.
    public ResponseEntity<CityResponse> getCityById(@PathVariable String cityId){ //URL'deki değeri alır cityId değişkenine atar.
        CityResponse city = cityService.getCityById(cityId); //Veritabanından o ID'ye ait şehiri getirir
        return ResponseEntity.ok(city);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<CityResponse>> filterCities( //URL sonu filter olan get isteğini karşılar
        @RequestParam(required = false) String name, //Kullanıcı isterse bu parametleri verebilir.
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String area,
        @RequestParam(required = false) String zone,
        @RequestParam(required = false) String state
     )   {
        //Servis parametleri gönderir, filtre uygular.
        List<CityResponse> filtered = cityService.filterCities(name, country, area, zone, state );
        return ResponseEntity.ok(filtered);
    }
    @PutMapping("/{cityId}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable String cityId, //Hangi şehir güncellenecek onu URL'den alır
            @RequestBody CityUpdateRequest request){ //Yeni verileri JSON olarak alır ve DTO'ya çevirir
        CityResponse updated = cityService.updateCity(cityId, request); //Güncellenmiş şehir DB'ye kaydedilir.
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable String cityId){ //Silinecek şehir ID'si URL'den alınır.
        cityService.deleteCity(cityId);
        return ResponseEntity.noContent().build(); //Silme başarılı ama dönecek veri yok anlamına gelir.
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCityCount() {
        long count = cityService.getCityCount(); // Servisten toplam şehir sayısını al
        return ResponseEntity.ok(count); // Sayıyı döndür
    }






}
