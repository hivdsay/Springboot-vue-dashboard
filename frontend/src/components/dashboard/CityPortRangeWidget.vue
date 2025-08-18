<script setup>
// … diğer import’lar…
import { useLayout } from '@/layout/composables/layout'
import { onMounted, ref, watch } from 'vue'
import Chart from 'primevue/chart'
import { getCityPortCountByRange } from '@/service/port'

const { getPrimary, getSurface, isDarkTheme } = useLayout()
const chartData = ref(null)
const chartOptions = ref(null)

function setChartData(rawData) {
  const documentStyle = getComputedStyle(document.documentElement)
  // 1'den 10'a etiketler
  const orderedLabels = Array.from({ length: 10 }, (_, i) => String(i + 1))

  return {
    labels: orderedLabels,
    datasets: [
      {
        type: 'bar',
        label: 'Şehir Sayısı',
        backgroundColor: documentStyle.getPropertyValue('--p-primary-400'),
        data: orderedLabels.map(label => {
          // API’dan gelen alana göre eşle
          const item = rawData.find(d => d.rangeLabel === label)
          // gelen obje: { rangeLabel: "1", cityCount: 330 }
          return item ? item.cityCount : 0
        }),
        borderRadius: { topLeft: 8, topRight: 8 },
        borderSkipped: false,
        barThickness: 24
      }
    ]
  }
}

function setChartOptions(rawData) {
  const documentStyle  = getComputedStyle(document.documentElement)
  const borderColor     = documentStyle.getPropertyValue('--surface-border')
  const textMutedColor  = documentStyle.getPropertyValue('--text-color-secondary')

  // API’dan dönen benzersiz cityCount değerlerini al ve sıralı hale getir
  const counts = Array.from(new Set(rawData.map(d => d.cityCount)))
                      .sort((a, b) => a - b)

  return {
    maintainAspectRatio: false,
    aspectRatio: 1.2,
    scales: {
      x: {
        ticks: { color: textMutedColor },
        grid: { display: false }
      },
      y: {
        // Eksen sınırlarını en küçük ve en büyük cityCount’a sabitle
        min: counts[0] || 0,
        max: counts[counts.length - 1] || 0,
        ticks: {
          color: textMutedColor,
          autoSkip: false,       // hiçbir tick’i atlama
          // Sadece counts içindeki değerlere etiket yaz, diğerlerini boş bırak
          callback: value => counts.includes(value) ? value : ''
        },
        grid: {
          color: borderColor,
          drawTicks: false
        }
      }
    }
  }
}



const loadChart = async () => {
  const data = await getCityPortCountByRange()
  console.log('PORT RANGE RAW DATA:', data)  // Konsolda kontrol edebilirsiniz
  chartData.value    = setChartData(data)
  chartOptions.value = setChartOptions(data)
}

watch([getPrimary, getSurface, isDarkTheme], loadChart)
onMounted(loadChart)
</script>

<template>
  <div class="card h-full">
    <div class="font-semibold text-xl mb-4">
      City Distribution According to Number of Ports
    </div>
    <Chart
      type="bar"
      :data="chartData"
      :options="chartOptions"
      class="h-80"
    />
  </div>
</template>

