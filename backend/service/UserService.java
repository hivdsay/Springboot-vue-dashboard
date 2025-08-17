package com.example.dashboard_api.service;

import com.example.dashboard_api.entity.User;
import com.example.dashboard_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 Bu sınıf, UserDetailsService arayüzünü implement eder.
 Yani Spring Security bu sınıfa der ki:
 “Ben sana bir username (bizde email) vereceğim, sen bana bu kullanıcıyı getir.”
 */
@Service
public class UserService implements UserDetailsService { //Spring Security bu sınıfı kullanıcıları bulmak için kullanır.
    //Bu interface'in tek metodu var: loadUserByUsername
    //Spring Security login işlemi sırasında bu sınıfı çağırır.
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * E-posta adresine göre kullanıcıyı veritabanından getirir.
     *
     * @param username genelde email
     * @return UserDetails nesnesi
     * @throws UsernameNotFoundException eğer kullanıcı bulunamazsa
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Spring Security bir kullanıcı login olmak istediğinde buraya gelir.
        //Gelen username → bizim sistemimizde email'dir.
        //Bu email’e göre kullanıcıyı veritabanında arar.
        //Eğer bulursa User entity'sini döner (çünkü o zaten UserDetails'i implement etmişti).
        //Eğer kullanıcı bulunamazsa UsernameNotFoundException fırlatır → login başarısız olur.
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
    }
}

/**
 * JwtAuthenticationFilter → Token’daki email’i kullanarak bu servisle kullanıcıyı çeker.
 *
 * AuthenticationManager → Login işlemlerinde bu servisi otomatik olarak çağırır.
 *
 * User entity'si, UserDetails interface’ini implement ettiği için doğrudan döner.
 *  Neden Bu Sınıf Önemli?
 * Spring Security’nin login/doğrulama mekanizması buraya bağlıdır.
 *
 * Hem authenticate() (login), hem de JWT token kontrolü sırasında bu servis kullanılır.
 *
 * Her zaman email ile kullanıcı getirilmesini sağlar
 */
