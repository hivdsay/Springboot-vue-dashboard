<script setup>
import { ref, onMounted } from 'vue'
import { getTotalPortCount, getTopPortCity } from '@/service/port'
import { getTotalCityCount } from '@/service/city'
import { getTotalExchangeRateCount } from '@/service/exchangeRate'

const cityCount = ref(0)
const portCount = ref(0)
const rateCount = ref(0)
const mostPortCity = ref('')

onMounted(async () => {
  try {
    cityCount.value = await getTotalCityCount()
    portCount.value = await getTotalPortCount()
    rateCount.value = await getTotalExchangeRateCount()
    mostPortCity.value = await getTopPortCity()
  } catch (error) {
    console.error('Failed to load dashboard stats:', error)
  }
})
</script>

<template>
  <div class="grid grid-cols-12 gap-4 mb-4">
    <!-- City -->
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Total Cities</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ cityCount }}</div>
          </div>
          <div class="flex items-center justify-center bg-blue-100 dark:bg-blue-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-map-marker text-blue-500 !text-xl"></i>
          </div>
        </div>
        <span class="text-primary font-medium">All Cities</span>
      </div>
    </div>

    <!-- Port -->
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Total Ports</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ portCount }}</div>
          </div>
          <div class="flex items-center justify-center bg-orange-100 dark:bg-orange-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-compass text-orange-500 !text-xl"></i>
          </div>
        </div>
        <span class="text-primary font-medium">All Ports</span>
      </div>
    </div>

    <!-- Exchange Rate -->
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Total Exchange Rates</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ rateCount }}</div>
          </div>
          <div class="flex items-center justify-center bg-cyan-100 dark:bg-cyan-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-money-bill text-cyan-500 !text-xl"></i>
          </div>
        </div>
        <span class="text-primary font-medium">All Exchange Records</span>
      </div>
    </div>

    <!-- Most Port City -->
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Top Port City</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ mostPortCity }}</div>
          </div>
          <div class="flex items-center justify-center bg-purple-100 dark:bg-purple-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-star text-purple-500 !text-xl"></i>
          </div>
        </div>
        <span class="text-primary font-medium">City with Most Ports</span>
      </div>
    </div>
  </div>
</template>




