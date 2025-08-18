<script setup>
import { ref, onMounted, watch } from 'vue'

const base = ref('USD')
const target = ref('EUR')
const lastFiveRates = ref([])

const today = new Date()
const pastDate = new Date()
pastDate.setDate(today.getDate() - 6)

const formatDate = (date) => date.toISOString().split('T')[0]
const startDate = formatDate(pastDate)
const endDate = formatDate(today)

// Para biriminden ülke koduna eşleyen fonksiyon (bayrak için)
const getFlagClass = (currency) => {
  const map = {
    USD: 'us',
    EUR: 'de',
    TRY: 'tr',
    GBP: 'gb',
    CAD: 'ca',
    AUD: 'au',
    JPY: 'jp',
    CHF: 'ch'

  };
  return map[currency] ? `flag flag-${map[currency]}` : '';
}

const fetchRates = async () => {
  try {
    const response = await fetch(
      `https://api.frankfurter.app/${startDate}..${endDate}?from=${base.value}&to=${target.value}`
    )
    const data = await response.json()

    if (!data.rates) {
      console.error('No rates found', data)
      return
    }

    const sortedDates = Object.keys(data.rates).sort().reverse()
    lastFiveRates.value = sortedDates.slice(0, 5).map((date) => ({
      date,
      rate: data.rates[date][target.value],
      from: base.value,
      to: target.value,
      rateType: 'Market'
    }))
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

onMounted(fetchRates)
watch([base, target], fetchRates)
</script>

<template>
  <div class="card">
    <div class="text-xl font-semibold mb-4 text-primary">Latest 5 Exchange Rates</div>

    <!-- Para birimi seçimi -->
    <div class="flex gap-4 items-center mb-3">
      <select v-model="base" class="p-2 border rounded-md">
        <option value="USD">USD</option>
        <option value="TRY">TRY</option>
        <option value="EUR">EUR</option>
        <option value="GBP">GBP</option>
        <option value="JPY">JPY</option>
      </select>
      <span>→</span>
      <select v-model="target" class="p-2 border rounded-md">
        <option value="USD">USD</option>
        <option value="TRY">TRY</option>
        <option value="EUR">EUR</option>
        <option value="GBP">GBP</option>
        <option value="JPY">JPY</option>
      </select>
    </div>

    <!-- DataTable -->
    <DataTable
      :value="lastFiveRates"
      stripedRows
      class="shadow-md rounded-xl overflow-hidden"
      tableStyle="min-width: 100%; background-color: white"
    >
      <Column header="From">
        <template #body="{ data }">
          <div class="flex items-center gap-2">
            <span
              :class="getFlagClass(data.from)"
              class="inline-block w-[20px] h-[14px] bg-cover bg-center"
            />
            <span>{{ data.from }}</span>
          </div>
        </template>
      </Column>

      <Column header="To">
        <template #body="{ data }">
          <div class="flex items-center gap-2">
            <span
              :class="getFlagClass(data.to)"
              class="inline-block w-[20px] h-[14px] bg-cover bg-center"
            />
            <span>{{ data.to }}</span>
          </div>
        </template>
      </Column>

      <Column field="date" header="Date">
        <template #body="{ data }">
          <span class="whitespace-nowrap">{{ data.date }}</span>
        </template>
      </Column>

      <Column field="rate" header="Rate" />
      <Column field="rateType" header="Rate Type" />
    </DataTable>

    <div v-if="lastFiveRates.length === 0" class="text-gray-400 mt-4">
      Loading data...
    </div>
  </div>
</template>



