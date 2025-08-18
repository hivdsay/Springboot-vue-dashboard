<template>
  <Card>
    <!-- Kart başlığı -->
    <template #title>Live Exchange Rate Trend (Selected Range)</template>

    <!-- Kart içeriği -->
    <template #content>
      <div class="flex gap-3 mb-4 flex-wrap">
        <!-- Base currency dropdown -->
        <Dropdown
          v-model="base"
          :options="currencies"
          optionLabel="label"
          optionValue="value"
          placeholder="Select Base"
          class="w-40"
        />

        <!-- Target currency dropdown -->
        <Dropdown
          v-model="target"
          :options="currencies"
          optionLabel="label"
          optionValue="value"
          placeholder="Select Target"
          class="w-40"
        />

        <!-- Tarih aralığı seçimi -->
        <Calendar
          v-model="dateRange"
          selectionMode="range"
          dateFormat="yy-mm-dd"
          placeholder="Select Date Range"
          showIcon
          class="w-60"
        />

        <!-- Fetch butonu -->
        <Button label="Fetch" icon="pi pi-search" @click="fetchRates" />
      </div>

      <!-- Grafik bileşeni -->
      <Chart type="line" :data="chartData" />
    </template>
  </Card>
</template>

<script setup>
/* PrimeVue bileşenlerini ve Vue'nun reaktif sistemini import ediyoruz */
import { ref } from 'vue'
import Dropdown from 'primevue/dropdown'
import Button from 'primevue/button'
import Card from 'primevue/card'
import Chart from 'primevue/chart'
import Calendar from 'primevue/calendar'

/* Seçilen para birimleri ve tarih aralığı */
const base = ref('USD')         // Başlangıç para birimi
const target = ref('TRY')       // Hedef para birimi
const dateRange = ref([])       // Tarih aralığı (başlangıç - bitiş)

/* Grafik verisi */
const chartData = ref({
  labels: [],       // Tarih etiketleri
  datasets: []      // Oran verileri
})

/* Kullanıcıya sunulacak para birimi seçenekleri */
const currencies = [
  { label: 'US Dollar (USD)', value: 'USD' },
  { label: 'Euro (EUR)', value: 'EUR' },
  { label: 'Turkish Lira (TRY)', value: 'TRY' },
  { label: 'British Pound (GBP)', value: 'GBP' },
  { label: 'Japanese Yen (JPY)', value: 'JPY' },
  { label: 'Swiss Franc (CHF)', value: 'CHF' },
]

/* Tarih formatlama yardımcı fonksiyonu (ISO 8601 -> YYYY-MM-DD) */
const formatDate = (date) => {
  return date.toISOString().split('T')[0]
}

/* API'den döviz kuru verisi çeken fonksiyon */
const fetchRates = async () => {
  // Tarih aralığı seçilmemişse kullanıcıyı uyar
  if (!dateRange.value[0] || !dateRange.value[1]) {
    alert('Please select a date range')
    return
  }

  // Başlangıç ve bitiş tarihlerini uygun formata çevir
  const startDate = formatDate(dateRange.value[0])
  const endDate = formatDate(dateRange.value[1])

  try {
    // API çağrısı (Frankfurter API)
    const response = await fetch(
      `https://api.frankfurter.app/${startDate}..${endDate}?from=${base.value}&to=${target.value}`
    )
    const data = await response.json()

    // Eğer veri yoksa hata ver
    if (!data.rates) {
      console.error('No rates found', data)
      return
    }

    // Tarihleri sırala
    const dates = Object.keys(data.rates).sort()

    // Her bir tarihteki hedef para birimi kurunu al
    const rates = dates.map((date) => data.rates[date][target.value])

    // Grafik verisini ayarla
    chartData.value = {
      labels: dates,
      datasets: [
        {
          label: `${base.value} to ${target.value}`, // Grafik başlığı
          data: rates,                               // Kur verisi
          fill: false,
          borderColor: '#42A5F5',                    // Mavi çizgi rengi
          tension: 0.4                               // Eğrilik (smooth curve)
        },
      ],
    }
  } catch (error) {
    // Hata durumunda consola yaz
    console.error('Error fetching rates:', error)
  }
}
</script>

<!-- Stil kısmı (dropdowların genişliğini ayarlıyoruz) -->
<style scoped>
.w-40 {
  width: 10rem;
}
.w-60 {
  width: 15rem;
}
</style>

