// authService içindeki login ve register fonksiyonlarını içeri aktar
import * as authService from '@/service/auth'

// Vue'dan reaktif değişken tanımlamak için ref fonksiyonu alınır
import { ref } from 'vue'

// Vue Router fonksiyonlarıyla sayfa yönlendirme için useRouter hook'u alınır
import { useRouter } from 'vue-router'

// Bu composable fonksiyon, login ve register işlemlerini içerir
export function useAuth() {
  // Yüklenme durumu (buton disable, spinner vs. için kullanılır)
  const isLoading = ref(false)

  // Hata mesajlarını tutmak için reaktif değişken
  const error = ref(null)

  // Yönlendirme yapmak için router nesnesi alınır
  const router = useRouter()

  // Kullanıcının giriş yapmasını sağlayan fonksiyon
  async function login(email, password) {
    isLoading.value = true         // işlem başladı, yükleniyor
    error.value = null             // hata varsa sıfırlanır

    try {
      // authService.login fonksiyonu çağrılır ve JWT token döner
      const token = await authService.login({ email, password })

      // Token localStorage'a kaydedilir (diğer isteklerde kullanılmak üzere)
      localStorage.setItem('auth_token', token)

      // Giriş başarılıysa dashboard sayfasına yönlendirilir
      router.push({ name: 'dashboard' })
    } catch (e) {
      // Eğer hata olursa mesaj alınır, yoksa varsayılan hata verilir
      error.value = e.response?.data?.message || 'Giriş başarısız'
    } finally {
      // İşlem bittiğinde yüklenme durumu kapatılır
      isLoading.value = false
    }
  }

  // Kullanıcının kayıt olmasını sağlayan fonksiyon
  async function register(email, password) {
    isLoading.value = true         // işlem başladı
    error.value = null             // hata sıfırlanır

    try {
      // authService.register fonksiyonu çağrılır
      await authService.register({ email, password })

      // Kayıt başarılı olursa login sayfasına yönlendirilir
      router.push({ name: 'login' })
    } catch (e) {
      // Hata varsa mesaj alınır, yoksa varsayılan hata gösterilir
      error.value = e.response?.data?.message || 'Kayıt başarısız'
    } finally {
      // İşlem bitince loading kapatılır
      isLoading.value = false
    }
  }

  // Bu fonksiyon dışa login, register fonksiyonlarını ve reaktif değişkenleri döner
  return {
    login,
    register,
    isLoading,
    error
  }
}



