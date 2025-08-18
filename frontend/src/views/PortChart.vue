<template>
  <div class="card">
    <h5>City - Port Distribution</h5>
    <Dropdown
      v-model="selectedCity"
      :options="cities"
      optionLabel="label"
      optionValue="value"
      placeholder="Select a City"
      class="mb-4 w-full md:w-14rem"
    />
    <Chart type="pie" :data="chartData" :options="chartOptions" style="max-width: 600px; max-height: 400px; margin: auto" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import Chart from 'primevue/chart'
import Dropdown from 'primevue/dropdown'
import { getAllPorts } from '@/service/port'

const chartData = ref({})
const chartOptions = ref({})
const ports = ref([])
const cities = ref([])
const selectedCity = ref(null)

const prepareChartData = (cityName) => {
  const filtered = ports.value.filter((port) =>
    port.cityName?.toLowerCase() === cityName.toLowerCase()
  )

  chartData.value = {
    labels: filtered.map((port) => port.name),
    datasets: [
      {
        label: `Ports in ${cityName}`,
        backgroundColor: generateColors(filtered.length),
        data: filtered.map(() => 1)
      }
    ]
  }
}

// Pie chart için rastgele renk üretici (görsellik için)
const generateColors = (count) => {
  const colors = []
  for (let i = 0; i < count; i++) {
    const r = Math.floor(Math.random() * 200) + 30
    const g = Math.floor(Math.random() * 200) + 30
    const b = Math.floor(Math.random() * 200) + 30
    colors.push(`rgb(${r}, ${g}, ${b})`)
  }
  return colors
}

onMounted(async () => {
  ports.value = await getAllPorts()
  console.log('Port verisi:', ports.value)

  const uniqueCities = Array.from(
    new Set(ports.value.map((port) => port.cityName))
  ).filter(Boolean)

  cities.value = uniqueCities.map((city) => ({
    label: city,
    value: city
  }))
})

watch(selectedCity, (newCity) => {
  if (newCity) prepareChartData(newCity)
})

chartOptions.value = {
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom'
    }
  }
}
</script>

<style scoped>
.card {
  padding: 2rem;
}
</style>


