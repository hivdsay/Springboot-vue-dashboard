<template>
  <div class="card h-full p-4 shadow-md">
    <!-- Kart başlığı ve yenileme butonu -->
    <div class="flex justify-between items-center mb-4">
      <div class="flex items-center gap-2">
        <i class="pi pi-map-marker text-green-500 text-xl"></i>
        <!-- Kullanıcıya görünen başlık  -->
        <span class="font-semibold text-xl">Ports in London</span>
      </div>
      <!-- Yenileme butonu: veriyi tekrar yükler -->
      <Button icon="pi pi-sync" class="p-button-text" @click="loadPorts" />
    </div>

    <!-- Liman listesini gösteren DataTable -->
    <DataTable
      :value="ports"
      :loading="loading"
      paginator
      :rows="6"
      class="p-datatable-sm p-datatable-gridlines p-datatable-striped"
      :rowHover="true"
      responsiveLayout="scroll"
    >
      <!-- Liman adı sütunu -->
      <Column
        field="name"
        header="Port Name"
        style="width: 70%;"
        sortable
      />
      <!-- Liman kodu sütunu -->
      <Column
        field="code"
        header="Port Code"
        style="width: 30%;"
        sortable
      />
    </DataTable>

    <!-- Boş liste durumu için mesaj -->
    <div
      v-if="!loading && ports.length === 0"
      class="flex justify-center items-center h-48 text-500 italic"
    >
      No ports found for London.
    </div>
  </div>
</template>

<script setup>
// Vue Composition API’dan fonksiyonlar
import { ref, onMounted } from 'vue'
// PrimeVue bileşenleri
import DataTable from 'primevue/datatable'
import Column    from 'primevue/column'
import Button    from 'primevue/button'
// API servis fonksiyonları
import { getAllCities } from '@/service/city'
import { getAllPorts }  from '@/service/port'

// Reaktif değişkenler
const ports   = ref([])    // filtrelenmiş liman listesi
const loading = ref(false) // yükleme durumu

/**
 * Liman verilerini yükler:
 * 1) Şehirleri alır
 * 2) İsmi 'London' olanı bulur
 * 3) Tüm limanları alır
 * 4) cityId eşleşenleri filtreler
 */
async function loadPorts() {
  loading.value = true
  try {
    const cities = await getAllCities()
    const london = cities.find(c => c.name === 'London')
    if (!london) {
      ports.value = []
      return
    }
    const allPorts = await getAllPorts()
    ports.value = allPorts.filter(p => p.cityId === london.city)
  } finally {
    loading.value = false
  }
}

// Bileşen yüklendiğinde veri yükle
onMounted(loadPorts)
</script>

<style scoped>
/* Kart stil: beyaz zemin, yuvarlak köşe */
.card {
  @apply bg-white rounded-lg;
}

/* Tablo başlık hücrelerinin altında ince çizgi */
:deep(.p-datatable-thead > tr > th) {
  border-bottom-width: 1px !important;
  border-color: rgba(0, 0, 0, 0.08) !important;
}

/* Paginator butonlarının padding’ini biraz azalt */
:deep(.p-paginator .p-paginator-page) {
  padding: 0.25rem 0.5rem !important;
}
</style>
